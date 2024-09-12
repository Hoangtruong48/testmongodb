package com.example.demo_test.repo;

import com.dts.tsdc.common.util.AppUtils;
import com.example.demo_test.Utility.MongoUtils;
import com.example.demo_test.config.DBField;
import com.example.demo_test.model.Student;

import com.mongodb.client.result.UpdateResult;
import com.netflix.config.WatchedUpdateResult;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ObjectUtils;

import java.lang.String;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomStudentRepoImpl implements CustomStudentRepo {
    MongoTemplate mongoTemplate;

    @Override
    public List<Student> findAllByTenSV(String tenSV) {
        String regex = ".*" + tenSV + ".*";
        Criteria criteria = Criteria.where("tenSV").regex(regex, "i");
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Student.class);
    }

    @Override
    public List<Student> findALlByKhoaAndOrderByTenSV(String khoa) {
        String regex = ".*" + khoa + ".*";
        Criteria criteria = Criteria.where("KHOA").regex(regex, "i");
        Query query = new Query(criteria);
        query.with(Sort.by(Sort.Direction.ASC, "TEN_SV"));
        return mongoTemplate.find(query, Student.class);
    }

    @Override
    public void add10000StudentToDB() {
        long cnt = mongoTemplate.count(new Query(), Student.class);
        for (int i = 0; i < 10000; i++){
            Student studenInsert = Student
                    .builder()
                    .maSV(String.valueOf(cnt))
                    .tenSV("Thanh")
                    .khoa("CNTT")
                    .diaChi("Thanh Xuan, Ha Noi, Viet Nam")
                    .ngayTao(String.valueOf(LocalDateTime.now()))
                    .ngayCapNhat(null)
                    .khoaHoc(17)
                    .build();
            cnt++;
            mongoTemplate.insert(studenInsert);
        }
    }

    @Override
    public List<Student> getStudentByKhoaPageable(String khoa, int start, int limit) {
        Criteria criteria = new Criteria();
        criteria.where("KHOA").is(khoa);
        Query query = new Query(criteria);
        if (!ObjectUtils.isEmpty(start) && !ObjectUtils.isEmpty(limit)){
            Pageable pageable = MongoUtils.getPageRequest(start, limit, Sort.Direction.DESC, "NGAY_TAO");
            query.with(pageable);
        }
        return mongoTemplate.find(query, Student.class);
    }

    @Override
    public long updateStudentByKhoaAndKhoaHoc(String khoa, Integer khoaHoc) {
        String now = String.valueOf(LocalDateTime.now());
        if (AppUtils.isNullOrEmpty(khoa)){
            khoa = "CNTT";
        }
        if (ObjectUtils.isEmpty(khoaHoc)){
            khoaHoc = 15;
        }

        Criteria criteria = new Criteria();
        criteria.and("KHOA").is(khoa).and("KHOA_HOC").is(khoaHoc);
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("NGAY_CAP_NHAT", now);
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Student.class);
        return updateResult.getModifiedCount();
    }


}
