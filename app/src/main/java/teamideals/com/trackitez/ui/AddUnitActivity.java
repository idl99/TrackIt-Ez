package teamideals.com.trackitez.ui;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.kofigyan.stateprogressbar.StateProgressBar;

import teamideals.com.trackitez.R;

public class AddUnitActivity extends FragmentActivity
        implements EnterItemDetails.OnFragmentInteractionListener {

    private StateProgressBar mStaticProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);

        mStaticProgressBar = findViewById(R.id.add_item_spb);
        initStateProgressBar();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void initStateProgressBar(){
        mStaticProgressBar.setStateDescriptionData(
                new String[]{"Item\nDetails","Add\nQuantities","Scan\nTags"}
        );
        mStaticProgressBar.setDescriptionTopSpaceIncrementer(40);
        mStaticProgressBar.setStateNumberTextSize(40);
        mStaticProgressBar.setStateDescriptionSize(18);
    }

}
