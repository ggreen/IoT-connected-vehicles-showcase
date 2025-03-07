# IoT-data-showCase


 ![dashboard-map.png](docs/images/dashboard-map.png)

**Use cases**

- Hundreds of thousands of vehicles driving across the globe, 24 hours a day, 7 days a week.
- Need to access aggregated real-time location, driving, behavior data, temperature sensors and more.

**Solution**

- Scalable, resiliency Kubernetes platform based on Tanzu
- Cloud Native Microservices based on Spring
- Reliable messaging deliverable with RabbitMQ
- Low latency data read/write operations with GemFire




**Demo/Records/Webinars**

- [Building Scalable Resilient IoT Messaging (RabbitMQ Summit)](https://www.youtube.com/watch?v=BWcofi2FKHo) 


# Project Modules


| Applications                                                     | Notes                                                                                        |
|------------------------------------------------------------------|----------------------------------------------------------------------------------------------|
| [applications/vehicle-dashboard](applications/vehicle-dashboard) | GUI interface to views vehicle information                                                   |
| [applications/vehicle-sink](applications/vehicle-sink)           | Microservice streaming sink for storing Vehicle data in GemFire                              |
| [vehicle-telemetry-sink](applications/vehicle-telemetry-sink)    | Microservice streaming sink for storing Vehicle data in GemFire                              |


## Prerequisite

- Apache Maven 3.9.1 or higher
- Docker
- Java 17


## Build Jar

This project using Tanzu GemFire.

You will need to set your Broadcom Maven Repository user credentials.

See https://gemfire.dev/quickstart/java/
