package nl.tudelft.wis.datamanagement.backend.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.tudelft.wis.datamanagement.api.entitydef.BasicType;
import nl.tudelft.wis.datamanagement.api.entitydef.EntityDescriptor;
import nl.tudelft.wis.datamanagement.api.entitydef.Field;
import nl.tudelft.wis.datamanagement.api.entitydef.types.BagType;
import nl.tudelft.wis.datamanagement.api.entitydef.types.BooleanType;
import nl.tudelft.wis.datamanagement.api.entitydef.types.CompositeType;
import nl.tudelft.wis.datamanagement.api.entitydef.types.LiteralType;
import nl.tudelft.wis.datamanagement.api.entitydef.types.TupleType;

public class DatabaseManager {

    private MySQLQueryExecutor executor = new MySQLQueryExecutor();
    private List<String> createStatements;
    private List<String> dropStatements;
    private List<String> fkStatements;
    private boolean readAccess;
    private boolean writeAccess;

    public void updateDatabaseScheme(EntityDescriptor newEntity,
	    EntityDescriptor oldEntity) throws Exception {

	createStatements = new ArrayList<String>();
	dropStatements = new ArrayList<String>();
	fkStatements = new ArrayList<String>();

	readAccess = newEntity.isReadAccess();
	writeAccess = newEntity.isWriteAccess();

	createEntity(null, newEntity.getName(), newEntity.getEntity());

	if (oldEntity != null) {
	    removeEntity(null, oldEntity.getName(), oldEntity.getEntity());
	}

	List<String> statements = new ArrayList<String>();
	
	Collections.reverse(dropStatements);
	statements.addAll(dropStatements);
	statements.addAll(createStatements);
	statements.addAll(fkStatements);
	
	executor.execute(statements);
    }

    public void removeDatabaseScheme(EntityDescriptor entity) throws Exception {
	dropStatements = new ArrayList<String>();
	removeEntity(null, entity.getName(), entity.getEntity());
	Collections.reverse(dropStatements);
	executor.execute(dropStatements);
    }

    private void createEntity(String parentName, String name, TupleType entity) {
	StringBuilder sb = new StringBuilder();
	sb.append("CREATE TABLE " + name + "(");

	sb.append("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY");

	if (parentName != null) {
	    sb.append(", " + parentName + "_id INT");
	}

	for (Field f : entity.getProperties()) {
	    if (f.getValue() instanceof BasicType) {
		BasicType basicType = (BasicType) f.getValue();
		sb.append(", " + f.getName() + " " + basicType.getDBType());
	    } else if (f.getValue() instanceof TupleType) {
		TupleType compositeType = (TupleType) f.getValue();
		sb.append(", " + name + "_" + f.getName() + "_id INT");
		createFK(name, f.getName());
		createEntity(null, name + "_" + f.getName(),
			compositeType.getTuple());
	    } else if (f.getValue() instanceof BagType) {
		CompositeType compositeType = (CompositeType) f.getValue();
		createEntity(name, name + "_" + f.getName(),
			compositeType.getTuple());
	    }
	}

	sb.append(")  ENGINE=InnoDB CHARSET=utf8;");

	createStatements.add(sb.toString());

	setAccessOptions(name);
    }

    private void createFK(String source, String target) {
	String stm = "ALTER TABLE " + source + " ADD FOREIGN KEY (" + source
		+ "_" + target + "_id)" + "REFERENCES " + source + "_" + target
		+ "(id) ON DELETE SET NULL";
	fkStatements.add(stm);
    }

    private void setAccessOptions(String name) {
	// TODO Auto-generated method stub

    }

    private void removeEntity(String parent, String name, TupleType entity) {
	StringBuilder sb = new StringBuilder();
	sb.append("DROP TABLE IF EXISTS " + name + ";");

	if (entity.getProperties() != null) {
	    for (Field f : entity.getProperties()) {
		if (f.getValue() instanceof CompositeType) {
		    CompositeType compositeType = (CompositeType) f.getValue();
		    removeEntity(name, name + "_" + f.getName(),
			    compositeType.getTuple());
		}
	    }
	}

	dropStatements.add(sb.toString());
    }

    public static void main(String[] args) throws Exception {
	TupleType desk = new TupleType();
	List<Field> properties = new ArrayList<Field>();
	properties.add(new Field("deskName", new LiteralType()));
	properties.add(new Field("number", new BooleanType()));
	desk.setProperties(properties);

	TupleType department = new TupleType();
	properties = new ArrayList<Field>();
	properties.add(new Field("departmentName", new LiteralType()));
	department.setProperties(properties);

	TupleType employee = new TupleType();
	properties = new ArrayList<Field>();
	properties.add(new Field("name", new LiteralType()));
	properties.add(new Field("departments", new BagType(department)));
	properties.add(new Field("desk", desk));
	employee.setProperties(properties);

	EntityDescriptor desc = new EntityDescriptor();

	desc.setName("Employee");
	desc.setEntity(employee);

	DatabaseManager databaseManager = new DatabaseManager();
	databaseManager.updateDatabaseScheme(desc, desc);
    }

}
