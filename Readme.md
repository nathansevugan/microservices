# schedule-service-springboot
**system parameters**

-Dserver.port=9090 -Dmongodb.hostname=localhost -Dmongodb.port=27017


# Fluent/Fluentd

Download fluentd image from docker hub

````
docker pull fluent/fluentd
**There is a docker file for this that can be used in minishift
````
# fluentd config

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

*elk fluentd.conf (** needs to be tested)*
````
<source>
  @type forward
  port 24224
</source>
<match **>
  @type elasticsearch
  logstash_format true
  host localhost
  port 9200
  index_name fluentd
  type_name fluentd
</match>
````


## Starting fluent/fluentd
1. Launch oracle ami from AWS Console or API
````
docker run -p 24224:24224 -p 24224:24224/udp -v /tmp:/fluentd/etc -e FLUENTD_CONF=fluentd.conf fluent/fluentd
docker start fluentd + elk
docker run -p 24224:24224 -p 24224:24224/udp -p 192.168.20.172:9200:9200 -v /tmp:/fluentd/etc fluent/fluentd

````


## linking fluentd with elk (** needs to be tested)

````
Download sep/elk image from docker hub
````

## data contract V1
```json
{
    "id" : "351937",
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
## data contract V2 (minor change)
<pre>
```json
{
    "id" : "351937",
    "flightLegId" : {
        "flightNumber":"123A",
        "departureAirport":"DFW",
        "arrivalAirport": "MSP",
        "flightDate" : "2018-05-07T08:00:00-06:00"
        "airlineCode":"9W"
    },
    "flightTimes" : {
        "scheduleDepartureTime" : "2018-05-07T8:00:00-06:00",
        "scheduleArrivalTime" : "2018-05-07T11:00:00-06:00",
        "latestDepartureTime" : "2018-05-07T08:10:00-06:00",
        "latestArrivalTime" : "2018-05-07T8:11:20-06:00",<span style="color:#00f"><b>
        "out":"2018-05-07T08:10:00-06:00"
    },
    "registration":"5BDAQ",
    "status" : "SCH",
    "serviceType" : "J"
    
}
```
</pre>


## data contract V3 (major change)
<pre>
```
{
    "id" : "351937",
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
        "latestArrivalTime" : "2018-05-07T8:11:20-06:00",
        "out":"2018-05-07T08:10:00-06:00",
    },
    <span style="color:#00f"><b>"equipment":{
    "registration":"5BDAQ",
    "aircraftType": "787"
    }</b></span>,
    "status" : "SCH",
    "serviceType" : "J"
}
```
</pre>
