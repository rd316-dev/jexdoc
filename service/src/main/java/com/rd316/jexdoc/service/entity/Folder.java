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
public class Folder {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    private String name;

    @JoinColumn(columnDefinition = "parent_id")
    @ManyToOne
    private Folder parent;

    @OneToMany(mappedBy = "parent")
    private List<Folder> subFolders;

    public Folder() {}

}
