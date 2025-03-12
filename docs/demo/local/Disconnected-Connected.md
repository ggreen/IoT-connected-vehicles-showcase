# Demo



The demo currently saves order records to Postgres when a change oil indicator is turned on.  

The vehicle details and change oil status are sames in an embedded GemFire. They are synced to a different GemFire instance. The events from the GemFire car instance survives outage/networks issues with a central GemFire instance. SCDF is used to sync data from GemFire central instance to Postgres. Postgres can be replaced with Greenplum.




## Prerequisite

- Apache Maven 3.9.1 or higher 
- Docker
- Java 17
- [wget](https://www.gnu.org/software/wget/)


## Getting Started

Start Gemfire

```shell
docker network create gemfire-cache --driver bridge
./deployments/local/dataServices/gemfire/start-docker-gemfire.sh
```

Start Postgres

```sql
docker run -it --rm --name postgres -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres debezium/example-postgres:2.3.3.Final
```

Connect to Postgres database using psql

```shell
docker exec -it postgres psql -d postgres -U postgres
```

Create table

```shell
CREATE TABLE vehicle_orders (
    order_id    serial primary key,
    vin varchar(255) not null,
    order_item varchar(255) not null,
    order_dt timestamp default current_timestamp
);
```


```shell
SELECT * FROM pg_stat_activity;
```
Test

```sql
insert into vehicle_orders(vin, order_item) values('TESTVIN',
'TEST ORDER');

select * from vehicle_orders;
```

Start Vehicle

```shell
java -jar applications/server/vehicle-server/target/vehicle-server-0.0.1-SNAPSHOT.jar --vehicle.vin=vin002 --server.port=7013 --gemfire.jmx.manager.port=20399 --gemfire.server.port=20300 --gemfire.startLocators="localhost[3010]" --gemfire.working.dir=runtime/vin003 --gemfire.distributedSystemId=3 --position.latitude=36.33282855329259 --position.longitude=-75.60540243388559
```

```shell
open http://localhost:7013
```


Start Vehicle Dashboard

```shell
java -jar applications/vehicle-dashboard/target/vehicle-dashboard-0.0.1-SNAPSHOT.jar --spring.profiles.active=gemfire
```

Open dashboard

```shell
open http://localhost:7010
```



## Install/Start SCDF

```shell
./deployments/local/spring/scdf/scdf-install.sh 
```

-----------------------------------
# SCDF

## SCDF Setup

```shell
./deployments/local/spring/scdf/scdf-install.sh 
```



```shell
open http://localhost:9393/dashboard
```

- Click "Add application(s) -> Stream application starters for RabbitMQ/Maven -> IMPORT APPLICATIONS

- ![SCDF-ADD-APPS.png](image/SCDF-ADD-APPS.png)


## Create DataFlow

Click Streams -> Create STREAM(S)


Paste the following definition

```shell
check-engine-light=gemfire: geode --region-name=Vehicle --query="select * from /Vehicle where checkEngine=true" --pdx-read-serialized=true --subscription-enabled=true | greenplum:upsert
```


```properties
app.gemfire.geode.client.pdx-read-serialized=true
app.gemfire.geode.pool.subscription-enabled=true
app.gemfire.geode.region.region-name=Vehicle
app.gemfire.geode.supplier.query=select * from /Vehicle where checkEngine=true
app.greenplum.jdbc.upsert.insert-sql=insert into vehicle_orders(vin, order_item) values(:vin,'SYNTHETIC OIL')
app.greenplum.spring.datasource.driver-class-name=org.postgresql.Driver
app.greenplum.spring.datasource.url=jdbc:postgresql://localhost/postgres
app.greenplum.spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
app.greenplum.spring.datasource.username=postgres
app.greenplum.spring.datasource.password=postgres
deployer.gemfire.bootVersion=2
deployer.greenplum.bootVersion=3
```


Open Vehicle Server
```shell
open http://localhost:7010
```

Open Dashboard
```shell
open http://localhost:7010
```



----------------

# Testing 


Tight light on/off to trigger event to save an oil order to Postgres

![turn-light.png](image/turn-light.png)



