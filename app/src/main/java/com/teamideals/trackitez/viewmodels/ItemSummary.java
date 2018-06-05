package com.teamideals.trackitez.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.teamideals.trackitez.datastores.UnitDatastore;
import com.teamideals.trackitez.entities.Unit;
import com.teamideals.trackitez.entities.UnitStatus;

public class ItemSummary extends ViewModel {

    private UnitDatastore unitDatastore = UnitDatastore.getInstance();

    private MutableLiveData<List<ItemUnit>> mListOfItemUnit = new MutableLiveData<>();

    public ItemSummary(){
        mListOfItemUnit.setValue(new ArrayList<ItemUnit>());
        unitDatastore.getRef().child(String.valueOf(UnitStatus.IN_STORAGE)).addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        addItemUnit(dataSnapshot);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }
        );
    }

    public MutableLiveData<List<ItemUnit>> getListOfItemUnit() {
        return mListOfItemUnit;
    }

    public void addItemUnit(DataSnapshot snapshot){

        long qty = snapshot.getChildrenCount();

        GenericTypeIndicator<Map<String,Unit>> t = new GenericTypeIndicator<>();
        Iterable unitIterable = snapshot.getChildren();

        Unit unit = ((DataSnapshot)unitIterable.iterator().next())
                    .getValue(Unit.class);

        String itemName = unit.getItem().getItemName();
        Date expiryDate = unit.getExpiryDate();

        ItemUnit itemUnit = new ItemUnit(itemName,qty,expiryDate);
        List<ItemUnit> temp = mListOfItemUnit.getValue();
        temp.add(itemUnit);
        mListOfItemUnit.setValue(temp);

    }

    public final class ItemUnit implements Comparable<ItemUnit>{

        private String itemName;
        private long quantity;
        private Date expiryDate;

        private ItemUnit(String itemName, long quantity, Date expiryDate){
            this.itemName = itemName;
            this.quantity = quantity;
            this.expiryDate = expiryDate;
        }

        public String getItemName(){
            return this.itemName;
        }

        public Long getQuantity(){
            return this.quantity;
        }

        public Date getExpiryDate(){
            return this.expiryDate;
        }

        public long getExpiryinDays(){
            return TimeUnit.DAYS.convert(
                    (this.expiryDate.getTime()-System.currentTimeMillis()),
                    TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(@NonNull ItemUnit o) {
            long thisExpiry,oExpiry = 0;
            thisExpiry = this.getExpiryinDays();
            oExpiry = o.getExpiryinDays();

            return (int)(thisExpiry - oExpiry);

        }
    }



}