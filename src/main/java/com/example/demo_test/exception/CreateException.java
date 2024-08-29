package com.example.demo_test.exception;

/**
 * @author MinhDV
 */
public class CreateException extends Exception {

    public CreateException() {
        super("Có lỗi xảy ra trong quá trình tạo. Vui lòng thử lại sau !");
    }
}
