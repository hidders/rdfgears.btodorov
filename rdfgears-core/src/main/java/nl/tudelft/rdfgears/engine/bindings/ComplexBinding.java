package nl.tudelft.rdfgears.engine.bindings;

import nl.tudelft.rdfgears.engine.diskvalues.DatabaseManager;
import nl.tudelft.rdfgears.engine.diskvalues.ValueInflator;
import nl.tudelft.rdfgears.rgl.datamodel.value.RGLValue;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;

public abstract class ComplexBinding extends TupleBinding<RGLValue> {

	protected long id;
	protected TupleBinding<RGLValue> innerBinding;

	public ComplexBinding() {
		innerBinding = getInnerBinding();
	}

	protected abstract TupleBinding<RGLValue> getInnerBinding();

	public final RGLValue complexEntryToObject(DatabaseEntry entry) {
		return innerBinding.entryToObject(entry);
	}
	
	@Override
	public final RGLValue entryToObject(TupleInput in) {
		id = in.readLong();
		return innerBinding.entryToObject(DatabaseManager.getComplexEntry(id));
	}

	@Override
	public final void objectToEntry(RGLValue value, TupleOutput out) {
		if (ValueInflator.registerComplex(value.getId())) {
			Database complexStore = DatabaseManager.getComplexStore();
			DatabaseEntry valueEntry = new DatabaseEntry();
			innerBinding.objectToEntry(value, valueEntry);
			DatabaseEntry idEntry = DatabaseManager.long2entry(value.getId());
			complexStore.put(null, idEntry, valueEntry);
		}
		out.writeLong(value.getId());
	}
}
