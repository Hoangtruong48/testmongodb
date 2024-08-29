package com.example.demo_test.exception;

/**
 * @author MinhDV
 */
public class GetException extends Exception {

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public GetException() {
        super("Có lỗi trong quá trình lấy dữ liệu. Vui lòng thử lại sau !");
    }
}
