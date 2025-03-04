package com.example.inventory.service;

import com.example.inventory.Exception.EntityNotFoundException;
import com.example.inventory.model.Device;
import com.example.inventory.model.ShelfPositionVO;
import com.example.inventory.model.ShelfVO;
import com.example.inventory.repository.DeviceRepository;
import com.example.inventory.repository.ShelfPositionRepository;
import com.example.inventory.repository.ShelfRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTests {

    @Mock
    private ShelfRepository shelfRepo;

    @Mock
    private ShelfPositionRepository shelfPosRepo;

    @Mock
    private DeviceRepository deviceRepo;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private ShelfVO shelf;
    private ShelfPositionVO shelfPosition;
    private Device device;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        shelf = new ShelfVO();
        shelf.setId(1L);
        shelf.setName("Shelf 1");
        shelf.setShelfType("Type A");
        shelf.setShelfPositionId(2L);

        shelfPosition = new ShelfPositionVO();
        shelfPosition.setId(2L);
        shelfPosition.setName("Position 1");
        shelfPosition.setDeviceId(3L);

        device = new Device();
        device.setId(3L);
        device.setName("Device 1");
        device.setDeviceType("Type B");

        // Fix: Initialize shelf positions list to prevent NullPointerException
        device.setShelfPosition(new ArrayList<>());
    }

    @Test
    void testSaveShelf() {
        when(shelfRepo.save(any(ShelfVO.class))).thenReturn(shelf);
        ShelfVO savedShelf = inventoryService.saveShelf(shelf);
        assertNotNull(savedShelf);
        assertEquals(1L, savedShelf.getId());
        verify(shelfRepo, times(1)).save(shelf);
    }

    @Test
    void testSaveShelfPosition() {
        when(shelfPosRepo.save(any(ShelfPositionVO.class))).thenReturn(shelfPosition);
        ShelfPositionVO savedShelfPos = inventoryService.saveShelfPosition(shelfPosition);
        assertNotNull(savedShelfPos);
        assertEquals(2L, savedShelfPos.getId());
        verify(shelfPosRepo, times(1)).save(shelfPosition);
    }

    @Test
    void testGetShelf() {
        when(shelfRepo.findAll()).thenReturn(Arrays.asList(shelf));
        List<ShelfVO> shelves = inventoryService.getShelf();
        assertEquals(1, shelves.size());
        verify(shelfRepo, times(1)).findAll();
    }

    @Test
    void testGetShelfPosition() {
        when(shelfPosRepo.findAll()).thenReturn(Arrays.asList(shelfPosition));
        List<ShelfPositionVO> shelfPositions = inventoryService.getShelfPosition();
        assertEquals(1, shelfPositions.size());
        verify(shelfPosRepo, times(1)).findAll();
    }

    @Test
    void testAddShelfPositionToDevice_Success() {
        when(deviceRepo.findById(3L)).thenReturn(Optional.of(device));
        when(shelfPosRepo.findById(2L)).thenReturn(Optional.of(shelfPosition));

        inventoryService.addShelfPositionToDevice(3L, 2L);

        verify(deviceRepo, times(1)).findById(3L);
        verify(shelfPosRepo, times(1)).findById(2L);
        verify(shelfPosRepo, times(1)).save(shelfPosition);
        verify(deviceRepo, times(1)).save(device);
    }

    @Test
    void testAddShelfPositionToDevice_DeviceNotFound() {
        when(deviceRepo.findById(3L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> inventoryService.addShelfPositionToDevice(3L, 2L));

        verify(deviceRepo, times(1)).findById(3L);
        verify(shelfPosRepo, times(0)).findById(2L); // Should not be called
    }

    @Test
    void testAddShelfToShelfPosition_Success() {
        when(shelfRepo.findById(1L)).thenReturn(Optional.of(shelf));
        when(shelfPosRepo.findById(2L)).thenReturn(Optional.of(shelfPosition));

        inventoryService.addShelfToShelfPosition(2L, 1L);

        verify(shelfRepo, times(1)).findById(1L);
        verify(shelfPosRepo, times(1)).findById(2L);
        verify(shelfPosRepo, times(1)).save(shelfPosition);
    }

    @Test
    void testAddShelfToShelfPosition_ShelfNotFound() {
        when(shelfRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> inventoryService.addShelfToShelfPosition(2L, 1L));

        verify(shelfRepo, times(1)).findById(1L);
        verify(shelfPosRepo, times(0)).findById(2L); // Should not be called
    }

    @Test
    void testAddShelfToShelfPosition_PositionAlreadyAssigned() {
        shelfPosition.setShelf(shelf);

        when(shelfRepo.findById(1L)).thenReturn(Optional.of(shelf));
        when(shelfPosRepo.findById(2L)).thenReturn(Optional.of(shelfPosition));

        assertThrows(IllegalStateException.class, () -> inventoryService.addShelfToShelfPosition(2L, 1L));
        verify(shelfRepo, times(1)).findById(1L);
        verify(shelfPosRepo, times(1)).findById(2L);
    }
}