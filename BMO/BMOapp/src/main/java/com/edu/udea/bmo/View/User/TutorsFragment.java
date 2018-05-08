package com.edu.udea.bmo.View.User;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.udea.bmo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorsFragment extends Fragment {


    public TutorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_tutors, container, false);
    }


}
