# buildspec-lambda-appspec-fix Bugfix Design

## Overview

The `buildspec.yml` used by AWS CodeBuild to build, package, and deploy a Spring Boot Lambda function has six compounding defects. They all stem from the same root misunderstanding: that `export` persists state across CodeBuild phases and across YAML list-item commands. The consequences are silent bad deploys where CodeDeploy receives an empty or structurally invalid AppSpec, and AWS CLI failures that do not halt the build.

The fix consolidates all variable-dependent work into a single bash command block inside `post_build`, retrieves `CURRENT_VERSION` at the point of use, guards against a missing ZIP file, enables fail-fast with `set -e`, and replaces the brittle line-by-line `echo` approach with a `printf` heredoc to produce correct, reproducible YAML.

The artifact structure (`deployment/` directory containing the Lambda ZIP and `appspec.yml`) is preserved exactly.

---

## Glossary

- **Bug_Condition (C)**: The set of conditions that cause the buildspec to produce an invalid or incomplete deployment — specifically: cross-phase variable loss, subshell variable loss, missing ZIP guard absence, silent AWS CLI failure continuation, malformed AppSpec generation, and unquoted version strings.
- **Property (P)**: The desired behavior for each buggy condition — variables are available where used, the build halts on missing artifacts or CLI errors, and the AppSpec is structurally valid YAML with quoted version strings.
- **Preservation**: All existing build behaviors that must remain unchanged — running tests, packaging the JAR into a Lambda ZIP, uploading to AWS, publishing a Lambda version, and producing the `deployment/` artifact directory.
- **`buildspec.yml`**: The AWS CodeBuild build specification file in the project root that orchestrates test, build, and deploy phases.
- **CodeBuild phase isolation**: Each CodeBuild phase (`install`, `pre_build`, `build`, `post_build`) runs in a separate shell process; environment variables set with `export` in one phase are not visible in the next.
- **YAML list-item subshell**: Each `-` command entry in a CodeBuild phase's `commands` list is executed in its own subshell; `export` within one list item does not persist to the next list item.
- **AppSpec**: The `appspec.yml` file consumed by AWS CodeDeploy to perform a Lambda canary/linear/all-at-once deployment, specifying `CurrentVersion` and `TargetVersion` as quoted strings.

---

## Bug Details

### Bug Condition

The bug manifests across six distinct conditions in `buildspec.yml`. Each condition is a case where the assumption that `export` persists state is false, or where failure is not detected and the build continues silently.

**Formal Specification:**

```
FUNCTION isBugCondition(X)
  INPUT: X of type BuildCommand
  OUTPUT: boolean

  RETURN (X.variableSetInPhase ≠ X.variableReadInPhase)          // cross-phase loss
      OR (X.variableExportedInListItem ≠ X.variableReadInListItem) // subshell loss
      OR (X.lambdaZipPath = "" AND noGuardExists)                  // missing ZIP guard
      OR (X.awsCliExitCode ≠ 0 AND buildContinues)               // silent failure
      OR (X.appspecGeneratedViaEchoLines = true)                   // fragile generation
      OR (X.versionFieldUnquoted = true)                           // unquoted version
END FUNCTION
```

### Examples

- **Cross-phase loss**: `CURRENT_VERSION` is exported in `pre_build`; when `post_build` runs its `echo "Current version: $CURRENT_VERSION"` the variable is empty, so the AppSpec gets `CurrentVersion: ""`.
- **Subshell loss**: `LAMBDA_ZIP` is set with `export` in one list item via `find`; the next list item that calls `aws lambda update-function-code --zip-file "fileb://$LAMBDA_ZIP"` sees an empty variable and passes a malformed path to the CLI.
- **Missing ZIP guard**: If the Maven build produces a differently-named artifact, `find` returns nothing, `LAMBDA_ZIP` is empty, and the AWS CLI call fails with a cryptic "No such file" error rather than a clear build failure.
- **Silent failure**: `aws lambda update-function-code` returns a non-zero exit code (e.g., permissions error), but because there is no `set -e` or `|| exit 1`, the buildspec continues to `publish-version`, potentially publishing a stale function version.
- **Fragile echo AppSpec**: The line `echo "        CurrentVersion: \"${CURRENT_VERSION}\""` is sensitive to shell quoting, indentation counting, and the empty-variable problem above — producing malformed or empty YAML.
- **Unquoted version fields**: Even when variables are populated, `echo "        CurrentVersion: ${CURRENT_VERSION}"` produces `CurrentVersion: 5`, which YAML parsers may treat as an integer; CodeDeploy requires a string like `"5"`.

