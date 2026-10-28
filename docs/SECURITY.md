# Security model and improvement backlog

Implemented examples:
- Trivy filesystem and container image scans.
- Non-root runtime container user.
- Kubernetes non-root security context, dropped Linux capabilities, and no privilege escalation.
- Protected-branch conditions and manual deployment gate examples.
- Secret values supplied via CI variables/credentials rather than repository files.

Before production use:
- Pin container images by digest and CI actions/tasks by verified immutable versions where supported.
- Use short-lived OIDC/workload identity and least-privilege roles.
- Sign images and generate SBOM/provenance (for example, with approved Cosign/Syft workflows).
- Add dependency/SCA scanning and secret scanning.
- Configure Kubernetes NetworkPolicies, Pod Security Admission, RBAC, resource quotas and admission policy.
- Store artifacts immutably and retain checksums/digests.
- Add approvals, audit logging, and separation of duties.
- Establish a vulnerability exception process with owner and expiry date.
