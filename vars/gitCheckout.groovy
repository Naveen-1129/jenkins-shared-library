def call(String repoUrl, String branch) {

    echo "Checking out source code from GitHub..."

    git branch: branch,
        url: repoUrl
}
