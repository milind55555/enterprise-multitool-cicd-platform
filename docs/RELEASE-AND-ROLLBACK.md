# Release, verification, and rollback

## Release checklist

- [ ] Pull request reviewed; branch protection and required checks enabled.
- [ ] Maven tests pass and reports are retained.
- [ ] SonarQube quality gate passes.
- [ ] Trivy filesystem and image scans pass policy.
- [ ] Image is tagged with commit SHA/release version and published to approved repository.
- [ ] Deployment uses least-privilege identity and protected environment approval.
- [ ] Kubernetes rollout is healthy and smoke test passes.
- [ ] Release notes and artifact digest are recorded.

## Deploy with Helm

```bash
helm upgrade --install enterprise-demo ./helm/enterprise-demo   --namespace enterprise-demo --create-namespace   --set image.repository=REGISTRY/TEAM/enterprise-demo   --set image.tag=COMMIT_SHA --wait --timeout 180s
```

## Roll back

```bash
helm history enterprise-demo -n enterprise-demo
helm rollback enterprise-demo PREVIOUS_REVISION -n enterprise-demo --wait
kubectl rollout status deployment/enterprise-demo -n enterprise-demo --timeout=180s
```

If smoke tests fail, stop promotion, capture logs/events, roll back to the last known-good revision, and record the root cause. Do not automatically roll back every transient scan or registry error without distinguishing build-time from runtime failures.
