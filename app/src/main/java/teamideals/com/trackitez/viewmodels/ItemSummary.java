package teamideals.com.trackitez.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import teamideals.com.trackitez.datastores.UnitDatastore;
import teamideals.com.trackitez.entities.Unit;
import teamideals.com.trackitez.entities.UnitStatus;

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

        String barcode = snapshot.getKey();
        long qty = snapshot.getChildrenCount();

        GenericTypeIndicator<Map<String,Unit>> t = new GenericTypeIndicator<>();
        Iterable unitIterable = snapshot.getChildren();

        String itemName = null;
        long maxDayDiff = 0;
        while(unitIterable.iterator().hasNext()){

            Unit unit = ((DataSnapshot)unitIterable.iterator().next())
                    .getValue(Unit.class);

            if(itemName == null) itemName = unit.getItem().getItemName();

            Date expiryDate = unit.getExpiryDate();

            long dayDiff = (expiryDate.getTime() - System.currentTimeMillis())/(1000*60*60*24);

            if(dayDiff>maxDayDiff) maxDayDiff = dayDiff;
        }

        String expiryPeriod = generateExpiryPeriod(maxDayDiff);

        ItemUnit itemUnit = new ItemUnit(itemName,qty,expiryPeriod);
        List<ItemUnit> temp = mListOfItemUnit.getValue();
        temp.add(itemUnit);
        mListOfItemUnit.setValue(temp);

    }

    private String generateExpiryPeriod(long dayDiff){
        if(dayDiff>90){
            return (dayDiff/30)+"m";
        } else if(dayDiff>14){
            return (dayDiff/7)+"w";
        } else
            return dayDiff+"d";
    }

    public final class ItemUnit{

        private String itemName;
        private long quantity;
        private String expiryPeriod;

        private ItemUnit(String itemName, long quantity, String daysToExpiry){
            this.itemName = itemName;
            this.quantity = quantity;
            this.expiryPeriod = daysToExpiry;
        }

        public String getItemName(){
            return this.itemName;
        }

        public Long getQuantity(){
            return this.quantity;
        }

        public String getExpiryPeriod(){
            return this.expiryPeriod;
        }

    }

}