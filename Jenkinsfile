#!/usr/bin/env groovy

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './gradlew assemle' // fails the build
            }
        }
    }

    post {
        failure {
            github_failure();
        }
    }
}

def github_failure() {
    withCredentials([string(credentialsId: 'gh-thepieterdc', variable: 'GH_TOKEN')]) {
        sh "curl --silent -H 'Authorization: token $GH_TOKEN' -X POST '{\"body\": \"Build failed :(\"}' https://api.github.com/repos/thepieterdc/ugent-dodona/commits/${env.GIT_COMMIT}/comments"
    }
}
