pipeline {
  agent any
  stages {
    stage('Clean') {
      steps {
        sh './gradlew clean'
      }
    }
    stage('Test') {
      steps {
        sh './gradlew -p api test jacocoTestReport'
        junit(testResults: 'api/build/test-results/test/*.xml', allowEmptyResults: true)
      }
    }
    stage('Build') {
      steps {
        sh './gradlew api:bootJar'
        archiveArtifacts 'api/build/libs/*.jar'
      }
    }
    stage('Integration Test') {
      steps {
        sh './gradlew -p api integrationTest jacocoTestReport '
        jacoco(buildOverBuild: false, execPattern: '**/**.exec', classPattern: '**/classes', exclusionPattern: '**/*Test*.class,**/dto/*,**/model/*', sourcePattern: '**/src/main/kotlin', changeBuildStatus: false, minimumLineCoverage: '75', minimumComplexityCoverage: '45', minimumMethodCoverage: '50')
        publishHTML target: [
            allowMissing: false,
            alwaysLinkToLastBuild: false,
            keepAll: true,
            reportDir: 'build/reports/tests/jacoco',
            reportFiles: 'index.html',
            reportName: 'API Coverage Report'
        ]
      }
    }
    stage('Push Container') {
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