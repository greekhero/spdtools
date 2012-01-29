package ua.org.tumakha.spdtool.dao.jpa;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Yuriy Tumakha
 */
public abstract class AbstractJpaDao<T> {

    protected final Log log = LogFactory.getLog(getClass());

    @PersistenceContext
    protected EntityManager entityManager;

    public T find(Object id) {
        Class<T> clazz = getGenericType();
        return entityManager.find(clazz, id);
    }

    public void persist(T entity) {
        entityManager.persist(entity);
    }

    public T merge(T entity) {
        return entityManager.merge(entity);
    }

    public void remove(T entity) {
        entityManager.remove(entity);
    }

    @SuppressWarnings("unchecked")
    private Class<T> getGenericType() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Utility method for extracting a single element from a collection. Expects
     * the collection's size to be zero or one - if it's not - throws
     * java.lang.IllegalStateException with the debug info provided.
     * 
     * @param entitiesList
     *            the list a single elements to be extracted from
     * @param debugInfo
     *            additional info to be shown in the exception message
     * @return null if the list is empty, first element if the list contains one
     *         element
     * @throws IllegalStateException
     *             if the list contains more than one element
     */
    protected <K> K getSingleResult(List<K> entitiesList, Object... debugInfo) throws IllegalStateException {
        if (entitiesList == null || entitiesList.size() == 0) {
            return null;
        }
        if (entitiesList.size() > 1) {
            throw new IllegalStateException("Expected zero or one entity in the collection " + entitiesList
                    + "; debug info: " + Arrays.toString(debugInfo));
        }
        return entitiesList.get(0);
    }
}
