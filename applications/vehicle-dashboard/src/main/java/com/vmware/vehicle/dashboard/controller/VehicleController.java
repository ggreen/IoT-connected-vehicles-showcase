package com.vmware.vehicle.dashboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmware.tanzu.data.IoT.vehicles.domains.Vehicle;
import com.vmware.vehicle.dashboard.repository.VehicleRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VehicleController {
    private final VehicleRepository repository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @CrossOrigin
    @RequestMapping("/updates")
    public void update(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/event-stream");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");

        var writer = response.getWriter();

        var vehicles = repository.findAll();
        for (Vehicle vehicle: vehicles)
        {
            log.info("vehicle: {}",vehicle);
            log.info("vehicle: {}",repository.findById(vehicle.getId()));
            var json =  objectMapper.writeValueAsString(vehicle);
            log.info("json: {}",json);

            writer.println("data: "+json+"\r\n");
        }
        writer.flush();
    }

    @PostMapping("/saveVehicle")
    public void saveVehicle(@RequestBody Vehicle vehicle) {
        repository.save(vehicle);
    }
}
