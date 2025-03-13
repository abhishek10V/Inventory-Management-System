package com.example.inventory.model;

public class DeviceToShelfPosition {
    private Long deviceId;
    private Long shelfPositionId;

    public Long getDeviceId() {
        return deviceId;
    }

    public Long getShelfPositionId() {
        return shelfPositionId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public void setShelfPositionId(Long shelfPositionId) {
        this.shelfPositionId = shelfPositionId;
    }
}
