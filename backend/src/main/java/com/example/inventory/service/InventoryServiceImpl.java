package com.example.inventory.service;

import com.example.inventory.Exception.EntityAlreadyExistsException;
import com.example.inventory.Exception.EntityNotFoundException;
import com.example.inventory.model.Device;
import com.example.inventory.model.ShelfDetailsVO;
import com.example.inventory.model.ShelfPositionVO;
import com.example.inventory.model.ShelfVO;
import com.example.inventory.repository.DeviceRepository;
import com.example.inventory.repository.ShelfPositionRepository;
import com.example.inventory.repository.ShelfRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    @Autowired
    private ShelfRepository shelfRepo;
    @Autowired
    private ShelfPositionRepository shelfPosRepo;
    @Autowired
    private DeviceRepository deviceRepo;

    public ShelfVO saveShelf(ShelfVO shelf) {
        Optional<ShelfVO> existingShelf = shelfRepo.findById(shelf.getId());

        if(existingShelf.isPresent()){
            logger.warn("Shelf with id {} already exists", shelf.getId());
            throw new EntityAlreadyExistsException("Shelf ID already exists. Choose a unique ID.");
        }

        logger.info("Saving new shelf: {}", shelf);
        ShelfVO savedShelf = shelfRepo.save(shelf);
        logger.info("Shelf saved successfully with ID: {}", savedShelf.getId());
        return savedShelf;
    }

    public ShelfPositionVO saveShelfPosition(ShelfPositionVO shelfPos) {
        Optional<ShelfPositionVO> existingShelfPosition = shelfPosRepo.findById(shelfPos.getId());

        if(existingShelfPosition.isPresent()){
            logger.warn("Shelf Position with id {} already exists", shelfPos.getId());
            throw new EntityAlreadyExistsException("Shelf Position ID already exists. Choose a unique ID.");
        }

        logger.info("Saving new shelf position: {}", shelfPos);
        ShelfPositionVO savedShelfPos = shelfPosRepo.save(shelfPos);
        logger.info("Shelf position saved successfully with ID: {}", savedShelfPos.getId());
        return savedShelfPos;
    }

    public List<ShelfVO> getShelf() {
        logger.info("Fetching all shelves from database");
        List<ShelfVO> shelves = (List<ShelfVO>) shelfRepo.findAll();
        logger.info("Total shelves fetched: {}", shelves.size());
        return shelves;
    }

    public List<ShelfPositionVO> getShelfPosition() {
        logger.info("Fetching all shelf positions from database");
        List<ShelfPositionVO> shelfPositions = (List<ShelfPositionVO>) shelfPosRepo.findAll();
        logger.info("Total shelf positions fetched: {}", shelfPositions.size());
        return shelfPositions;
    }

    public ShelfVO getShelfById(Long id) {
        logger.info("Fetching shelf with ID: {}", id);
        Optional<ShelfVO> shelf = shelfRepo.findById(id);
        if (shelf.isPresent()) {
            logger.info("Shelf found: {}", shelf.get());
            return shelf.get();
        } else {
            logger.warn("Shelf with ID {} not found", id);
            throw new EntityNotFoundException("Shelf with ID " + id + " not found");
        }
    }

    public ShelfPositionVO getShelfPositionById(Long id) {
        logger.info("Fetching shelf position with ID: {}", id);
        Optional<ShelfPositionVO> shelfPos = shelfPosRepo.findById(id);
        if (shelfPos.isPresent()) {
            logger.info("Shelf position found: {}", shelfPos.get());
            return shelfPos.get();
        } else {
            logger.warn("Shelf position with ID {} not found", id);
            throw new EntityNotFoundException("Shelf position with ID " + id + " not found");
        }
    }

    @Override
    public void addShelfPositionToDevice(Long deviceId, Long shelfPosId) {
        logger.info("Adding shelf position ID {} to device ID {}", shelfPosId, deviceId);

        if(!deviceRepo.findById(deviceId).isPresent()){
            logger.error("Device with ID {} not found", deviceId);
        } else if (!shelfPosRepo.findById(shelfPosId).isPresent()) {
            logger.error("Shelf position with ID {} not found", shelfPosId);
        }else{
            shelfPosRepo.setDevice(deviceId, shelfPosId);
        }

        logger.info("Shelf position added successfully to device");
    }

    @Override

    public void addShelfToShelfPosition(Long shelfPosId, Long shelfId) {
        logger.info("Received request to assign Shelf ID: {} to Shelf Position ID: {}", shelfId, shelfPosId);

        ShelfPositionVO shelfPos = shelfPosRepo.findById(shelfPosId)
                .orElseThrow(() -> {
                    logger.error("Shelf Position with ID {} not found", shelfPosId);
                    return new EntityNotFoundException("Shelf Position with ID " + shelfPosId + " not found");
                });

        if (shelfPos.getShelf() != null) {
            logger.warn("ShelfPosition ID {} is already assigned to Shelf ID {}", shelfPosId, shelfPos.getShelf().getId());
            throw new IllegalStateException("ShelfPosition ID " + shelfPosId + " is already assigned to another Shelf");
        }

        logger.info("Assigning ShelfPosition ID {} to Shelf ID {}", shelfPosId, shelfId);

        shelfPosRepo.setShelf(shelfId , shelfPosId);

        shelfPosRepo.save(shelfPos);

        logger.info("Successfully assigned Shelf ID {} to Shelf Position ID {}", shelfId, shelfPosId);
    }

    @Override
    public ShelfVO deleteShelf(Long shelfId){
        logger.info("Attempting to delete shelf with ID: {}", shelfId);
        Optional<ShelfVO> shelf = shelfRepo.findById(shelfId);

        if(shelf.isPresent()){
            shelfRepo.deleteById(shelfId);
            logger.info("Shelf deleted successfully with ID: {}", shelfId);
            return shelf.get();
        }else{
            logger.warn("Shelf with ID {} not found, deletion aborted", shelfId);
            return null;
        }
    }

    public ShelfPositionVO deleteShelfPosition(Long shelfposId){
        logger.info("Attempting to delete shelf position with ID: {}", shelfposId);
        Optional<ShelfPositionVO> shelfPos = shelfPosRepo.findById(shelfposId);

        if(shelfPos.isPresent()){
            shelfPosRepo.deleteById(shelfposId);
            logger.info("Shelf Position deleted successfully with ID: {}", shelfposId);
            return shelfPos.get();
        }else{
            logger.warn("Shelf with ID {} not found, deletion aborted", shelfposId);
            return null;
        }
    }

    @Override
    public ShelfDetailsVO getShelfDetails(Long shelfId){
        return shelfRepo.getShelfDetails(shelfId).orElse(null);
    }

}