---

## Expected Behavior

### Preservation Requirements

**Unchanged Behaviors:**
- Unit tests must continue to run in `pre_build` via `./mvnw test` before packaging.
- The Spring Boot application must continue to be packaged into a Lambda ZIP via `./mvnw clean package -DskipTests`.
- The Lambda ZIP must continue to be uploaded to AWS and a new Lambda version published via the AWS CLI.
- The `deployment/` directory must continue to be produced as the CodeBuild artifact, containing both the Lambda ZIP and `appspec.yml`.
- `FUNCTION_NAME` and `ALIAS_NAME` environment variables must remain the source of truth throughout the buildspec.
- The CodeBuild build number (`CODEBUILD_BUILD_NUMBER`) must continue to be recorded in the Lambda version description.

**Scope:**
All inputs where none of the six bug conditions hold — i.e., a correctly structured buildspec where variables are used in the same shell they are set — should be completely unaffected. The fix changes only the shell execution model (consolidating into a single bash block) and the AppSpec generation method; it does not alter the logical sequence of build steps.

---

## Hypothesized Root Cause

1. **Phase Isolation Misunderstanding**: CodeBuild runs each phase in a separate process. `export CURRENT_VERSION=...` in `pre_build` writes to that process's environment; when `post_build` starts, it is a fresh process with no inherited exports from `pre_build`. The author likely assumed CodeBuild behaves like a single top-level shell script.

2. **YAML List-Item Subshell Behaviour**: CodeBuild executes each `- command` YAML list entry by invoking a new shell. An `export` in list item N is therefore invisible to list item N+1. This is a common misconception — developers expect YAML list items to behave like sequential lines in a shell script, but they do not.

3. **No Fail-Fast Setting**: Without `set -e` (or explicit `|| exit 1` guards), bash continues executing after a non-zero exit code. AWS CLI commands that fail mid-phase leave the build in a partially-applied state that subsequent commands then act on incorrectly.

4. **Echo-Based YAML Generation Fragility**: Constructing YAML by echo-ing individual lines is error-prone because it requires precise control of indentation (spaces not tabs), shell quoting of embedded variables, and correct ordering. A single off-by-one space breaks YAML parsing. The `version: 0.0` line is additionally ambiguous — YAML parses it as the float `0.0`, not the string `"0.0"`.

5. **Unquoted Numeric Strings in AppSpec**: AWS CodeDeploy's AppSpec schema requires `CurrentVersion` and `TargetVersion` to be strings. Lambda version numbers are digit strings like `"3"` or `"12"`. Writing them without quotes causes YAML parsers to infer integer type, which CodeDeploy rejects.

---

## Correctness Properties

Property 1: Bug Condition — Variable Availability and Build Integrity

_For any_ buildspec execution where the bug condition holds (cross-phase variable loss, subshell variable loss, missing ZIP, silent CLI failure, fragile AppSpec generation, or unquoted versions), the fixed `buildspec.yml` SHALL ensure that all required variables (`CURRENT_VERSION`, `LAMBDA_ZIP`, `TARGET_VERSION`) are non-empty at point of use, that the build halts immediately on any AWS CLI failure or missing artifact, and that the generated `appspec.yml` is structurally valid YAML with `CurrentVersion` and `TargetVersion` as quoted string values.

