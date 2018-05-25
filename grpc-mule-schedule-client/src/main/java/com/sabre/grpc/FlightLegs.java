package com.sabre.grpc;

import java.util.HashMap;

import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.protobuf.util.JsonFormat;
import com.sabre.as.flight.schedule.service.ProtoFlightLeg;
import com.sabre.as.flight.schedule.service.ProtoFlightLegId;
import com.sabre.as.flight.schedule.service.ProtoFlightLegs;
import com.sabre.as.flight.schedule.service.ProtoFlightTimes;
import com.sabre.as.flight.schedule.service.ProtoLegId;
import com.sabre.as.flight.schedule.service.ProtoQueryByAirlineAndAirport;
import com.sabre.as.flight.schedule.service.ProtoStatus;
import com.sabre.as.flight.schedule.service.ScheduleServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class FlightLegs {

	private static Logger logger = LoggerFactory.getLogger(FlightLegs.class);
	private static String ipAddress = "localhost";
	private static int port = 8080;

	public String getFlightLegsByDepartureAirport(HashMap<String, Object> payload) throws Exception {

		logger.info("*** Starting getFlightLegsFromgRPC : " + payload);
		
		String jsonArrayStr = null;
		ManagedChannel channel = null;
		
		try {
			channel = ManagedChannelBuilder.forAddress(ipAddress, port).usePlaintext(true).build();
			ScheduleServiceGrpc.ScheduleServiceBlockingStub stub = ScheduleServiceGrpc.newBlockingStub(channel);
	
			ProtoFlightLegs flightLegResponse = stub.getFlightLegsByDepartureAirport(ProtoQueryByAirlineAndAirport
					.newBuilder()
					.setAirportCode(((String) payload.get("departureAirport")).toUpperCase())
					.setAirlineCode(((String) payload.get("airline")).toUpperCase()).build());

			logger.info("No of Legs :\n" + flightLegResponse.getFlightLegsList().size());
			jsonArrayStr = "{ \"count\" :"+flightLegResponse.getFlightLegsList().size()+", \"flightLegs\": [ ";

			for (int i = 0; i < flightLegResponse.getFlightLegsList().size(); i++) {
				ProtoFlightLeg protoFlightLeg = flightLegResponse.getFlightLegsList().get(i);
				String jsonStr = JsonFormat.printer().print(protoFlightLeg);
				jsonArrayStr += jsonStr + ",";
			}
			jsonArrayStr = jsonArrayStr.substring(0, jsonArrayStr.length() - 1);
			jsonArrayStr += " ] }";

		} catch (Exception ex) {
			if(ex.getMessage().contains("UNAVAILABLE:"))
				jsonArrayStr = "{ \"Error\":\"" + ex.getMessage() +", Please Check gRPC server is running on "+ipAddress+":"+port + "\"}";
			else
				jsonArrayStr = "{ \"Error\":\"" + ex.getMessage() + "\"}";
			ex.printStackTrace();
		}
		// logger.info("jsonArrayStr :\n" + jsonArrayStr);
		
		if (channel != null) 
			channel.shutdown();
		return jsonArrayStr;
	}
	
	
	public String getFlightLegById(HashMap<String, Object> payload) throws Exception {

		logger.info("*** Starting getFlightLegById : " + payload);
		
		String jsonStr = null;
		ManagedChannel channel = null;
		
		try {
			channel = ManagedChannelBuilder.forAddress(ipAddress, port).usePlaintext(true).build();
			ScheduleServiceGrpc.ScheduleServiceBlockingStub stub = ScheduleServiceGrpc.newBlockingStub(channel);
			
			ProtoFlightLeg flightLeg = stub.getFlightLegById(ProtoLegId.newBuilder()
					.setId(Long.parseLong((String)payload.get("id")))
					.setVersion(Long.parseLong((String)payload.get("version"))).build());
			
			//logger.info("flightLeg :" + flightLeg);
			jsonStr = JsonFormat.printer().print(flightLeg);
			logger.info("jsonStr :" + jsonStr);
			
		} catch (Exception ex) {
			jsonStr = "{ \"Error\":\"" + ex.getMessage() + "\"}";
			ex.printStackTrace();
		}
		if (channel != null) 
			channel.shutdown();
		return jsonStr;
	}
	
	
	public String updateFlightLeg(HashMap<String, Object> payload) throws Exception {

		logger.info("*** Starting updateFlightLeg : " + payload);
		
		String jsonStr = "";
		ManagedChannel channel = null;
		
		try {
			channel = ManagedChannelBuilder.forAddress(ipAddress, port).usePlaintext(true).build();
			ScheduleServiceGrpc.ScheduleServiceBlockingStub stub = ScheduleServiceGrpc.newBlockingStub(channel);
			String id =     (String) ((HashMap)payload.get("legId")).get("id");
			String version = (String)((HashMap)payload.get("legId")).get("version");
				
			String flightNumber =  (String)((HashMap)payload.get("flightLegId")).get("flightNumber");
			String departureAirport =  (String)((HashMap)payload.get("flightLegId")).get("departureAirport");
			String arrivalAirport =  (String)((HashMap)payload.get("flightLegId")).get("arrivalAirport");
			String airlineCode =  (String)((HashMap)payload.get("flightLegId")).get("airlineCode");
			String flightDate =  (String)((HashMap)payload.get("flightLegId")).get("flightDate");
			
			String scheduleDepartureTime =  (String)((HashMap)payload.get("flightTimes")).get("scheduleDepartureTime");
			String scheduleArrivalTime =  (String)((HashMap)payload.get("flightTimes")).get("scheduleArrivalTime");
			String latestDepartureTime =  (String)((HashMap)payload.get("flightTimes")).get("latestDepartureTime");
			String latestArrivalTime =  (String)((HashMap)payload.get("flightTimes")).get("latestArrivalTime");	
			
			String registration =  (String)payload.get("registration");
			String status =  (String)payload.get("status");
			String serviceType =  (String)payload.get("serviceType");
			
			ProtoFlightLeg protoFlightLeg = ProtoFlightLeg.newBuilder()
				.setLegId(ProtoLegId.newBuilder().setId(Integer.parseInt(id)).setVersion(Integer.parseInt(version)))
				.setFlightLegId(ProtoFlightLegId.newBuilder().setFlightNumber(flightNumber)
						.setDepartureAirport(departureAirport)
						.setArrivalAirport(arrivalAirport)
						.setAirlineCode(airlineCode)
						.setFlightDate(ISODateTimeFormat.dateTimeParser().parseDateTime(flightDate).toString()))
				.setFlightTimes(ProtoFlightTimes.newBuilder()
						.setScheduleDepartureTime(ISODateTimeFormat.dateTimeParser().parseDateTime(scheduleDepartureTime).toString())
						.setScheduleArrivalTime(ISODateTimeFormat.dateTimeParser().parseDateTime(scheduleArrivalTime).toString())
						.setLatestDepartureTime(ISODateTimeFormat.dateTimeParser().parseDateTime(latestDepartureTime).toString())
						.setLatestArrivalTime(ISODateTimeFormat.dateTimeParser().parseDateTime(latestArrivalTime).toString()))
				.setRegistration(registration)
				.setStatus(status)
				.setServiceType(serviceType)
				.build();

			//logger.info("*** protoFlightLeg : " + protoFlightLeg);
			logger.info("protoFlightLeg jsonStr :\n" + JsonFormat.printer().print(protoFlightLeg));
			
			ProtoStatus protoStatus = stub.updateFlightLeg(protoFlightLeg);
			logger.info("protoStatus  :" + protoStatus);
			jsonStr = JsonFormat.printer().print(protoStatus);
			logger.info("protoStatus jsonStr :" + jsonStr);
		} catch (Exception ex) {
			jsonStr = "{ \"Error\":\"" + ex.getMessage() + "\"}";
			ex.printStackTrace();
		}
		if (channel != null) 
			channel.shutdown();
		return jsonStr;
	}
	
	
}
