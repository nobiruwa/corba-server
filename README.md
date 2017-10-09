# Usage

## RMI

### HelloServer & HelloClient

```
$ ./docker-init.sh
$ ./docker-run.sh
# gradle build
# orbd -ORBInitialPort 1050 &
# java -cp build/libs/workspace.jar -Djava.naming.factory.initial=com.sun.jndi.cosnaming.CNCtxFactory -Djava.naming.provider.url=iiop://localhost:1050 rmi.HelloServer &
# java -cp build/libs/workspace.jar -Djava.naming.factory.initial=com.sun.jndi.cosnaming.CNCtxFactory -Djava.naming.provider.url=iiop://localhost:1050 rmi.HelloClient
```

## Java IDL with POA-Tie Server-Side Model

### TCPServer <-> CORBA-Web Server <-> CORBA Client or Web Client

```
$ ./docker-init.sh
$ ./docker-run.sh
# gradle build
# orbd -ORBInitialPort 1050 &
# java -cp build/libs/workspace.jar hostmock.service.TCPServer 8005 &
# java -cp build/libs/workspace.jar hostmock.Server &
# java -cp build/libs/workspace.jar hostmock.corba.HostClient
# java -cp build/libs/workspace.jar hostmock.service.ServiceClient
```
