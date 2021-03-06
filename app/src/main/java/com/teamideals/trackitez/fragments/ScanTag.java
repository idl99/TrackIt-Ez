package com.teamideals.trackitez.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teamideals.trackitez.R;
import com.teamideals.trackitez.activities.AddUnitActivity;
import com.teamideals.trackitez.databinding.FragmentScanTagBinding;
import com.teamideals.trackitez.entities.NfcTag;
import com.teamideals.trackitez.listeners.OnTagScanListener;
import com.teamideals.trackitez.listeners.TagScanNotifier;
import com.teamideals.trackitez.viewmodels.AddUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScanTag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScanTag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanTag extends Fragment implements OnTagScanListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private AddUnit mAddUnitViewModel;

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

        mAddUnitViewModel = ViewModelProviders.of(getActivity()).get(AddUnit.class);

        setRetainInstance(true);

        TagScanNotifier tagScanNotifier = new TagScanNotifier(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragments
        FragmentScanTagBinding fragmentScanTagBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_scan_tag, container, false
        );

        fragmentScanTagBinding.setLifecycleOwner(this);
        fragmentScanTagBinding.setItemName(mAddUnitViewModel.getItem().getItemName());
        fragmentScanTagBinding.setTagsToScan(mAddUnitViewModel.getListOfUnits().size());
        fragmentScanTagBinding.setExpiryDate(mAddUnitViewModel.getExpiryDate());

        final Observer<Integer> tagsScannedObserver = integer -> {
            Log.d("My_Log", "Number of tags scanned: " + integer);
            fragmentScanTagBinding.setTagsScanned(integer);
        };

        mAddUnitViewModel.getTagsScanned().observe(this, tagsScannedObserver);

        View view = fragmentScanTagBinding.getRoot();

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mButtonNext.setOnClickListener(v -> {
            mAddUnitViewModel.writeUnits();
            ((AddUnitActivity) getActivity()).goToSummary();
        });

        invalidateButton();

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

    public void invalidateButton(){
        if (mAddUnitViewModel.getTagsScanned().getValue()
                == mAddUnitViewModel.getQuantity()){
            mButtonNext.setEnabled(true);
        }

    }

    @Override
    public void newTagScanned(NfcTag nfcTag) {
        mAddUnitViewModel.onTagScanned(nfcTag.getUid());
        invalidateButton();
    }

    @Override
    public void inUseTagScanned(NfcTag nfcTag) {
        Log.d("My_Log","Tag already in use");
    }

    @Override
    public void notInUseTagScanned(NfcTag nfcTag) {
        mAddUnitViewModel.onTagScanned(nfcTag.getUid());
        invalidateButton();
    }

}
