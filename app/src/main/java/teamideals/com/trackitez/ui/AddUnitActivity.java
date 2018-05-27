package teamideals.com.trackitez.ui;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.kofigyan.stateprogressbar.StateProgressBar;
import teamideals.com.trackitez.R;

public class AddUnitActivity extends FragmentActivity
        implements ItemLookup.OnFragmentInteractionListener, ScanTag.OnFragmentInteractionListener {

    private StateProgressBar mStaticProgressBar;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);

        mStaticProgressBar = findViewById(R.id.add_item_spb);
        initStateProgressBar();

        mFragmentManager = getSupportFragmentManager();
        stepProgress(1);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void initStateProgressBar(){
        mStaticProgressBar.setStateDescriptionData(
                new String[]{"Item\nDetails","Add\nQuantities","Scan\nTags"}
        );
        mStaticProgressBar.setDescriptionTopSpaceIncrementer(40);
        mStaticProgressBar.setStateNumberTextSize(24);
        mStaticProgressBar.setStateDescriptionSize(18);
    }

    public void stepProgress(int step){
        Fragment fragment = null;
        switch (step){
            case 1:
                fragment = new ItemLookup();
                mFragmentManager.beginTransaction().replace(R.id.fragmentView,fragment).commit();
                break;
            case 2:
                fragment = new ScanTag();
                mFragmentManager.beginTransaction().replace(R.id.fragmentView,fragment).commit();
        }
    }

}
