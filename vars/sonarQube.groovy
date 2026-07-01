def call(String serverName) {

    echo "Scanning with SonarQube..."

    withSonarQubeEnv(serverName) {

        sh "mvn sonar:sonar"

    }

}
