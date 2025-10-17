pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "my-spring-boot-app"
        DOCKER_TAG = "latest"
    }

    stages {
        stage('Build with Maven') {
            agent {
                docker {
                    image 'maven:3.9.9-eclipse-temurin-17'
                    args '-v /var/run/docker.sock:/var/run/docker.sock'
                }
            }
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Run Tests') {
            agent {
                docker {
                    image 'maven:3.9.9-eclipse-temurin-17'
                }
            }
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE:$DOCKER_TAG .'
            }
        }

        stage('Run Container') {
            steps {
                sh '''
                   docker rm -f $DOCKER_IMAGE || true
                   docker run -d --name $DOCKER_IMAGE -p 8080:8080 $DOCKER_IMAGE:$DOCKER_TAG
                '''
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished!'
        }
    }
}
