pipeline {
    agent any

    environment {
        ANDROID_HOME = "/opt/android-sdk"  // Update based on your setup
        PATH = "$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$PATH"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/girisettyramakrishna/android.git'
            }
        }

        stage('Build QRcodeLibrary APK') {
            steps {
                dir('.') {
                    sh 'chmod +x ./gradlew'
                    sh './gradlew :QRcodeLibrary:assembleDebug'
                }
            }
        }

        stage('Archive APK') {
            steps {
                archiveArtifacts artifacts: 'QRcodeLibrary/build/outputs/**/*.apk', allowEmptyArchive: true
            }
        }
    }

    post {
        failure {
            echo "Build failed! Please check the logs."
        }
        success {
            echo "Build succeeded. APK archived."
        }
    }
}
