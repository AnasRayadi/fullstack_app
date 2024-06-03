package com.rayadi.backend.constants;

public final class ErrorMessagesConstant {
    private ErrorMessagesConstant(){
        // Constant class
    }

    public static final String BOOK_NOT_FOUND = "Book with id [%s] not found";
    public static final String CATEGORY_NOT_FOUND = "Category with id [%s] not found";
    public static final String AUTHOR_NOT_FOUND = "Author with id [%s] not found";
    public static final String AUTHOR_EXISTS = "Author with name [%s] already exists";
    public static final String BOOK_TITLE_EXISTS = "Book with title [%s] already exists";
    public static final String BOOK_TITLE_TAKEN = "Book title taken before";
    public static final String NO_CHANGE_DETECTED = "No change detected";
    public static final String INVALID_EDITION_DATE = "Invalid edition date";
    public static final String INVALID_BIRTH_DATE = "Invalid birth date";
}
