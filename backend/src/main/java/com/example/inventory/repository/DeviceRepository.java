package com.example.inventory.repository;

import com.example.inventory.model.Device;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends Neo4jRepository<Device, Long> {
}
