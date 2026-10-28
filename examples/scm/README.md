# Legacy SCM adapters: SVN and ClearCase

These examples are intentionally separate from the core Git pipeline.

- **SVN:** provide `SVN_URL`, `SVN_USERNAME`, and a masked `SVN_PASSWORD` in the CI secret store. Install the SVN client on the runner.
- **IBM ClearCase:** use a licensed, network-authorized self-hosted agent with `cleartool` and a preconfigured view. ClearCase command behavior varies by version and view type; validate with your ClearCase administrator.
- **Migration approach:** export a controlled snapshot, preserve history where required using an approved migration tool, validate build reproducibility, and make Git the canonical SCM only after approval.

Never put credentials, ClearCase configuration secrets, or proprietary source in this public repository.
