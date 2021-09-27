package com.rd316.jexdoc.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Folder parent;

    @OneToMany(mappedBy = "parent")
    private List<Folder> subFolders;

    public Folder(String name) {
        this.name = name;
    }
}
