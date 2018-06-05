package teamideals.com.trackitez.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import teamideals.com.trackitez.R;
import teamideals.com.trackitez.viewmodels.ItemSummary;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // UI elements
    @BindView(R.id.listview_unit_list)
    ListView mUnitListView;
    @BindViews({R.id.fab_scan_tag, R.id.fab_add_item, R.id.fab_grocery_list})
    List<LinearLayout> mFabMenu;
    @BindView(R.id.fab_menu_btn)
    FloatingActionButton mFabBtn;
    // ViewModels
    ItemSummary mItemSummary; // View model for item entry
    boolean isFABOpen;
    private Unbinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Attaching view model to this instance of MainActivity
        mItemSummary = ViewModelProviders.of(this).get(ItemSummary.class);

        Unbinder binder = ButterKnife.bind(this);

        List<ItemSummary.ItemUnit> itemUnitList = mItemSummary.getListOfItemUnit().getValue();

        mFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        mFabMenu.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CameraActivity.class);
                startActivity(intent);
            }
        });

        mFabMenu.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddUnitActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mUnitListView.setAdapter(
                new ArrayAdapter<ItemSummary.ItemUnit>(
                        getApplicationContext(),
                        R.layout.unit_view,
                        R.id.itemName,
                        itemUnitList) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);

                        ItemSummary.ItemUnit itemUnit = itemUnitList.get(position);
                        String itemName = itemUnit.getItemName();
                        long quantity = itemUnit.getQuantity();
                        long daysToExpiry = itemUnit.getExpiryinDays();


                        TextView tvItemName = view.findViewById(R.id.itemName);
                        tvItemName.setText(itemName);

                        TextView tvQuantity = view.findViewById(R.id.Quantity);
                        tvQuantity.setText(
                                String.format((getResources().getString(R.string.no_of_units)),
                                quantity
                        ));

                        TextView tvDaysToExpiry = view.findViewById(R.id.noOfDays);
                        String expiryTag = expiryTag(daysToExpiry);
                        tvDaysToExpiry.setText(expiryTag);

                        ImageView imageView = view.findViewById(R.id.circleExpiryIndicator);
                        setCeiLevel(imageView, daysToExpiry);

                        return view;
                    }
                });

        mItemSummary.getListOfItemUnit().observe(
                this, new Observer<List<ItemSummary.ItemUnit>>() {
                    @Override
                    public void onChanged(@Nullable List<ItemSummary.ItemUnit> units) {
                        Collections.sort(units);
                        ((BaseAdapter) mUnitListView.getAdapter()).notifyDataSetChanged();
                    }
                }
        );
    }

    private String expiryTag(long expiryInDays){
        if(expiryInDays>90){
            return (expiryInDays/30)+"m";
        } else if(expiryInDays>14){
            return (expiryInDays/7)+"w";
        } else
            return expiryInDays+"d";
    }

    private void setCeiLevel(ImageView imageView, long daysToExpiry) {

        if(daysToExpiry<=3){
            imageView.setImageDrawable(getResources()
                    .getDrawable(R.drawable.danger_expiry_indicator));
        } else if(daysToExpiry<=7){
            imageView.setImageDrawable(getResources()
                    .getDrawable(R.drawable.warning_expiry_indicator));
        } else if(daysToExpiry>=14){
            imageView.setImageDrawable(getResources()
                    .getDrawable(R.drawable.expiry_indicator));
        }

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

    /*@Override
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
    }*/

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
    private void showFABMenu() {

        isFABOpen = true;

        mFabBtn.setImageDrawable(getResources().
                getDrawable(R.drawable.ic_keyboard_arrow_down_white_24dp));

        for (int i = 0; i < mFabMenu.size(); i++) {
            LinearLayout fabOption = mFabMenu.get(i);
            fabOption.getChildAt(0).setVisibility(View.VISIBLE);
            fabOption.setTranslationY(
                    (-75 - (70 * i)) * this.getResources().getDisplayMetrics().density
            );
        }

    }

    /**
     * Method which collapses the FAB menu
     */
    private void closeFABMenu() {

        isFABOpen = false;

        mFabBtn.setImageDrawable(getResources().
                getDrawable(R.drawable.ic_keyboard_arrow_up_white_24dp));

        for (int i = 0; i < mFabMenu.size(); ) {
            LinearLayout fabOption = mFabMenu.get(i++);
            fabOption.getChildAt(0).setVisibility(View.INVISIBLE);
            fabOption.setTranslationY(0);
        }

    }

}
