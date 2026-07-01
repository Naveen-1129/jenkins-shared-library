def call(String imageName,
         String tag,
         String credentialId,
         String deploymentFile,
         String gitUser,
         String gitEmail,
         String repository) {

    withCredentials([
        usernamePassword(
            credentialsId: credentialId,
            usernameVariable: 'GITHUB_USER',
            passwordVariable: 'GITHUB_TOKEN'
        )
    ]) {

        sh """
        git config --global user.email "${gitEmail}"
        git config --global user.name "${gitUser}"

        sed -i "s|image: ${imageName}:.*|image: ${imageName}:${tag}|g" ${deploymentFile}

        git add ${deploymentFile}

        git commit -m "Updated image to build ${tag}" || true

        git push https://\$GITHUB_USER:\$GITHUB_TOKEN@github.com/${repository}.git HEAD:main
        """

    }

}
