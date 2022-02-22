pipeline {
    agent {
        label 'infra'
    }
    tools {
        terraform 'terraform151'
    }
    stages {
        stage('Hello world') {
            steps {
                sh 'terraform -version'
            }
        }
    }
}