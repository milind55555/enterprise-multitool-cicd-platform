pipeline {
  agent any
  options { timestamps(); disableConcurrentBuilds() }
  environment {
    APP_DIR = 'app'
    IMAGE_NAME = "${DOCKER_REGISTRY}/enterprise-demo:${GIT_COMMIT}"
  }
  stages {
    stage('Checkout') { steps { checkout scm } }
    stage('Build and Unit Test') {
      steps { sh 'cd app && mvn -B -ntp clean verify' }
      post { always { junit allowEmptyResults: true, testResults: 'app/target/surefire-reports/*.xml' } }
    }
    stage('SonarQube') {
      when { expression { return env.SONARQUBE_ENV != null } }
      steps {
        withSonarQubeEnv('SonarQube') {
          sh 'cd app && mvn -B -ntp sonar:sonar -Dsonar.projectKey=enterprise-demo'
        }
      }
    }
    stage('Trivy Filesystem Scan') {
      steps { sh 'trivy fs --exit-code 1 --severity HIGH,CRITICAL --ignore-unfixed .' }
    }
    stage('Build and Scan Image') {
      steps {
        sh 'docker build --pull -t "$IMAGE_NAME" .'
        sh 'trivy image --exit-code 1 --severity HIGH,CRITICAL --ignore-unfixed "$IMAGE_NAME"'
      }
    }
    stage('Publish Image') {
      when { branch 'main' }
      steps {
        withCredentials([usernamePassword(credentialsId: 'container-registry',
          usernameVariable: 'REGISTRY_USER', passwordVariable: 'REGISTRY_PASSWORD')]) {
          sh 'echo "$REGISTRY_PASSWORD" | docker login "$DOCKER_REGISTRY" -u "$REGISTRY_USER" --password-stdin'
          sh 'docker push "$IMAGE_NAME"'
        }
      }
    }
    stage('Deploy to Kubernetes') {
      when { branch 'main' }
      steps {
        input message: 'Deploy this verified image to staging?', ok: 'Deploy'
        withCredentials([file(credentialsId: 'staging-kubeconfig', variable: 'KUBECONFIG')]) {
          sh 'helm upgrade --install enterprise-demo ./helm/enterprise-demo --namespace enterprise-demo --create-namespace --set image.repository="$DOCKER_REGISTRY/enterprise-demo" --set image.tag="$GIT_COMMIT" --wait --timeout 180s'
          sh './scripts/smoke-test.sh "$APP_URL"'
        }
      }
    }
  }
  post { always { archiveArtifacts artifacts: 'app/target/*.jar', allowEmptyArchive: true } }
}
