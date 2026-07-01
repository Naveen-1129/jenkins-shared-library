# Jenkins Shared Library

## Overview

This repository contains a reusable Jenkins Shared Library that centralizes common CI/CD pipeline stages. Instead of duplicating pipeline code across multiple Jenkins projects, reusable functions are maintained here and imported into Jenkins pipelines using the `@Library` annotation.

This shared library is designed for Java/Maven applications and includes reusable stages for:

* Git Checkout
* Maven Compile
* SonarQube Code Analysis
* Maven Package
* Docker Image Build
* Trivy Image Scan
* Docker Image Push
* Deployment Manifest Update

---

# Repository Structure

```text
jenkins-shared-library/
│
├── vars/
│   ├── gitCheckout.groovy
│   ├── compile.groovy
│   ├── sonarScan.groovy
│   ├── buildApp.groovy
│   ├── dockerBuild.groovy
│   ├── trivyScan.groovy
│   ├── dockerPush.groovy
│   └── updateManifest.groovy
│
├── src/
│
├── resources/
│
└── README.md
```

---

# Prerequisites

Before using this Shared Library, ensure the following are configured in Jenkins:

* Jenkins Pipeline Plugin
* Git Plugin
* Pipeline: Groovy Libraries
* Pipeline Utility Steps Plugin
* Credentials Binding Plugin
* Docker installed on the Jenkins agent
* Maven installed
* Trivy installed
* SonarQube server configured
* Docker Hub credentials configured
* GitHub Personal Access Token configured

---

# Configure the Shared Library in Jenkins

Navigate to:

```
Manage Jenkins
    ↓
System
    ↓
Global Trusted Pipeline Libraries
```

Configure the following:

| Field                  | Value                                                                |
| ---------------------- | -------------------------------------------------------------------- |
| Name                   | devops-lib                                                           |
| Default Version        | main                                                                 |
| Retrieval Method       | Modern SCM                                                           |
| Source Code Management | Git                                                                  |
| Repository URL         | https://github.com/<your-github-username>/jenkins-shared-library.git |
| Credentials            | None (Public Repository)                                             |

Save the configuration.

---

# Using the Shared Library

Import the library at the top of your Jenkinsfile.

```groovy
@Library('devops-lib') _
```

---

# Example Jenkinsfile

```groovy
@Library('devops-lib') _

pipeline {

    agent any

    tools {
        maven 'maven3'
    }

    stages {

        stage('Checkout') {
            steps {
                gitCheckout(
                    "https://github.com/username/project.git",
                    "main"
                )
            }
        }

        stage('Compile') {
            steps {
                compile()
            }
        }

        stage('SonarQube Scan') {
            steps {
                sonarScan("SonarQube")
            }
        }

        stage('Build') {
            steps {
                buildApp()
            }
        }

        stage('Docker Build') {
            steps {
                dockerBuild(
                    "username/application",
                    "${BUILD_NUMBER}"
                )
            }
        }

        stage('Trivy Scan') {
            steps {
                trivyScan(
                    "username/application",
                    "${BUILD_NUMBER}"
                )
            }
        }

        stage('Docker Push') {
            steps {
                dockerPush(
                    "username/application",
                    "${BUILD_NUMBER}",
                    "dockerhub"
                )
            }
        }

        stage('Update Manifest') {
            steps {
                updateManifest(
                    "username/application",
                    "${BUILD_NUMBER}",
                    "githubtoken",
                    "deploymentfiles/deploy.yaml",
                    "GitHubUser",
                    "user@example.com",
                    "username/project"
                )
            }
        }
    }
}
```

---

# Shared Library Functions

## gitCheckout()

Checks out the source code from the Git repository.

### Parameters

* Repository URL
* Branch Name

Example:

```groovy
gitCheckout(repoUrl, branch)
```

---

## compile()

Compiles the Maven project.

Example:

```groovy
compile()
```

---

## sonarScan()

Performs static code analysis using SonarQube.

### Parameters

* SonarQube Server Name

Example:

```groovy
sonarScan("SonarQube")
```

---

## buildApp()

Packages the Maven application.

Example:

```groovy
buildApp()
```

---

## dockerBuild()

Builds the Docker image.

### Parameters

* Image Name
* Image Tag

Example:

```groovy
dockerBuild(imageName, imageTag)
```

---

## trivyScan()

Scans the Docker image for vulnerabilities.

### Parameters

* Image Name
* Image Tag

Example:

```groovy
trivyScan(imageName, imageTag)
```

---

## dockerPush()

Pushes the Docker image to Docker Hub.

### Parameters

* Image Name
* Image Tag
* Docker Credential ID

Example:

```groovy
dockerPush(imageName, imageTag, "dockerhub")
```

---

## updateManifest()

Updates the Kubernetes deployment manifest with the latest Docker image and pushes the changes to GitHub.

### Parameters

* Image Name
* Image Tag
* GitHub Credential ID
* Deployment Manifest Path
* Git Username
* Git Email
* Git Repository

Example:

```groovy
updateManifest(
    imageName,
    imageTag,
    githubCredential,
    deploymentFile,
    gitUser,
    gitEmail,
    repository
)
```

---

# Benefits of Using This Shared Library

* Eliminates duplicate pipeline code.
* Improves maintainability.
* Encourages reusable CI/CD components.
* Standardizes build and deployment processes.
* Simplifies Jenkinsfiles across multiple projects.
* Makes updates easier by modifying the library in a single location.

---

# Future Enhancements

* SonarQube Quality Gate validation
* Docker image cleanup
* Slack notifications
* Email notifications
* Amazon ECR support
* Kubernetes deployment automation
* Argo CD integration
* Helm chart deployment
* Multi-environment support (Dev, QA, UAT, Production)

---

# Author

**Naveen Asarala**

AWS DevOps Engineer
