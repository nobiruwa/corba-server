# Usage

## HelloServer & HelloClient

```
$ ./docker-init.sh
$ ./docker-run.sh
# gradle build
# orbd -ORBInitialPort 1050
# java -cp .:build/libs/workspace.jar -Djava.naming.factory.initial=com.sun.jndi.cosnaming.CNCtxFactory -Djava.naming.provider.url=iiop://localhost:1050 HelloServer &
# java -cp .:build/libs/workspace.jar -Djava.naming.factory.initial=com.sun.jndi.cosnaming.CNCtxFactory -Djava.naming.provider.url=iiop://localhost:1050 HelloClient
```
