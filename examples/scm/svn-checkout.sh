#!/usr/bin/env sh
# Legacy SCM adapter example. Store SVN credentials in the CI secret store.
set -eu
: "${SVN_URL:?Set SVN_URL}"
: "${SVN_USERNAME:?Set SVN_USERNAME}"
: "${SVN_PASSWORD:?Set SVN_PASSWORD through a masked secret variable}"
svn checkout --non-interactive --username "$SVN_USERNAME" \
  --password "$SVN_PASSWORD" "$SVN_URL" legacy-svn-checkout
# Prefer migrating approved source into Git for the canonical pipeline.
