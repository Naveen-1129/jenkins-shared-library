def call(String imageName, String tag) {

    echo "Building Docker Image..."

    sh """
        docker build -t ${imageName}:${tag} .
    """

    sh "docker images"

}
