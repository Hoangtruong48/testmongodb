package com.example.demo_test.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "students")
public class Student implements Serializable {
    @Id
    @Field("id")
    String id;
    @Field("MA_SINH_VIEN")
    @JsonProperty("MA_SINH_VIEN")
    String maSV;
    @Field("TEN_SV")
    @JsonProperty("TEN_SV")
    String tenSV;
    @Field("KHOA")
    @JsonProperty("KHOA")
    String khoa;
    @Field("KHOA_HOC")
    @JsonProperty("KHOA_HOC")
    Integer khoaHoc;
    @Field("DIA_CHI")
    @JsonProperty("DIA_CHI")
    String diaChi;
    @Field("NGAY_TAO")
    @JsonProperty("NGAY_TAO")
    String ngayTao;
    @Field("NGAY_CAP_NHAT")
    @JsonProperty("NGAY_CAP_NHAT")
    String ngayCapNhat;
}
