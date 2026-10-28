#!/usr/bin/env sh
set -eu
URL="${1:?Usage: smoke-test.sh https://application-url}"
URL="${URL%/}"
echo "Checking ${URL}/actuator/health"
attempt=1
while [ "$attempt" -le 12 ]; do
  if curl --fail --silent --show-error "${URL}/actuator/health"; then
    echo
    echo "Health check passed"
    exit 0
  fi
  echo "Attempt ${attempt}/12 failed; waiting 5 seconds"
  attempt=$((attempt + 1))
  sleep 5
done
echo "Health check failed after retries" >&2
exit 1
