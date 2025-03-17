package com.example.inventory.service;

import com.example.inventory.Exception.EntityAlreadyExistsException;
import com.example.inventory.model.Device;
import com.example.inventory.model.ShelfPositionVO;
import com.example.inventory.repository.DeviceRepository;
import com.example.inventory.repository.ShelfPositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ShelfPositionRepository shelfPosRepo;


    public Device saveDevice(Device device) {
        logger.info("Saving new device: {}", device);

        Optional<Device> existingDevice = deviceRepository.findById(device.getId());
        if (existingDevice.isPresent()) {
            logger.warn("Device with ID {} already exists. Cannot save duplicate.", device.getId());
            throw new EntityAlreadyExistsException("Device ID already exists. Choose a unique ID.");
        }

        Device savedDevice = deviceRepository.save(device);
        logger.info("Device saved successfully with ID: {}", savedDevice.getId());
        return savedDevice;
    }

    
    public List<Device> getDevice() {
        logger.info("Fetching all devices from database");
        List<Device> devices = deviceRepository.findAll();
        logger.info("Total devices fetched: {}", devices.size());
        return devices;
    }

    public Device deleteDevice(Long id) {
        logger.info("Attempting to delete device with ID: {}", id);
        Optional<Device> device = deviceRepository.findById(id);

        if (device.isPresent()) {
            deviceRepository.deleteDevice(id);
            logger.info("Device deleted successfully with ID: {}", id);
            return device.get();
        } else {
            logger.warn("Device with ID {} not found, deletion aborted", id);
            return null;
        }
    }

    public Device getDeviceById(Long id) {
        logger.info("Fetching device with ID: {}", id);
        Optional<Device> device = deviceRepository.findById(id);
        if (device.isPresent()) {
            logger.info("Device found: {}", device.get());
            return device.get();
        } else {
            logger.warn("Device with ID {} not found", id);
            return null;
        }
    }

    public Device updateDevice(Long id, Device device) {
        logger.info("Attempting to update device with ID: {}", id);
        Optional<Device> deviceOpt = deviceRepository.findById(id);
        if (deviceOpt.isPresent()) {
            Device deviceEntity = deviceOpt.get();
            logger.info("Existing device data before update: {}", deviceEntity);
            updateDeviceEntity(device, deviceEntity);
            Device updatedDevice = deviceRepository.save(deviceEntity);
            logger.info("Device updated successfully: {}", updatedDevice);
            return updatedDevice;
        } else {
            logger.warn("Device with ID {} not found, update aborted", id);
            return null;
        }
    }

    public void updateDeviceEntity(Device request, Device deviceEntity) {
        logger.info("Updating device entity with new values");
        if (request.getName() != null && !request.getName().isEmpty()) {
            logger.info("Updating name from '{}' to '{}'", deviceEntity.getName(), request.getName());
            deviceEntity.setName(request.getName());
        }
        if (request.getDeviceType() != null && !request.getDeviceType().isEmpty()) {
            logger.info("Updating deviceType from '{}' to '{}'", deviceEntity.getDeviceType(), request.getDeviceType());
            deviceEntity.setDeviceType(request.getDeviceType());
        }
        logger.info("Device entity updated: {}", deviceEntity);
    }
}
