# Implementation Plan

- [x] 1. Write bug condition exploration test
  - **Property 1: Bug Condition** - Variable Scoping and Build Integrity Failures
  - **CRITICAL**: This test MUST FAIL on unfixed code — failure confirms the bugs exist
  - **DO NOT attempt to fix the test or the code when it fails**
  - **NOTE**: This test encodes the expected behavior — it will validate the fix when it passes after implementation
  - **GOAL**: Surface counterexamples that demonstrate each of the six bug conditions
  - **Scoped PBT Approach**: Scope each sub-case to the concrete failing scenario (e.g. cross-phase variable loss with `CURRENT_VERSION`, subshell loss with `LAMBDA_ZIP`, empty-path propagation, silent CLI failure continuation, echo-based empty AppSpec, and unquoted version type)
  - Create `scripts/test-bug-condition.sh` that:
    - Sub-case 1 (Cross-phase loss): Set `CURRENT_VERSION` via `export` in a subshell, then read it in a second independent subshell; assert the second subshell sees an empty value
    - Sub-case 2 (Subshell list-item loss): Run `export LAMBDA_ZIP=$(echo 'some.zip')` as one shell invocation and read `$LAMBDA_ZIP` in a second invocation; assert the second sees an empty value
    - Sub-case 3 (Empty-path propagation): Run the unfixed `aws lambda update-function-code` line with `LAMBDA_ZIP=""` using a stubbed `aws` that records its arguments; assert the stub was called with `fileb://` (empty path) and the script did NOT exit
    - Sub-case 4 (Silent CLI failure): Stub `aws` to exit 1; run the unfixed post_build command sequence; assert subsequent commands still execute (build does not halt)
    - Sub-case 5 (Malformed AppSpec): Run the echo-based AppSpec generation with `CURRENT_VERSION=""` and `TARGET_VERSION=""`; assert `CurrentVersion:` and `TargetVersion:` lines contain empty values
    - Sub-case 6 (Unquoted version type): Run the echo-based generation with `CURRENT_VERSION=5`; parse the output with `python3 -c "import yaml, sys; d=yaml.safe_load(sys.stdin); ..."` and assert `CurrentVersion` is an integer type, not a string
  - Run test against the **current (unfixed)** `buildspec.yml` logic
  - **EXPECTED OUTCOME**: Every sub-case demonstrates the defect (variables empty, build continues after failure, AppSpec invalid) — this confirms the bugs exist
  - Document each counterexample found (e.g. "`CURRENT_VERSION` is empty in post_build", "build continues after `aws` exit 1")
  - Mark task complete when test is written, run, and all six failures are documented
  - _Requirements: 1.1, 1.2, 1.3, 1.4, 1.5, 1.6_

- [x] 2. Write preservation property tests (BEFORE implementing fix)
  - **Property 2: Preservation** - Existing Build Behaviors Unchanged
  - **IMPORTANT**: Follow observation-first methodology — observe the unfixed code's behavior on non-buggy inputs first, then encode those observations as properties
  - Create `scripts/test-preservation.sh` that exercises the following using a fully stubbed `aws` CLI and a mock `mvnw` script:
    - Observe: `./mvnw test` is the first meaningful command invoked in `pre_build`; a non-zero exit from it halts the build before packaging
    - Observe: `./mvnw clean package -DskipTests` is invoked in `build` and produces a ZIP matching `*-lambda-package.zip` under `target/`
    - Observe: After a successful run the `deployment/` directory contains both the Lambda ZIP and `appspec.yml`
    - Observe: The `publish-version` description contains the value of `CODEBUILD_BUILD_NUMBER`
    - Observe: Every `aws lambda` call uses `$FUNCTION_NAME` and every alias-related call uses `$ALIAS_NAME`
  - Write property-based tests (using `bash` with a small random-input loop or a Python `hypothesis`-style harness) that assert:
    - For any valid `FUNCTION_NAME` (alphanumeric, hyphens, underscores) and any `CODEBUILD_BUILD_NUMBER` (1–9999), the above five observations hold
  - Run tests against the **current (unfixed)** `buildspec.yml` logic (these inputs do not trigger the bug conditions)
  - **EXPECTED OUTCOME**: All preservation tests PASS on unfixed code (confirms baseline behavior to protect)
  - Mark task complete when tests are written, run, and passing on unfixed code
  - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.5, 3.6_

