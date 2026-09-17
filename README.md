# Java Tomcat CI Demo

A Java 17 servlet packaged as a WAR, with an HTTP-tested liveness endpoint and a
Jenkins pipeline that keeps deployment disabled unless explicitly requested.

## Build and test

Requires JDK 17 and Maven 3.9+. Deployment targets Apache Tomcat **10.1.x**
(Jakarta Servlet 6.0), not the older Tomcat 10.0/Servlet 5 line.

```bash
mvn --batch-mode --no-transfer-progress clean verify
```

Maven packages `target/java-tomcat-ci-demo.war`, then Failsafe starts an embedded
Tomcat 10.1 container on a random loopback port and tests that exact WAR. It checks
JSON status/content type/cache headers, the legacy URL, HEAD/POST behavior,
landing-page packaging, and missing routes. The container stops after tests.
Reports are in `target/failsafe-reports/` and uploaded by GitHub Actions.

`mvn package` alone does not execute the packaged-WAR integration tests; use
`verify` locally and in CI. No cloud account or external service is needed.

## HTTP contract

Under the deployed context `/java-tomcat-ci-demo`:

| Method and path | Result |
| --- | --- |
| `GET /` | Static landing page |
| `GET /health` | 200 JSON: `{"status":"ok","application":"java-tomcat-ci-demo"}` |
| `GET /health.jsp` | Compatibility alias for the same servlet |
| `HEAD /health` | 200 headers, no body |
| `POST /health` | 405 Method Not Allowed |

Health responses include `Cache-Control: no-store`. This endpoint reports process
liveness only; it does not establish database or downstream readiness. The WAR
contains the compiled servlet, static HTML, and Servlet 6.0 descriptor. Container
and test dependencies are not bundled as application dependencies.

## Jenkins

Use a Multibranch Pipeline with JDK 17, Maven tool `MVN_HOME`, and the standard
Pipeline, Credentials Binding, SSH Agent, and JUnit plugins. The pipeline checks
out the requested SCM revision (including PR revisions), verifies the WAR, and
archives reports and artifacts. Ordinary builds require no deployment secrets.

Deployment runs only when the branch is `main` **and** the build's `DEPLOY`
parameter is explicitly enabled. Configure these only on a trusted deployment job:

- `tomcat-credentials`: SSH private-key credential for a restricted deployment account.
- `tomcat-deploy-target`: secret text in `user@hostname` form.
- `tomcat-known-hosts`: secret file containing independently verified SSH host keys.

The optional step uploads the WAR to `/opt/tomcat/webapps/` with strict host-key
verification. It does not restart the container or claim a successful health
rollout. Configure Tomcat's deployment behavior, permissions, and an operational
rollback/health procedure before enabling it. Never expose deployment credentials
to untrusted PR jobs.

## Scope

This is a small CI and servlet integration example, not a production service.
There are no user accounts, database, authentication, business APIs, or high
availability mechanisms. A live Tomcat installation is not created by CI.