**Validates: Requirements 2.1, 2.2, 2.3, 2.4, 2.5, 2.6**

Property 2: Preservation — Existing Build Behaviors Unchanged

_For any_ buildspec execution where the bug condition does NOT hold (variables are correctly scoped, artifacts exist, CLI calls succeed), the fixed `buildspec.yml` SHALL produce the same observable outcomes as the original: tests run in `pre_build`, the Lambda ZIP is packaged, uploaded, and a new version published, the `deployment/` artifact directory contains both the ZIP and `appspec.yml`, `FUNCTION_NAME`/`ALIAS_NAME` remain the source of truth, and the build number is recorded in the version description.

**Validates: Requirements 3.1, 3.2, 3.3, 3.4, 3.5, 3.6**

---

## Fix Implementation

### Changes Required

**File**: `buildspec.yml`

#### 1. Remove CURRENT_VERSION from pre_build — fetch it in post_build

Move the `aws lambda get-alias` call into `post_build`, immediately before it is consumed. This eliminates the cross-phase variable loss entirely.

**Before:**
```yaml
pre_build:
  commands:
    - echo "Getting current Lambda alias version..."
    - export CURRENT_VERSION=$(aws lambda get-alias --function-name "$FUNCTION_NAME" --name "$ALIAS_NAME" --query 'FunctionVersion' --output text)
    - echo "Current live version: $CURRENT_VERSION"
```

**After** (alias fetch removed from pre_build entirely; moved into post_build block):
```yaml
pre_build:
  commands:
    - echo "Running tests..."
    - ./mvnw -Dmaven.repo.local=/tmp/maven-repository test
```

#### 2. Consolidate all variable-dependent post_build work into a single bash -c block

Wrapping all commands in `bash -c '...'` (or `bash -c "..."`) means they share one shell process and one environment. Variables set with `export` (or simply assigned) in the block are visible to all subsequent lines within that same block.

```yaml
post_build:
  commands:
    - |
      set -e

      echo "=== Fetching current live Lambda version ==="
      CURRENT_VERSION=$(aws lambda get-alias \
        --function-name "$FUNCTION_NAME" \
        --name "$ALIAS_NAME" \
        --query 'FunctionVersion' \
        --output text)
      echo "Current live version: $CURRENT_VERSION"

      echo "=== Locating Lambda ZIP ==="
      LAMBDA_ZIP=$(find target -maxdepth 1 -name '*-lambda-package.zip' -print -quit)
      [ -n "$LAMBDA_ZIP" ] || { echo "ERROR: Lambda ZIP not found in target/"; exit 1; }
      echo "Lambda ZIP: $LAMBDA_ZIP"

      echo "=== Uploading Lambda function code ==="
      aws lambda update-function-code \
        --function-name "$FUNCTION_NAME" \
        --zip-file "fileb://$LAMBDA_ZIP"

      echo "=== Waiting for Lambda update to complete ==="
      aws lambda wait function-updated --function-name "$FUNCTION_NAME"

      echo "=== Publishing new Lambda version ==="
      TARGET_VERSION=$(aws lambda publish-version \
        --function-name "$FUNCTION_NAME" \
        --description "CodeBuild ${CODEBUILD_BUILD_NUMBER}" \
        --query 'Version' \
        --output text)
      echo "Target version: $TARGET_VERSION"

      echo "=== Creating deployment artifact ==="
      mkdir -p deployment

      printf 'version: "0.0"\n\nResources:\n  - TargetService:\n      Type: AWS::Lambda::Function\n      Properties:\n        Name: "%s"\n        Alias: "%s"\n        CurrentVersion: "%s"\n        TargetVersion: "%s"\n' \
        "$FUNCTION_NAME" "$ALIAS_NAME" "$CURRENT_VERSION" "$TARGET_VERSION" \
        > deployment/appspec.yml

      cp "$LAMBDA_ZIP" deployment/

      echo "=== Generated AppSpec ==="
      cat deployment/appspec.yml

      echo "=== Deployment directory ==="
      ls -la deployment
```

