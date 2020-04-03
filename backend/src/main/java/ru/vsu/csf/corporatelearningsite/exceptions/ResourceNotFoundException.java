package ru.vsu.csf.corporatelearningsite.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public static final String RESOURCE_NOT_FOUND_WITH_EXCEPTION_MESSAGE = "%s not found with %s: '%s'";
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format(RESOURCE_NOT_FOUND_WITH_EXCEPTION_MESSAGE, resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
