package com.example.inventory.repository;

import com.example.inventory.model.ShelfVO;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelfRepository extends Neo4jRepository<ShelfVO, Long> {
}
