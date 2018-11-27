package com.comm.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ACommonDao<E extends Serializable> implements ICommonDao<E> {
    private Class<E> entityClass;
    private Logger log;

    @Autowired
    private SessionFactory sessionFactory;

    protected ACommonDao(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    protected Class<E> getEntityClass() {
        return entityClass;
    }

    protected String getEntityName() {
        return getEntityClass().getSimpleName();
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected Logger getLog() {
        log = Logger.getLogger(this.getEntityClass());
        return log;
    }

    // /////////////////////////////////////////////////////////////////////////////////////

    // /////////////////////////////////////////////////////////////////////////////////////
    // -------------------------接口中的方法实现步骤-------------------------------------------/
    // /////////////////////////////////////////////////////////////////////////////////////

    // //////////////////////////////////////////////////////
    // -------------------------保存对象----------------------/
    // //////////////////////////////////////////////////////
    /**
     * @category 保存当前对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (Object)
     * @return void
     */
    public void save(E obj) {
        try {
            getCurrentSession().save(obj);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex);
        }
    }
    
    /**
     * @category 保存当前对象,返回主键
     * @author ymr
     * @since 2016-11-29
     * 
     * @param (Object)
     * @return Serializable
     */
    public Serializable save4Result(E obj) {
        try {
            return getCurrentSession().save(obj);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex);
            return null;
        }
    }

    /**
     * @category 保存一组实例对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (Object)
     * @return void
     */
    public void saveList(List<E> obj) {
        try {
            for (int i = 0; i < obj.size(); i++) {
                getCurrentSession().saveOrUpdate(obj.get(i));
                if (i % 50 == 0) {
                    getCurrentSession().flush();
                    getCurrentSession().clear();
                }
            }
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    /**
     * @category 保存或修改实例对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (Object)
     * @return void
     */
    public void saveOrUpdate(E obj) {
        try {
            getCurrentSession().saveOrUpdate(obj);
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    // //////////////////////////////////////////////////////
    // -------------------------修改对象----------------------/
    // //////////////////////////////////////////////////////
    /**
     * @category 修改当前实例对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (Object)
     * @return void
     */
    public void update(E obj) {
        try {
            getCurrentSession().update(obj);
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    // //////////////////////////////////////////////////////
    // -------------------------删除对象----------------------/
    // //////////////////////////////////////////////////////
    /**
     * @category 删除当前实例对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (Object)
     * @return void
     */
    public void delete(E obj) {
        try {
            getCurrentSession().delete(obj);
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    /**
     * @category 通过主键来删除当前实例对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (long)
     * @return void
     */
    public int deleteById(long id) {
        int iRet = 0;
        String hql = "delete from " + getEntityName() + " where id=:id";
        iRet = executeUpdate(hql, new String[] { "id" }, new Object[] { id });
        return iRet;
    }

    /**
     * @category 通过主键来删除当前实例对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (long)
     * @return void
     */
    public int deleteBySeq(long seq) {
        int iRet = 0;
        String hql = "delete from " + getEntityName() + " where seq=:seq";
        iRet = executeUpdate(hql, new String[] { "seq" }, new Object[] { seq });
        return iRet;
    }

    /**
     * @category 通过主键名和主键值来删除当前实例对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String,Serializable)
     * @return void
     */
    public int deleteByKeyAndValue(String key, Serializable val) {
        int iRet = 0;
        String hql = "delete from " + getEntityName() + " where " + key.trim() + "=:" + key.trim();
        iRet = executeUpdate(hql, new String[] { key.trim() }, new Object[] { val });
        return iRet;
    }

    /**
     * @category 通过多个条件删除当前实例对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String[]
     *            keys, Object[] values)
     * @return void
     */
    public int deleteByKeyAndValue(String[] keys, Object[] values) {
        int iRet = 0;
        try {
            String hql = "delete FROM " + getEntityName();
            for (int i = 0; i < values.length; i++) {
                if (hql.indexOf("where") >= 0)
                    hql += " and " + keys[i].trim() + "=:" + keys[i].trim();
                else
                    hql += " where " + keys[i].trim() + "=:" + keys[i].trim();
            }
            iRet = executeUpdate(hql, keys, values);
        } catch (SecurityException | IllegalArgumentException e) {
            log.error(e);
        }
        return iRet;
    }

    /**
     * @category 通过多个条件删除当前实例对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (List<String>
     *            keys, List<Object> values)
     * @return void
     */
    public int deleteByKeyAndValue(List<String> keys, List<Object> values) {
        int iRet = 0;
        try {
            String hql = "delete FROM " + getEntityName();
            for (int i = 0; i < values.size(); i++) {
                if (hql.indexOf("where") >= 0)
                    hql += " and " + keys.get(i).trim() + "=:" + keys.get(i).trim();
                else
                    hql += " where " + keys.get(i).trim() + "=:" + keys.get(i).trim();
            }
            iRet = executeUpdate(hql, keys, values);
        } catch (SecurityException | IllegalArgumentException e) {
            log.error(e);
        }
        return iRet;
    }

    // //////////////////////////////////////////////////////
    // -------------------------查询对象----------------------/
    // //////////////////////////////////////////////////////

    /**
     * @category 通过主键来找到当前的实例对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (long)
     * @return E
     */
    public E queryById(long id) {
        try {
            return (E) getCurrentSession().get(entityClass, id);
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }

    /**
     * @category 通过主键和主键值来找到当前的实例对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String,Serializable)
     * @return E
     */
    @SuppressWarnings("unchecked")
    public E queryByKeyAndValue(String key, Serializable val) {
        try {
            String hql = "FROM " + getEntityName();

            if (StringUtils.isNotEmpty(key) && val != null && StringUtils.isNotEmpty(val.toString())) {
                hql = hql + " where " + key.trim() + "=:" + key.trim();
            }
            return (E) queryUniqueResult(hql, new String[] { key.trim() }, new Object[] { val });
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }

    /**
     * @category 通过某些键名和键值来找到当前的实例对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (List<String>,List<E>)
     * @return E
     */
    @SuppressWarnings("unchecked")
    public E queryByKeysAndValues(String[] keys, Object[] values) {
        try {
            String hql = "FROM " + getEntityName();

            if (keys == null || keys.length <= 0 || values == null || values.length <= 0) {
                return (E) queryUniqueResult(hql, keys, values);
            }

            for (int i = 0; i < values.length; i++) {
                // 条件不为空的情况下添加
                if (StringUtils.isNotEmpty(keys[i]) && values[i] != null
                        && StringUtils.isNotEmpty(values[i].toString())) {
                    if (hql.indexOf("where") >= 0) {
                        hql += " and " + keys[i].trim() + "=:" + keys[i].trim();
                    } else {
                        hql += " where " + keys[i].trim() + "=:" + keys[i].trim();
                    }
                }
            }
            return (E) queryUniqueResult(hql, keys, values);
        } catch (SecurityException | IllegalArgumentException e) {
            log.error(e);
        }
        return null;
    }

    /**
     * @category 通过某些键名和键值来找到当前的实例对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (List<String>,List<E>)
     * @return E
     */
    @SuppressWarnings("unchecked")
    public E queryByKeysAndValues(List<String> keys, List<Object> values) {
        try {
            String hql = "FROM " + getEntityName();

            if (keys == null || keys.size() <= 0 || values == null || values.size() <= 0) {
                return (E) queryUniqueResult(hql, keys, values);
            }

            for (int i = 0; i < values.size(); i++) {
                // 条件不为空的情况下添加
                if (StringUtils.isNotEmpty(keys.get(i)) && values.get(i) != null
                        && StringUtils.isNotEmpty(values.get(i).toString())) {
                    if (hql.indexOf("where") >= 0) {
                        hql += " and " + keys.get(i).trim() + "=:" + keys.get(i).trim();
                    } else {
                        hql += " where " + keys.get(i).trim() + "=:" + keys.get(i).trim();
                    }
                }
            }
            return (E) queryUniqueResult(hql, keys, values);
        } catch (SecurityException | IllegalArgumentException e) {
            log.error(e);
        }
        return null;
    }

    /**
     * @category 通过反射类来查询key->value
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (E)
     * @return E
     */
    @SuppressWarnings("unchecked")
    public E queryByObject(E obj) {
        try {
            Class<? extends Serializable> _class = obj.getClass();
            Field[] fields = _class.getDeclaredFields();
            List<String> names = new ArrayList<String>();
            List<Object> objs = new ArrayList<Object>();
            String hql = "FROM " + getEntityName();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                String fieldName = field.getName();
                String upperName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                if (fieldName.equals("serialVersionUID") || fieldName.equals("id"))
                    continue;
                Method method = _class.getMethod("get" + upperName);
                Object fieldValue = method.invoke(obj);
                if (fieldValue == null) {
                    continue;
                }

                if (hql.indexOf("where") >= 0)
                    hql += " and " + fieldName.trim() + "=:" + fieldName.trim();
                else
                    hql += " where " + fieldName.trim() + "=:" + fieldName.trim();

                names.add(fieldName);
                objs.add(fieldValue);

            }
            return (E) queryUniqueResult(hql, names, objs);
        } catch (SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            log.error(e);
        }
        return null;
    }

    /**
     * @category 通过HQL获取某一条数据对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String,String[],Object[])
     * @return Object
     */
    public Object queryUniqueResult(String hql, String[] names, Object[] args) {
        Query query = getCurrentSession().createQuery(hql);
        applyArguments(query, names, args);
        return query.uniqueResult();
    }

    /**
     * @category 通过HQL获取所有数据对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String,String[],
     *            Object[])
     * @return List
     */
    public Object query(String hql, String[] names, Object[] args) {
        Query query = getCurrentSession().createQuery(hql);
        applyArguments(query, names, args);
        return query.list();
    }

    /**
     * @category 通过HQL获取所有数据对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String,List<String>,
     *            List<Object>)
     * @return List
     */
    public Object query(String hql, List<String> names, List<Object> args) {
        Query query = getCurrentSession().createQuery(hql);
        applyArguments(query, names, args);
        return query.list();
    }

    /**
     * @category 通过HQL获取所有数据对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String,String[],
     *            Object[], int, int)
     * @return List
     */
    private Object query(String hql, String[] names, Object[] args, int first, int length) {

        Query query = getCurrentSession().createQuery(hql);
        applyArguments(query, names, args);

        if (first > -1) {
            query.setFirstResult(first);
        }
        if (length > -1) {
            query.setMaxResults(length);
        }

        return query.list();
    }

    /**
     * @category 通过HQL获取所有数据对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String,List<String>,
     *            List<Object>, int, int)
     * @return List
     */
    private Object query(String hql, List<String> names, List<Object> args, int first, int length) {

        Query query = getCurrentSession().createQuery(hql);
        applyArguments(query, names, args);

        if (first > -1) {
            query.setFirstResult(first);
        }
        if (length > -1) {
            query.setMaxResults(length);
        }

        return query.list();
    }

    /**
     * @category 通过HQL获取所有数据对象
     * @author ymr
     * @since 2016-3-23
     * 
     * @param (String,String[],
     *            Object[], int, int)
     * @return List
     */
    private Object query(String hql, String[] keys, Object[] values, String[] likeKeys, Object[] likeValues, int first,
            int length) {
        Query query = getCurrentSession().createQuery(hql);
        applyArguments(query, keys, values);
        applyArgumentsLike(query, likeKeys, likeValues);

        if (first > -1) {
            query.setFirstResult(first);
        }
        if (length > -1) {
            query.setMaxResults(length);
        }

        return query.list();
    }

    /**
     * @category 通过HQL获取所有数据对象
     * @author ymr
     * @since 2016-3-23
     * 
     * @param (String,List<String>,
     *            OList<Object>, List<String>,
     *            OList<Object>, int, int)
     * @return List
     */
    private Object query(String hql, List<String> keys, List<Object> values, List<String> likeKeys, List<Object> likeValues, int first,
            int length) {
        Query query = getCurrentSession().createQuery(hql);
        applyArguments(query, keys, values);
        applyArgumentsLike(query, likeKeys, likeValues);

        if (first > -1) {
            query.setFirstResult(first);
        }
        if (length > -1) {
            query.setMaxResults(length);
        }

        return query.list();
    }

    /**
     * @category 通过HQL获取某一条数据对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String,List<String>,List<Object>)
     * @return Object
     */
    public Object queryUniqueResult(String hql, List<String> names, List<Object> args) {
        Query query = getCurrentSession().createQuery(hql);
        applyArguments(query, names, args);
        return query.uniqueResult();
    }

    /**
     * @category 通过键名+键值来获取所有符合条件的对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String,Serializable)
     * @return Object
     */
    @SuppressWarnings("unchecked")
    public List<E> listByKeyAndValue(String key, Serializable val) {
        try {
            String hql = "FROM " + getEntityName();
            if (StringUtils.isNotEmpty(key) && val != null && StringUtils.isNotEmpty(val.toString())) {
                hql = hql + " where " + key.trim() + "=:" + key.trim();
            }
            return (List<E>) query(hql, new String[] { key.trim() }, new Object[] { val });
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }

    /**
     * @category 通过多个键名+键值来获取所有符合条件的对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String[],Object[])
     * @return List<E>
     */
    @SuppressWarnings("unchecked")
    public List<E> listByKeysAndValues(String[] keys, Object[] values) {
        try {
            String hql = "FROM " + getEntityName();

            if (keys == null || keys.length <= 0 || values == null || values.length <= 0) {
                return (List<E>) query(hql, keys, values);
            }

            for (int i = 0; i < values.length; i++) {
                if (StringUtils.isNotEmpty(keys[i]) && values[i] != null
                        && StringUtils.isNotEmpty(values[i].toString())) {
                    if (hql.indexOf("where") >= 0) {
                        hql += " and " + keys[i].trim() + "=:" + keys[i].trim();
                    } else {
                        hql += " where " + keys[i].trim() + "=:" + keys[i].trim();
                    }
                }
            }
            return (List<E>) query(hql, keys, values);
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }

    /**
     * @category 通过多个键名+键值来获取所有符合条件的对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (List<String>,List<Object>)
     * @return List<E>
     */
    @SuppressWarnings("unchecked")
    public List<E> listByKeysAndValues(List<String> keys, List<Object> values) {
        try {
            String hql = "FROM " + getEntityName();

            if (keys == null || keys.size() <= 0 || values == null || values.size() <= 0) {
                return (List<E>) query(hql, keys, values);
            }

            for (int i = 0; i < values.size(); i++) {
                if (StringUtils.isNotEmpty(keys.get(i)) && values.get(i) != null
                        && StringUtils.isNotEmpty(values.get(i).toString())) {
                    if (hql.indexOf("where") >= 0) {
                        hql += " and " + keys.get(i).trim() + "=:" + keys.get(i).trim();
                    } else {
                        hql += " where " + keys.get(i).trim() + "=:" + keys.get(i).trim();
                    }
                }
            }
            return (List<E>) query(hql, keys, values);
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }

    /**
     * @category 查询所有该类实体对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param ()
     * @return List<E>
     */
    public List<E> listAll() {
        try {
            return listAll(-1, -1);
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }

    /**
     * @category 分页:起始位和长度 来返回的该类实体对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (int,int)
     * @return List<E>
     */
    public List<E> listAll(int first, int length) {
        try {
            String hql = "FROM " + getEntityName();
            return list(hql, first, length);
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }

    /**
     * @category 总记录数
     * @author ymr
     * @since 2016-2-18
     * 
     * @return int
     */
    @Override
    public int listAllCnt() {
        String hql = "select count(*) FROM " + getEntityName();
        String[] names = null;
        Object[] args = null;
        int count = getCnt(hql, names, args);
        return count;
    }

    /**
     * @category 分页:通过hql来分页 返回的该类实体对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (hql,int,int)
     * @return List<E>
     */
    public List<E> list(String hql, int first, int length) {
        return list(hql, null, first, length);
    }

    /**
     * @category 分页:通过hql来分页 返回的该类实体对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (hql,object[],int,int)
     * @return List<E>
     */
    @SuppressWarnings("unchecked")
    protected List<E> list(String hql, Object[] args, int first, int length) {
        Query query = getCurrentSession().createQuery(hql);
        applyArguments(query, args);
        if (first > -1) {
            query.setFirstResult(first);
        }
        if (length > -1) {
            query.setMaxResults(length);
        }
        return query.list();
    }

    /**
     * @category 分页:通过hql来分页并且排序 返回的该类实体对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String[],object[],int,int,String)
     * @return List<E>
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<E> listByKeysAndValues(String[] keys, Object[] values, int first, int length, String order) {
        try {
            String hql = "FROM " + getEntityName();

            if (values != null && values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    if (StringUtils.isNotEmpty(keys[i]) && values[i] != null
                            && StringUtils.isNotEmpty(values[i].toString())) {
                        if (hql.indexOf("where") >= 0) {
                            hql += " and " + keys[i].trim() + "=:" + keys[i].trim();
                        } else {
                            hql += " where " + keys[i].trim() + "=:" + keys[i].trim();
                        }
                    }
                }
            }

            // 设置order
            if (StringUtils.isNotEmpty(order)) {
                hql = hql + " order by " + order;
            }

            return (List<E>) query(hql, keys, values, first, length);
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }

    /**
     * @category 分页:通过hql来分页并且排序 返回的该类实体对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (List<String>,List<Object>,int,int,String)
     * @return List<E>
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<E> listByKeysAndValues(List<String> keys, List<Object> values, int first, int length, String order) {
        try {
            String hql = "FROM " + getEntityName();

            if (values != null && values.size() > 0) {
                for (int i = 0; i < values.size(); i++) {
                    if (StringUtils.isNotEmpty(keys.get(i)) && values.get(i) != null
                            && StringUtils.isNotEmpty(values.get(i).toString())) {
                        if (hql.indexOf("where") >= 0) {
                            hql += " and " + keys.get(i).trim() + "=:" + keys.get(i).trim();
                        } else {
                            hql += " where " + keys.get(i).trim() + "=:" + keys.get(i).trim();
                        }
                    }
                }
            }

            // 设置order
            if (StringUtils.isNotEmpty(order)) {
                hql = hql + " order by " + order;
            }

            return (List<E>) query(hql, keys, values, first, length);
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }

    /**
     * @category 分页:通过条件来分页并且排序 返回查询的记录总数
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String[],object[])
     * @return int
     */
    @Override
    public int listCntByKeysAndValues(String[] keys, Object[] values) {
        String hql = "select count(*) FROM " + getEntityName();
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                if (StringUtils.isNotEmpty(keys[i]) && values[i] != null
                        && StringUtils.isNotEmpty(values[i].toString())) {
                    if (hql.indexOf("where") >= 0) {
                        hql += " and " + keys[i].trim() + "=:" + keys[i].trim();
                    } else {
                        hql += " where " + keys[i].trim() + "=:" + keys[i].trim();
                    }
                }
            }
        }

        int count = getCnt(hql, keys, values);
        return count;
    }

    /**
     * @category 分页:通过条件来分页并且排序 返回查询的记录总数
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String[],object[])
     * @return int
     */
    @Override
    public int listCntByKeysAndValues(List<String> keys, List<Object> values) {
        String hql = "select count(*) FROM " + getEntityName();
        if (values != null && values.size() > 0) {
            for (int i = 0; i < values.size(); i++) {
                if (StringUtils.isNotEmpty(keys.get(i)) && values.get(i) != null
                        && StringUtils.isNotEmpty(values.get(i).toString())) {
                    if (hql.indexOf("where") >= 0) {
                        hql += " and " + keys.get(i).trim() + "=:" + keys.get(i).trim();
                    } else {
                        hql += " where " + keys.get(i).trim() + "=:" + keys.get(i).trim();
                    }
                }
            }
        }

        int count = getCnt(hql, keys, values);
        return count;
    }

    /**
     * @category 分页:通过hql来分页并且排序 返回的该类实体对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String[],object[],String,String,String,int,int,String)
     * @return List<E>
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<E> listByKeysAndValues(String[] keys, Object[] values, String dateKey, String startDate, String endDate,
            int first, int length, String order) {
        try {
            String hql = "FROM " + getEntityName();

            if (values != null && values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    if (StringUtils.isNotEmpty(keys[i]) && values[i] != null
                            && StringUtils.isNotEmpty(values[i].toString())) {
                        if (hql.indexOf("where") >= 0) {
                            hql += " and " + keys[i].trim() + "=:" + keys[i].trim();
                        } else {
                            hql += " where " + keys[i].trim() + "=:" + keys[i].trim();
                        }
                    }
                }
            }

            if (hql.indexOf("where") >= 0) {
                if (dateKey != null && !dateKey.isEmpty()) {
                    if (startDate != null && !startDate.isEmpty()) {
                        hql += " and " + dateKey + ">='" + startDate + "'";
                        if (endDate != null && !endDate.isEmpty()) {
                            hql += " and " + dateKey + "<='" + endDate + "'";
                        }
                    } else if (endDate != null && !endDate.isEmpty()) {
                        hql += " and " + dateKey + "<='" + endDate + "'";
                    }
                }
            } else {
                if (dateKey != null && !dateKey.isEmpty()) {
                    if (startDate != null && !startDate.isEmpty()) {
                        hql += " where " + dateKey + ">='" + startDate + "'";
                        if (endDate != null && !endDate.isEmpty()) {
                            hql += " and " + dateKey + "<='" + endDate + "'";
                        }
                    } else if (endDate != null && !endDate.isEmpty()) {
                        hql += " where " + dateKey + "<='" + endDate + "'";
                    }
                }
            }

            // 设置order
            if (StringUtils.isNotEmpty(order)) {
                hql = hql + " order by " + order;
            }

            return (List<E>) query(hql, keys, values, first, length);
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }
    
    /**
     * @category 分页:通过hql来分页并且排序 返回的该类实体对象
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (List<String>,List<Object>,String,String,String,int,int,String)
     * @return List<E>
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<E> listByKeysAndValues(List<String> keys, List<Object> values, String dateKey, String startDate, String endDate,
            int first, int length, String order) {
        try {
            String hql = "FROM " + getEntityName();

            if (values != null && values.size() > 0) {
                for (int i = 0; i < values.size(); i++) {
                    if (StringUtils.isNotEmpty(keys.get(i)) && values.get(i) != null
                            && StringUtils.isNotEmpty(values.get(i).toString())) {
                        if (hql.indexOf("where") >= 0) {
                            hql += " and " + keys.get(i).trim() + "=:" + keys.get(i).trim();
                        } else {
                            hql += " where " + keys.get(i).trim() + "=:" + keys.get(i).trim();
                        }
                    }
                }
            }

            if (hql.indexOf("where") >= 0) {
                if (dateKey != null && !dateKey.isEmpty()) {
                    if (startDate != null && !startDate.isEmpty()) {
                        hql += " and " + dateKey + ">='" + startDate + "'";
                        if (endDate != null && !endDate.isEmpty()) {
                            hql += " and " + dateKey + "<='" + endDate + "'";
                        }
                    } else if (endDate != null && !endDate.isEmpty()) {
                        hql += " and " + dateKey + "<='" + endDate + "'";
                    }
                }
            } else {
                if (dateKey != null && !dateKey.isEmpty()) {
                    if (startDate != null && !startDate.isEmpty()) {
                        hql += " where " + dateKey + ">='" + startDate + "'";
                        if (endDate != null && !endDate.isEmpty()) {
                            hql += " and " + dateKey + "<='" + endDate + "'";
                        }
                    } else if (endDate != null && !endDate.isEmpty()) {
                        hql += " where " + dateKey + "<='" + endDate + "'";
                    }
                }
            }

            // 设置order
            if (StringUtils.isNotEmpty(order)) {
                hql = hql + " order by " + order;
            }

            return (List<E>) query(hql, keys, values, first, length);
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }

    /**
     * @category 分页:通过条件来分页并且排序 返回查询的记录总数
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String[],object[],String,String,String)
     * @return int
     */
    @Override
    public int listCntByKeysAndValues(String[] keys, Object[] values, String dateKey, String startDate,
            String endDate) {
        String hql = "select count(*) FROM " + getEntityName();

        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                if (StringUtils.isNotEmpty(keys[i]) && values[i] != null
                        && StringUtils.isNotEmpty(values[i].toString())) {
                    if (hql.indexOf("where") >= 0) {
                        hql += " and " + keys[i].trim() + "=:" + keys[i].trim();
                    } else {
                        hql += " where " + keys[i].trim() + "=:" + keys[i].trim();
                    }
                }
            }
        }

        if (hql.indexOf("where") >= 0) {
            if (dateKey != null && !dateKey.isEmpty()) {
                if (startDate != null && !startDate.isEmpty()) {
                    hql += " and " + dateKey + ">='" + startDate + "'";
                    if (endDate != null && !endDate.isEmpty()) {
                        hql += " and " + dateKey + "<='" + endDate + "'";
                    }
                } else if (endDate != null && !endDate.isEmpty()) {
                    hql += " and " + dateKey + "<='" + endDate + "'";
                }
            }
        } else {
            if (dateKey != null && !dateKey.isEmpty()) {
                if (startDate != null && !startDate.isEmpty()) {
                    hql += " where " + dateKey + ">='" + startDate + "'";
                    if (endDate != null && !endDate.isEmpty()) {
                        hql += " and " + dateKey + "<='" + endDate + "'";
                    }
                } else if (endDate != null && !endDate.isEmpty()) {
                    hql += " where " + dateKey + "<='" + endDate + "'";
                }
            }
        }

        int count = getCnt(hql, keys, values);
        return count;
    }

    /**
     * @category 分页:通过条件来分页并且排序 返回查询的记录总数
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String[],object[],String,String,String)
     * @return int
     */
    @Override
    public int listCntByKeysAndValues(List<String> keys, List<Object> values, String dateKey, String startDate,
            String endDate) {
        String hql = "select count(*) FROM " + getEntityName();

        if (values != null && values.size() > 0) {
            for (int i = 0; i < values.size(); i++) {
                if (StringUtils.isNotEmpty(keys.get(i)) && values.get(i) != null
                        && StringUtils.isNotEmpty(values.get(i).toString())) {
                    if (hql.indexOf("where") >= 0) {
                        hql += " and " + keys.get(i).trim() + "=:" + keys.get(i).trim();
                    } else {
                        hql += " where " + keys.get(i).trim() + "=:" + keys.get(i).trim();
                    }
                }
            }
        }

        if (hql.indexOf("where") >= 0) {
            if (dateKey != null && !dateKey.isEmpty()) {
                if (startDate != null && !startDate.isEmpty()) {
                    hql += " and " + dateKey + ">='" + startDate + "'";
                    if (endDate != null && !endDate.isEmpty()) {
                        hql += " and " + dateKey + "<='" + endDate + "'";
                    }
                } else if (endDate != null && !endDate.isEmpty()) {
                    hql += " and " + dateKey + "<='" + endDate + "'";
                }
            }
        } else {
            if (dateKey != null && !dateKey.isEmpty()) {
                if (startDate != null && !startDate.isEmpty()) {
                    hql += " where " + dateKey + ">='" + startDate + "'";
                    if (endDate != null && !endDate.isEmpty()) {
                        hql += " and " + dateKey + "<='" + endDate + "'";
                    }
                } else if (endDate != null && !endDate.isEmpty()) {
                    hql += " where " + dateKey + "<='" + endDate + "'";
                }
            }
        }

        int count = getCnt(hql, keys, values);
        return count;
    }

    /**
     * @category 分页:通过hql来分页并且排序 返回的该类实体对象
     * @author ymr
     * @since 2016-3-23
     * 
     * @param (String[],object[],String[],object[],String,String,String,int,int,
     *            String)
     * @return List<E>
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<E> listByKeysAndValues(String[] keys, Object[] values, String[] likeKeys, Object[] likeValues,
            String dateKey, String startDate, String endDate, int first, int length, String order) {
        try {
            String hql = "FROM " + getEntityName();

            // 相等条件拼接
            if (values != null && values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    if (StringUtils.isNotEmpty(keys[i]) && values[i] != null
                            && StringUtils.isNotEmpty(values[i].toString())) {
                        if (hql.indexOf("where") >= 0) {
                            hql += " and " + keys[i].trim() + "=:" + keys[i].trim();
                        } else {
                            hql += " where " + keys[i].trim() + "=:" + keys[i].trim();
                        }
                    }
                }
            }

            // like 条件拼接
            if (likeValues != null && likeValues.length > 0) {
                for (int i = 0; i < likeValues.length; i++) {
                    if (StringUtils.isNotEmpty(likeKeys[i]) && likeValues[i] != null
                            && StringUtils.isNotEmpty(likeValues[i].toString())) {
                        if (hql.indexOf("where") >= 0) {
                            hql += " and " + likeKeys[i].trim() + " like :" + likeKeys[i].trim();
                        } else {
                            hql += " where " + likeKeys[i].trim() + " like :" + likeKeys[i].trim();
                        }
                    }
                }
            }

            // 日期条件拼接
            if (hql.indexOf("where") >= 0) {
                if (dateKey != null && !dateKey.isEmpty()) {
                    if (startDate != null && !startDate.isEmpty()) {
                        hql += " and " + dateKey + ">='" + startDate + "'";
                        if (endDate != null && !endDate.isEmpty()) {
                            hql += " and " + dateKey + "<='" + endDate + "'";
                        }
                    } else if (endDate != null && !endDate.isEmpty()) {
                        hql += " and " + dateKey + "<='" + endDate + "'";
                    }
                }
            } else {
                if (dateKey != null && !dateKey.isEmpty()) {
                    if (startDate != null && !startDate.isEmpty()) {
                        hql += " where " + dateKey + ">='" + startDate + "'";
                        if (endDate != null && !endDate.isEmpty()) {
                            hql += " and " + dateKey + "<='" + endDate + "'";
                        }
                    } else if (endDate != null && !endDate.isEmpty()) {
                        hql += " where " + dateKey + "<='" + endDate + "'";
                    }
                }
            }

            // 设置order
            if (StringUtils.isNotEmpty(order)) {
                hql = hql + " order by " + order;
            }

            return (List<E>) query(hql, keys, values, likeKeys, likeValues, first, length);
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }
    
    /**
     * @category 分页:通过hql来分页并且排序 返回的该类实体对象
     * @author ymr
     * @since 2016-3-23
     * 
     * @param (String[],object[],String[],object[],String,String,String,int,int,
     *            String)
     * @return List<E>
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<E> listByKeysAndValues(List<String> keys, List<Object> values, List<String> likeKeys, List<Object> likeValues,
            String dateKey, String startDate, String endDate, int first, int length, String order) {
        try {
            String hql = "FROM " + getEntityName();

            // 相等条件拼接
            if (values != null && values.size() > 0) {
                for (int i = 0; i < values.size(); i++) {
                    if (StringUtils.isNotEmpty(keys.get(i)) && values.get(i) != null
                            && StringUtils.isNotEmpty(values.get(i).toString())) {
                        if (hql.indexOf("where") >= 0) {
                            hql += " and " + keys.get(i).trim() + "=:" + keys.get(i).trim();
                        } else {
                            hql += " where " + keys.get(i).trim() + "=:" + keys.get(i).trim();
                        }
                    }
                }
            }

            // like 条件拼接
            if (likeValues != null && likeValues.size() > 0) {
                for (int i = 0; i < likeValues.size(); i++) {
                    if (StringUtils.isNotEmpty(likeKeys.get(i)) && likeValues.get(i) != null
                            && StringUtils.isNotEmpty(likeValues.get(i).toString())) {
                        if (hql.indexOf("where") >= 0) {
                            hql += " and " + likeKeys.get(i).trim() + " like :" + likeKeys.get(i).trim();
                        } else {
                            hql += " where " + likeKeys.get(i).trim() + " like :" + likeKeys.get(i).trim();
                        }
                    }
                }
            }

            // 日期条件拼接
            if (hql.indexOf("where") >= 0) {
                if (dateKey != null && !dateKey.isEmpty()) {
                    if (startDate != null && !startDate.isEmpty()) {
                        hql += " and " + dateKey + ">='" + startDate + "'";
                        if (endDate != null && !endDate.isEmpty()) {
                            hql += " and " + dateKey + "<='" + endDate + "'";
                        }
                    } else if (endDate != null && !endDate.isEmpty()) {
                        hql += " and " + dateKey + "<='" + endDate + "'";
                    }
                }
            } else {
                if (dateKey != null && !dateKey.isEmpty()) {
                    if (startDate != null && !startDate.isEmpty()) {
                        hql += " where " + dateKey + ">='" + startDate + "'";
                        if (endDate != null && !endDate.isEmpty()) {
                            hql += " and " + dateKey + "<='" + endDate + "'";
                        }
                    } else if (endDate != null && !endDate.isEmpty()) {
                        hql += " where " + dateKey + "<='" + endDate + "'";
                    }
                }
            }

            // 设置order
            if (StringUtils.isNotEmpty(order)) {
                hql = hql + " order by " + order;
            }

            return (List<E>) query(hql, keys, values, likeKeys, likeValues, first, length);
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }

    /**
     * @category 分页:通过条件来分页并且排序 返回查询的记录总数
     * @author ymr
     * @since 2016-3-23
     * 
     * @param (String[],object[],String[]
     *            likeKeys, Object[] likeValues,String,String,String)
     * @return int
     */
    @Override
    public int listCntByKeysAndValues(String[] keys, Object[] values, String[] likeKeys, Object[] likeValues,
            String dateKey, String startDate, String endDate) {
        String hql = "select count(*) FROM " + getEntityName();

        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                if (StringUtils.isNotEmpty(keys[i]) && values[i] != null
                        && StringUtils.isNotEmpty(values[i].toString())) {
                    if (hql.indexOf("where") >= 0) {
                        hql += " and " + keys[i].trim() + "=:" + keys[i].trim();
                    } else {
                        hql += " where " + keys[i].trim() + "=:" + keys[i].trim();
                    }
                }
            }
        }

        // like 条件拼接
        if (likeValues != null && likeValues.length > 0) {
            for (int i = 0; i < likeValues.length; i++) {
                if (StringUtils.isNotEmpty(likeKeys[i]) && likeValues[i] != null
                        && StringUtils.isNotEmpty(likeValues[i].toString())) {
                    if (hql.indexOf("where") >= 0) {
                        hql += " and " + likeKeys[i].trim() + " like :" + likeKeys[i].trim();
                    } else {
                        hql += " where " + likeKeys[i].trim() + " like :" + likeKeys[i].trim();
                    }
                }
            }
        }

        if (hql.indexOf("where") >= 0) {
            if (dateKey != null && !dateKey.isEmpty()) {
                if (startDate != null && !startDate.isEmpty()) {
                    hql += " and " + dateKey + ">='" + startDate + "'";
                    if (endDate != null && !endDate.isEmpty()) {
                        hql += " and " + dateKey + "<='" + endDate + "'";
                    }
                } else if (endDate != null && !endDate.isEmpty()) {
                    hql += " and " + dateKey + "<='" + endDate + "'";
                }
            }
        } else {
            if (dateKey != null && !dateKey.isEmpty()) {
                if (startDate != null && !startDate.isEmpty()) {
                    hql += " where " + dateKey + ">='" + startDate + "'";
                    if (endDate != null && !endDate.isEmpty()) {
                        hql += " and " + dateKey + "<='" + endDate + "'";
                    }
                } else if (endDate != null && !endDate.isEmpty()) {
                    hql += " where " + dateKey + "<='" + endDate + "'";
                }
            }
        }

        int count = getCnt(hql, keys, values, likeKeys, likeValues);
        return count;
    }

    /**
     * @category 分页:通过条件来分页并且排序 返回查询的记录总数
     * @author ymr
     * @since 2016-3-23
     * 
     * @param (String[],object[],String[]
     *            likeKeys, Object[] likeValues,String,String,String)
     * @return int
     */
    @Override
    public int listCntByKeysAndValues(List<String> keys, List<Object> values, List<String> likeKeys, List<Object> likeValues,
            String dateKey, String startDate, String endDate) {
        String hql = "select count(*) FROM " + getEntityName();

        if (values != null && values.size() > 0) {
            for (int i = 0; i < values.size(); i++) {
                if (StringUtils.isNotEmpty(keys.get(i)) && values.get(i) != null
                        && StringUtils.isNotEmpty(values.get(i).toString())) {
                    if (hql.indexOf("where") >= 0) {
                        hql += " and " + keys.get(i).trim() + "=:" + keys.get(i).trim();
                    } else {
                        hql += " where " + keys.get(i).trim() + "=:" + keys.get(i).trim();
                    }
                }
            }
        }

        // like 条件拼接
        if (likeValues != null && likeValues.size() > 0) {
            for (int i = 0; i < likeValues.size(); i++) {
                if (StringUtils.isNotEmpty(likeKeys.get(i)) && likeValues.get(i) != null
                        && StringUtils.isNotEmpty(likeValues.get(i).toString())) {
                    if (hql.indexOf("where") >= 0) {
                        hql += " and " + likeKeys.get(i).trim() + " like :" + likeKeys.get(i).trim();
                    } else {
                        hql += " where " + likeKeys.get(i).trim() + " like :" + likeKeys.get(i).trim();
                    }
                }
            }
        }

        if (hql.indexOf("where") >= 0) {
            if (dateKey != null && !dateKey.isEmpty()) {
                if (startDate != null && !startDate.isEmpty()) {
                    hql += " and " + dateKey + ">='" + startDate + "'";
                    if (endDate != null && !endDate.isEmpty()) {
                        hql += " and " + dateKey + "<='" + endDate + "'";
                    }
                } else if (endDate != null && !endDate.isEmpty()) {
                    hql += " and " + dateKey + "<='" + endDate + "'";
                }
            }
        } else {
            if (dateKey != null && !dateKey.isEmpty()) {
                if (startDate != null && !startDate.isEmpty()) {
                    hql += " where " + dateKey + ">='" + startDate + "'";
                    if (endDate != null && !endDate.isEmpty()) {
                        hql += " and " + dateKey + "<='" + endDate + "'";
                    }
                } else if (endDate != null && !endDate.isEmpty()) {
                    hql += " where " + dateKey + "<='" + endDate + "'";
                }
            }
        }

        int count = getCnt(hql, keys, values, likeKeys, likeValues);
        return count;
    }

    // //////////////////////////////////////////////////////
    // -------------------------HQL执行操作---------------- --/
    // //////////////////////////////////////////////////////
    /**
     * @deprecated Hibernate 4.1之后对于HQL中查询参数的占位符做了改进,
     *             用命名参数(:paramName)或者JPA占位符(?0,?1...)两中种方法来代替老的占位符查询方法。
     * @category 根据hql和参数来执行编辑
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String,Object[])
     * @return int
     */
    public int executeUpdate(String hql, Object[] args) {
        Query query = getCurrentSession().createQuery(hql);
        applyArguments(query, args);
        return query.executeUpdate();
    }

    /**
     * @category 绑定HQL和参数
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (Query,Object[])
     * @return void
     */
    protected void applyArguments(Query query, Object[] args) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null && StringUtils.isNotEmpty(args[i].toString())) {
                    query.setParameter(i, args[i]);
                }
            }
        }
    }

    /**
     * @category 根据hql和字段名还有参数来执行编辑
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String,String[],Object[])
     * @return int
     */
    public int executeUpdate(String hql, String[] names, Object[] args) {
        Query query = getCurrentSession().createQuery(hql);
        applyArguments(query, names, args);
        return query.executeUpdate();
    }

    /**
     * @category 根据hql和字段名还有参数来执行编辑
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (String,String[],Object[])
     * @return int
     */
    public int executeUpdate(String hql, List<String> names, List<Object> args) {
        Query query = getCurrentSession().createQuery(hql);
        applyArguments(query, names, args);
        return query.executeUpdate();
    }

    /**
     * @category 绑定HQL和参数
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (Query,String[],Object[])
     * @return void
     */
    protected void applyArguments(Query query, String[] names, Object[] args) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (StringUtils.isNotEmpty(names[i]) && args[i] != null && StringUtils.isNotEmpty(args[i].toString())) {
                    query.setParameter(names[i], args[i]);
                }
            }
        }
    }

    /**
     * @category 绑定HQL和like参数
     * @author ymr
     * @since 2016-3-23
     * 
     * @param (Query,String[],Object[])
     * @return void
     */
    private void applyArgumentsLike(Query query, String[] likeKeys, Object[] likeValues) {
        if (likeValues != null) {
            for (int i = 0; i < likeValues.length; i++) {
                if (StringUtils.isNotEmpty(likeKeys[i]) && likeValues[i] != null
                        && StringUtils.isNotEmpty(likeValues[i].toString())) {
                    query.setParameter(likeKeys[i], "%" + likeValues[i] + "%");
                }
            }
        }
    }

    /**
     * @category 绑定HQL和like参数
     * @author ymr
     * @since 2016-3-23
     * 
     * @param (Query,String[],Object[])
     * @return void
     */
    private void applyArgumentsLike(Query query, List<String> likeKeys, List<Object> likeValues) {
        if (likeValues != null) {
            for (int i = 0; i < likeValues.size(); i++) {
                if (StringUtils.isNotEmpty(likeKeys.get(i)) && likeValues.get(i) != null
                        && StringUtils.isNotEmpty(likeValues.get(i).toString())) {
                    query.setParameter(likeKeys.get(i), "%" + likeValues.get(i) + "%");
                }
            }
        }
    }

    /**
     * @category 绑定HQL和参数
     * @author ymr
     * @since 2016-2-3
     * 
     * @param (Query,List<String>,
     *            List<Object>)
     * @return void
     */
    protected void applyArguments(Query query, List<String> names, List<Object> args) {
        if (args != null) {
            for (int i = 0; i < args.size(); i++) {
                if (StringUtils.isNotEmpty(names.get(i)) && args.get(i) != null
                        && StringUtils.isNotEmpty(args.get(i).toString())) {
                    query.setParameter(names.get(i), args.get(i));
                }
            }
        }
    }

    /**
     * 根据条件查询记录数
     * 
     * @param hql
     * @param names
     * @param args
     * @return
     */
    protected int getCnt(String hql, String[] names, Object[] args) {
        Query query = getCurrentSession().createQuery(hql);
        applyArguments(query, names, args);
        return ((Number) query.uniqueResult()).intValue();
    }

    /**
     * 根据条件查询记录数
     * 
     * @param hql
     * @param names
     * @param args
     * @return
     */
    protected int getCnt(String hql, List<String> names, List<Object> args) {
        Query query = getCurrentSession().createQuery(hql);
        applyArguments(query, names, args);
        return ((Number) query.uniqueResult()).intValue();
    }

    /**
     * 根据条件查询记录数
     * 
     * @param hql
     * @param keys
     * @param values
     * @param likeKeys
     * @param likeValues
     * @return
     */
    protected int getCnt(String hql, String[] keys, Object[] values, String[] likeKeys, Object[] likeValues) {
        Query query = getCurrentSession().createQuery(hql);
        applyArguments(query, keys, values);
        applyArgumentsLike(query, likeKeys, likeValues);
        return ((Number) query.uniqueResult()).intValue();
    }

    /**
     * 根据条件查询记录数
     * 
     * @param hql
     * @param keys
     * @param values
     * @param likeKeys
     * @param likeValues
     * @return
     */
    protected int getCnt(String hql, List<String> keys, List<Object> values, List<String> likeKeys, List<Object> likeValues) {
        Query query = getCurrentSession().createQuery(hql);
        applyArguments(query, keys, values);
        applyArgumentsLike(query, likeKeys, likeValues);
        return ((Number) query.uniqueResult()).intValue();
    }

    /**
     * @category 保存当前对象
     * @author lingjingong
     * @since 2016-6-8
     * 
     * @param (Object)
     * @return long
     */
    public long saveObj(E obj) {
        try {
            long bakUsrSeq = Long.parseLong(getCurrentSession().save(obj).toString());
            return bakUsrSeq;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex);
        }
        return 0L;
    }

}
