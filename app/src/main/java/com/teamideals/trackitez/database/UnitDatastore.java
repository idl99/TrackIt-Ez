package com.teamideals.trackitez.database;

import com.google.firebase.database.DatabaseReference;
import com.teamideals.trackitez.entities.Unit;

public class UnitDatastore implements Datastore<Unit> {

    private static final UnitDatastore ourInstance = new UnitDatastore();

    private final DatabaseReference dbRef =
            Datastore.firebaseDatabase.
                    getReference("/userDatastore/userId1/unitsStored");


    public static UnitDatastore getInstance() {
        return ourInstance;
    }

    private UnitDatastore() {
    }

    @Override
    public void add(Unit unit) {
        dbRef.child(unit.getStatus().toString())
                .child(unit.getItem().getItemName())
                .child(unit.getUnitId())
                .setValue(unit);
    }

    @Override
    public void update(Unit unit) {

    }

    @Override
    public void remove(Unit unit) {

    }

    @Override
    public DatabaseReference getRef() {
        return this.dbRef;
    }

}
