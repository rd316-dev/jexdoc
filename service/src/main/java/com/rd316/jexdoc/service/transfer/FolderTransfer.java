package com.rd316.jexdoc.service.transfer;

import com.rd316.jexdoc.service.entity.Folder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FolderTransfer {

    private UUID id;
    private String name;

    private String parentName;
    private UUID parentId;

    public FolderTransfer(String name) {
        this.name = name;
    }

    public FolderTransfer(Folder folder) {
        id = folder.getId();
        name = folder.getName();

        if (folder.getParent() != null) {
            parentName = folder.getParent().getName();
            parentId = folder.getParent().getId();
        }
    }

    public Folder getEntity() {
        Folder folder = new Folder();
        folder.setId(id);
        folder.setName(name);

        Folder parent = new Folder();
        parent.setName(parentName);
        parent.setId(parentId);

        folder.setParent(parent);

        return folder;
    }
}
