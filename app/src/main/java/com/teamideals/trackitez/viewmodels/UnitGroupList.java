package com.teamideals.trackitez.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.teamideals.trackitez.database.UnitDatastore;
import com.teamideals.trackitez.entities.Unit;
import com.teamideals.trackitez.entities.UnitStatus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UnitGroupList extends ViewModel implements ChildEventListener {

    private UnitDatastore unitDatastore = UnitDatastore.getInstance();
    private MutableLiveData<List<UnitGroup>> mListOfUnitGroup = new MutableLiveData<>();

    public UnitGroupList() {
        mListOfUnitGroup.setValue(new ArrayList<UnitGroup>());
        DatabaseReference dbRef = unitDatastore.getRef().
                child(String.valueOf(UnitStatus.IN_STORAGE));
        dbRef.addChildEventListener(this);
    }

    public MutableLiveData<List<UnitGroup>> getListOfUnit() {
        return mListOfUnitGroup;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        List<Unit> listOfUnits = extractFromSnapshot(dataSnapshot);
        UnitGroup unitGroup = new UnitGroup(listOfUnits);

        List<UnitGroup> temp = mListOfUnitGroup.getValue();
        temp.add(unitGroup);
        mListOfUnitGroup.setValue(temp);

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        List<Unit> listOfUnits = extractFromSnapshot(dataSnapshot);
        UnitGroup unitGroup = new UnitGroup(listOfUnits);

        List<UnitGroup> temp = mListOfUnitGroup.getValue();
        temp.remove(unitGroup);
        temp.add(unitGroup);
        mListOfUnitGroup.setValue(temp);

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

        List<Unit> listOfUnits = extractFromSnapshot(dataSnapshot);
        UnitGroup unitGroup = new UnitGroup(listOfUnits);

        List<UnitGroup> temp = mListOfUnitGroup.getValue();
        temp.remove(unitGroup);
        mListOfUnitGroup.setValue(temp);

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public List<Unit> extractFromSnapshot(DataSnapshot dataSnapshot) {

        List<Unit> _toReturn = new ArrayList<>();

        Iterable unitIterable = dataSnapshot.getChildren();
        Iterator iterator = unitIterable.iterator();
        while (iterator.hasNext()) {
            Unit unit = ((DataSnapshot) iterator.next()).getValue(Unit.class);
            _toReturn.add(unit);
        }

        return _toReturn;

    }

    public class UnitGroup implements Comparable<UnitGroup> {

        private List<Unit> unitGroup;

        public UnitGroup(List<Unit> unitGroup) {
            this.unitGroup = unitGroup;
        }

        public String getItemName() {
            Unit unit = unitGroup.get(0);
            return unit.getItem().getItemName();
        }

        public int getQuantity() {
            return unitGroup.size();
        }

        public long getNearestExpiry() {

            long minDaysToExpiry = unitGroup.get(0).getExpiryInDays();

            for (Unit unit : unitGroup) {
                long daysToExpiry = unit.getExpiryInDays();
                if (daysToExpiry < minDaysToExpiry) {
                    minDaysToExpiry = daysToExpiry;
                }
            }

            return minDaysToExpiry;

        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj) ||
                    getItemName().equals(((UnitGroup) obj).getItemName());
        }

        @Override
        public int compareTo(@NonNull UnitGroup o) {
            return (int) (getNearestExpiry() - o.getNearestExpiry());
        }
    }

}