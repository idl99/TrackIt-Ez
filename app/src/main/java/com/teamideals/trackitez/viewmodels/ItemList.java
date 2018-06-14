package com.teamideals.trackitez.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.teamideals.trackitez.datastores.ItemDatastore;
import com.teamideals.trackitez.entities.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemList extends ViewModel {

    private final ItemDatastore itemDatastore = ItemDatastore.getInstance();

    private MutableLiveData<List<Item>> mListOfItem;

    public ItemList() {
        itemDatastore.getRef().addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        addToListOfItem(dataSnapshot.getValue(Item.class));
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        removeFromListOfItem(dataSnapshot.getValue(Item.class));
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

    public MutableLiveData<List<Item>> getListOfItem() {
        if (mListOfItem == null) {
            mListOfItem = new MutableLiveData<>();
            mListOfItem.setValue(new ArrayList<>());
        }
        return mListOfItem;
    }

    public void setListOfItem(List<Item> data) {
        mListOfItem.setValue(data);
    }

    public void addToListOfItem(Item item) {
        List<Item> temp = mListOfItem.getValue();
        temp.add(item);
        setListOfItem(temp);
    }

    public void removeFromListOfItem(Item item) {
        List<Item> temp = mListOfItem.getValue();
        temp.remove(item);
        setListOfItem(temp);
    }

}
