package com.example.inventory.model;


import lombok.Data;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node
public class ShelfVO {

    @Id
    private Long id;
    private String name;
    private String shelfType;
}
