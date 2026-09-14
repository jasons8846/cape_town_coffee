# Bugfix Requirements Document

## Introduction

The `buildspec.yml` used in AWS CodeBuild to build, package, and deploy a Spring Boot Lambda function fails at runtime due to several compounding issues: environment variables set with `export` do not survive across CodeBuild phases or across separate YAML list-item commands; the AppSpec file is generated unreliably via `echo` statements; there is no guard against an empty `LAMBDA_ZIP` path; and failed AWS CLI calls do not halt the build. The result is a broken deployment artifact or a silent bad deploy where CodeDeploy receives an invalid or incomplete AppSpec.

---

## Bug Analysis

### Current Behavior (Defect)

1.1 WHEN `CURRENT_VERSION` is exported in `pre_build` and then referenced in `post_build` THEN the system produces an empty or unset variable because CodeBuild runs each phase in a separate shell process.

1.2 WHEN `LAMBDA_ZIP`, `TARGET_VERSION`, or any other `export`ed variable is set in one `- command` list item and referenced in a subsequent list item within the same phase THEN the system produces an empty variable because each YAML list item runs in its own subshell.

1.3 WHEN the `find` command returns no results and `LAMBDA_ZIP` is empty THEN the system passes an empty path to `aws lambda update-function-code`, resulting in a cryptic CLI error rather than a clear failure.

1.4 WHEN `aws lambda update-function-code` fails THEN the system continues executing subsequent commands silently, potentially publishing a stale or broken Lambda version.

1.5 WHEN the `appspec.yml` is generated line-by-line via `echo` statements THEN the system may produce malformed YAML due to incorrect indentation, unquoted numeric values (e.g. `version: 0.0` parsed as a float), or shell quoting issues with embedded variables.

1.6 WHEN `CurrentVersion` and `TargetVersion` fields in the AppSpec are written without surrounding quotes THEN CodeDeploy may reject the AppSpec because it expects these fields as quoted strings.

---

### Expected Behavior (Correct)

2.1 WHEN `CURRENT_VERSION` is needed in `post_build` THEN the system SHALL retrieve it within `post_build` itself (or use a shared mechanism such as writing to a file and sourcing it) so the value is always available in the phase that uses it.

2.2 WHEN multiple variable assignments and AWS CLI calls must share state within a phase THEN the system SHALL chain them in a single shell command (using `&&` or a script block) or write values to a sourced file so that variable values persist across the command sequence.

2.3 WHEN the `find` command is used to locate the Lambda ZIP THEN the system SHALL verify that `LAMBDA_ZIP` is non-empty before invoking `aws lambda update-function-code`, and SHALL fail the build with a clear error message if the file is not found.

2.4 WHEN `aws lambda update-function-code` or any critical AWS CLI call fails THEN the system SHALL immediately halt the build and surface the error rather than continuing to subsequent commands.

2.5 WHEN the `appspec.yml` is generated THEN the system SHALL use a `printf` heredoc or equivalent multi-line write approach so that indentation, quoting, and YAML structure are correct and reproducible.

2.6 WHEN `CurrentVersion` and `TargetVersion` are written to the AppSpec THEN the system SHALL wrap both values in double quotes so that CodeDeploy receives them as string literals regardless of their numeric content.

---

### Unchanged Behavior (Regression Prevention)

3.1 WHEN the build succeeds THEN the system SHALL CONTINUE TO package the Spring Boot application into a Lambda ZIP using `./mvnw clean package -DskipTests`.

3.2 WHEN the build succeeds THEN the system SHALL CONTINUE TO run unit tests in the `pre_build` phase before packaging.

3.3 WHEN the build succeeds THEN the system SHALL CONTINUE TO upload the Lambda ZIP to AWS and publish a new Lambda version via the AWS CLI.

3.4 WHEN the build succeeds THEN the system SHALL CONTINUE TO produce a `deployment/` directory containing both the Lambda ZIP and the `appspec.yml` as the CodeBuild artifact.

3.5 WHEN the build succeeds THEN the system SHALL CONTINUE TO use `FUNCTION_NAME` and `ALIAS_NAME` environment variables as the source of truth for the Lambda function name and alias throughout the buildspec.

3.6 WHEN the build succeeds THEN the system SHALL CONTINUE TO record the CodeBuild build number in the Lambda version description for traceability.

---

## Bug Condition Analysis

**Bug Condition Function — Phase/Subshell Variable Scoping:**
```pascal
FUNCTION isBugCondition(X)
  INPUT: X of type BuildCommand
  OUTPUT: boolean

  RETURN (X.variableSetInPhase ≠ X.variableReadInPhase)
      OR (X.variableExportedInListItem ≠ X.variableReadInListItem)
END FUNCTION
```

**Property: Fix Checking**
```pascal
FOR ALL X WHERE isBugCondition(X) DO
  result ← executeBuildspec'(X)
  ASSERT result.CURRENT_VERSION ≠ ""
      AND result.LAMBDA_ZIP ≠ ""
      AND result.TARGET_VERSION ≠ ""
      AND result.appspec_valid = true
END FOR
```

**Preservation Goal:**
```pascal
FOR ALL X WHERE NOT isBugCondition(X) DO
  ASSERT executeBuildspec(X) = executeBuildspec'(X)
END FOR
```
