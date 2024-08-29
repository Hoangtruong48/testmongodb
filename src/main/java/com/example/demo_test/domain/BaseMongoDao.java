package com.example.demo_test.domain;

import com.dts.tsdc.common.domain.document.BaseEntity;
import com.dts.tsdc.common.domain.document.tudien.Counters;
import com.dts.tsdc.common.util.AppUtils;
import com.example.demo_test.Utility.MongoUtils;
import com.example.demo_test.config.DBField;
import com.example.demo_test.exception.CreateException;
import com.example.demo_test.exception.DeleteException;
import com.example.demo_test.exception.GetException;
import com.example.demo_test.exception.UpdateException;
import com.mongodb.MongoClient;
import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.CloseableIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BaseMongoDao<T extends BaseEntity> extends BaseDao<T> {
    protected MongoTemplate mongoTemplate;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private MongoClient mongoClient = null;
    private static final Object lock = new Object();
    public BaseMongoDao(String tableName, Class<T> clazz) {
        super(tableName, clazz);
    }
    public <T> void saveAll(List<T> entities) throws UpdateException {
        try {
            for (Object o : entities) {
                mongoTemplate.save(o, TABLE_NAME);
            }
        } catch (Throwable throwable) {
            throw new UpdateException();
        }
    }

    @Override
    public List findAll() throws GetException {
        try {
            return mongoTemplate.find(new Query(), clazz);
        } catch (Throwable throwable) {
            throw new GetException();
        }
    }

    @Override
    public T insert(T entity) throws CreateException {
        try {
            return mongoTemplate.insert(entity, TABLE_NAME);
        } catch (Throwable throwable) {
            throw new CreateException();
        }
    }

    @Override
    public <T> void insertAll(List<T> entities) throws CreateException {
        try {
            mongoTemplate.insertAll(entities);
        } catch (Throwable throwable) {
            throw new CreateException();
        }
    }

    @Override
    public void remove(T entity) throws DeleteException {
        try {
            mongoTemplate.remove(entity, TABLE_NAME);
        } catch (Throwable throwable) {
            throw new DeleteException();
        }
    }

    @Override
    public void removeAll(String[] ids) throws DeleteException {
        try {
            List<String> listIds = Arrays.asList(ids);
            List<ObjectId> objectIds = listIds.stream().map(BaseMongoDao::convertStringToObjectId).collect(Collectors.toList());
            mongoTemplate.remove(new Query(Criteria.where("_id").in(objectIds)), clazz);
        } catch (Throwable throwable) {
            throw new DeleteException();
        }
    }

    @Override
    public void removeAll(List<T> entities) throws DeleteException {
        try {
            List<ObjectId> objectIds = entities.stream().map(BaseMongoDao::convertEntityToObjectId).collect(Collectors.toList());
            mongoTemplate.remove(new Query(Criteria.where("_id").in(objectIds)), clazz);
        } catch (Throwable throwable) {
            throw new DeleteException();
        }
    }

    @Override
    public void removeAllByIds(List<String> ids) throws DeleteException {
        try {
            List<ObjectId> objectIds = ids.stream().map(BaseMongoDao::convertStringToObjectId).collect(Collectors.toList());
            mongoTemplate.remove(new Query(Criteria.where("_id").in(objectIds)), clazz);
        } catch (Throwable throwable) {
            throw new DeleteException();
        }
    }

    @Override
    public long removeAllByIds(List<String> ids, String collectionName) throws DeleteException {
        try {
            List<ObjectId> objectIds = ids.stream().map(BaseMongoDao::convertStringToObjectId).collect(Collectors.toList());
            DeleteResult deleteResult = mongoTemplate.remove(new Query(Criteria.where("_id").in(objectIds)), collectionName);
            return deleteResult.getDeletedCount();
        } catch (Throwable throwable) {
            throw new DeleteException();
        }
    }

    @Override
    public T save(T entity) throws UpdateException {
        try {
            return mongoTemplate.save(entity, TABLE_NAME);
        } catch (Throwable throwable) {
            throw new UpdateException();
        }
    }

    @Override
    public T findById(String id) throws GetException {
        try {
            return mongoTemplate.findOne(new Query(Criteria.where("_id").is(new ObjectId(id))), clazz);
        } catch (Throwable throwable) {
            throw new GetException();
        }
    }

    @Override
    public boolean existsById(String id) throws GetException {
        try {
            return mongoTemplate.exists(new Query(Criteria.where("_id").is(new ObjectId(id))), clazz);
        } catch (Throwable throwable) {
            throw new GetException();
        }
    }

    @Override
    public List findAllById(List<String> ids) throws GetException {
        try {
            List<ObjectId> objectIds = ids.stream().map(BaseMongoDao::convertStringToObjectId).collect(Collectors.toList());
            return mongoTemplate.find(new Query(Criteria.where("_id").in(objectIds)), clazz);
        } catch (Throwable throwable) {
            throw new GetException();
        }
    }

    @Override
    public long count() throws GetException {
        try {
            return mongoTemplate.count(new Query(), TABLE_NAME);
        } catch (Throwable throwable) {
            throw new GetException();
        }
    }

    @Override
    public void deleteById(String id) throws DeleteException {
        try {
            mongoTemplate.remove(new Query(Criteria.where("_id").is(new ObjectId(id))), TABLE_NAME);
        } catch (Throwable throwable) {
            throw new DeleteException();
        }
    }

    @Override
    public void delete(T entity) throws DeleteException {
        try {
            mongoTemplate.remove(entity);
        } catch (Throwable throwable) {
            throw new DeleteException();
        }
    }

    @Override
    public void deleteAll() throws DeleteException {
        try {
            mongoTemplate.remove(new Query(), TABLE_NAME);
        } catch (Throwable throwable) {
            throw new DeleteException();
        }
    }

    @Override
    public long getSequenceId(String collectionName) {
        synchronized (lock) {

            Counters metaData = mongoTemplate.findOne(new Query(Criteria.where(DBField.NAME.toLowerCase()).is(collectionName)), Counters.class);
            long nextSequence = 1;
            if (metaData != null) {
                nextSequence = metaData.getIdSeq() + 1;

            } else {
                metaData = new Counters();
                metaData.setName(collectionName);
            }
            metaData.setIdSeq(nextSequence);
            mongoTemplate.save(metaData);
            return nextSequence;
        }
    }

    @Override
    public void saveSequenceId(String collectionName, long sequence) {
        synchronized (lock) {

            Counters metaData = mongoTemplate.findOne(new Query(Criteria.where(DBField.NAME.toLowerCase()).is(collectionName)), Counters.class);
            if (metaData == null) {
                metaData = new Counters();
                metaData.setName(collectionName);
            }
            metaData.setIdSeq(sequence);
            mongoTemplate.save(metaData);
        }
    }

    @Override
    public <K> List<K> findListDocument(Class<K> entityClass, String collectionName) {
        return mongoTemplate.find(new Query(), entityClass, collectionName);
    }

    @Override
    public <K> List<K> findListDocument(Class<K> entityClass, String collectionName, PageQuery pageQuery) {

        Query query = new Query();
        createPageQuery(pageQuery, query);
        return mongoTemplate.find(query, entityClass, collectionName);
    }

    @Override
    public <K> List<K> findListDocumentWithProjectFields(Class<K> entityClass, String collectionName, PageQuery pageQuery, String... projectFields) throws GetException {
        return getListWithProjection(null, pageQuery, collectionName, entityClass, projectFields);
    }

    private void createPageQuery(PageQuery pageQuery, Query query) {
        if (pageQuery != null) {
            if (pageQuery.getStart() != null && pageQuery.getLimit() != null) {
                Pageable pageable = MongoUtils.getPageRequest(pageQuery.getStart(), pageQuery.getLimit(),
                        pageQuery.getSortType() == BaseDao.ASC ? Sort.Direction.ASC : Sort.Direction.DESC, pageQuery.getSortField());
                query.with(pageable);
            } else {
                query.with(Sort.by(pageQuery.getSortType() == BaseDao.ASC ? Sort.Direction.ASC : Sort.Direction.DESC, pageQuery.getSortField()));
            }
        }
    }

    @Override
    public <K> K findDocument(String field, Object value, Class<K> entityClass, String collectionName,
                              String... projectFields) throws GetException {
        try {
            Criteria criteria = Criteria.where(field).is(value);
            if (projectFields == null || projectFields.length == 0) {
                Query query = new Query(criteria);
                return mongoTemplate.findOne(query, entityClass);
            } else {
                return findDoc(criteria, entityClass, collectionName, projectFields);
            }
        } catch (Throwable throwable) {
            throw new GetException();
        }
    }


    private <K> K findDoc(Criteria criteria, Class<K> entityClass, String collectionName,
                          String... projectFields) throws GetException {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setStart(0);
        pageQuery.setLimit(1);
        List<K> list = getListWithProjection(criteria, pageQuery, collectionName, entityClass, projectFields);
        return !AppUtils.isListNullOrEmpty(list) ? list.get(0) : null;
    }

    protected Criteria buildCriteriaGet(String[] fields, Object[] values) {
        Criteria criteria = new Criteria();
        if (fields.length == values.length) {
            for (int i = 0; i < fields.length; i++) {
                if (values[i] != null)
                    criteria.and(fields[i]).is(values[i]);
            }
        }
        return criteria;
    }

    @Override
    public <K> K findDocument(String[] fields, Object[] values, Class<K> entityClass,
                              String collectionName,
                              String... projectFields) throws GetException {
        try {
            Criteria criteria = buildCriteriaGet(fields, values);
            if (projectFields != null && projectFields.length > 0) {
                return findDoc(criteria, entityClass, collectionName, projectFields);
            } else {
                Query query = new Query(criteria);
                return mongoTemplate.findOne(query, entityClass);
            }
        } catch (Throwable throwable) {
            throw new GetException();
        }
    }

    @Override
    public <K> List<K> findListDocument(String[] fields, Object[] values, Class<K> entityClass,
                                        String collectionName,
                                        PageQuery pageQuery,
                                        String... projectFields) throws GetException {

        try {
            Criteria criteria = buildCriteriaGet(fields, values);
            if (projectFields != null && projectFields.length > 0) {
                return getListWithProjection(criteria, pageQuery, collectionName, entityClass, projectFields);
            } else {
                Query query = new Query(criteria);
                return mongoTemplate.find(query, entityClass, collectionName);
            }
        } catch (Throwable throwable) {
            throw new GetException();
        }
    }


    @Override
    public <K> List<K> findListDocumentWithExistsField(String[] fields, Object[] values, Class<K> entityClass, String collectionName, String... existsField) throws GetException {
        try {
            Criteria criteria = buildCriteriaGet(fields, values);

            if (existsField != null && existsField.length > 0) {
                for (String field : existsField) {
                    criteria.and(field).exists(true);
                }
            }
            Query query = new Query(criteria);
            return mongoTemplate.find(query, entityClass, collectionName);
        } catch (Throwable throwable) {
            throw new GetException();
        }
    }

    @Override
    public <K> List<K> findListDocument(String field, Object value, Class<K> entityClass,
                                        String collectionName, PageQuery pageQuery, String... projectFields) throws GetException {
        try {
            Criteria criteria = Criteria.where(field).is(value);
            if (projectFields != null && projectFields.length > 0) {
                return getListWithProjection(criteria, pageQuery, collectionName, entityClass, projectFields);
            } else {
                Query query = new Query(criteria);
                createPageQuery(pageQuery, query);
                return mongoTemplate.find(query, entityClass, collectionName);
            }
        } catch (Throwable throwable) {
            throw new GetException();
        }
    }


    @Override
    public long countListDocument(String[] fields, Object[] values, String collectionName) throws GetException {
        try {
            Query query = new Query(buildCriteriaGet(fields, values));
            return mongoTemplate.count(query, collectionName);
        } catch (Throwable throwable) {
            throw new GetException();
        }
    }

    @Override
    public boolean isExistDocument(String[] fields, Object[] values, String collectionName) throws GetException {
        try {
            Query query = new Query(buildCriteriaGet(fields, values));
            return mongoTemplate.exists(query, collectionName);
        } catch (Throwable throwable) {
            throw new GetException();
        }
    }

    @Override
    public void saveDocument(Object entity, String collectionName) throws UpdateException {
        try {
            mongoTemplate.save(entity, collectionName);
        } catch (Throwable error) {
            throw new UpdateException();
        }
    }

    @Override
    public void insertDocument(Object entity, String collectionName) throws CreateException {
        try {
            mongoTemplate.insert(entity, collectionName);
        } catch (Throwable error) {
            throw new CreateException();
        }
    }

    @Override
    public long deleteAllByField(String[] fields, Object[] values, String collectionName) throws DeleteException {
        try {
            Query query = new Query(buildCriteriaGet(fields, values));
            return mongoTemplate.remove(query, collectionName).getDeletedCount();
        } catch (Throwable throwable) {
            throw new DeleteException();
        }
    }

    @Override
    public long deleteAllByField(String field, Object value, String collectionName) throws DeleteException {
        try {
            Query query = new Query(Criteria.where(field).is(value));
            return mongoTemplate.remove(query, collectionName).getDeletedCount();
        } catch (Throwable throwable) {
            throw new DeleteException();
        }
    }

    @Override
    public <K> K findDistinceDocument(String[] fields, Object[] values, Class<K> entityClass, String collectionName,
                                      String fieldDistince, String... projectFields) throws GetException {
        try {
            Criteria criteria = buildCriteriaGet(fields, values);

            List<K> list;
            if (projectFields != null && projectFields.length > 0) {
                PageQuery pageQuery = new PageQuery();
                pageQuery.setStart(0);
                pageQuery.setLimit(1);
                pageQuery.setSortType(BaseDao.DESC);
                if (fieldDistince != null)
                    pageQuery.setSortField(fieldDistince);
                else
                    pageQuery.setSortField(DBField.CREATED_DATE);

                list = getListWithProjection(criteria, pageQuery, collectionName, entityClass, projectFields);
            } else {
                Pageable pageable = MongoUtils.getPageRequest(0, 1, Sort.Direction.DESC, !AppUtils.isNullOrEmpty(fieldDistince) ? fieldDistince : DBField.CREATED_DATE);
                Query query = new Query(criteria);
                query.with(pageable);
                list = mongoTemplate.find(query, entityClass, collectionName);
            }
            if (!list.isEmpty())
                return list.get(0);
        } catch (Throwable throwable) {
            throw new GetException();
        }
        return null;
    }

    private static ObjectId convertStringToObjectId(String id) {
        return new ObjectId(id);
    }

//    public static ObjectId convertObjectToObjectId(Object entity) {
//        return new ObjectId(entity.toString());
//    }

    private static ObjectId convertEntityToObjectId(BaseEntity entity) {
        return new ObjectId(entity.getId());
    }


    private <K> List<K> getListWithProjection(Criteria criteria, PageQuery pageQuery, String collectionName,
                                              Class<K> clazz, String... projectFields) throws GetException {
        try {
            List<AggregationOperation> aggregationOperations = new ArrayList<>();
            if (criteria != null)
                aggregationOperations.add(new MatchOperation(criteria));
            if (pageQuery != null) {
                aggregationOperations.addAll(MongoUtils.buildLimitOperation(pageQuery.getStart(), pageQuery.getLimit()));
                aggregationOperations.add(Aggregation.sort(pageQuery.getSortType() == BaseDao.ASC ? Sort.Direction.ASC : Sort.Direction.DESC,
                        pageQuery.getSortField()));
            }

            aggregationOperations.add(Aggregation.project(projectFields).andExclude("_id"));
            Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);

            CloseableIterator<K> aggregationResults = mongoTemplate.aggregateStream(aggregation, collectionName, clazz);
            List<K> res = new ArrayList<>();
            while (aggregationResults.hasNext()) {
                K info = aggregationResults.next();
                if (info != null) {
                    res.add(info);
                }
            }
            return res;
        } catch (Throwable throwable) {
            throw new GetException();
        }
    }
}
