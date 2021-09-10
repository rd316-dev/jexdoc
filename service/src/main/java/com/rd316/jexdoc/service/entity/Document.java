package com.rd316.jexdoc.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
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

    private boolean canModify;

    @ManyToMany
    @JoinTable(name = "assigned_tag",
            joinColumns = @JoinColumn(columnDefinition = "document_id"),
            inverseJoinColumns = @JoinColumn(columnDefinition = "tag_id"))
    private ArrayList<Tag> tags;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Snapshot> snapshots;

    public Document() {}

}
