pipeline {
    agent any
    
    environment {
        ANDROID_HOME = "/home/ubuntu/android-sdk"
        PATH = "$ANDROID_HOME/cmdline-tools/bin:$ANDROID_HOME/platform-tools:$PATH"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', 
                url: 'https://github.com/girisettyramakrishna/android.git'
            }
        }
        
        stage('Build Debug APK') {
            steps {
                script {
                    // Verify files exist first
                    sh 'ls -la'
                    sh 'ls -la app/'
                    
                    // Only proceed if gradlew exists
                    if (fileExists('gradlew')) {
                        sh './gradlew assembleDebug'
                    } else {
                        error('gradlew file not found! Check project structure.')
                    }
                }
            }
        }
        
        stage('Archive APK') {
            steps {
                archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk'
            }
        }
    }
}
