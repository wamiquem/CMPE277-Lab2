package com.example.remembermystuff;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StuffDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StuffDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "name";
    private static final String ARG_PARAM3 = "location";

    // TODO: Rename and change types of parameters
    private int mId;
    private String mName;
    private String mLocation;

    public StuffDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Parameter 1.
     * @param name Parameter 2.
     * @param location Parameter 3.
     * @return A new instance of fragment StuffDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StuffDetailsFragment newInstance(int id, String name, String location) {
        StuffDetailsFragment fragment = new StuffDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, name);
        args.putString(ARG_PARAM3, location);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getInt(ARG_PARAM1);
            mName = getArguments().getString(ARG_PARAM2);
            mLocation = getArguments().getString(ARG_PARAM3);
        }
        setHasOptionsMenu(true);
        ((MainActivity) getActivity())
                .setActionBarTitle("Stuff Details");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stuff_details, container, false);
        TextView nameView = view.findViewById(R.id.detail_stuff_name);
        nameView.setText(mName);
        TextView locationView = view.findViewById(R.id.detail_stuff_location);
        locationView.setText(mLocation);
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //clear the menu/hide the icon & disable the search access/function ...
        //this will clear the menu entirely, so rewrite/draw the menu items after if needed
        menu.clear();
    }

}
