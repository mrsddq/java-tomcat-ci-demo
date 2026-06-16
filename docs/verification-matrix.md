# Verification Matrix

| Layer | Command or check | Expected signal |
| --- | --- | --- |
| Build | `mvn clean package` | WAR artifact builds successfully |
| Unit/smoke | Add JUnit or servlet smoke test | Basic endpoint behavior is verified |
| CI | GitHub Actions workflow | Build runs on push and pull request |
| Deploy | Tomcat deploy notes | WAR path and context root are clear |

Local note: Maven must be installed before this project can be fully verified on a fresh machine.
