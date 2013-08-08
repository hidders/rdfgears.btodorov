package nl.tudelft.wis.datamanagement.backend.persistance;

import nl.tudelft.wis.datamanagement.backend.Config;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final Session sessionFactory = buildSessionFactory();

    private static Session buildSessionFactory() {
	try {
	    // Create the SessionFactory from hibernate.cfg.xml
	    Configuration configuration = new Configuration();
	    configuration
		    .addDirectory(Config.getHBMDir());
	    Session openSession = configuration.configure().buildSessionFactory()
		    .openSession();

	    return openSession;
	} catch (Throwable ex) {
	    System.err.println("Initial SessionFactory creation failed." + ex);
	    throw new ExceptionInInitializerError(ex);
	}
    }

    public static Session getSession() {
	return sessionFactory;
    }

}