#### 3. set -e for fail-fast behaviour

The `set -e` at the top of the block ensures any non-zero exit code from any command immediately aborts the entire block. This replaces the need for explicit `|| exit 1` on every individual command (though the ZIP guard uses an explicit check for a clearer error message).

#### 4. Explicit ZIP guard

```bash
[ -n "$LAMBDA_ZIP" ] || { echo "ERROR: Lambda ZIP not found in target/"; exit 1; }
```

This produces a human-readable error and exits cleanly instead of passing an empty path to the AWS CLI.

#### 5. printf heredoc for appspec.yml

```bash
printf 'version: "0.0"\n\nResources:\n  - TargetService:\n      Type: AWS::Lambda::Function\n      Properties:\n        Name: "%s"\n        Alias: "%s"\n        CurrentVersion: "%s"\n        TargetVersion: "%s"\n' \
  "$FUNCTION_NAME" "$ALIAS_NAME" "$CURRENT_VERSION" "$TARGET_VERSION" \
  > deployment/appspec.yml
```

`printf` with `%s` placeholders handles variable substitution safely and produces exactly the right indentation in one atomic write. `CurrentVersion` and `TargetVersion` are wrapped in double-quote literals in the format string, so the output YAML always contains quoted string values regardless of whether the version number looks like an integer.

---

## Testing Strategy

### Validation Approach

The testing strategy follows a two-phase approach: first, surface counterexamples that demonstrate each defect against the original (unfixed) `buildspec.yml`; then verify the fix resolves all six conditions and preserves the existing build behaviors.

Because `buildspec.yml` runs inside AWS CodeBuild, automated tests operate on the shell logic extracted into test scripts rather than invoking CodeBuild directly. Each bug condition can be reproduced in a local bash environment that mimics the relevant constraint (subshell isolation, empty variables, non-zero exit codes).

---

### Exploratory Bug Condition Checking

**Goal**: Surface counterexamples that demonstrate each of the six bugs BEFORE applying the fix. Confirm or refute the root cause analysis. If any root cause is refuted, re-hypothesize.

**Test Plan**: Extract individual command sequences from the original `buildspec.yml` into minimal shell scripts. Run each script in an environment that mimics CodeBuild's constraints and assert the observable failure.

**Test Cases**:

1. **Cross-Phase Variable Loss**: Run pre_build commands in one subshell and then attempt to read `$CURRENT_VERSION` in a new subshell (simulating a new phase). Assert that the variable is empty. (Will demonstrate the bug on unfixed logic.)

2. **Subshell List-Item Loss**: Run the `export LAMBDA_ZIP=$(find ...)` command as one subshell invocation and then run `echo $LAMBDA_ZIP` as a second independent subshell invocation. Assert that the second invocation sees an empty variable. (Will demonstrate the bug on unfixed logic.)

3. **Empty LAMBDA_ZIP Propagation**: Run `aws lambda update-function-code --zip-file "fileb://"` (with an intentionally empty path) and observe whether the script continues or halts. Assert that the unfixed script continues past the error. (Will demonstrate silent failure.)

4. **Silent AWS CLI Failure**: Stub `aws` to return exit code 1. Run the unfixed post_build commands and assert that the buildspec does not halt — i.e., subsequent commands still execute. (Will demonstrate silent failure continuation.)

5. **Malformed AppSpec Generation**: Run the echo-based AppSpec generation with `CURRENT_VERSION=""` and `TARGET_VERSION=""`. Assert that the output file contains empty values for `CurrentVersion` and `TargetVersion`. (Will demonstrate cross-phase data loss impact on output.)

6. **Unquoted Version Type**: Parse the echo-generated `appspec.yml` with a YAML parser and assert that `CurrentVersion` is typed as an integer rather than a string when the version value is a number like `5`.

**Expected Counterexamples**:
- Variables are empty in the phase or list item that reads them after being set in a prior phase/list item.
- The build continues executing after an AWS CLI failure with a non-zero exit code.
- The generated `appspec.yml` contains empty or unquoted version values.

