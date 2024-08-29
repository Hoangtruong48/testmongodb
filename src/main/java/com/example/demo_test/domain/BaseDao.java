package com.example.demo_test.domain;

import com.example.demo_test.config.DBField;
import com.example.demo_test.exception.CreateException;
import com.example.demo_test.exception.DeleteException;
import com.example.demo_test.exception.GetException;
import com.example.demo_test.exception.UpdateException;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public abstract class BaseDao<T> {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    protected final String TABLE_NAME;
    protected final Class<T> clazz;
    public final static int DESC = 1;
    public final static int ASC = 0;
    public BaseDao(String tableName, Class<T> clazz) {
        this.TABLE_NAME = tableName;
        this.clazz = clazz;
        //initConnection();
    }

    //public abstract void initConnection();

    public abstract <T> void saveAll(List<T> entities) throws UpdateException;

    public abstract List findAll() throws GetException;

    public abstract T insert(T entity) throws CreateException;

    public abstract <T> void insertAll(List<T> entities) throws CreateException;

    public abstract void remove(T entity) throws DeleteException;

    public abstract void removeAll(String[] ids) throws DeleteException;

    public abstract void removeAll(List<T> entities) throws DeleteException;

    public abstract void removeAllByIds(List<String> ids) throws DeleteException;

    public abstract long removeAllByIds(List<String> ids, String collectionName) throws DeleteException;

    public abstract T save(T entity) throws UpdateException;

    public abstract T findById(String id) throws GetException;

    public abstract boolean existsById(String id) throws GetException;

    public abstract List findAllById(List<String> ids) throws GetException;

    public abstract long count() throws GetException;

    public abstract void deleteById(String id) throws DeleteException;

    public abstract void delete(T entity) throws DeleteException;

    public abstract void deleteAll() throws DeleteException;

    public abstract long getSequenceId(String collectionName);

    public abstract void saveSequenceId(String collectionName, long sequence);

    //for all table

    public abstract <K> List<K> findListDocument(Class<K> entityClass,
                                                 String collectionName);

    public abstract <K> List<K> findListDocument(Class<K> entityClass,
                                                 String collectionName, PageQuery pageQuery);

    public abstract <K> List<K> findListDocumentWithProjectFields(Class<K> entityClass,
                                                                  String collectionName,
                                                                  PageQuery pageQuery,
                                                                  String... projectFields) throws GetException;

    public abstract <K> K findDocument(String field, Object value, Class<K> entityClass,
                                       String collectionName,
                                       String... projectFields) throws GetException;

    public abstract <K> List<K> findListDocumentWithExistsField(String[] fields, Object[] values, Class<K> entityClass,
                                                                String collectionName,
                                                                String... existsField) throws GetException;

    public abstract <K> K findDocument(String[] fields, Object[] values, Class<K> entityClass,
                                       String collectionName, String... projectFields) throws GetException;

    public abstract <K> List<K> findListDocument(String[] fields, Object[] values, Class<K> entityClass,
                                                 String collectionName, PageQuery pageQuery,
                                                 String... projectFields) throws GetException;

    public abstract <K> List<K> findListDocument(String field, Object value, Class<K> entityClass,
                                                 String collectionName, PageQuery pageQuery,
                                                 String... projectFields) throws GetException;

    public abstract long countListDocument(String[] fields, Object[] values,
                                           String collectionName) throws GetException;

    public abstract boolean isExistDocument(String[] fields, Object[] values,
                                            String collectionName) throws GetException;

    public abstract void saveDocument(Object entity, String collectionName) throws UpdateException;

    public abstract void insertDocument(Object entity, String collectionName) throws CreateException;

    public abstract long deleteAllByField(String[] fields, Object[] values,
                                          String collectionName) throws DeleteException;

    public abstract long deleteAllByField(String field, Object value, String collectionName) throws DeleteException;

    public abstract <K> K findDistinceDocument(String[] fields, Object[] values, Class<K> entityClass,
                                               String collectionName, String fieldDistince, String... projectFields) throws GetException;

    @Data
    public static class PageQuery {
        private Integer start;
        private Integer limit;
        private String sortField = DBField.CREATED_DATE;
        private Integer sortType = ASC;
    }

}
