package com.teamideals.trackitez.entities;

public class NfcTag implements DatastoreEntity {

    private String uid;
    private boolean currentInUse;

    public NfcTag(){

    }

    public NfcTag(String uid, boolean currentInUse) {
        this.uid = uid;
        this.currentInUse = currentInUse;
    }

    public String getUid() {
        return uid;
    }

    public boolean isCurrentInUse(){
        return currentInUse;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) ||
                uid.equals(((NfcTag)obj).getUid());
    }

}
