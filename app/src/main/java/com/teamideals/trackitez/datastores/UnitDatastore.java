package com.teamideals.trackitez.datastores;

import com.google.firebase.database.DatabaseReference;
import com.teamideals.trackitez.entities.Unit;

import java.util.List;

public class UnitDatastore implements Datastore<Unit> {

    private static final UnitDatastore ourInstance = new UnitDatastore();
    private final DatabaseReference dsReference =
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
    public void addAll(List<Unit> unitList) {
        for (Unit unit : unitList) {
            dsReference.child(unit.getStatus().toString())
                    .child(unit.getItem().getBarcode())
                    .child(unit.getUnitId()).setValue(unit);
        }
    }

    @Override
    public void update(Unit unit) {

    }

    @Override
    public void remove(Unit unit) {

    }

    @Override
    public DatabaseReference getRef() {
        return this.dsReference;
    }

}
