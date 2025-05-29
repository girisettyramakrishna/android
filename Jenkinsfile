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
        
        stage('Build Libraries') {
            steps {
                script {
                    // Build each library module
                    dir('QRcodeLibrary') {
                        sh './gradlew assembleDebug'
                    }
                    dir('VIN') {
                        sh './gradlew assembleDebug'
                    }
                }
            }
        }
        
        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: '**/build/outputs/**/*.apk'
            }
        }
    }
}
