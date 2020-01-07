package octava.dto;

import lombok.Data;

public @Data class StoredFileDto {

    private String path;

    private String url;

    private String fileName;
}
