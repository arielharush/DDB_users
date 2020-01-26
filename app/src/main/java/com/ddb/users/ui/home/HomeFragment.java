package com.ddb.users.ui.home;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddb.users.Entities.Address;
import com.ddb.users.Entities.Parcel;
import com.ddb.users.Entities.User;
import com.ddb.users.MainActivity;
import com.ddb.users.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class HomeFragment extends Fragment {


    public RecyclerView parcelsRecycleView;
    private List<Parcel> parcels;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        parcelsRecycleView = root.findViewById(R.id.parcelsTDRecycleView);
        parcelsRecycleView.setHasFixedSize(true);
        parcelsRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));


        try {

            homeViewModel.getParcels().observe(this, new Observer<List<Parcel>>() {
                @Override
                public void onChanged(List<Parcel> parcels_S) {
                    try {
                        if (parcelsRecycleView.getAdapter() == null) {
                            parcels = parcels_S;
                            parcelsRecycleView.setAdapter(new TakeDeliveryRecycleViewAdapter(getActivity().getApplicationContext(), parcels));
                        } else {
                            parcels.clear();
                            parcels.addAll(parcels_S);
                            parcelsRecycleView.getAdapter().notifyDataSetChanged();


                        }

                    } catch (Exception e) {

                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        DatabaseReference rootRef = FirebaseDatabase.getInstance("https://dblogisticare.firebaseio.com/").getReference("users");
                        rootRef = rootRef.child("Errors");
                        rootRef.setValue(e.toString());
                    }

                }


            });


        } catch (Exception e) {


        }

        return root;
    }
}