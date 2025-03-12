package iot.connected.vehicle.server;

import iot.connected.vehicle.server.service.SelfDrivingService;
import iot.connected.vehicles.engine.VehicleEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
public class DrivingConfig {

    @Bean
    VehicleEngine vehicleEngine(@Value("${vehicle.distanceIncrements}") Double distanceIncrements,
                                @Value("${vehicle.vin}") String vin,
                                @Value("${position.latitude}") double latitude,
                                @Value("${position.longitude}") double longitude)
    {
        return new VehicleEngine(distanceIncrements,vin,latitude,longitude);
    }
    @Bean
    Thread drivingThread(SelfDrivingService service){
        return new Thread(service);
    }


    @Bean("reactiveThreadFactory")
    public ThreadFactory threadFactory() {
        return Executors.defaultThreadFactory();
    }

}
