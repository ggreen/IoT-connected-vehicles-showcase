# Vehicle Server



![dashboard-map.png](docs/img/server-dashboard.png)


## Running the server


```shell
java -jar applications/server/vehicle-server/target/vehicle-server-0.0.1-SNAPSHOT.jar --vehicle.vin=vin002 --server.port=7012 --gemfire.jmx.manager.port=20392 --gemfire.server.port=20302 --gemfire.startLocators="127.0.0.1[3012]" --gemfire.working.dir=runtime/vin002 --position.latitude=37.58388784106871 --position.longitude=-71.29707193977676 --gemfire.distributedSystemId=2 --gemfire.jmx.host.name.for.clients=host.docker.internal --gemfire.host.name.for.clients=host.docker.internal --gemfire.bind.address=host.docker.internal --gemfire.http.service.port=7072
```


## Docker


```shell
mvn install
cd applications/server/vehicle-server
mvn package


docker build  --platform linux/amd64,linux/arm64 -t vehicle-server:0.0.1-SNAPSHOT .
docker tag vehicle-server:0.0.1-SNAPSHOT cloudnativedata/vehicle-server:0.0.1-SNAPSHOT
docker push cloudnativedata/vehicle-server:0.0.1-SNAPSHOT
```


Run in Docker


```shell
docker run  --rm  --name vin002 --network=gemfire-cache cloudnativedata/vehicle-server:0.0.1-SNAPSHOT  --vehicle.vin=vin002 --server.port=7012 --gemfire.jmx.manager.port=20392 --gemfire.server.port=20302 --gemfire.startLocators="vin002[3012]" --gemfire.bind.address=vin002 --gemfire.jmx.host.name.for.clients=vin002 --gemfire.host.name.for.clients=vin002 --gemfire.working.dir=runtime/vin002 --position.latitude=37.58388784106871 --position.longitude=-71.29707193977676 --gemfire.distributedSystemId=2 --gemfire.http.service.port=7072 --gemfire.remoteLocators="gf-locator[10334]"
```

