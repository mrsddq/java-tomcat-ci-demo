currentBuild.displayName = "my-app-demo - ${currentBuild.number}"

pipeline {
    agent any

    tools {
        maven 'MVN_HOME'
    }

    environment {
        APP_WAR = 'target/my-app-demo.war'
        DEPLOY_TARGET = credentials('tomcat-deploy-target')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: 'git-credentials', url: 'https://github.com/mrsddq/my-app-demo.git'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Deploy to Tomcat') {
            when {
                allOf {
                    branch 'main'
                    expression { return env.DEPLOY_TARGET?.trim() }
                }
            }
            steps {
                sshagent(['tomcat-credentials']) {
                    sh '''
                        scp -o StrictHostKeyChecking=no "$APP_WAR" "$DEPLOY_TARGET:/opt/tomcat/webapps/"
                        ssh "$DEPLOY_TARGET" /opt/tomcat/bin/shutdown.sh || true
                        ssh "$DEPLOY_TARGET" /opt/tomcat/bin/startup.sh
                    '''
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/*.war', allowEmptyArchive: true
        }
    }
}


