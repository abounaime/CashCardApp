pipeline {
    agent any
    triggers {
        pollSCM('* * * * *') // Check if this frequency is suitable for your needs
    }
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
        stage("Package") {
            steps {
                sh "gradle build"
            }
        }
        stage("Docker build") {
            steps {
                sh "docker build -t bounaimeabdeljalil/cashcard ."
            }
        }
        stage("Docker push") {
            steps {
                sh "docker push bounaimeabdeljalil/cashcard"
            }
        }
        stage("Deploy to staging") {

             steps {

                  sh "docker run -d --rm -p 8765:8080
                  --name cashcard bounaimeabdeljalil/cashcard"

             }

        }
    } // Closing brace for stages
} // Closing brace for pipeline
