pipeline {
  agent any
  stages {
    stage('API Test') {
      steps {
        sh './gradlew clean api:test'
        junit(testResults: 'api/build/test-results/test/*.xml', allowEmptyResults: true)
      }
    }
    stage('API Build') {
      steps {
        sh './gradlew api:build'
        archiveArtifacts 'api/build/libs/*.jar'
      }
    }
  }
}