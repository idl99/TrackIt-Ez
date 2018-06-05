package com.teamideals.trackitez.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.kofigyan.stateprogressbar.StateProgressBar;

import com.teamideals.trackitez.R;
import com.teamideals.trackitez.viewmodels.AddUnit;

public class AddUnitActivity extends BaseActivity
        implements ItemDetails.OnFragmentInteractionListener,
        ScanTag.OnFragmentInteractionListener,
        FinishAddItem.OnFragmentInteractionListener{

    private StateProgressBar mStaticProgressBar;
    private FragmentManager mFragmentManager;
    private AddUnit mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);

        mViewModel = ViewModelProviders.of(this).get(AddUnit.class);

        mStaticProgressBar = findViewById(R.id.add_item_spb);
        initStateProgressBar();
        switch (mViewModel.getCurrentProgressState()) {
            case 1:
                mStaticProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                break;
            case 2:
                mStaticProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                break;
            case 3:
                mStaticProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                break;
        }

        mFragmentManager = getSupportFragmentManager();

        Fragment itemDetailFragment =
                mFragmentManager.findFragmentByTag("Item_Detail_Fragment");
        Fragment scanTagFragment =
                mFragmentManager.findFragmentByTag("Scan_Tag_Fragment");


        if (mViewModel.getCurrentProgressState() == 1
                && itemDetailFragment == null) {
            goToItemDetails();
        } else if (mViewModel.getCurrentProgressState() == 2
                && scanTagFragment == null) {
            goToScanTag();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void initStateProgressBar() {
        mStaticProgressBar.setStateDescriptionData(
                new String[]{"Item\nDetails", "Scan\nTags", "Finish\n"}
        );
    }

    public void goToItemDetails() {
        mFragmentManager.beginTransaction().replace(
                R.id.fragmentView,
                new ItemDetails(), "" +
                        "Item_Detail_Fragment").commit();
    }

    public void goToScanTag() {
        mViewModel.setCurrentProgressState(2);
        mStaticProgressBar.enableAnimationToCurrentState(true);
        mStaticProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
        mFragmentManager.beginTransaction().replace(
                R.id.fragmentView,
                new ScanTag(), "" +
                        "Scan_Tag_Fragment").commit();
    }

    public void goToSummary() {
        mViewModel.setCurrentProgressState(3);
        mStaticProgressBar.enableAnimationToCurrentState(true);
        mStaticProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
        mFragmentManager.beginTransaction().replace(
                R.id.fragmentView,
                new FinishAddItem(), "" +
                        "FinishAddItem").commit();
    }

}
