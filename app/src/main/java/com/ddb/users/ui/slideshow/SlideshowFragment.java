package com.ddb.users.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddb.users.Entities.Parcel;
import com.ddb.users.R;
import com.ddb.users.ui.gallery.GalleryFragment;
import com.ddb.users.ui.gallery.GalleryViewModel;
import com.ddb.users.ui.home.TakeDeliveryRecycleViewAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public RecyclerView parcelsRecycleView;
    private List<Parcel> parcels;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        parcelsRecycleView = root.findViewById(R.id.parcelsHRecycleView);
        parcelsRecycleView.setHasFixedSize(true);
        parcelsRecycleView.setLayoutManager(new LinearLayoutManager(root.getContext()));


        try {

            slideshowViewModel.getParcels().observe(this, new Observer<List<Parcel>>() {
                @Override
                public void onChanged(List<Parcel> parcels_S) {
                    try {
                        if (parcelsRecycleView.getAdapter() == null) {
                            parcels = parcels_S;
                            parcelsRecycleView.setAdapter(new HistoryRecycleViewAdapter(SlideshowFragment.this.getContext(), parcels));
                        } else {
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