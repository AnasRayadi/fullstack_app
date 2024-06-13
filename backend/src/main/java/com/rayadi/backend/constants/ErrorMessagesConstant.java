package com.rayadi.backend.constants;

public class ErrorMessagesConstant {
    private ErrorMessagesConstant() {
        // Constant class
    }
    public static final String TITLE_REQUIRED = "Title is required";
    public static final String AUTHOR_REQUIRED = "Author is required";
    public static final String DESCRIPTION_REQUIRED = "Description is required";
    public static final String DESCRIPTION_MIN_LENGTH = "Description must be at least 10 characters long";
    public static final String IMAGE_REQUIRED = "Image is required";
    public static final String IMAGE_VALID_URL = "Image must be a valid URL ending with .png or .jpg";
    public static final String EDITION_REQUIRED = "Edition is required";
    public static final String CATEGORY_ID_REQUIRED = "Category ID is required";
    public static final String PRICE = "Price is required";

}
