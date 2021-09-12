package com.rd316.jexdoc.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Tag {

    @Id
    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Document> documents;

    public Tag(String name) {
        this.name = name;
    }

}
