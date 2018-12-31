pipeline {
  agent any
  stages {
    stage('API Test') {
      steps {
        sh './gradlew clean api:jacocoTestReport api:test'
        junit(testResults: 'api/build/test-results/test/*.xml', allowEmptyResults: true)
        jacoco(buildOverBuild: true, execPattern: '**/**.exec', exclusionPattern: '**/*Test*.class,**/*dto*/*,**/*model*/*', classPattern: '**/classes', minimumLineCoverage: '75', maximumMethodCoverage: '85', sourcePattern: '**/src/main/kotlin')
      }
    }
    stage('API Build') {
      steps {
        sh './gradlew api:build'
        archiveArtifacts 'api/build/libs/*.jar'
      }
    }
    stage('API Container') {
      steps {
        sh './gradlew docker'
        sh './gradlew dockerPush'
      }
    }
  }
}