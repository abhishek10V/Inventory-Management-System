package com.example.inventory.service;

import com.example.inventory.model.Device;
import com.example.inventory.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceImplTests {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceServiceImpl deviceService;

    private Device device1;
    private Device device2;

    @BeforeEach
    void setUp() {
        device1 = new Device();
        device1.setId(1L);
        device1.setName("Device A");
        device1.setDeviceType("Type A");

        device2 = new Device();
        device2.setId(2L);
        device2.setName("Device B");
        device2.setDeviceType("Type B");
    }

    @Test
    void testSaveDevice() {
        when(deviceRepository.save(device1)).thenReturn(device1);
        Device result = deviceService.saveDevice(device1);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Device A", result.getName());
        verify(deviceRepository, times(1)).save(device1);
    }

    @Test
    void testGetDevice() {
        when(deviceRepository.findAll()).thenReturn(Arrays.asList(device1, device2));
        List<Device> devices = deviceService.getDevice();
        assertEquals(2, devices.size());
        assertEquals("Device A", devices.get(0).getName());
        verify(deviceRepository, times(1)).findAll();
    }

    @Test
    void testDeleteDevice_Success() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device1));
        Device deletedDevice = deviceService.deleteDevice(1L);
        assertNotNull(deletedDevice);
        assertEquals(1L, deletedDevice.getId());
        verify(deviceRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDevice_NotFound() {
        when(deviceRepository.findById(3L)).thenReturn(Optional.empty());
        Device deletedDevice = deviceService.deleteDevice(3L);
        assertNull(deletedDevice);
        verify(deviceRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetDeviceById_Found() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device1));
        Device foundDevice = deviceService.getDeviceById(1L);
        assertNotNull(foundDevice);
        assertEquals(1L, foundDevice.getId());
    }

    @Test
    void testGetDeviceById_NotFound() {
        when(deviceRepository.findById(5L)).thenReturn(Optional.empty());
        Device foundDevice = deviceService.getDeviceById(5L);
        assertNull(foundDevice);
    }

    @Test
    void testUpdateDevice_Success() {
        Device updatedDevice = new Device();
        updatedDevice.setName("Updated Device");
        updatedDevice.setDeviceType("Updated Type");

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device1));
        when(deviceRepository.save(any(Device.class))).thenReturn(device1);

        Device result = deviceService.updateDevice(1L, updatedDevice);
        assertNotNull(result);
        assertEquals("Updated Device", result.getName());
        assertEquals("Updated Type", result.getDeviceType());
        verify(deviceRepository, times(1)).save(device1);
    }

    @Test
    void testUpdateDevice_NotFound() {
        when(deviceRepository.findById(4L)).thenReturn(Optional.empty());
        Device updatedDevice = new Device();
        updatedDevice.setName("Updated Name");

        Device result = deviceService.updateDevice(4L, updatedDevice);
        assertNull(result);
        verify(deviceRepository, never()).save(any(Device.class));
    }
}