package com.example.inventory.model;

public class ShelfPositionToShelf {
    private Long shelfId;
    private Long shelfPositionId;

    public Long getShelfId() {
        return this.shelfId;
    }

    public Long getShelfPositionId() {
        return this.shelfPositionId;
    }

    public void setShelfId(Long shelfId) {
        this.shelfId = shelfId;
    }

    public void setShelfPosId(Long shelfPositionId) {
        this.shelfPositionId = shelfPositionId;
    }
}
