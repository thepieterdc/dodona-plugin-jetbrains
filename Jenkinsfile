#!/usr/bin/env groovy

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './gradlew assemble'
            }
        }
    }
}