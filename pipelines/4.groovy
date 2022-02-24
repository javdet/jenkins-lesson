pipeline {
    agent {
        label 'infra'
    }
    tools {
        terraform 'terraform115'
    }
    environment {
      AWS_ACCESS_KEY_ID = credentials('aws_access_key_id')
      AWS_SECRET_ACCESS_KEY = credentials('aws_secret_access_key')
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