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
                sh '''
                    chmod +x gradlew
                    ./gradlew assembleDebug
                '''
            }
        }
        
        stage('Archive APK') {
            steps {
                archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', 
                fingerprint: true
            }
        }
    }
}
