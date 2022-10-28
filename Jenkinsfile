pipeline {
    agent {
        node {
            label 'maven'
        }
    }
    environment {
        DOCKER_CREDENTIAL_ID = 'dockerhub-id'
        GITEE_CREDENTIAL_ID = 'github-id'
        KUBECONFIG_CREDENTIAL_ID = 'kubeconfig-demo'
        REGISTRY = 'docker.io'
        DOCKERHUB_NAMESPACE = 'santiagosky'
        GITEE_ACCOUNT = 'santiagobin'
        SONAR_CREDENTIAL_ID = 'sonar-qube'
        BRANCH_NAME = 'main'
    }
    parameters {
        string(name:'PROJECT_NAME',defaultValue: '',description:'')
        string(name:'PROJECT_VERSION',defaultValue: 'v0.0Beta',description:'')
    }
    stages {
        stage('拉取代码') {
            steps {
                git(credentialsId: 'gitee-id', url: 'https://gitee.com/santiagobin/gulimall.git', branch: 'main', changelog: true, poll: false)
                sh 'echo 正在构建 $PROJECT_NAME 版本:$PROJECT_VERSION 将会提交给$REGISTRY镜像仓库'
                sh "echo 正在完整编译项目"
                container ('maven') {
                    sh "mvn clean install -Dmaven.test.skip=true"
                }

            }
        }

        stage('sonarqube代码质量分析') {
            steps {
                container ('maven') {
                    withCredentials([string(credentialsId: "$SONAR_CREDENTIAL_ID", variable: 'SONAR_TOKEN')]) {
                        withSonarQubeEnv('sonar') {
                            sh "echo 当前目录 `pwd`"
                            sh "mvn sonar:sonar -gs `pwd`/mvn-settings.xml -Dsonar.branch=$BRANCH_NAME -Dsonar.login=$SONAR_TOKEN"
                        }
                    }
                    timeout(time: 1, unit: 'HOURS') {
                        waitForQualityGate abortPipeline: true
                    }
                }
            }
        }
    }
}