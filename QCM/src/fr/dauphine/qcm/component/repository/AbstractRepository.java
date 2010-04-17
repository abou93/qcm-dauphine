package fr.dauphine.qcm.component.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.dauphine.qcm.model.AbstractEntity;

/**
 * D�p�t g�n�rique.
 * 
 * @param <T>
 *            Classe entit�
 */
@SuppressWarnings("unchecked")
public abstract class AbstractRepository<T extends AbstractEntity<?>> {

	/**
	 * Nombre de r�sultats par page.
	 */
	private static final int NB_RESULTS_BY_PAGE = 10;

	/**
	 * Session factory Hibernate.
	 */
	@Autowired
	private SessionFactory sessionFactory;

	private Class templateClass;

	public AbstractRepository() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass()
				.getGenericSuperclass();

		templateClass = (Class) parameterizedType.getActualTypeArguments()[0];
	}

	/**
	 * Retourne toutes les entit�s de type <code>T</code>.
	 * 
	 * @return La liste des entit�s de type <code>T</code>
	 */
	public List<T> loadAll() {
		return getCurrentSession().createCriteria(templateClass).list();
	}

	/**
	 * Retourne l'entit� correspondant � la cl�.
	 * 
	 * @param primaryKey
	 *            La cl�
	 * @return L'entit�
	 */
	public T load(Serializable primaryKey) {
		return (T) getCurrentSession().load(templateClass, primaryKey);
	}

	public void save(T entity) {
		getCurrentSession().save(entity);
	}

	public void saveOrUpdate(T entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	public void update(T entity) {
		getCurrentSession().update(entity);
	}

	public void delete(T entity) {
		getCurrentSession().delete(entity);
	}

	public void evict(T entity) {
		getCurrentSession().evict(entity);
	}

	public void refresh(T entity) {
		getCurrentSession().refresh(entity);
	}

	/**
	 * R�cup�re la session actuellement ouverte.
	 * 
	 * @return La session actuellement ouverte
	 */
	protected final Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	protected void paginate(Criteria criteria, int page) {
		criteria.setMaxResults(NB_RESULTS_BY_PAGE);
		criteria.setFirstResult(NB_RESULTS_BY_PAGE * page);
	}

	protected void paginate(Query query, int page) {
		query.setMaxResults(NB_RESULTS_BY_PAGE);
		query.setFirstResult(NB_RESULTS_BY_PAGE * page);
	}
}
