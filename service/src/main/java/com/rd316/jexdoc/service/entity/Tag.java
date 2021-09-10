package com.rd316.jexdoc.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Entity
public class Tag {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Document> documents;

    public Tag() {}

}
