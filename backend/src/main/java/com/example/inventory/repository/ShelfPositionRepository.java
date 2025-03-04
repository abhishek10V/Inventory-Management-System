package com.example.inventory.repository;

import com.example.inventory.model.ShelfPositionVO;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelfPositionRepository extends Neo4jRepository<ShelfPositionVO , Long> {
}
