pipeline {
    agent any

    environment {
        PATH = "/opt/gradle/gradle-7.6/bin:${env.PATH}"
    }

    stages {
        stage('Verify Gradle Installation') {
            steps {
                script {
                    sh 'gradle --version'
                }
            }
        }
        stage('Clean') {
            steps {
                script {
                    sh 'gradle clean'
                }
            }
        }
        stage('Compile') {
            steps {
                script {
                    sh 'gradle compileJava'
                }
            }
        }
        stage("Unit test") {

            steps {
                    sh "gradle test"

            }

        }
        stage("Code coverage") {

             steps {

                  sh "gradle jacocoTestReport"

                  sh "gradle jacocoTestCoverageVerification"

             }

        }
        stage("Static code analysis") {

             steps {

                  sh "gradle checkstyleMain"

             }

        }
    }
}
