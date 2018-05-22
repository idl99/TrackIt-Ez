package teamideals.com.trackitez;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

import teamideals.com.trackitez.Entities.Item;

public class ItemListViewModel extends ViewModel{

    public MutableLiveData<List<Item>> listOfItem;

    public ItemListViewModel(){
    }

    public MutableLiveData<List<Item>> getListOfItem(){
        if(listOfItem==null) {
            listOfItem = new MutableLiveData<>();
            loadListOfItems();
        }
        return listOfItem;
    }

    private void loadListOfItems(){
        List<Item> list = new ArrayList<>();
        list.add(new Item(1,"Anchor Milk Powder"));
        listOfItem.setValue(list);
    }

}
