# Troubleshooting notes

| Symptom | Checks |
|---|---|
| Maven tests fail | Verify JDK 17, Maven wrapper/version, test logs, and dependency proxy access |
| Sonar stage skipped | `SONAR_HOST_URL` and `SONAR_TOKEN` must exist; verify server connectivity and token permissions |
| Trivy exits non-zero | Review findings; update vulnerable dependencies/base image or document an approved exception—do not suppress blindly |
| Docker daemon unavailable | Check runner permissions and Docker-in-Docker configuration; prefer isolated ephemeral runners |
| Image push denied | Verify registry path, token scope, and image repository permissions |
| Helm timeout | Check `kubectl get pods,events -n enterprise-demo`, image pull errors, probes, resource requests and cluster connectivity |
| Smoke test fails | Confirm `APP_URL`, ingress/DNS/TLS, service endpoints, and `/actuator/health` response |
| SVN checkout fails | Validate URL, trust chain, client version, and masked credentials |
| ClearCase command fails | Confirm licensed agent, view configuration, VOB access, and ClearCase version-specific syntax |

Never paste secret values into logs or support tickets.
