package com.example.inventory.repository;

import com.example.inventory.model.ShelfDetailsVO;
import com.example.inventory.model.ShelfVO;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShelfRepository extends Neo4jRepository<ShelfVO, Long> {
    @Query("""
             MATCH (s:ShelfVO {id : $shelfId})
             OPTIONAL MATCH (sp:ShelfPositionVO)-[:HAS]->(s)
             OPTIONAL MATCH (d:Device)-[:HAS]->(sp)
             RETURN s.id as shelfId, s.name as shelfName, s.shelfType as shelfType, sp.id as shelfPositionId, sp.name as shelfPositionName, d.id as deviceId, d.name as deviceName, d.deviceType as deviceType
             """)
    Optional<ShelfDetailsVO> getShelfDetails(@Param("shelfId")Long shelfId);
}
