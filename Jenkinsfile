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

    post {
        always {
            githubNotify status: 'FAILURE', description: 'Notify test'
        }
    }
}
