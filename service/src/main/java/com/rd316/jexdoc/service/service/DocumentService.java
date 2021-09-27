package com.rd316.jexdoc.service.service;

import com.rd316.jexdoc.service.entity.Document;
import com.rd316.jexdoc.service.entity.Folder;
import com.rd316.jexdoc.service.entity.Snapshot;
import com.rd316.jexdoc.service.repository.DocumentRepository;
import com.rd316.jexdoc.service.repository.FolderRepository;
import com.rd316.jexdoc.service.repository.SnapshotRepository;
import com.rd316.jexdoc.service.transfer.DocumentTransfer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DocumentService {

    private String basePath;

    private final DocumentRepository documentRepository;
    private final FolderRepository folderRepository;
    private final SnapshotRepository snapshotRepository;

    public DocumentService(DocumentRepository documentRepository,
                           FolderRepository folderRepository,
                           SnapshotRepository snapshotRepository) {
        this.documentRepository = documentRepository;
        this.folderRepository = folderRepository;
        this.snapshotRepository = snapshotRepository;

        if (!folderRepository.existsByNameAndParent("_default", null))
            folderRepository.save(new Folder("_default"));

        if (basePath == null || basePath.isBlank()) {
            basePath = "files" + File.separator;
        }

    }

    public List<DocumentTransfer> getAllDocuments() {
        List<DocumentTransfer> documents = new ArrayList<>();
        documentRepository.findAll().forEach(document -> documents.add(new DocumentTransfer(document)));

        return documents;
    }

    public DocumentTransfer getDocumentById(UUID id) {
        return new DocumentTransfer(documentRepository.getById(id));
    }

    public File getSnapshotFile(UUID snapshotId) throws FileNotFoundException {
        Snapshot snapshot = snapshotRepository.getById(snapshotId);

        return new File(snapshot.getPath());
    }

    public UUID saveNewSnapshot(UUID documentId, MultipartFile file) {
        try {
            Document document = documentRepository.getById(documentId);

            Folder folder = document.getFolder();
            StringBuilder pathStringBuilder = new StringBuilder();

            while (folder != null && !folder.getName().equals("_default") && folder.getParent() != null) {
                pathStringBuilder.insert(0, folder.getName());
                pathStringBuilder.insert(0, File.separator);

                folder = folder.getParent();
            }

            String[] orig = file.getOriginalFilename() == null ?
                    file.getName().split("\\.") : file.getOriginalFilename().split("\\.");
            String extension = orig[orig.length - 1];

            String path = basePath + pathStringBuilder + document.getName() + '.' + extension;
            Snapshot snapshot = new Snapshot(file.getName(), path, "", OffsetDateTime.now());

            snapshot.setDocument(document);
            UUID snapshotId = snapshotRepository.save(snapshot).getId();

            file.transferTo(Paths.get(path));
            document.setLastModificationDateTime(OffsetDateTime.now());

            return snapshotId;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to save file");
        }
    }

    public UUID addDocument(DocumentTransfer document) {
        Document entity = document.getEntity();

        entity.setCreationDateTime(OffsetDateTime.now());
        entity.setLastModificationDateTime(OffsetDateTime.now());

        entity.setFolder(folderRepository.getByNameAndParent("_default", null));

        UUID id = documentRepository.save(entity).getId();
        document.setId(id);

        return id;
    }

    public void updateDocument(UUID id, DocumentTransfer document) {
        if (documentRepository.getById(id).isCanModify()) {
            document.setId(id);
            documentRepository.save(document.getEntity());
        } else {
            throw new IllegalArgumentException("Document can't be edited");
        }
    }

    public void deleteDocument(UUID id) {
        documentRepository.deleteById(id);
    }

}
