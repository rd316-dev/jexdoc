package com.rd316.jexdoc.service.repository;

import com.rd316.jexdoc.service.entity.Snapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SnapshotRepository extends JpaRepository<Snapshot, UUID> {
}