package ua.org.tumakha.spdtool.dao.jpa;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

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
	public List<T> findAll() {
		Class<T> clazz = getGenericType();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(clazz);
		query.select(query.from(clazz));
		return entityManager.createQuery(query).getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public long countEntries() {
		Class<T> clazz = getGenericType();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		query.select(cb.count(query.from(clazz)));
		return entityManager.createQuery(query).getSingleResult();
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<T> findEntries(int firstResult, int maxResults) {
		Class<T> clazz = getGenericType();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(clazz);
		query.select(query.from(clazz));
		return entityManager.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

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
