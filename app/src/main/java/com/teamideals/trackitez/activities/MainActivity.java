package com.teamideals.trackitez.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.teamideals.trackitez.R;
import com.teamideals.trackitez.util.UnitGroupAdapter;
import com.teamideals.trackitez.util.ocr.ScanReceipt;
import com.teamideals.trackitez.viewmodels.UnitGroupList;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // UI Bindings
    private Unbinder binder;

    @BindView(R.id.listview_unit_list)
    ListView mUnitListView;

    boolean isFABOpen;
    @BindViews({R.id.fab_scan_tag, R.id.fab_add_item, R.id.fab_grocery_list})
    List<LinearLayout> mFabMenu;

    @BindView(R.id.fab_menu_btn)
    FloatingActionButton mFabBtn;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    UnitGroupList mUnitGroupList; // View model


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting custom toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Attaching view model to this instance of MainActivity
        mUnitGroupList = ViewModelProviders.of(this).get(UnitGroupList.class);

        // Attaching activity to Butterknife binder
        binder = ButterKnife.bind(this);

        mFabBtn.setOnClickListener(view -> {
            if (!isFABOpen)
                showFabMenu();
            else
                hideFabMenu();
        });

        mFabMenu.get(0).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ScanReceipt.class);
            startActivity(intent);
        });

        mFabMenu.get(1).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddUnitActivity.class);
            startActivity(intent);
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        List<UnitGroupList.UnitGroup> unitList = mUnitGroupList.getListOfUnit().getValue();
        mUnitListView.setAdapter(
                new UnitGroupAdapter(
                        this,
                        R.layout.unit_group_view,
                        R.id.itemName,
                        unitList
                )
        );

        mUnitGroupList.getListOfUnit().observe(
                this, units -> {
                    Collections.sort(units);
                    ((BaseAdapter) mUnitListView.getAdapter()).notifyDataSetChanged();
                });

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

    // Method which expands the fab menu
    private void showFabMenu() {

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

    // Method which close the fab menu
    private void hideFabMenu() {

        isFABOpen = false;

        mFabBtn.setImageDrawable(getResources().
                getDrawable(R.drawable.ic_keyboard_arrow_up_white_24dp));

        for (int i = 0; i < mFabMenu.size(); ) {
            LinearLayout fabOption = mFabMenu.get(i++);
            fabOption.getChildAt(0).setVisibility(View.INVISIBLE);
            fabOption.setTranslationY(0);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }
}
