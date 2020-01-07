package octava.dto;

import lombok.Data;

public @Data class RinexFileDto {

    private String name;

    private String status;

    private StoredFileDto storedFile;
}
