package teamideals.com.trackitez;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import teamideals.com.trackitez.Entities.Item;

public class ItemEntryViewModel extends ViewModel{

    public MutableLiveData<List<Item>> listOfItem;

    public ItemEntryViewModel(){
        listOfItem = new MutableLiveData<>();
        listOfItem.postValue(new ArrayList<>());
    }

    public MutableLiveData<List<Item>> getListOfItem(){
        return listOfItem;
    }

}
