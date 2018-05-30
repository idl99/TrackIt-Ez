package teamideals.com.trackitez.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import teamideals.com.trackitez.datastores.ItemDatastore;
import teamideals.com.trackitez.datastores.UnitDatastore;
import teamideals.com.trackitez.entities.Item;
import teamideals.com.trackitez.entities.Unit;
import teamideals.com.trackitez.entities.UnitStatus;

public class AddUnitViewModel extends ViewModel {

    // Reference to relevant datastores
    private ItemDatastore itemDatastore = ItemDatastore.getInstance();
    private UnitDatastore unitDatastore = UnitDatastore.getInstance();

    // Instance variables
    private MutableLiveData<Item> item; // Item of units to be added
    private MutableLiveData<List<Unit>> listOfUnits; // List containing units to be added
    private Date expiryDate; // Expiry Date of units to be added

    private int currentProgressState;
    private MutableLiveData<Integer> tagsScanned;

    public AddUnitViewModel() {
        item = new MutableLiveData<>();
        item.setValue(new Item());
        listOfUnits = new MutableLiveData<>();
        listOfUnits.setValue(new ArrayList<>(1));
        currentProgressState = 1;
        tagsScanned = new MutableLiveData<>();
        tagsScanned.setValue(new Integer(0));
    }

    public Item getItem() {
        return item.getValue();
    }

    public void setItem(Item item) {
        this.item.setValue(item);
    }

    public List<Unit> getListOfUnits() {
        return listOfUnits.getValue();
    }

    public void setListOfUnits(List<Unit> listOfUnits) {
        this.listOfUnits.setValue(listOfUnits);
    }

    public void initListOfUnits(int quantity) {
        for (int i = 0; i < quantity; i++) {
            listOfUnits.getValue().add(new Unit(
                    item.getValue(),
                    String.valueOf(Math.round(1 + (Math.random() * Math.pow(10, 5)))),
                    UnitStatus.IN_STORAGE,
                    this.expiryDate
            ));
        }
    }

    public int getCurrentProgressState() {
        return currentProgressState;
    }

    public void setCurrentProgressState(int currentProgressState) {
        this.currentProgressState = currentProgressState;
    }

    public String getExpiryDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(expiryDate);
    }

    public void setExpiryDate(String expiryDate) {
        try {
            this.expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse(expiryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<Integer> getTagsScanned(){
        return this.tagsScanned;
    }

    public void incrementTagsScanned(){
        if(tagsScanned.getValue()<getListOfUnits().size()){
            int current =tagsScanned.getValue().intValue();
            tagsScanned.setValue(
                    current+=1
            );
        }
    }

    public int getNumberOfTagsToScan(){
        return getListOfUnits().size();
    }

}