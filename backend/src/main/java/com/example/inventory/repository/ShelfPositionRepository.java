package com.example.inventory.repository;

import com.example.inventory.model.ShelfPositionVO;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelfPositionRepository extends Neo4jRepository<ShelfPositionVO , Long> {
    @Query("""
            MATCH(d:Device {id : $deviceId})
            MATCH (s:ShelfPositionVO {id : $spId})
            MERGE (d)-[:HAS]->(s)
            """)
    void setDevice(@Param("deviceId") Long deviceId , @Param("spId") Long spId);

    @Query("""
            MATCH(s:ShelfVO {id:$shelfId})
            MATCH(sp:ShelfPositionVO {id:$spId})
            MERGE (sp)-[:HAS]->(s)
            """)
    void setShelf(@Param("shelfId") Long shelfId, @Param("spId") Long spId);
}
