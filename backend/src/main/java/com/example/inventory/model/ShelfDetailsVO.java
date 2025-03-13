package com.example.inventory.model;

import lombok.Data;

import javax.xml.transform.sax.SAXResult;

@Data
public class ShelfDetailsVO {
    private Long shelfId;
    private String shelfName;
    private String shelfType;
    private Long shelfPositionId;
    private String shelfPositionName;
    private Long deviceId;
    private String deviceName;
    private String deviceType;
}
