def call(String imageName, String tag, String credentialId) {

    withCredentials([
        usernamePassword(
            credentialsId: credentialId,
            usernameVariable: 'DOCKER_USER',
            passwordVariable: 'DOCKER_PASS'
        )
    ]) {

        sh """
        echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin

        docker push ${imageName}:${tag}

        docker logout
        """

    }

}
