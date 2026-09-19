pipeline {
    agent any

    tools {
        maven 'MVN_HOME'
    }

    options {
        skipDefaultCheckout(true)
        disableConcurrentBuilds()
        timeout(time: 15, unit: 'MINUTES')
    }

    parameters {
        booleanParam(name: 'DEPLOY', defaultValue: false,
            description: 'Explicitly opt in to deploying a verified main-branch WAR.')
    }

    environment {
        APP_WAR = 'target/java-tomcat-ci-demo.war'
    }

    stages {
        stage('Checkout requested revision') {
            steps {
                checkout scm
            }
        }

        stage('Verify packaged application') {
            steps {
                sh 'mvn --batch-mode --no-transfer-progress clean verify'
            }
        }

        stage('Deploy to Tomcat') {
            when {
                allOf {
                    branch 'main'
                    expression { return params.DEPLOY }
                }
            }
            steps {
                withCredentials([
                    string(credentialsId: 'tomcat-deploy-target', variable: 'DEPLOY_TARGET'),
                    file(credentialsId: 'tomcat-known-hosts', variable: 'KNOWN_HOSTS')
                ]) {
                    sshagent(['tomcat-credentials']) {
                        sh '''
                            set -eu
                            printf '%s' "$DEPLOY_TARGET" | grep -Eq '^[A-Za-z_][A-Za-z0-9_-]*@[A-Za-z0-9][A-Za-z0-9.-]*$' || {
                                echo 'Deployment target must have the form user@hostname.' >&2
                                exit 2
                            }
                            scp -o BatchMode=yes -o StrictHostKeyChecking=yes \\
                                -o UserKnownHostsFile="$KNOWN_HOSTS" \\
                                "$APP_WAR" "$DEPLOY_TARGET:/opt/tomcat/webapps/java-tomcat-ci-demo.war"
                        '''
                    }
                }
            }
        }
    }

    post {
        always {
            junit testResults: 'target/failsafe-reports/TEST-*.xml', allowEmptyResults: true
            archiveArtifacts artifacts: 'target/*.war', allowEmptyArchive: true, fingerprint: true
        }
    }
}
