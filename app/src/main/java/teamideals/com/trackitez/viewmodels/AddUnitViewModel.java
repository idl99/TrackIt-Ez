package teamideals.com.trackitez.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;
import teamideals.com.trackitez.datastores.ItemDatastore;
import teamideals.com.trackitez.datastores.UnitDatastore;
import teamideals.com.trackitez.entities.Item;
import teamideals.com.trackitez.entities.Unit;

public class AddUnitViewModel extends ViewModel{

    // Reference to relevant datastores
    private ItemDatastore itemDatastore = ItemDatastore.getInstance();
    private UnitDatastore unitDatastore = UnitDatastore.getInstance();

    // Instance variables
    private MutableLiveData<Item> item; // Item of units to be added
    private MutableLiveData<List<Unit>> listOfUnits; // List containing units to be added

    private int currentProgressState;

    public AddUnitViewModel() {
        item = new MutableLiveData<>();
        item.setValue(new Item());
        listOfUnits = new MutableLiveData<>();
        listOfUnits.setValue(new ArrayList<>());
        currentProgressState = 1;
    }

    public Item getItem() {
        return item.getValue();
    }

    public void setItem(Item item){
        this.item.setValue(item);
    }

    public List<Unit> getListOfUnits() {
        return listOfUnits.getValue();
    }

    public void setListOfUnits(List<Unit> listOfUnits){
        this.listOfUnits.setValue(listOfUnits);
    }

    public int getCurrentProgressState() {
        return currentProgressState;
    }

    public void setCurrentProgressState(int currentProgressState) {
        this.currentProgressState = currentProgressState;
    }
}