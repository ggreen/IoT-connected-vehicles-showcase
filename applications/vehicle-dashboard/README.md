# Dashboard

http://localhost:7010


## Starting GemFire

Set GEMFIRE_HOME
```shell
export GEMFIRE_HOME=/Users/devtools/repositories/IMDG/gemfire/vmware-gemfire-10.0.2
```

```shell
./deployments/local/dataServices/gemfire/start.sh
```


# Start Application


```shell
java -jar applications/vehicle-dashboard/target/vehicle-dashboard-0.0.1-SNAPSHOT.jar
```

----------------------------


# Docker 


## Run in Docker

```shell
docker run  --rm  --name dashboard --network=gemfire-cache  -p 7010:7010 cloudnativedata/vehicle-dashboard:0.0.1-SNAPSHOT --server.port=7010 --spring.profiles.active=gemfire --spring.data.gemfire.pool.default.locators="gf-locator[10334]"
```

## building image


```shell
mvn install
cd applications/vehicle-dashboard
mvn package

docker build  --platform linux/amd64,linux/arm64 -t vehicle-dashboard:0.0.1-SNAPSHOT .
docker tag vehicle-dashboard:0.0.1-SNAPSHOT cloudnativedata/vehicle-dashboard:0.0.1-SNAPSHOT
docker push cloudnativedata/vehicle-dashboard:0.0.1-SNAPSHOT
```



Push Image

```shell
docker push cloudnativedata/vehicle-dashboard:0.0.1-SNAPSHOT
```
