package ua.org.tumakha.spdtool.dao.jpa;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Yuriy Tumakha
 */
public abstract class AbstractJpaDao<T> {

	protected final Log log = LogFactory.getLog(getClass());

	@PersistenceContext
	protected EntityManager entityManager;

	@Transactional(propagation = Propagation.SUPPORTS)
	public T find(Object id) {
		Class<T> clazz = getGenericType();
		return entityManager.find(clazz, id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void persist(T entity) {
		entityManager.persist(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T merge(T entity) {
		return entityManager.merge(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(T entity) {
		entityManager.remove(entity);
	}

	@SuppressWarnings("unchecked")
	private Class<T> getGenericType() {
		return (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

}
