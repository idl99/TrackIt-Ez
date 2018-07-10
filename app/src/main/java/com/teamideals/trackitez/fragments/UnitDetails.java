package com.teamideals.trackitez.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.teamideals.trackitez.R;
import com.teamideals.trackitez.activities.AddUnitActivity;
import com.teamideals.trackitez.entities.Item;
import com.teamideals.trackitez.entities.ItemCategory;
import com.teamideals.trackitez.viewmodels.AddUnit;
import com.teamideals.trackitez.viewmodels.UnitGroupList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UnitDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UnitDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UnitDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private AddUnit mAddUnitViewModel;
    private UnitGroupList mUnitGroupListViewModel;

    private Unbinder unbinder;

    @BindView(R.id.actv_item_name)
    AutoCompleteTextView mItemName;
    @BindView(R.id.multiAutoCompleteTextView)
    MultiAutoCompleteTextView mItemCategories;
    @BindView(R.id.editText_quantity)
    EditText mQuantity;
    @BindView(R.id.editText_expiryDate)
    EditText mExpiryDate;
    @BindView(R.id.step1Btn)
    Button mStep1Btn;

    public UnitDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UnitDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static UnitDetails newInstance(String param1, String param2) {
        UnitDetails fragment = new UnitDetails();
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
        mUnitGroupListViewModel = ViewModelProviders.of(getActivity()).get(UnitGroupList.class);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_details,
                container, false);
        unbinder = ButterKnife.bind(this, view);

        mStep1Btn.setOnClickListener(v -> processItemDetails());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mItemName.setAdapter(new ArrayAdapter<UnitGroupList.UnitGroup>(getActivity(),
                android.R.layout.select_dialog_item,
                mUnitGroupListViewModel.getListOfUnit().getValue())
        );
        mItemName.setThreshold(3);
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

    public void processItemDetails() {

        // TODO : sort out barcode query & complete multiautocomplete for categories
        String barcode = String.valueOf(Math.round(1 + (Math.random() * Math.pow(10, 12))));
        List<ItemCategory> listOfCategories = new ArrayList<>();
        listOfCategories.add(ItemCategory.OTHER);

        Item item = new Item(mItemName.getText().toString(), listOfCategories);
        mAddUnitViewModel.setItem(item);

        mAddUnitViewModel.setExpiryDate(mExpiryDate.getEditableText().toString());
        mAddUnitViewModel.initListOfUnits(
                Integer.valueOf(mQuantity.getEditableText().toString()));

        ((AddUnitActivity) getActivity()).goToScanTag();
    }

}
