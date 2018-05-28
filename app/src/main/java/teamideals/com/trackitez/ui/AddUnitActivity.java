package teamideals.com.trackitez.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.kofigyan.stateprogressbar.StateProgressBar;
import teamideals.com.trackitez.R;
import teamideals.com.trackitez.viewmodels.AddUnitViewModel;

public class AddUnitActivity extends FragmentActivity
        implements ItemDetails.OnFragmentInteractionListener, ScanTag.OnFragmentInteractionListener {

    private StateProgressBar mStaticProgressBar;
    private FragmentManager mFragmentManager;
    private AddUnitViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);

        mViewModel = ViewModelProviders.of(this).get(AddUnitViewModel.class);

        mStaticProgressBar = findViewById(R.id.add_item_spb);
        initStateProgressBar();

        mFragmentManager = getSupportFragmentManager();

        Fragment itemDetailFragment =
                mFragmentManager.findFragmentByTag("Item_Detail_Fragment");
        Fragment scanTagFragment =
                mFragmentManager.findFragmentByTag("Scan_Tag_Fragment");

        if(mViewModel.getCurrentProgressState()==1
                && itemDetailFragment==null){
            goToItemDetails();
        } else if(mViewModel.getCurrentProgressState()==2
                && scanTagFragment==null){
            goToScanTag();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void initStateProgressBar(){
        mStaticProgressBar.setStateDescriptionData(
                new String[]{"Item\nDetails","Scan\nTags","Finish\n"}
        );
    }

    public void goToItemDetails(){
        mFragmentManager.beginTransaction().replace(
                R.id.fragmentView,(Fragment)
                        new ItemDetails(),"" +
                        "Item_Detail_Fragment").commit();
    }

    public void goToScanTag(){
        mViewModel.setCurrentProgressState(2);
        mStaticProgressBar.enableAnimationToCurrentState(true);
        mStaticProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
        mFragmentManager.beginTransaction().replace(
                R.id.fragmentView,(Fragment)
                        new ScanTag(),"" +
                        "Scan_Tag_Fragment").commit();
    }
}
