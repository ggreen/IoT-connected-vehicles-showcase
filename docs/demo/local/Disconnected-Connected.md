
## Prerequisite

- Apache Maven 3.9.1 or higher 
- Docker
- Java 17
- [wget](https://www.gnu.org/software/wget/)


## Getting Started

Start Gemfire

```shell
./deployments/local/dataServices/gemfire/start-docker-gemfire.sh
```

Start Vehicle

```shell
java -jar applications/server/vehicle-server/target/vehicle-server-0.0.1-SNAPSHOT.jar --vehicle.vin=vin002 --server.port=7013 --gemfire.jmx.manager.port=20399 --gemfire.server.port=20300 --gemfire.startLocators="localhost[3010]" --gemfire.working.dir=runtime/vin003 --gemfire.distributedSystemId=3
```


Install/Start SCDF

```shell
./deployments/local/spring/scdf/scdf-install.sh 
```

-----------------------------------
# SCDF 




## SCDF Setup


``````

```shell
open http://localhost:9393/dashboard
```

- Click "Add application(s) -> Stream application starters for RabbitMQ/Maven -> IMPORT APPLICATIONS

- ![SCDF-ADD-APPS.png](image/SCDF-ADD-APPS.png)


## Create DataFlow

Click Streams -> Create STREAM(S)


Paste the following definition

```shell
check-engine-light=gemfire: geode --region-name=Vehicle --query="select * from /Vehicle where checkEngine=true" --pdx-read-serialized=true --subscription-enabled=true | log
```


Open Vehicle Server
```shell
open http://localhost:7010
```

Open Dashboard
```shell
open http://localhost:7010
```



Start Gfsh

```shell
docker exec -it gf-locator  gfsh
```
