package com.abhi.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abhi.androidapp.databinding.ActivitySignInBinding;
import com.abhi.androidapp.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

// Firebase Auth
public class SignIn extends AppCompatActivity {

    EditText editPhone,editPassword;
    Button btnSignIn;
    ActivitySignInBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_in);

        editPassword = (MaterialEditText)binding.editPassword;
        editPhone = (MaterialEditText)binding.editPhone;
        btnSignIn=(Button)binding.btnSignIn;

        // init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user= database.getReference("user");
         btnSignIn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                 mDialog.setMessage("Just a moment...");
                 mDialog.show();
                 table_user.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         if(dataSnapshot.child(editPhone.getText().toString()).exists()){
                             mDialog.dismiss();
                             //get user info
                             User user = dataSnapshot.child(editPhone.getText().toString()).getValue(User.class);
                             if(user.getPassword().equals(editPassword.getText().toString())){

                                 Toast.makeText(SignIn.this, "SignIn Successfully !", Toast.LENGTH_SHORT).show();
                             }
                             else
                                 {
                                     mDialog.dismiss();
                                 Toast.makeText(SignIn.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                             }
                         }
                         else{
                             // If User does not exist
                             Toast.makeText(SignIn.this,"User doesn't exist",Toast.LENGTH_SHORT).show();

                         }

                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {


                     }
                 });

             }
         });




    }
}
