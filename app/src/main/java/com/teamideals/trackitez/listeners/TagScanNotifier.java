package com.teamideals.trackitez.listeners;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.teamideals.trackitez.database.TagDatastore;
import com.teamideals.trackitez.entities.NfcTag;

public class TagScanNotifier implements ValueEventListener,ChildEventListener {

    private DatabaseReference mDbRef = TagDatastore.getInstance().getRef();
    private OnTagScanListener mListener;
    private boolean isInitialDataLoaded;

    public TagScanNotifier(OnTagScanListener listener) {
        mDbRef.addChildEventListener(this);
        mDbRef.addListenerForSingleValueEvent(this);
        mListener = listener;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if(isInitialDataLoaded){
            NfcTag tag = extractTagFromSnapshot(dataSnapshot);
            mListener.newTagScanned(tag);
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        NfcTag nfcTag = extractTagFromSnapshot(dataSnapshot);
        if(nfcTag.isCurrentInUse()){
            mListener.notInUseTagScanned(nfcTag);
        } else if(!nfcTag.isCurrentInUse()){
            mListener.inUseTagScanned(nfcTag);
        }
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        isInitialDataLoaded = true;
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    private static NfcTag extractTagFromSnapshot(DataSnapshot snapshot){
        String uid = snapshot.getKey();
        boolean inUse = snapshot.child("inUse").getValue(Boolean.class);
        return new NfcTag(uid,inUse);
    }

}
