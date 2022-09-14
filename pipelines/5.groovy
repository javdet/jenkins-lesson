pipeline {
    agent {
        label 'infra'
    }
    environment {
      YC_TOKEN = credentials('yc_token')
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
                sh 'sudo apt update && sudo apt install -y ca-certificates'
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