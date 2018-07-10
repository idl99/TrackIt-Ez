package com.teamideals.trackitez.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamideals.trackitez.entities.DatastoreEntity;

public interface Datastore<T extends Object & DatastoreEntity> {

    static final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public void add(T t);

    public void update(T t);

    public void remove(T t);

    public DatabaseReference getRef();

}
