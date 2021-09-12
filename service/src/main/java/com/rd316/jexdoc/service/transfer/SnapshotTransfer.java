package com.rd316.jexdoc.service.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.OffsetDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SnapshotTransfer {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String path;
    private String hash;

    private OffsetDateTime creationDateTime;

}
