#!/usr/bin/env sh
set -eu
: "${IMAGE_REPOSITORY:?Set IMAGE_REPOSITORY}"
: "${IMAGE_TAG:?Set IMAGE_TAG}"
NAMESPACE="${KUBE_NAMESPACE:-enterprise-demo}"
RELEASE="${HELM_RELEASE:-enterprise-demo}"
helm upgrade --install "$RELEASE" ./helm/enterprise-demo \
  --namespace "$NAMESPACE" --create-namespace \
  --set image.repository="$IMAGE_REPOSITORY" \
  --set image.tag="$IMAGE_TAG" \
  --wait --timeout 180s
