package teamideals.com.trackitez;

import android.arch.lifecycle.ViewModel;

public class ItemEntryViewModel extends ViewModel{

    public Item item;

    public ItemEntryViewModel(){
        item = new Item();
    }

}
