package nl.tudelft.wis.datamanagement.backend;

import java.io.File;

public class Config {
    public static File getHBMDir() {
	File destdir = new File(System.getProperty("java.io.tmpdir")
		+ "/rdfgears/hbm");
	if (!destdir.exists())
	    destdir.mkdirs();

	return destdir;
    }

    public static File getDataTypesDir() {
	File destdir = new File(System.getProperty("java.io.tmpdir")
		+ "/rdfgears/dataTypes");
	if (!destdir.exists())
	    destdir.mkdir();

	return destdir;
    }

    public static String getDbURL() {
	return "jdbc:mysql://localhost:3306/tutorial";
    }

    public static String getDbUsername() {
	return "root";
    }

    public static String getDbPassword() {
	return "SECRET123";
    }
}
