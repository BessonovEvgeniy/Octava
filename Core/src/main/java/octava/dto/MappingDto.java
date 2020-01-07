package octava.dto;

import lombok.Data;

import javax.persistence.Transient;

@Data
public class MappingDto {

    private String fullMapping;

    private String controllerMapping;

    @Transient
    private String methodMapping;

    private String controller;

    private String method;

    @Transient
    private String returnType;
}