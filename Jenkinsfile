pipeline {
   agent any
   tools {
          maven 'M3'
        }
   stages {

     stage('Initialize'){
      steps {
        script {
            def dockerHome = tool 'myDocker'
            env.PATH = "${dockerHome}/bin:${env.PATH}"
           }
         }
      }



      stage('Checkout') {
          steps {

            git branch:'master', credentialsId: 'GIT_HUB_CREDENTIALS', url: 'https://github.com/mcool8180/Employee-Repository.git'


        echo 'Checkout Done'
        }
      }

      stage('Compile') {
         steps {
           sh 'mvn clean package'
            echo 'Compilation done'

         }
      }
     stage('Build') {
         steps {
//           sh 'mvn clean package'
            sh './service.sh'
// to do
// build service jar
// 1. docker image build from jar
//3 docker image run
           sh 'docker stop docker-emp-service'
           sh 'docker rm docker-emp-service'
           sh 'docker build -t docker-emp-service .'
           sh 'docker run --name docker-emp-service -it -d -p 8888:8888 -v /var/run/mysqld/mysqld.sock:/tmp/mysql.sock --network=host docker-emp-service'
              echo 'Build Done'

         }
      }




      stage('Checkout Test') {
         steps {

               git branch:'master', credentialsId: 'GIT_HUB_CREDENTIALS', url: 'https://github.com/mcool8180/employee-restAssured.git'

         echo 'Checkout Test Done'
         }
       }

      stage('Compile Test') {
          steps {
             sh 'mvn clean compile'
             echo 'Compilation of Test is done'

          }
       }
      stage('Test') {
         steps {
            sh 'mvn clean test -Dgroups=addEmployee'
            echo 'Test case passed successfully'

         }
       }



   }
}