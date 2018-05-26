package teamideals.com.trackitez.datastores;

import com.google.firebase.database.DatabaseReference;

import teamideals.com.trackitez.entities.Unit;

public class UnitDatastore implements Datastore<Unit> {

    private static final UnitDatastore ourInstance = new UnitDatastore();
    private static final DatabaseReference dsReference =
            Datastore.firebaseDatabase.getReference("/userDatastore/userId1/unitsStored");

    public static UnitDatastore getInstance() {
        return ourInstance;
    }

    private UnitDatastore() {
    }

    @Override
    public void add(Unit unit) {

    }

    @Override
    public void update(Unit unit) {

    }

    @Override
    public void remove(Unit unit) {

    }
}
