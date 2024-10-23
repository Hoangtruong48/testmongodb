package com.example.demo_test.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
TODO: Lớp này dùng để convert các field có giá trị EMPTY_VALUE, NULL_VALUE về null hoặc "" tương ứng, s
  remap: từ null_value về null.
  map: từ null về null value
 */
public class MappingService<T> {
    public static final String EMPTY_VALUE = "emptstring";
    public static final String NULL_VALUE = "null_value";
    public static final List<String> FIELDS_NEED_TO_MAP_EMPTY_OR_NULL =
            Collections.unmodifiableList(Arrays.asList(
                "diaChi"
            ));
    // build methot get : ví dụ: getName, getAge
    private String buildNameOfGetMethod(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
    // build method set: ví dụ: setName, setAge
    private String buildNameOfSetMethod(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
    private static final Logger log = LoggerFactory.getLogger(MappingService.class);

    //
    public void remapEmptyValue(T object) {
        Class clazz = object.getClass();
        for (String fieldName : getListField(clazz)) {
            try {
                Method method = clazz.getMethod(buildNameOfGetMethod(fieldName), null);
                if (method.getReturnType().equals(String.class)) {
                    String result = (String) method.invoke(object);
                    if (result != null) {
                        method = clazz.getMethod(buildNameOfSetMethod(fieldName), String.class);
                        if (result.equals(NULL_VALUE)) {
                            method.invoke(object, (String) null);
                        } else if (result.equals(EMPTY_VALUE)) {
                            method.invoke(object, "");
                        }
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
    public void reMapEmptyValueAllFields(T object){
        Class clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            try{
                String fieldName = field.getName();
                Method method = clazz.getMethod(buildNameOfGetMethod(fieldName), null);
                if (method.getReturnType().equals(String.class)){
                    String result = (String) method.invoke(object);
                    if (result != null){
                        method = clazz.getMethod(buildNameOfSetMethod(fieldName), null);
                        if (result.equals(NULL_VALUE)){
                            method.invoke(object, (String) null);
                        } else if (result.equals(EMPTY_VALUE)){
                            method.invoke(object, "");
                        }
                    }
                }
            } catch (Exception ex){
                log.error(ex.getMessage());
            }
        }
    }
    public void mapEmptyValue(T object) {
        Class clazz = object.getClass();
        for (String fieldName : getListField(clazz)) {
            try {
                Method method = clazz.getMethod(buildNameOfGetMethod(fieldName), null);
                if (method.getReturnType().equals(String.class)) {
                    String result = (String) method.invoke(object);
                    method = clazz.getMethod(buildNameOfSetMethod(fieldName), String.class);
                    if (result == null) {
                        method.invoke(object, NULL_VALUE);
                    } else if (result.isEmpty()) {
                        method.invoke(object, EMPTY_VALUE);
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
    public List<String> getListField(Class clazz){
        return getAllFields(clazz);
    }
    private List<String> getAllFields(Class clazz){
        Field[] fields = clazz.getDeclaredFields();
        List<String> fieldNames = new ArrayList<>();
        Arrays.stream(fields).forEach(field -> {
            if (FIELDS_NEED_TO_MAP_EMPTY_OR_NULL.contains(field.getName())){
                fieldNames.add(field.getName());
            }
        });
        return fieldNames;
    }
}
