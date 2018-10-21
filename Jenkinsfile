pipeline {
  agent any
  stages {
    stage('API Test & Build') {
      steps {
        sh './gradlew clean api:build'
      }
    }
  }
}