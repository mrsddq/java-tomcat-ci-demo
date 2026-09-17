# Verification matrix

| Layer | Verification |
| --- | --- |
| Compile/package | Java 17 compilation and WAR packaging in `mvn clean verify` |
| Actual runtime | Failsafe deploys the built WAR to ephemeral loopback Tomcat 10.1 |
| HTTP contract | Health JSON, cache header, legacy alias, HEAD, unsupported POST |
| Web packaging | Landing page and unknown-route response |
| CI | Same Maven verify lifecycle on pushes and PRs; report artifact retained |
| External deployment | Opt-in Jenkins main build; requires separately verified SSH host keys |

Automated local integration tests do not prove a remote Tomcat rollout or a
Jenkins installation. The pipeline requires the plugins/tools described in the
project README; deployment is disabled by default.
