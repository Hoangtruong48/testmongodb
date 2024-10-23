package com.example.demo_test.Util;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class EncodeAndDecode {
    private static final char[] normalChars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',
            'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final Logger log = LoggerFactory.getLogger(EncodeAndDecode.class);

    public static String customEncodeMaBaoMat(String maBaoMat) {
        if (Strings.isNullOrEmpty(maBaoMat)) {
            return maBaoMat;
        }
        int step = new Random().nextInt(7) + 2;
        maBaoMat = Base64.getEncoder().encodeToString(maBaoMat.getBytes());
        for (int idx = 0; idx < step; idx++) {
            maBaoMat = genMaBaoMat(step) + Base64.getEncoder().encodeToString(maBaoMat.getBytes());
        }
        return step + maBaoMat;
    }
    public static String genMaBaoMat(int length) {
        int normalCharsLength = normalChars.length;
        Random rand = new Random();
        StringBuilder maBaoMat = new StringBuilder();
        for (int i = 0; i < length; i++) {
            maBaoMat.append(normalChars[rand.nextInt(normalCharsLength)]);
        }
        return maBaoMat.toString();
    }

    public static String decodeMaBaoMat(String maBaoMatEncode) {
        if (!Strings.isNullOrEmpty(maBaoMatEncode)) {
            if (maBaoMatEncode.length() == 6) {
                return maBaoMatEncode;
            }
        }
        return customDecodeMaBaoMat(maBaoMatEncode);
//        return maBaoMatEncode;
    }
    public static String customDecodeMaBaoMat(String encodedMaBaoMat) {
        try {
            if (Strings.isNullOrEmpty(encodedMaBaoMat)) {
                return encodedMaBaoMat;
            }
            int step = Character.getNumericValue(encodedMaBaoMat.charAt(0));
            String encoded = encodedMaBaoMat.substring(1);
            for (int idx = 0; idx < step; idx++) {
                encoded = new String(Base64.getDecoder().decode(encoded.substring(step)));
            }
            return new String(Base64.getDecoder().decode(encoded));
        } catch (Exception e) {
            return null;
        }
    }
    public static String generateHash(String input, String algo){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance(algo);
            byte[] hashInBytes = messageDigest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex){
            log.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    public static void main(String[] args) {
        System.out.println(decodeMaBaoMat("1jTVRJek5EVTI="));
    }
}
