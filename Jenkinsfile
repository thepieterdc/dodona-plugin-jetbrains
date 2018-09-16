#!/usr/bin/env groovy

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script {
                    result_build = sh script: './gradlew assemle', returnStdout: true
                }
            }

            post {
                failure {
                    github_failure_build();
                }
            }
        }
    }
}

String author_name(String email) {
    if (email.toLowerCase().contains("pieter")) {
        return "Pieter"
    } else if(email.toLowerCase().contains("tobiah")) {
        return "Tobiah"
    } else if(email.toLowerCase().contains("rien")) {
        return "Rien"
    }
    return "Someone"
}

def github_failure_build() {
    String author = author_name(sh(script: 'git show -s --pretty=%ae', returnStdout: true).trim())
    String failure = result_build
    String message = "${author} screwed things up once more.\n\n*Build log:*\n${failure}"
    withCredentials([string(credentialsId: 'gh-thepieterdc', variable: 'GH_TOKEN')]) {
        sh "curl --silent -H 'Authorization: token $GH_TOKEN' -X POST -d '{\"body\": \"${message}\"}' https://api.github.com/repos/thepieterdc/ugent-dodona/commits/${env.GIT_COMMIT}/comments"
    }
}
