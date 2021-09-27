package com.rd316.jexdoc.service.transfer;

import com.rd316.jexdoc.service.entity.Document;
import com.rd316.jexdoc.service.entity.Folder;
import com.rd316.jexdoc.service.entity.Snapshot;
import com.rd316.jexdoc.service.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class DocumentTransfer {

    private UUID id;

    private String name;
    private String path;

    private OffsetDateTime creationDateTime;
    private OffsetDateTime lastModificationDateTime;

    private boolean canModify;

    private final List<String> tags;
    private final List<UUID> snapshots;

    private FolderTransfer folder;

    public DocumentTransfer() {
        tags = new ArrayList<>();
        snapshots = new ArrayList<>();
        canModify = true;
        folder = new FolderTransfer("_default");
    }

    public DocumentTransfer(Document document) {
        this();
        this.id = document.getId();
        this.name = document.getName();
        this.path = document.getPath();

        this.creationDateTime = document.getCreationDateTime();
        this.lastModificationDateTime = document.getLastModificationDateTime();

        this.canModify = document.isCanModify();
        document.getSnapshots().forEach(snapshot -> snapshots.add(snapshot.getId()));

        if (document.getFolder() != null)
            this.folder = new FolderTransfer(document.getFolder());
    }

    public Document getEntity() {
        Document document = new Document();
        document.setId(id);
        document.setName(name);
        document.setPath(path);

        document.setCanModify(canModify);

        document.setCreationDateTime(creationDateTime);
        document.setLastModificationDateTime(lastModificationDateTime);

        List<Tag> tags = new ArrayList<>();
        this.tags.forEach(tag -> tags.add(new Tag(tag)));
        document.setTags(tags);

        document.setFolder(folder.getEntity());

        List<Snapshot> snapshots = new ArrayList<>();
        this.snapshots.forEach(id -> snapshots.add(new Snapshot(id)));
        document.setSnapshots(snapshots);

        return document;
    }

}
