package com.example.inventory.controller;

import com.example.inventory.model.Device;
import com.example.inventory.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    // Get all devices
    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        List<Device> devices = deviceService.getDevice();
        return ResponseEntity.ok(devices);
    }

    // Get device by ID
    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable("id") Long id) {
        Device device = deviceService.getDeviceById(id);
        return ResponseEntity.ok(device);
    }

    // Create a new device
    @PostMapping
    public ResponseEntity<Device> saveDevice(@RequestBody Device device) {
        Device savedDevice = deviceService.saveDevice(device);
        return ResponseEntity.ok(savedDevice);
    }

    // Update an existing device
    @PatchMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable("id") Long id, @RequestBody Device device) {
        Device updatedDevice = deviceService.updateDevice(id, device);
        return ResponseEntity.ok(updatedDevice);
    }

    // Delete a device
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable("id") Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

}
