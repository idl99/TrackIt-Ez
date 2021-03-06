package com.teamideals.trackitez.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.teamideals.trackitez.database.UnitDatastore;
import com.teamideals.trackitez.entities.Item;
import com.teamideals.trackitez.entities.Unit;
import com.teamideals.trackitez.entities.UnitStatus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddUnit extends ViewModel {

    // Reference to relevant datastores
    private UnitDatastore unitDatastore = UnitDatastore.getInstance();

    // Instance variables
    private MutableLiveData<Item> item = new MutableLiveData<>(); // Item of units to be added
    private MutableLiveData<List<Unit>> listOfUnits = new MutableLiveData<>(); // List containing units to be added
    private MutableLiveData<Integer> tagsScanned = new MutableLiveData<>();
    private Date expiryDate; // Expiry Date of units to be added
    private int currentProgressState = 1;

    public AddUnit() {
        item.setValue(new Item());
        listOfUnits.setValue(new ArrayList<>(1));
        tagsScanned.setValue(0);
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

    public MutableLiveData<Integer> getTagsScanned() {
        return this.tagsScanned;
    }

    public void onTagScanned(String uuid) {
        if (tagsScanned.getValue() < getListOfUnits().size()) {
            listOfUnits.getValue().get(tagsScanned.getValue())
                    .setNfcTagSerial(uuid);
            int current = tagsScanned.getValue();
            tagsScanned.setValue(
                    ++current
            );
        }
    }

    public int getQuantity() {
        return getListOfUnits().size();
    }

    public void writeUnits() {
        for (Unit unit : getListOfUnits()) {
            unitDatastore.add(unit);
        }
    }

}