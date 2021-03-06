# Reference architecture

# Domain

This example uses flight leg as the domain object. This object is stored in a one node mongodb. A python 
script allows you to load data into the mongodb. Data from the mongodb is accessible via a Spring Boot java
service that exposes 3 endpoints via grpc (google protocol buffer). A mule API layer allows you to
access the service endpoint via http protocol.

**data contract V1**

```json
{
    "_id" :{
        "leg_id" : 351937,
        "version" : 0
     },
    "flightLegId" : {
        "flightNumber":"123A",
        "departureAirport":"DFW",
        "arrivalAirport": "MSP",
        "flightDate" : "2018-05-07T8:00:00-06:00"
        "airlineCode":"9W"
    },
    "flightTimes" : {
        "scheduleDepartureTime" : "2018-05-07T8:00:00-06:00",
        "scheduleArrivalTime" : "2018-05-07T11:00:00-06:00",
        "latestDepartureTime" : "2018-05-07T08:10:00-06:00",
        "latestArrivalTime" : "2018-05-07T8:11:20-06:00"
    },
    "registration":"5BDAQ",
    "status" : "SCH",
    "serviceType" : "J"
    
}
```


__Endpoints are exposed via a mule api layer backed by a springboot backend layer.__

    a. Update flight
    b. Retrieve flight by Id
    c. Retrieve flights by departure airport code and airline code

The example also shows how the data in mongodb is versioned and allows one to build audits easily.



## Deploy components in the order listed below
# Fluent/Fluentd
Deploy fluentd from docker hub into the minishift single node cluster using the following command
. Allow it to use the default configuration. Once we run start springboot we
can oc rsh into the fluentd pod to see where it stores the log. Fluentd can be
configured to redirect logs to elk setup.

    default configuration:
        Listens on 24224 port
    deployment:
        oc new-app fluent/fluentd

**fluentd config**

*basic fluentd.conf*

````
<source>
  @type forward
  port 24224
</source>
<match **>
  @type stdout
</match>

````
# Mongodb

A single node mongodb is deployed in the minishift cluster and preloaded with
data using a python script.

<pre>
    deployment:
        oc new-app mongo
    loading data:
        oc new-app mongo
        oc port-forward <<pod instance id>> 27017:27017
        python load_flights.py <span style="color:#00f"><b>
        (script folder is in mongodb-dataloader project)</b></span>
</pre>


## schedule-service-springboot
Simple grpc springboot multimodule service that is backed by mongodb. Source code for this project is in github to avoid 
proxy challenges. This module uses logback to log to fluentd.

<pre>
    source: https://github.com/nathansevugan/microservices.git
            
    endpoints: 
                http://<host address>:8085/api/getFlightLegsByDepartureAirport
                http://<host address>:8085/api/updateFlightLeg
                http://<host address>:8085/api/getFlightLegById
    environment variables:
               -Dmongodb.hostname=<<mongodb hostname>>
               -Dmongodb.port=<<mongodb portnumber>>
               -Dfluentd.remote.host=<<fluentd host>>
               -Dservice.port=<<spring boot service port>> 
                <span style="color:#00f"><b>This has to default to 8080 since the 
                base image provided by codecentric/springboot-maven3-centos exposes 8080. If a different port is needed,
                build your base image off of codecentric and expose your ports.</b></span>
                 
    deployment:
               oc new-app codecentric/springboot-maven3-centos~https://github.com/nathansevugan/microservices.git --name=schedule-service --strategy=source --build-env='APP_TARGET=schedule-service-springboot/target' -e JAVA_OPTS='-Dmongodb.hostname=172.30.91.14 -Dmongodb.port=27017 -Dserver.port=8080 
</pre>

## grpc-mule-schedule-client (Mulesoft API client)
This is a simple front end controller that exposes a rest api on port 8085. The port is hardcoded
for now. This layer takes springboot grpc service host ${grpc.host} and port ${grpc.port} as -D/env parameters. 
 
    endpoints: 
            http://<host address>:8085/api/getFlightLegsByDepartureAirport
            http://<host address>:8085/api/updateFlightLeg
            http://<host address>:8085/api/getFlightLegById
  
    deployment:
    
    Steps
    1. building docker client and deploying to docker hub
            1. Install docker locally
            2. CD to the folder where grpc-mule-schedule-client dockerfile exists
         
    2. Execute commands shown below to build a docker image and export the image
            1. docker image build . -t schedule-service-mule-api
            2. docker tag <image id> schedule-service-mule-api:<tag>
            3. docker save schedule-service-mule-api:<tag> schedule-service-mule-api.tar
            
    3. Loading the tar in minishift docker registry
           1. eval $(minishift oc-env)
           2. eval $(minishift docker-env)
           3. docker load < schedule-service-mule-api.tar
            
    4. deploying mule client in minishift    
           oc new-app schedule-service-mule-api:v1 -e JAVA_OPTS='-M-DgrpcHost=172.30.196.90'
           oc expose svc/schedule-service-mule-api --port=8085 
            
## schedule-client
Is a simple java application that allows you to test the schedule-service-spring-boot from outside the minishift cluster.
Minishift does not expose the grpc port for external consumption. Inorder to test your spring boot schedule service
we will port forward to test our deployment using schedule client

    deployment:
            1. oc port-forward <<pod instance id of the schedule:service>> 8080:8080




## Useful OC commands

    1. oc login
    2. oc get projects
    3. oc project <<project name>>
    4. oc new-project <<project name>>
    5. oc status
    6. oc get all
    7. oc logs bc/<<build-config-name>>
    8. oc edit bc/<<build-config-name>> -o=json/yaml
    9. oc describe bc/<<build-config-name>>
    10. bc can be replaced with dc - deployment config, is - image stream etc, svc - service
    11. oc get pods
    12. oc expose svc/<<service-name>>
    13. oc get routes
    14. oc port-forward <<pod name>> 27017:27017
    15. oc delete all --all
    16. oc delete pod/<<instance name>
    17. <<pod>> can be replaced with svc, route, dc, bc, is etc.
    18. oc rsh <<pod name>>
    
## Useful docker commands
    1. docker ps
    2. docker images
    3. docker image rm -f <<image id>>
    4. docker run <<image id>
    5. docker stop <<container id>>
    6. docker exec -it <<container id>> [/bin/sh or bash]  
    7. docker image build . -t <<image name>>
    
    

    
    
    
