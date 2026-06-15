# Demo Deployment Checklist

Use this when presenting the Java/Tomcat/Jenkins workflow.

## Local Verification

```bash
mvn clean package
```

## Evidence To Capture

- Maven build output.
- Generated WAR path under `target/`.
- Tomcat deployment screenshot or log excerpt.
- `/health.jsp` JSON response.
- Jenkins pipeline screenshot with archived artifact.

## Production Boundary

This is a deployment practice project. Add authentication, structured logging, and environment-specific configuration before treating it as an application template.
