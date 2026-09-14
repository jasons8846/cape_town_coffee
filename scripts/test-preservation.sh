#!/usr/bin/env sh
# =============================================================================
# test-preservation.sh
# Property 2: Preservation — Existing Build Behaviors Unchanged
#
# Validates: Requirements 3.1, 3.2, 3.3, 3.4, 3.5, 3.6
#
# This script delegates to test-preservation.py which implements the full
# property-based test suite using Python + Hypothesis. Python is required.
#
# Usage:
#   bash scripts/test-preservation.sh
#   sh scripts/test-preservation.sh
#   python scripts/test-preservation.py   (direct invocation)
# =============================================================================

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PY_SCRIPT="$SCRIPT_DIR/test-preservation.py"

# Prefer python3, fall back to python
if command -v python3 >/dev/null 2>&1; then
    PYTHON=python3
elif command -v python >/dev/null 2>&1; then
    PYTHON=python
else
    echo "ERROR: Python is not available. Install Python 3 to run preservation tests."
    exit 1
fi

echo "Running preservation tests via $PYTHON..."
exec "$PYTHON" "$PY_SCRIPT" "$@"