---

### Fix Checking

**Goal**: Verify that for all inputs where the bug condition holds, the fixed `buildspec.yml` produces the expected behavior.

**Pseudocode:**
```
FOR ALL X WHERE isBugCondition(X) DO
  result := executeBuildspec_fixed(X)
  ASSERT result.CURRENT_VERSION ≠ ""
      AND result.LAMBDA_ZIP ≠ ""
      AND result.TARGET_VERSION ≠ ""
      AND result.buildHaltedOnCliFailure = true
      AND result.appspec_valid_yaml = true
      AND result.CurrentVersion_is_quoted_string = true
      AND result.TargetVersion_is_quoted_string = true
END FOR
```

---

### Preservation Checking

**Goal**: Verify that for all inputs where the bug condition does NOT hold, the fixed `buildspec.yml` produces the same observable outcomes as the original.

**Pseudocode:**
```
FOR ALL X WHERE NOT isBugCondition(X) DO
  ASSERT buildspec_original(X).artifact_structure
       = buildspec_fixed(X).artifact_structure
      AND buildspec_original(X).lambda_version_description
       = buildspec_fixed(X).lambda_version_description
      AND buildspec_original(X).tests_ran = true
      AND buildspec_fixed(X).tests_ran = true
END FOR
```

**Testing Approach**: Property-based testing across shell script inputs verifies that the consolidated bash block preserves the build sequence. Generate random `FUNCTION_NAME`, `ALIAS_NAME`, `CODEBUILD_BUILD_NUMBER`, and version string combinations and assert output consistency.

**Test Cases**:

1. **Test Phase Preservation**: Verify that `./mvnw test` is still invoked in `pre_build` and that a test failure still halts the build before packaging begins.
2. **Package Phase Preservation**: Verify that `./mvnw clean package -DskipTests` is still invoked in `build` and produces a ZIP in `target/`.
3. **Artifact Directory Preservation**: Verify that `deployment/` contains both the Lambda ZIP and `appspec.yml` after a successful run.
4. **Build Number in Version Description**: Verify that `CODEBUILD_BUILD_NUMBER` is embedded in the `publish-version` description string.
5. **Environment Variable Source of Truth**: Verify that `FUNCTION_NAME` and `ALIAS_NAME` are used consistently in every AWS CLI call.

---

### Unit Tests

- Test that the ZIP guard (`[ -n "$LAMBDA_ZIP" ]`) exits with code 1 and prints the expected error message when `find` returns nothing.
- Test that `set -e` causes the bash block to abort when a stubbed `aws` command returns exit code 1.
- Test the `printf` format string with known input values and assert byte-for-byte equality of the output `appspec.yml`.
- Test that `CurrentVersion` and `TargetVersion` in the generated `appspec.yml` are YAML string scalars (quoted) when parsed by a YAML library.
- Test that `version` in the generated `appspec.yml` is the string `"0.0"` and not the float `0.0`.

### Property-Based Tests

- Generate random valid Lambda version numbers (1–999) and assert that the generated `appspec.yml` always wraps them in quotes, making them YAML string scalars.
- Generate random `FUNCTION_NAME` values (including names with hyphens and underscores) and assert the `printf`-based AppSpec always produces valid YAML.
- Generate random sequences of AWS CLI exit codes and assert that `set -e` always halts at the first non-zero code regardless of position in the block.

### Integration Tests

- Run the full fixed `buildspec.yml` against a real CodeBuild environment (or a local Docker container running the `aws/codebuild/standard` image) with a stubbed AWS CLI and assert that the `deployment/` artifact is produced with a valid, correctly-typed `appspec.yml`.
- Simulate a `find` that returns no results and assert the build exits with a non-zero code and the expected error message in the build log.
- Simulate an `aws lambda update-function-code` failure and assert that neither `publish-version` nor AppSpec generation is attempted.
