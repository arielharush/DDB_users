package com.ddb.users.ui.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ddb.users.R;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        //final TextView textView = root.findViewById(R.id.text_share);
        final TextView firstNameTextView = root.findViewById(R.id.first_name);
        final TextView lastNameTextView = root.findViewById(R.id.last_name);
        final TextView phoneNumberTextView = root.findViewById(R.id.phone_number);
        final TextView emailTextView = root.findViewById(R.id.email);
        final TextView countryTextView = root.findViewById(R.id.country);
        final TextView cityTextView = root.findViewById(R.id.city);
        final TextView streetTextView = root.findViewById(R.id.street);
        final TextView numberTextView = root.findViewById(R.id.number);
        final TextView zipCodeTextView = root.findViewById(R.id.zipCode);
        final Button okButton = root.findViewById(R.id.buttonOk);

        shareViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });
        return root;
    }
}