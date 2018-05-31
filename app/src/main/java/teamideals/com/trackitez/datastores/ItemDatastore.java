package teamideals.com.trackitez.datastores;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import teamideals.com.trackitez.entities.Item;

public class ItemDatastore implements Datastore<Item> {

    private static final ItemDatastore ourInstance = new ItemDatastore();
    private final DatabaseReference dsReference =
            Datastore.firebaseDatabase.getReference("/itemDatastore");

    public static ItemDatastore getInstance() {
        return ourInstance;
    }

    private ItemDatastore() {
    }

    @Override
    public void add(Item item) {

    }

    @Override
    public void addAll(List<Item> itemList) {

    }

    @Override
    public void update(Item item) {

    }

    @Override
    public void remove(Item item) {

    }

    @Override
    public DatabaseReference getRef() {
        return this.dsReference;
    }

}
