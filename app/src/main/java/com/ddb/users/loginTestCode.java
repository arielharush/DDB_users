package com.ddb.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

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

            Toast.makeText(getApplicationContext(), mAuth.getCurrentUser().getPhoneNumber(),Toast.LENGTH_SHORT).show();


        }
        //mAuth.setLanguageCode("en");




        findViewById(R.id.codecheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editText.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    editText.setError("Enter valid code");
                    editText.requestFocus();
                    return;
                }

                if (mAuth.getCurrentUser() != null){
                    return;
                }

                try {


                    //verifying the code entered manually
                    verifyVerificationCode(code);
                    editText.setEnabled(false);
                    findViewById(R.id.codecheck).setClickable(false);
                    findViewById(R.id.loading).setVisibility(View.VISIBLE);


                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_SHORT).show();
                    //EditText code = findViewById(R.id.code);
                    editText.setText(e.toString());
                    editText.setEnabled(true);
                    findViewById(R.id.codecheck).setClickable(true);
                    findViewById(R.id.loading).setVisibility(View.GONE);
                }

            }
        });

       }


       void  updateList(String sms){

        editText = findViewById(R.id.code);
        editText.setText(sms);
           try {


               //verifying the code entered manually
               verifyVerificationCode(sms);
               editText.setEnabled(false);
               findViewById(R.id.codecheck).setClickable(false);
               findViewById(R.id.loading).setVisibility(View.VISIBLE);


           }catch (Exception e){
               Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_SHORT).show();
               //EditText code = findViewById(R.id.code);
               editText.setEnabled(true);
               findViewById(R.id.codecheck).setClickable(true);
               findViewById(R.id.loading).setVisibility(View.GONE);
               editText.setText(e.toString());

           }

       }


    private void sendVerificationCode(String mobile) {
        if(mobile.length() == 10 && mobile.toCharArray()[0] == '0'){

            mobile = mobile.substring(1);
            //Toast.makeText(getApplicationContext(),"dfdfdfdfdf1234",Toast.LENGTH_SHORT).show();
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+972" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }


    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                editText.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(loginTestCode.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };


    private void verifyVerificationCode(String code) {
        //creating the credential


        try{
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            //signing the user
            signInWithPhoneAuthCredential(credential);

        }catch (Exception e){

            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();


        }

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
                                //Toast.makeText(getApplicationContext(),"dfdfdfdfdf1234",Toast.LENGTH_SHORT).show();
                            }
                             final String mobile1 = "+972" +mobilet;
                            rootRef = rootRef.child(mobile1);
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 if (dataSnapshot.getValue() == null) {
                                     // The child doesn't exist
                                    // Toast.makeText(getApplicationContext(),mobile1 + " error",Toast.LENGTH_SHORT).show();

//verification successful we will start the profile activity
                                     Intent intent = new Intent(loginTestCode.this, MainActivity.class);
                                     startActivity(intent);
                                     loginTestCode.this.finish();
                                 }else {
                                     Toast.makeText(getApplicationContext(),mobile1 + " sec",Toast.LENGTH_SHORT).show();

                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });

                            //verification successful we will start the profile activity
                            //Intent intent = new Intent(loginTestCode.this, MainActivity.class);
                            //startActivity(intent);
                            //loginTestCode.this.finish();

                        } else {
                            String message = "Somthing is wrong, we will fix it soon...";
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




}
