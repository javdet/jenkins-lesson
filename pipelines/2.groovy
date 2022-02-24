pipeline {
    agent {
        label 'infra'
    }
    tools {
        terraform 'terraform115'
    }
    stages {
        stage('Hello world') {
            steps {
                sh 'terraform -version'
            }
        }
    }
}