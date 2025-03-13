package com.example.inventory.service;

import com.example.inventory.model.Device;
import com.example.inventory.model.ShelfPositionVO;
import com.example.inventory.model.ShelfVO;

import java.util.List;

public interface DeviceService {
    Device saveDevice(Device device);
    List<Device> getDevice();
    Device deleteDevice(Long id);
    Device getDeviceById(Long id);
    Device updateDevice(Long id, Device device);
    void updateDeviceEntity(Device request, Device deviceEntity);

}
