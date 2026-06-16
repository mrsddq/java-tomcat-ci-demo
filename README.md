# Java Tomcat CI Demo

A minimal Java web application packaged as a WAR file for deployment to a servlet container such as Apache Tomcat, with a Jenkins pipeline and JSON health endpoint.

## Project Structure

```text
src/main/webapp/
  health.jsp
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
target/java-tomcat-ci-demo.war
```

## Endpoints

- `/` renders the demo landing page.
- `/health.jsp` returns a small JSON health response.

## Jenkins Deployment

The Jenkins pipeline builds the WAR and deploys it from the `main` branch. Configure these Jenkins credentials before enabling deployment:

- `git-credentials`
- `tomcat-credentials`
- `tomcat-deploy-target`

## Status

The repository has a complete baseline structure for a Maven Java web application:

- Maven WAR build
- JSP landing page
- JSON health endpoint
- Jakarta web descriptor
- Jenkins pipeline
- README
- `.gitignore`

## Quality Signals

- GitHub Actions Maven build workflow
- Jenkins deployment skeleton
- Jenkins artifact archiving
- deployment roadmap in [docs/deployment-roadmap.md](docs/deployment-roadmap.md)
- demo deployment checklist in [docs/DEMO_DEPLOYMENT_CHECKLIST.md](docs/DEMO_DEPLOYMENT_CHECKLIST.md)

This is best framed as a Java/Tomcat/CI deployment practice project.
