pipeline {
    agent any
    stages {
        stage('Clean All') {
            steps {
                sh './gradlew clean'
            }
        }
        stage('Build Auth') {
            environment {
                SPRING_REDIS_HOST = 'redis'
            }
            steps {
                sh './gradlew -p auth test jacocoTestReport'
                junit(testResults: 'auth/build/test-results/test/*.xml', allowEmptyResults: true)
                sh './gradlew -p auth bootJar'
                archiveArtifacts 'auth/build/libs/*.jar'
                sh './gradlew -p auth integrationTest jacocoTestReport '
                jacoco(changeBuildStatus: false,
                        execPattern: '**/**.exec',
                        classPattern: '**/classes',
                        exclusionPattern: '**/*Test*.class,**/dto/*,**/model/*',
                        sourcePattern: '**/src/main/kotlin',
                        minimumLineCoverage: '60',
                        minimumComplexityCoverage: '45',
                        minimumMethodCoverage: '50')
                publishHTML([
                        allowMissing         : false,
                        alwaysLinkToLastBuild: false,
                        keepAll              : true,
                        reportDir            : 'auth/build/reports/tests/jacoco',
                        reportFiles          : 'index.html',
                        reportName           : 'Auth Coverage Report'
                ])
            }
        }
        stage('Build API') {
            environment {
                SPRING_REDIS_HOST = 'redis'
            }
            steps {
                sh './gradlew -p api test jacocoTestReport'
                junit(testResults: 'api/build/test-results/test/*.xml', allowEmptyResults: true)
                sh './gradlew -p api bootJar'
                archiveArtifacts 'api/build/libs/*.jar'
                sh './gradlew -p api integrationTest jacocoTestReport '
                jacoco(changeBuildStatus: false,
                        execPattern: '**/**.exec',
                        classPattern: '**/classes',
                        exclusionPattern: '**/*Test*.class,**/dto/*,**/model/*',
                        sourcePattern: '**/src/main/kotlin',
                        minimumLineCoverage: '60',
                        minimumComplexityCoverage: '45',
                        minimumMethodCoverage: '50')
                publishHTML([
                        allowMissing         : false,
                        alwaysLinkToLastBuild: false,
                        keepAll              : true,
                        reportDir            : 'api/build/reports/tests/jacoco',
                        reportFiles          : 'index.html',
                        reportName           : 'API Coverage Report'
                ])
            }
        }
        stage('Publish API Container') {
            steps {
                sh './gradlew -p api docker dockerTagLatest dockerPush dockerPushLatest'
            }
        }
        stage('Build Frontend') {
            environment {
                SPRING_REDIS_HOST = 'redis'
            }
            steps {
                sh './gradlew -p frontend test jacocoTestReport'
                junit(testResults: 'frontend/build/test-results/test/*.xml', allowEmptyResults: true)
                sh './gradlew -p frontend bootJar'
                archiveArtifacts 'frontend/build/libs/*.jar'
                sh './gradlew -p frontend integrationTest jacocoTestReport '
                jacoco(changeBuildStatus: false,
                        execPattern: '**/**.exec',
                        classPattern: '**/classes',
                        exclusionPattern: '**/*Test*.class,**/dto/*,**/model/*',
                        sourcePattern: '**/src/main/kotlin',
                        minimumLineCoverage: '60',
                        minimumComplexityCoverage: '45',
                        minimumMethodCoverage: '50')
                publishHTML([
                        allowMissing         : false,
                        alwaysLinkToLastBuild: false,
                        keepAll              : true,
                        reportDir            : 'frontend/build/reports/tests/jacoco',
                        reportFiles          : 'index.html',
                        reportName           : 'Frontend Coverage Report'
                ])
            }
        }
        stage('Publish Frontend Container') {
            steps {
                sh './gradlew -p frontend docker dockerTagLatest dockerPush dockerPushLatest'
            }
        }
    }
}
