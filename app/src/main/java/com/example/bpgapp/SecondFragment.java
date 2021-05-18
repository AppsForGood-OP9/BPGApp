package com.example.bpgapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bpgapp.ui.BPFragment;

/**
 * The SecondFragment class corresponds to the Add Entry page and allows the user to select adding a blood pressure or glucose entry.
 */
public class SecondFragment extends Fragment {
    //Initialize variables
    private TextView PercentZoom;
    private ImageButton Large;
    private ImageButton Small;
    private int zoom = 100;
    private TextView makeEntry;
    private ImageButton heart;
    private ImageButton drop;
    private Button PressureButton;
    private ImageButton pressureImage;
    private Button glucoseButton;
    private ImageButton glucoseImage;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main, container, false);
        Button pressureButton = view.findViewById(R.id.BloodPressureButton);

        //Retrieve view objects from the xml
        makeEntry = (TextView) view.findViewById(R.id.Prompt);
        PercentZoom = (TextView) view.findViewById(R.id.percentZoom);
        Large = (ImageButton) view.findViewById(R.id.ZoomInButton);
        Small = (ImageButton) view.findViewById(R.id.ZoomOutButton);
        PressureButton = (Button) view.findViewById(R.id.BloodPressureButton);
        pressureImage = view.findViewById(R.id.HeartSymbolButton);
        glucoseButton = (Button) view.findViewById(R.id.BloodGlucoseButton);
        glucoseImage = view.findViewById(R.id.BloodSymbolButton);
        heart = (ImageButton) view.findViewById(R.id.HeartSymbolButton);
        drop = (ImageButton) view.findViewById(R.id.BloodSymbolButton);

        Large.setOnClickListener(new View.OnClickListener(){
            /**
             * Increases text size when plus sign magnifying button is clicked
             */
            @Override
            public void onClick(View v){
                makeEntry.setTextSize(21*(makeEntry.getTextSize())/40);
                PressureButton.setTextSize(21*(PressureButton.getTextSize())/40);
                glucoseButton.setTextSize(21*(glucoseButton.getTextSize())/40);
                zoom +=25;
                PercentZoom.setText(zoom+"%");
            }
        });

        Small.setOnClickListener(new View.OnClickListener(){
            /**
             * Decreases text size when minus sign magnifying button is clicked
             */
            @Override
            public void onClick(View v){
                makeEntry.setTextSize(19*(makeEntry.getTextSize())/40);
                PressureButton.setTextSize(19*(PressureButton.getTextSize())/40);
                glucoseButton.setTextSize(19*(glucoseButton.getTextSize())/40);
                zoom -=25;
                PercentZoom.setText(zoom+"%");
            }
        });

        pressureButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Navigates to the BPFragment when the blood pressure button is clicked
             * @param v the view
             */
            @Override
            public void onClick(View v) {
                Fragment fragment = new BPFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_main_blank, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

        });

        pressureImage.setOnClickListener(new View.OnClickListener() {
            /**
             * Navigates to the BPFragment when the blood pressure image is clicked
             * @param v the view
             */
            @Override
            public void onClick(View v) {
                Fragment fragment = new BPFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_main_blank, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

        });

        glucoseButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Navigates to the GFragment when the glucose button is clicked
             * @param v the view
             */
            @Override
            public void onClick(View v) {
                Fragment fragment = new GFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_main_blank, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        glucoseImage.setOnClickListener(new View.OnClickListener() {
            /**
             * Navigates to the GFragment when the glucose image is clicked
             * @param v the view
             */
            @Override
            public void onClick(View v) {
                Fragment fragment = new GFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_main_blank, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}
