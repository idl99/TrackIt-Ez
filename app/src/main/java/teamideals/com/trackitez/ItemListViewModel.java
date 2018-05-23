package teamideals.com.trackitez;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import teamideals.com.trackitez.Entities.Item;

public class ItemListViewModel extends ViewModel{

    private final DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().
            getReference("/itemDatastore/");

    private MutableLiveData<List<Item>> mListOfItem;

    public ItemListViewModel(){
        mDatabaseReference.addChildEventListener(
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

    public MutableLiveData<List<Item>> getListOfItem(){
        if(mListOfItem ==null) {
            mListOfItem = new MutableLiveData<>();
            mListOfItem.setValue(new ArrayList<>());
        }
        return mListOfItem;
    }

    public void setListOfItem(List<Item> data){
        mListOfItem.setValue(data);
    }

    public void addToListOfItem(Item item){
        List<Item> temp = mListOfItem.getValue();
        temp.add(item);
        setListOfItem(temp);
    }

    public void removeFromListOfItem(Item item){
        List<Item> temp = mListOfItem.getValue();
        temp.remove(item);
        setListOfItem(temp);
    }

}
