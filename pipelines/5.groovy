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
    options {
        timestamps()
        ansiColor('xterm')
    }
    parameters {
        choice(name: 'MODE', choices: ['check', 'apply'], description: 'Run mode: check or apply')
    }
    stages {
        stage('Init') {
            steps {
                sh 'apt update && apt install -y ca-certificates'
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
        stage('Apply') {
            when {
                equals expected: "apply", actual: env.MODE
            }
            steps {
                dir("terraform") {
                    sh 'terraform apply -auto-approve'
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