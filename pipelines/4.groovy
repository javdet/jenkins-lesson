pipeline {
    agent {
        label 'infra'
    }
    environment {
      YC_TOKEN = credentials('yc_token')
    }
    stages {
        stage('Init') {
            steps {
                sh 'mkdir -p ~/.ssh'
                sh 'ssh-keyscan -H github.com >> ~/.ssh/known_hosts'
                sh 'apt update && apt install -y ca-certificates'
                git branch: 'main', credentialsId: 'git', url: 'git@github.com:javdet/jenkins-lesson.git'
                dir("terraform") {
                    sh 'terraform init'
                }
            }
        }
        stage('Plan') {
            steps {
                dir("terraform") {
                    sh 'terraform plan'
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}