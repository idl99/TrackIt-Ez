package teamideals.com.trackitez.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import teamideals.com.trackitez.R;
import teamideals.com.trackitez.databinding.FragmentScanTagBinding;
import teamideals.com.trackitez.viewmodels.AddUnitViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScanTag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScanTag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanTag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private AddUnitViewModel mViewModel;

    private Unbinder unbinder;

    @BindView(R.id.btn_ScanTagNext)
    Button mButtonNext;

    public ScanTag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScanTag.
     */
    // TODO: Rename and change types and number of parameters
    public static ScanTag newInstance(String param1, String param2) {
        ScanTag fragment = new ScanTag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mViewModel = ViewModelProviders.of(getActivity()).get(AddUnitViewModel.class);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragments
        FragmentScanTagBinding fragmentScanTagBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_scan_tag, container, false
        );
        fragmentScanTagBinding.setLifecycleOwner(this);
        fragmentScanTagBinding.setItemName(mViewModel.getItem().getItemName());
        fragmentScanTagBinding.setTagsToScan(mViewModel.getListOfUnits().size());
        fragmentScanTagBinding.setExpiryDate(mViewModel.getExpiryDate());

        final Observer<Integer> tagsScannedObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Log.d("My_Log", "NUMBER OF TAGS SCANNED: " + integer);
                fragmentScanTagBinding.setTagsScanned(integer);
            }
        };

        mViewModel.getTagsScanned().observe(this, tagsScannedObserver);

        View view = fragmentScanTagBinding.getRoot();

        unbinder = ButterKnife.bind(this, view);

        new ScanTagAsyncTask(
                mViewModel.getTagsScanned(),
                mViewModel.getListOfUnits().size()
        ).execute();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        unbinder.unbind();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private static class ScanTagAsyncTask extends AsyncTask<Void, Void, Void> {

        private MutableLiveData<Integer> mlvTagsScanned;
        private int tagsScanned;
        private int tagsToBeScanned;

        private ScanTagAsyncTask(MutableLiveData<Integer> mlvTagsScanned,
                                 int tagsToBeScanned){
            this.mlvTagsScanned = mlvTagsScanned;
            this.tagsScanned = mlvTagsScanned.getValue();
            this.tagsToBeScanned = tagsToBeScanned;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Thread.sleep(3500);

                while (tagsScanned < tagsToBeScanned) {
                    mlvTagsScanned.postValue(
                            ++tagsScanned
                    );
                    Thread.sleep(1500);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;

        }

    }

}
