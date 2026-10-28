# Configuration guide

## GitLab CI/CD variables

Set these in **Settings → CI/CD → Variables**; mask/protect sensitive values.

| Variable | Purpose |
|---|---|
| `SONAR_HOST_URL` | SonarQube base URL |
| `SONAR_TOKEN` | SonarQube project token |
| `KUBE_CONFIG_B64` | Base64 kubeconfig for a demo cluster (prefer short-lived identity/OIDC where possible) |
| `KUBE_NAMESPACE` | Target namespace |
| `APP_URL` | Reachable service URL for smoke testing |

GitLab's built-in registry variables are used for the example image. Ensure Docker-in-Docker is enabled only on appropriately isolated runners.

## Jenkins credentials and tools

Create Jenkins tools/credentials rather than hard-coding secrets:
- `container-registry`: username/password credential
- `staging-kubeconfig`: secret file credential
- Configure Maven, JDK 17, Docker, Helm, kubectl, Trivy and SonarQube installation/server.
- Define `DOCKER_REGISTRY` and `APP_URL` as folder/job/environment variables.
- Use a trusted shared library and restrict who can modify deployment jobs in a real environment.

## Azure DevOps

Create a registry service connection and store `registryServiceConnection` and `containerRegistry` as pipeline variables. Configure environment approvals before adding a deployment stage. Use workload identity federation where supported; do not use long-lived cloud keys if avoidable.

## Artifactory

To publish Maven artifacts, configure a Maven `distributionManagement` repository and CI-managed `settings.xml` server credentials. For Docker images, configure an Artifactory Docker repository and use a dedicated service account. Do not commit `settings.xml` containing credentials.

Example Maven publishing command after repository configuration:

```bash
cd app
mvn -B -ntp deploy -DskipTests
```

## SonarQube

Configure a project key and token. Add `sonar-maven-plugin` explicitly to the POM/plugin management if your organization requires pinned versions. For a local server, see the Docker Compose example and SonarQube's current official deployment guidance.
