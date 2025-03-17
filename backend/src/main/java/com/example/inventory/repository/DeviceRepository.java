package com.example.inventory.repository;

import com.example.inventory.model.Device;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends Neo4jRepository<Device, Long> {
 @Query("""
         MATCH (d:Device {id : $deviceId})
         DETACH DELETE d
         """)
    Optional<Device> deleteDevice(@Param("deviceId") Long deviceId);
}
