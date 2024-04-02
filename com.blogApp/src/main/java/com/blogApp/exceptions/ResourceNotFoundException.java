package com.blogApp.exceptions;

import lombok.*;

@Setter
@Getter
public class ResourceNotFoundException  extends RuntimeException{

    String resourceName;
    String fieldName;
    long fieldValue;
    String name;
    public ResourceNotFoundException(String resourceName,String fieldName,long fieldValue){

        super(String.format("%s not found with %s : %1",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    public ResourceNotFoundException(String resourceName,String fieldName,String name){

        super(String.format("%s not found with %s : %1",resourceName,fieldName,name));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.name=name;
    }
}
