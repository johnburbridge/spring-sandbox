pipeline {
  agent any
  stages {
    stage('API Test') {
      steps {
        sh './gradlew clean api:test'
        junit '**/build/test-results/test/*.xml'
      }
    }
    stage('API Build') {
      steps {
        sh './gradlew api:build'
      }
    }
  }
}