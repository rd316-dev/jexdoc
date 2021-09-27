package com.rd316.jexdoc.service.repository;

import com.rd316.jexdoc.service.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public interface FolderRepository extends JpaRepository<Folder, UUID> {
    boolean existsByNameAndParent(@NotBlank String name, Folder parent);

    Folder getByNameAndParent(@NotBlank String name, Folder parent);
}