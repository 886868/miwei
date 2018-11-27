package com.comm.dao;

import java.io.Serializable;
import java.util.List;

public interface ICommonDao<E extends Serializable> {
    void save(E obj); // 保存实例对象
    
    Serializable save4Result(E obj); // 保存对象并且返回主键
    
    void saveList(List<E> obj); // 保存一组实例对象

    void saveOrUpdate(E obj); // 保存或修改实例对象

    void update(E obj); // 修改当前实例对象

    void delete(E obj); // 删除当前实例对象

    int deleteById(long id); // 通过主键来删除当前实例对象

    int deleteBySeq(long id); // 通过主键来删除当前实例对象

    int deleteByKeyAndValue(String key, Serializable value); // 通过键名+键值来删除当前实例对象

    int deleteByKeyAndValue(String[] keys, Object[] values); // 通过键名+键值来删除当前实例对象

    int deleteByKeyAndValue(List<String> keys, List<Object> values); // 通过键名+键值来删除当前实例对象

    E queryById(long id); // 通过主键来找到当前的实例对象

    E queryByKeyAndValue(String key, Serializable val); // 通过键名和键值来找到当前的实例对象

    E queryByKeysAndValues(String[] keys, Object[] values); // 通过某些键名和键值来找到当前的实例对象

    E queryByKeysAndValues(List<String> keys, List<Object> values); // 通过某些键名和键值来找到当前的实例对象

    E queryByObject(E obj); // 通过反射类来查询key->value

    Object queryUniqueResult(String hql, String[] names, Object[] args); // 通过HQL获取某一条数据对象

    List<E> listByKeyAndValue(String key, Serializable val); // 通过键名+键值来获取所有符合条件的对象

    List<E> listByKeysAndValues(String[] keys, Object[] values); // 通过多个键名+键值来获取所有符合条件的对象

    List<E> listByKeysAndValues(List<String> keys, List<Object> values); // 通过多个键名+键值来获取所有符合条件的对象

    List<E> listAll(); // 查询所有该类实体对象

    List<E> listAll(int first, int length); // 分页-起始位和长度 来返回的该类实体对象

    int listAllCnt();

    List<E> list(String hql, int first, int length); // 通过hql来分页 返回的该类实体对象

    List<E> listByKeysAndValues(String[] keys, Object[] values, int first, int length, String order); // 通过多个键名+键值来获取所有符合条件的对象

    List<E> listByKeysAndValues(List<String> keys, List<Object> values, int first, int length, String order); // 通过多个键名+键值来获取所有符合条件的对象

    int listCntByKeysAndValues(String[] keys, Object[] values);

    int listCntByKeysAndValues(List<String> keys, List<Object> values);

    List<E> listByKeysAndValues(String[] keys, Object[] values, String dateKey, String startDate, String endDate,
            int first, int length, String order); // 通过多个键名+键值和时间来获取所有符合条件的对象

    List<E> listByKeysAndValues(List<String> keys, List<Object> values, String dateKey, String startDate, String endDate,
            int first, int length, String order); // 通过多个键名+键值和时间来获取所有符合条件的对象

    int listCntByKeysAndValues(String[] keys, Object[] values, String dateKey, String startDate, String endDate);

    int listCntByKeysAndValues(List<String> keys, List<Object> values, String dateKey, String startDate, String endDate);

    List<E> listByKeysAndValues(String[] keys, Object[] values, String[] likeKeys, Object[] likeValues, String dateKey,
            String startDate, String endDate, int first, int length, String order); // 通过多个键名+键值和时间来获取所有符合条件的对象

    List<E> listByKeysAndValues(List<String> keys, List<Object> values, List<String> likeKeys, List<Object> likeValues, String dateKey,
            String startDate, String endDate, int first, int length, String order); // 通过多个键名+键值和时间来获取所有符合条件的对象

    int listCntByKeysAndValues(String[] keys, Object[] values, String[] likeKeys, Object[] likeValues, String dateKey,
            String startDate, String endDate);

    int listCntByKeysAndValues(List<String> keys, List<Object> values, List<String> likeKeys, List<Object> likeValues, String dateKey,
            String startDate, String endDate);

    int executeUpdate(String hql, Object[] args); // 根据hql和参数来执行编辑

    int executeUpdate(String hql, String[] names, Object[] args); // 根据hql和字段名还有参数来执行编辑

    long saveObj(E obj);                                                     //保存实例对象
}
