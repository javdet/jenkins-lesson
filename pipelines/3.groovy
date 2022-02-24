pipeline {
    agent {
        label 'infra'
    }
    tools {
        terraform 'terraform115'
    }
    stages {
        stage('Checkout repo') {
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
    }
    post {
        always {
            cleanWs()
        }
    }
}