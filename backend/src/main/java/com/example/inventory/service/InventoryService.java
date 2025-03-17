package com.example.inventory.service;

import com.example.inventory.model.ShelfDetailsVO;
import com.example.inventory.model.ShelfPositionVO;
import com.example.inventory.model.ShelfVO;

import java.util.List;

public interface InventoryService {
    ShelfVO saveShelf(ShelfVO shelf);
    List<ShelfVO> getShelf();
    ShelfVO getShelfById(Long id);
    ShelfPositionVO saveShelfPosition(ShelfPositionVO shelfPos);
    List<ShelfPositionVO> getShelfPosition();
    ShelfPositionVO getShelfPositionById(Long id);
    void addShelfPositionToDevice(Long deviceId , Long shelfPositionId);
    void addShelfToShelfPosition(Long shelfPositionId, Long ShelfId);
    ShelfPositionVO deleteShelfPosition(Long id);
    ShelfVO deleteShelf(Long id);
    ShelfDetailsVO getShelfDetails(Long shelfId);
}
