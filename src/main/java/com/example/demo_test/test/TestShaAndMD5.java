package com.example.demo_test.test;

import com.example.demo_test.model.Student;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import static com.example.demo_test.Util.EncodeAndDecode.generateHash;

@Log4j2
public class TestShaAndMD5 {
    public static void main(String[] args) {
        Student studenInsert = Student
                .builder()
                .maSV(String.valueOf(1))
                .tenSV("Thanh")
                .khoa("CNTT")
                .diaChi("null_value")
                .ngayTao(String.valueOf(LocalDateTime.now()))
                .ngayCapNhat(null)
                .khoaHoc(17)
                .build();
        String password = "#define Int64 longlong";
        String md5Pass = generateHash(password, "MD5");
        String sha1Pass = generateHash(password, "SHA-1");
        String sha256Pass = generateHash(password, "SHA-256");
        System.out.println(md5Pass + " " + sha1Pass + " " + sha256Pass);
        String sha512Object = generateHash(studenInsert.toString(), "SHA-512");
        System.out.println(sha512Object);
    }

}
