package com.ddb.users;

import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ddb.users.Entities.Address;
import com.ddb.users.Entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class loginTestCode extends AppCompatActivity {

    private static loginTestCode inst;
    private String mVerificationId;
    public  String phoneNumber;
    private FirebaseAuth mAuth;
    EditText editText;
    static {

    }

    public static loginTestCode instance() {
        return inst;
    }

    @Override
    protected void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onStop() {
        super.onStop();
        //inst = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editText = findViewById(R.id.code);
        mAuth= FirebaseAuth.getInstance();


        if(mAuth.getCurrentUser() == null){
            try {

                String t = getIntent().getStringExtra("number");
                sendVerificationCode(t);
                phoneNumber = t;
                //Toast.makeText(getApplicationContext(), t,Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_SHORT).show();
                //EditText code = findViewById(R.id.code);
                editText.setText(e.toString());
            }

        }else {
            if (!UserData.getUserEnteredData(getApplicationContext())) {
                setViewRegister();
            }
            Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.codecheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClickCodecheck();

            }
        });

    }


    void buttonOnClickCodecheck() {

        String code = editText.getText().toString().trim();
        if (code.isEmpty() || code.length() < 6) {
            editText.setError("Enter valid code");
            editText.requestFocus();
            return;
        }

        if (mAuth.getCurrentUser() != null) {

            return;
        }

        try {


            //verifying the code entered manually
            verifyVerificationCode(code);
            fieldsWaitingToFirebase();


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            //EditText code = findViewById(R.id.code);
            editText.setText(e.toString());
            fieldsStopWaitingToFirebase();
        }

    }
    private void sendVerificationCode(String mobile) {
        if(mobile.length() == 10 && mobile.toCharArray()[0] == '0'){
            mobile = mobile.substring(1);
        }

        try {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+972" + mobile,
                    60,
                    TimeUnit.SECONDS,
                    TaskExecutors.MAIN_THREAD,
                    mCallbacks);

            Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
        }

    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                editText.setText(code);
                verifyVerificationCode(code);
            } else {


                mAuth.signInWithCredential(phoneAuthCredential);
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(loginTestCode.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {
        try{
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            signInWithPhoneAuthCredential(credential);

        }catch (Exception e){

            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    private void addUserToFirebase() {
        TextView firstNameTextView = findViewById(R.id.first_name);
        TextView lastNameTextView = findViewById(R.id.last_name);
        TextView phoneNumberTextView = findViewById(R.id.phone_number);
        Spinner genderSpinner = findViewById(R.id.gender);
        TextView emailTextView = findViewById(R.id.email);
        TextView countryTextView = findViewById(R.id.country);
        TextView cityTextView = findViewById(R.id.city);
        TextView streetTextView = findViewById(R.id.street);
        TextView numberTextView = findViewById(R.id.number);
        TextView zipCodeTextView = findViewById(R.id.zipCode);
        if (firstNameTextView.getText().equals("") || firstNameTextView.getText().equals(" ") || firstNameTextView.getText().length() < 2) {
            firstNameTextView.setError("Enter valid Name");
            return;
        }
        if (lastNameTextView.getText().equals("") || lastNameTextView.getText().equals(" ") || lastNameTextView.getText().length() < 2) {
            lastNameTextView.setError("Enter valid Name");
            return;
        }

        if (emailTextView.getText().equals("") || emailTextView.getText().equals(" ") || emailTextView.getText().length() < 2) {
            emailTextView.setError("Enter valid Email");
            return;
        }
        String regex = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(emailTextView.getText());
        if (!matcher.matches()) {

            emailTextView.setError("Enter valid Email");
            return;
        }

        mAuth = FirebaseAuth.getInstance();
        DatabaseReference rootRef = FirebaseDatabase.getInstance("https://dblogisticare.firebaseio.com/").getReference("users");
        rootRef = rootRef.child(mAuth.getCurrentUser().getPhoneNumber());
        Location location = getLocationFromAddress(countryTextView.getText().toString() + " " + cityTextView.getText().toString() + " " + streetTextView.getText().toString() + " " + numberTextView.getText().toString());
        Address address = new Address(countryTextView.getText().toString(), cityTextView.getText().toString(), streetTextView.getText().toString(), numberTextView.getText().toString(), zipCodeTextView.getText().toString(), location.getLongitude(), location.getLatitude());
        User user = new User(mAuth.getCurrentUser().getPhoneNumber(), firstNameTextView.getText().toString(), lastNameTextView.getText().toString(), emailTextView.getText().toString(), address, User.stringToGender(genderSpinner.getSelectedItemId() + ""));
        rootRef.setValue(user);
        UserData.setUserEnteredData(getApplicationContext(), true);
        goToMainActivity();
    }

    public Location getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<android.location.Address> address;
        Location p1 = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            android.location.Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            p1 = new Location("a");
            p1.setLatitude(location.getLatitude());
            p1.setLongitude(location.getLongitude());
            return p1;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        return new Location("a");
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(loginTestCode.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            DatabaseReference rootRef = FirebaseDatabase.getInstance("https://dblogisticare.firebaseio.com/").getReference("users");
                            //rootRef.child("users");
                            String mobilet = phoneNumber;
                            if(mobilet.length() == 10 && mobilet.toCharArray()[0] == '0'){
                                mobilet = mobilet.substring(1);
                            }
                            final String mobile1 = "+972" + mobilet;
                            rootRef = rootRef.child(mobile1);
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() == null) {
                                        UserData.setUserEnteredData(getApplicationContext(), false);
                                        setViewRegister();


                                    } else {
                                        UserData.setUserEnteredData(getApplicationContext(), true);
                                        goToMainActivity();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            //Intent intent = new Intent(loginTestCode.this, MainActivity.class);
                            //startActivity(intent);
                            //loginTestCode.this.finish();

                        } else {
                            String message = "Error 09...";
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            findViewById(R.id.loading).setVisibility(View.GONE);
                            editText.setEnabled(true);
                            editText.setText("");
                            findViewById(R.id.codecheck).setClickable(true);


                        }
                    }
                });
    }

    private void setViewRegister() {
        findViewById(R.id.loginlayout).setVisibility(View.GONE);
        findViewById(R.id.register).setVisibility(View.VISIBLE);
        TextView phoneNumberTextView = findViewById(R.id.phone_number);
        mAuth = FirebaseAuth.getInstance();
        phoneNumberTextView.setText(mAuth.getCurrentUser().getPhoneNumber());
        Spinner spinnerBreakable = (Spinner) findViewById(R.id.gender);
        ArrayAdapter<CharSequence> adapter_breakable;
        adapter_breakable = ArrayAdapter.createFromResource(loginTestCode.this, R.array.gender_list, android.R.layout.simple_spinner_item);
        adapter_breakable.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBreakable.setAdapter(adapter_breakable);
        findViewById(R.id.buttonOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserToFirebase();
            }
        });
    }

    //////////////////////////////////////////

    void setEditTextCode(String sms) {

        editText = findViewById(R.id.code);
        editText.setText(sms);
        try {
            verifyVerificationCode(sms);
            fieldsWaitingToFirebase();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            editText.setText(e.toString());
            fieldsStopWaitingToFirebase();
        }

    }

    void goToMainActivity() {

        Intent intent = new Intent(loginTestCode.this, MainActivity.class);
        startActivity(intent);
        loginTestCode.this.finish();
    }

    void fieldsWaitingToFirebase() {

        editText.setEnabled(false);
        findViewById(R.id.codecheck).setClickable(false);
        findViewById(R.id.loading).setVisibility(View.VISIBLE);
    }

    void fieldsStopWaitingToFirebase() {
        editText.setEnabled(true);
        findViewById(R.id.codecheck).setClickable(true);
        findViewById(R.id.loading).setVisibility(View.GONE);
    }

}
