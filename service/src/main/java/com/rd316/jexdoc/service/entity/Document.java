package com.rd316.jexdoc.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Document {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    private String name;
    private String path;

    @NotNull
    private OffsetDateTime creationDateTime;

    @NotNull
    private OffsetDateTime lastModificationDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "folder_id", nullable = false)
    private Folder folder;

    private boolean canModify;

    @ManyToMany
    @JoinTable(name = "assigned_tag",
            joinColumns = @JoinColumn(columnDefinition = "document_id"),
            inverseJoinColumns = @JoinColumn(columnDefinition = "tag_id"))
    private List<Tag> tags;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Snapshot> snapshots;

}
