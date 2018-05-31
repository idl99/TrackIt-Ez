package teamideals.com.trackitez.datastores;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import teamideals.com.trackitez.entities.DatastoreEntity;

public interface Datastore<T extends Object & DatastoreEntity> {

    static final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public void add(T t);

    public void addAll(List<T> t);

    public void update(T t);

    public void remove(T t);

    public DatabaseReference getRef();

}
