package com.rd316.jexdoc.service.controller;

import com.rd316.jexdoc.service.service.DocumentService;
import com.rd316.jexdoc.service.transfer.DocumentTransfer;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping(produces = "application/json")
    public List<DocumentTransfer> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public DocumentTransfer getDocumentById(@PathVariable UUID id) {
        return documentService.getDocumentById(id);
    }

    @GetMapping(value = "/snapshots/{snapshotId}/contents")
    public ResponseEntity<Resource> getSnapshotFile(@PathVariable UUID snapshotId) {
        try {
            File file = documentService.getSnapshotFile(snapshotId);

            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .headers(headers)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();

            throw new RuntimeException("Unable to load file");
        }
    }

    @PostMapping(value = "/{id}/snapshots", consumes = "multipart/form-data")
    public UUID saveNewSnapshot(@PathVariable UUID id, @RequestParam MultipartFile file) {
        return documentService.saveNewSnapshot(id, file);
    }

    @PostMapping(consumes = "application/json")
    public UUID addDocument(@RequestBody DocumentTransfer document) {
        return documentService.addDocument(document);
    }

    @PutMapping(value = "/{id}",consumes = "application/json")
    public void updateDocument(@PathVariable UUID id, @RequestBody DocumentTransfer document) {
        documentService.updateDocument(id, document);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteDocument(@PathVariable UUID id) {
        documentService.deleteDocument(id);
    }

}
