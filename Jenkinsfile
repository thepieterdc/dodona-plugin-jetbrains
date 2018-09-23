#!/usr/bin/env groovy

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mv gradle-properties.jenkins gradle.properties'
                sh './gradlew assemble 2>&1 | tee build_log && (exit ${PIPESTATUS[0]})'
            }

            post {
                failure {
                    github_failure_build();
                }
            }
        }

        stage('Copyright headers') {
            steps {
                sh 'python3 scripts/jenkins/credits_checker.py src | tee copyright_log && (exit ${PIPESTATUS[0]})'
            }

            post {
                failure {
                    github_failure_copyright();
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
    String build_script = "python3 scripts/jenkins/build_log_to_json.py ${author} build_log build_log.json"
    String build_log = sh(script: build_script, returnStdout: true)
    withCredentials([string(credentialsId: 'gh-thepieterdc', variable: 'GH_TOKEN')]) {
        sh "curl --silent -H 'Authorization: token $GH_TOKEN' -X POST --data \"@build_log.json\" https://api.github.com/repos/thepieterdc/ugent-dodona/commits/${env.GIT_COMMIT}/comments >/dev/null"
    }
}

def github_failure_copyright() {
    String author = author_name(sh(script: 'git show -s --pretty=%ae', returnStdout: true).trim())
    String copyright_script = "python3 scripts/jenkins/credits_log_to_json.py ${author} copyright_log copyright_log.json"
    String copyright_log = sh(script: copyright_script, returnStdout: true)
    withCredentials([string(credentialsId: 'gh-thepieterdc', variable: 'GH_TOKEN')]) {
        sh "curl --silent -H 'Authorization: token $GH_TOKEN' -X POST --data \"@copyright_log.json\" https://api.github.com/repos/thepieterdc/ugent-dodona/commits/${env.GIT_COMMIT}/comments >/dev/null"
    }
}
