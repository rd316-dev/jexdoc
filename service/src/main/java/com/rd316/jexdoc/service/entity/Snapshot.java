package com.rd316.jexdoc.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Entity
public class Snapshot {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String path;
    private String hash;

    @NotNull
    private OffsetDateTime creationDateTime;

    private State state;

    enum State {
        STATE_LOADED,
        STATE_SNAPSHOT_SAVED,
        STATE_TRANSFERRED_TO_MAIN
    }

    @NotNull
    @JoinColumn(columnDefinition = "document_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Document document;

    public Snapshot() {
        creationDateTime = OffsetDateTime.now();
    }

    public Snapshot(UUID id) {
        this();
        this.id = id;
    }

    public Snapshot(String name, String path, String hash, OffsetDateTime creationDateTime) {
        this();
        this.name = name;
        this.path = path;
        this.hash = hash;
        this.creationDateTime = creationDateTime;
    }

}
