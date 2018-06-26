package com.teamideals.trackitez.database;

import com.google.firebase.database.DatabaseReference;
import com.teamideals.trackitez.entities.NfcTag;

public class TagDatastore implements Datastore<NfcTag> {

    private static final TagDatastore ourInstance = new TagDatastore();

    private final DatabaseReference dbRef =
            Datastore.firebaseDatabase.
                    getReference("/userId1/nfcTags/");

    public static TagDatastore getInstance() {
        return ourInstance;
    }

    private TagDatastore() {

    }


    @Override
    public void add(NfcTag nfcTag) {

    }

    @Override
    public void update(NfcTag nfcTag) {

    }

    @Override
    public void remove(NfcTag nfcTag) {

    }

    @Override
    public DatabaseReference getRef() {
        return dbRef;
    }

}
