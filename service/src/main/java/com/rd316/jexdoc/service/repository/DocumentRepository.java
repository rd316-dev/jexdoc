package com.rd316.jexdoc.service.repository;

import com.rd316.jexdoc.service.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
}