# My App Demo

A minimal Java web application packaged as a WAR file for deployment to a servlet container such as Apache Tomcat.

## Project Structure

```text
src/main/webapp/
  index.jsp
  WEB-INF/web.xml
Jenkinsfile
pom.xml
```

## Requirements

- Java 17 or newer
- Maven 3.9 or newer
- Tomcat 10 or another Jakarta Servlet 6 compatible runtime

## Build

```bash
mvn clean package
```

The WAR file is created at:

```text
target/my-app-demo.war
```

## Jenkins Deployment

The Jenkins pipeline builds the WAR and deploys it from the `main` branch. Configure these Jenkins credentials before enabling deployment:

- `git-credentials`
- `tomcat-credentials`
- `tomcat-deploy-target`

## Status

The repository has a complete baseline structure for a Maven Java web application:

- Maven WAR build
- JSP landing page
- Jakarta web descriptor
- Jenkins pipeline
- README
- `.gitignore`
