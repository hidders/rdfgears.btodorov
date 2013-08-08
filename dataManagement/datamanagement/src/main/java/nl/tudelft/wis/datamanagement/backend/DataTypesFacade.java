package nl.tudelft.wis.datamanagement.backend;

import nl.tudelft.wis.datamanagement.api.entitydef.EntityDescriptor;
import nl.tudelft.wis.datamanagement.backend.datatypes.DataTypesPersister;
import nl.tudelft.wis.datamanagement.backend.db.DatabaseManager;
import nl.tudelft.wis.datamanagement.backend.hbm.HBMPersister;

public class DataTypesFacade {
    
    public void storeDataType(EntityDescriptor ed) throws Exception {
	DataTypesPersister dataTypesPersister = new DataTypesPersister();
	DatabaseManager databaseManager = new DatabaseManager();
	HBMPersister hbmPersister = new HBMPersister();

	EntityDescriptor oldEntity = dataTypesPersister.getByName(ed.getName());

	databaseManager.updateDatabaseScheme(ed, oldEntity);
	hbmPersister.store(ed);
	dataTypesPersister.store(ed);
    }
    
    public void removeDataType(String name) throws Exception{
	DataTypesPersister dataTypesPersister = new DataTypesPersister();
	DatabaseManager databaseManager = new DatabaseManager();
	HBMPersister hbmPersister = new HBMPersister();

	EntityDescriptor entity = dataTypesPersister.getByName(name);

	databaseManager.removeDatabaseScheme(entity);
	hbmPersister.remove(name);
	dataTypesPersister.remove(name);
    }

}
