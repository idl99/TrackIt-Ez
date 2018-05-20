package teamideals.com.trackitez;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Intent
    private Intent mIntent;

    // UI elements
    private EditText mEditItemName;
    private EditText mEditItemExpiry;
    private ListView mItemListView;

    // ViewModels
    private ItemEntryViewModel mItemEntryViewModel; // View model for item entry

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Getting user details passed from LoginActivity to MainActivity
        mIntent = getIntent();
        User sessionUser = (User) mIntent.getExtras().getSerializable("user");

        // Attaching view model to this instance of MainActivity
        mItemEntryViewModel = ViewModelProviders.of(this).get(ItemEntryViewModel.class);

        mEditItemName = (EditText) findViewById(R.id.textEdit_item_name);
        mEditItemExpiry = (EditText) findViewById(R.id.textEdit_item_expiry);
        mItemListView = (ListView) findViewById(R.id.listView_itemList);

        mItemEntryViewModel.listOfItem.observe(
            this, new Observer<List<Item>>() {
                @Override
                public void onChanged(@Nullable List<Item> itemList) {
                    mItemListView.setAdapter(
                            new ArrayAdapter<Item>(
                                    getApplicationContext(),
                                    android.R.layout.simple_list_item_2,
                                    android.R.id.text1,
                                    itemList
                            ){
                                @NonNull
                                @Override
                                public View getView(int position,
                                                    @Nullable View convertView,
                                                    @NonNull ViewGroup parent) {
                                    View view = super.getView(position,convertView,parent);
                                    TextView viewItemName = (TextView) view.findViewById(android.R.id.text1);
                                    TextView viewItemExpiry = (TextView) view.findViewById(android.R.id.text2);

                                    viewItemName.setText(itemList.get(position).getItemName());
                                    viewItemExpiry.setText("Expiring on "+
                                            itemList.get(position).getItemExpiry());
                                    return view;
                                }
                            }
                    );
                }
            }
        );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addItem(View view){
        Item item = new Item(
                mEditItemName.getText().toString(),
                mEditItemExpiry.getText().toString()
        );
        List<Item> oldList = mItemEntryViewModel.getListOfItem().getValue();
        oldList.add(item);
        mItemEntryViewModel.getListOfItem().setValue(oldList);

        mEditItemName.requestFocus();
        mEditItemName.setText("");
        mEditItemExpiry.setText("");
    }

}
