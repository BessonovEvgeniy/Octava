package model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "STORED_FILES")
public @Data class StoredFileModel extends BaseModel {

    private String path;

    private String url;

    private String fileName;
}
