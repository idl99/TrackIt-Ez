package teamideals.com.trackitez;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;
import teamideals.com.trackitez.Entities.Item;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // UI elements
    private ListView mItemListView;
    FloatingActionButton mFabExpandMenu;
    LinearLayout mFabScanTag;
    LinearLayout mFabScanReceipt;
    LinearLayout mFabGroceryList;
    boolean isFABOpen;

    // ViewModels
    private ItemListViewModel mItemListViewModel; // View model for item entry

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Attaching view model to this instance of MainActivity
        mItemListViewModel = ViewModelProviders.of(this).get(ItemListViewModel.class);

        mItemListView = (ListView) findViewById(R.id.listView_itemList);
        List<Item> itemList = mItemListViewModel.getListOfItem().getValue();

        mFabExpandMenu = (FloatingActionButton) findViewById(R.id.fab_expand_menu);
        mFabScanTag = (LinearLayout) findViewById(R.id.fab_scan_tag);
        mFabScanReceipt = (LinearLayout) findViewById(R.id.fab_scan_receipt);
        mFabGroceryList = (LinearLayout) findViewById(R.id.fab_grocery_list);
        mFabExpandMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mItemListView.setAdapter(
                new ArrayAdapter<Item>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        itemList) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView viewItemName = (TextView) view.findViewById(android.R.id.text1);
                        viewItemName.setText(itemList.get(position).getItemName());
                        return view;
                    }
                }
        );

        mItemListViewModel.getListOfItem().observe(
                this, new Observer<List<Item>>() {
                    @Override
                    public void onChanged(@Nullable List<Item> items) {
                        ((BaseAdapter) mItemListView.getAdapter()).notifyDataSetChanged();
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

    /**
     * Method which expands the FAB menu
     */
    private void showFABMenu(){
        isFABOpen=true;
        mFabExpandMenu.setImageDrawable(getResources().
                getDrawable(R.drawable.ic_keyboard_arrow_down_white_24dp));

        mFabScanTag.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        mFabScanTag.setVisibility(View.VISIBLE);

        mFabScanReceipt.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        mFabScanReceipt.setVisibility(View.VISIBLE);

        mFabGroceryList.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        mFabGroceryList.setVisibility(View.VISIBLE);

    }

    /**
     * Method which collapses the FAB menu
     */
    private void closeFABMenu(){
        isFABOpen=false;
        mFabExpandMenu.setImageDrawable(getResources().
                getDrawable(R.drawable.ic_keyboard_arrow_up_white_24dp));

        mFabScanTag.animate().translationY(0);
        mFabScanTag.setVisibility(View.INVISIBLE);

        mFabScanReceipt.animate().translationY(0);
        mFabScanReceipt.setVisibility(View.INVISIBLE);

        mFabGroceryList.animate().translationY(0);
        mFabGroceryList.setVisibility(View.INVISIBLE);

    }

}
