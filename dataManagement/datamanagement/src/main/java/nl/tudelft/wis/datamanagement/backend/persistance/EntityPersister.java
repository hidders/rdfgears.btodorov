package nl.tudelft.wis.datamanagement.backend.persistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.LockModeType;

import org.hibernate.FlushMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author btodorov
 *
 */
/**
 * @author btodorov
 *
 */
public class EntityPersister {
    public void store(String name, Map<?, ?> entity) throws Exception {
	Session session = HibernateUtil.getSession();
	session.clear();
	session.beginTransaction();

	try {
	    // Save both
	    session.saveOrUpdate(name, entity);
	} catch (Throwable e) {
	    session.getTransaction().rollback();
	    throw new Exception("Cannot store EntityDescriptor", e);
	}
	session.getTransaction().commit();
    }

    public List<Map> executeQuery(String query, Map<String, String> params)
	    throws Exception {
	Session session = HibernateUtil.getSession();
	session.clear();
	session.beginTransaction();

	try {
	    Query q = session.createQuery(query);

		for (String key : q.getNamedParameters()) {
		    q.setParameter(key, params.get(key));
		}

	    // TODO consider non homogeneous results
	    String name = q.getReturnTypes()[0].getName();

	    List result = q.list();
	    for (int i = 0; i < result.size(); i++) {
		if (result.get(i) instanceof Map) {
		    Map entity = (Map) result.get(i);
		    entity.put("$type$", name);
		}
	    }

	    session.getTransaction().commit();

	    return result;
	} catch (Exception e) {
	    session.getTransaction().rollback();
	    throw e;
	}
    }

    public void delete(String query, Map<String, String> params)
	    throws Exception {
	List<Map> executeQuery = executeQuery(query, params);

	Session session = HibernateUtil.getSession();
	session.beginTransaction();
	session.clear();
	try {
	    for (Map m : executeQuery) {
		session.delete((String) m.get("$type$"), m);
	    }
	} catch (Exception e) {
	    session.getTransaction().rollback();
	    throw e;
	}
	session.getTransaction().commit();
    }

    
    /**
     * Updates the entities retrieved based on the query and updates its fields based on the provided setFields. Node 
     */
    public void update(String query, Map<String, String> params,
	    Map<String, Object> setFields) throws Exception {
	List<Map> executeQuery = executeQuery(query, params);

	Session session = HibernateUtil.getSession();
	session.beginTransaction();
	session.clear();
	try {
	    for (Map m : executeQuery) {
		for (String field : setFields.keySet()) {
		    m.put(field, setFields.get(field));
		}
		session.update((String) m.get("$type$"), m);
	    }

	    session.getTransaction().commit();
	} catch (Exception e) {
	    session.getTransaction().rollback();
	    throw e;
	}
    }

    public void insertChild(String query, Map<String, String> params,
	    String property, Map<String, Object> setFields) throws Exception {
	List<Map> executeQuery = executeQuery(query, params);

	Session session = HibernateUtil.getSession();
	session.beginTransaction();
	session.clear();
	try {
	    for (Map m : executeQuery) {

		Map newChild = new HashMap();
		for (String field : setFields.keySet()) {
		    newChild.put(field, setFields.get(field));
		}

		if (m.get(property) == null
			|| !(m.get(property) instanceof List<?>)) {
		    m.put(property, new ArrayList<Map>());
		}

		((List) m.get(property)).add(newChild);

		session.update((String) m.get("$type$"), m);
	    }

	    session.getTransaction().commit();
	} catch (Exception e) {
	    session.getTransaction().rollback();
	    throw e;
	}
    }
}
