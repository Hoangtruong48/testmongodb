package com.example.demo_test.Utility;

import org.bson.Document;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MinhDV
 */
public class MongoUtils {
    public static List<AggregationOperation> buildLimitOperation(Integer start, Integer limit) {
        List<AggregationOperation> list = new ArrayList<>();
        if (start == null || limit == null)
            return list;
        list.add(Aggregation.skip((long) start));
        list.add(Aggregation.limit(limit));
        return list;
    }

    private static int getPage(int start, int limit) {
        if (start <= 0)
            return 0;
        return start / limit;
    }

    public static Pageable getPageRequest(int start, int limit) {
        return PageRequest.of(getPage(start, limit), limit);
    }

    public static Pageable getPageRequest(int start, int limit, Sort.Direction direction, String... fieldSorts) {
        return PageRequest.of(getPage(start, limit), limit, direction, fieldSorts);
    }

    public static AggregationOptions buildAggregateOptionCollation(String lang) {
        Collation collation = Collation.of(lang);
        return AggregationOptions.builder().collation(collation).allowDiskUse(true).build();
    }

    public static AggregationOptions buildAggregateOptionCollationLang(String lang) {
        Collation collation = Collation.of(lang);
        return AggregationOptions.builder().collation(collation).build();
    }

    public static String getDocument(Document document, String key) {
        return document.getString(key);
    }

    public static void removeInfoDoc(Document document) {
        if (document == null)
            return;
        document.remove("_id");
        document.remove("delete");
        document.remove("_class");
        document.remove("CREATED_DATE");
        document.remove("CREATED_BY");
        document.remove("UPDATED_DATE");
    }
    
    public static void removeBasicInfo(Document document) {
        document.remove("CREATED_BY");
        document.remove("CREATED_DATE");
        document.remove("UPDATED_BY");
        document.remove("UPDATED_DATE");
        document.remove("delete");
        document.remove("_class");
        document.remove("_id");
    }

    public static Criteria buildListCriteria(List<Criteria> criteriaList) {
        if (criteriaList.isEmpty())
            return new Criteria();
        return new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
    }


}
