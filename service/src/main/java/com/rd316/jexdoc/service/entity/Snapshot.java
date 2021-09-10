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

    @NotBlank
    private String hash;

    @NotNull
    private OffsetDateTime creationDateTime;

    @NotNull
    @JoinColumn(columnDefinition = "document_id", nullable = false)
    @ManyToOne
    private Document document;

    public Snapshot() {}

}
