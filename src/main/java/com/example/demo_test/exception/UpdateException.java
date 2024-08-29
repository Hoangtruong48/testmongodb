package com.example.demo_test.exception;

/**
 * @author MinhDV
 */
public class UpdateException extends Throwable {

    public UpdateException() {
        super("Có lỗi xảy ra trong quá trình cập nhật. Vui lòng thử lại sau !");
    }
}
