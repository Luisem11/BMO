package com.edu.udea.bmo.View.User;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.udea.bmo.R;
import com.edu.udea.bmo.SplashFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainUserFragment extends Fragment {


    public MainUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
;

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_user, container, false);
    }

    @Override
    public void onStart() {

        ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_main_user, new TutorsFragment()).addToBackStack(null)
                .commit();
        super.onStart();
    }
}
