pipeline {
  agent any
  stages {
    stage('Clean') {
      steps {
        sh './gradlew clean'
      }
    }
    stage('API Test') {
      steps {
        sh './gradlew -p api test jacocoTestReport '
        junit(testResults: 'api/build/test-results/test/*.xml', allowEmptyResults: true)
        jacoco(buildOverBuild: false, execPattern: '**/**.exec', exclusionPattern: '**/*Test*.class,**/*dto*/*,**/*model*/*', classPattern: '**/classes', minimumLineCoverage: '75', maximumMethodCoverage: '85', sourcePattern: '**/src/main/kotlin')
      }
    }
    stage('API Build') {
      steps {
        sh './gradlew api:bootJar'
        archiveArtifacts 'api/build/libs/*.jar'
      }
    }
    stage('API Container') {
      steps {
        sh './gradlew api:dockerPush'
      }
    }
    stage('FE Test') {
      steps {
        sh './gradlew -p frontend test jacocoTestReport '
        junit(testResults: 'frontend/build/test-results/test/*.xml', allowEmptyResults: true)
        jacoco(buildOverBuild: false, execPattern: '**/**.exec', exclusionPattern: '**/*Test*.class,**/*dto*/*,**/*model*/*', classPattern: '**/classes', minimumLineCoverage: '75', maximumMethodCoverage: '85', sourcePattern: '**/src/main/kotlin')
      }
    }
    stage('FE Build') {
      steps {
        sh './gradlew frontend:bootJar'
        archiveArtifacts 'frontend/build/libs/*.jar'
      }
    }
    stage('FE Container') {
      steps {
        sh './gradlew frontend:dockerPush'
      }
    }
  }
}