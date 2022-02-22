pipeline {
    agent {
        label 'infra'
    }
    stages {
        stage('Hello world') {
            steps {
                sh 'echo hello'
            }
        }
    }
}