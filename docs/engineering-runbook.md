# WAR verification and diagnosis

Use JDK 17 and Maven 3.9+, then run `mvn clean verify`. Inspect
`target/failsafe-reports/` on failure. Tests launch the packaged WAR on a random
loopback port; no external Tomcat or credentials are required. A missing servlet
mapping, wrong context, or invalid WAR must fail HTTP assertions.

For a configured manual deployment, use the README's Jenkins credential names
and keep host-key verification enabled. The optional upload is not a rollout
health check; validate the remote context and retain a previous WAR for rollback.
No deployment is attempted by GitHub Actions or ordinary Jenkins builds.