- [ ] 3. Fix buildspec.yml

  - [x] 3.1 Remove CURRENT_VERSION fetch from pre_build
    - Delete the three lines from `pre_build` that call `aws lambda get-alias`, export `CURRENT_VERSION`, and echo the current version
    - `pre_build` should contain only the test-run command: `./mvnw -Dmaven.repo.local=/tmp/maven-repository test`
    - _Bug_Condition: isBugCondition(X) where X.variableSetInPhase (pre_build) ≠ X.variableReadInPhase (post_build)_
    - _Requirements: 1.1, 2.1_

  - [x] 3.2 Rewrite post_build as a single pipe (`|`) bash block with `set -e`
    - Replace all individual `- command` list items in `post_build.commands` with a single `- |` block
    - The first line inside the block MUST be `set -e` so any non-zero exit code immediately aborts the entire block
    - This eliminates subshell list-item scoping and ensures all variable assignments are visible to subsequent lines
    - _Bug_Condition: isBugCondition(X) where X.variableExportedInListItem ≠ X.variableReadInListItem; X.awsCliExitCode ≠ 0 AND buildContinues_
    - _Requirements: 1.2, 1.4, 2.2, 2.4_

  - [x] 3.3 Add CURRENT_VERSION fetch at the top of the post_build block
    - Inside the `|` block, immediately after `set -e`, add the `aws lambda get-alias` call and assign its output to `CURRENT_VERSION` (no `export` keyword needed within a single block)
    - Example: `CURRENT_VERSION=$(aws lambda get-alias --function-name "$FUNCTION_NAME" --name "$ALIAS_NAME" --query 'FunctionVersion' --output text)`
    - _Bug_Condition: isBugCondition(X) where X.variableSetInPhase ≠ X.variableReadInPhase (cross-phase loss)_
    - _Requirements: 1.1, 2.1_

  - [x] 3.4 Add LAMBDA_ZIP empty-path guard
    - After the `find` assignment inside the `|` block, add: `[ -n "$LAMBDA_ZIP" ] || { echo "ERROR: Lambda ZIP not found in target/"; exit 1; }`
    - This produces a human-readable error and exits cleanly instead of passing an empty path to the AWS CLI
    - _Bug_Condition: isBugCondition(X) where X.lambdaZipPath = "" AND noGuardExists_
    - _Requirements: 1.3, 2.3_

  - [x] 3.5 Replace echo-based appspec.yml generation with printf
    - Remove all `echo "..." >> deployment/appspec.yml` lines
    - Replace with a single `printf` call using `%s` placeholders for all four variable values (`FUNCTION_NAME`, `ALIAS_NAME`, `CURRENT_VERSION`, `TARGET_VERSION`):
      ```bash
      printf 'version: "0.0"\n\nResources:\n  - TargetService:\n      Type: AWS::Lambda::Function\n      Properties:\n        Name: "%s"\n        Alias: "%s"\n        CurrentVersion: "%s"\n        TargetVersion: "%s"\n' \
        "$FUNCTION_NAME" "$ALIAS_NAME" "$CURRENT_VERSION" "$TARGET_VERSION" \
        > deployment/appspec.yml
      ```
    - _Bug_Condition: isBugCondition(X) where X.appspecGeneratedViaEchoLines = true; X.versionFieldUnquoted = true_
    - _Expected_Behavior: result.appspec_valid_yaml = true AND result.CurrentVersion_is_quoted_string = true AND result.TargetVersion_is_quoted_string = true_
    - _Preservation: deployment/ directory still contains appspec.yml as the CodeBuild artifact_
    - _Requirements: 1.5, 1.6, 2.5, 2.6_

  - [x] 3.6 Verify bug condition exploration test now passes
    - **Property 1: Expected Behavior** - Variable Availability and Build Integrity
    - **IMPORTANT**: Re-run the SAME `scripts/test-bug-condition.sh` from task 1 — do NOT write a new test
    - The test from task 1 encodes the expected behavior; when it passes it confirms all six defects are resolved
    - Run `bash scripts/test-bug-condition.sh` against the fixed `buildspec.yml` logic
    - **EXPECTED OUTCOME**: All six sub-cases PASS — variables are non-empty, build halts on CLI failure, AppSpec is valid and quoted
    - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5, 2.6_

  - [-] 3.7 Verify preservation tests still pass
    - **Property 2: Preservation** - Existing Build Behaviors Unchanged
    - **IMPORTANT**: Re-run the SAME `scripts/test-preservation.sh` from task 2 — do NOT write new tests
    - Run `bash scripts/test-preservation.sh` against the fixed `buildspec.yml` logic
    - **EXPECTED OUTCOME**: All preservation tests PASS — no regressions in test execution, packaging, artifact structure, build number recording, or env var usage
    - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.5, 3.6_

- [~] 4. Checkpoint — Ensure all tests pass
  - Run both `scripts/test-bug-condition.sh` and `scripts/test-preservation.sh` together and confirm every assertion passes
  - Verify the final `buildspec.yml` contains no remaining individual `- export ...` list items in `post_build`
  - Verify `pre_build` no longer references `CURRENT_VERSION`
  - Verify the `printf` block produces byte-for-byte correct YAML by parsing the output with `python3 -c "import yaml, sys; d=yaml.safe_load(open('deployment/appspec.yml')); assert isinstance(d['Resources'][0]['TargetService']['Properties']['CurrentVersion'], str)"`
  - Ask the user if any questions arise before closing the task
