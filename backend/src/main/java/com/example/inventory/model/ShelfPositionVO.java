package com.example.inventory.model;


import lombok.Data;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Data
@Node
public class ShelfPositionVO {

    @Id
    private Long id;
    private String name;

    @Relationship(type = "HAS", direction = Relationship.Direction.OUTGOING)
    private ShelfVO shelf;


    public ShelfVO getShelf() {
        return shelf;
    }

}
