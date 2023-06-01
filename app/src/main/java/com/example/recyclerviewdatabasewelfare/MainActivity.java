package com.example.recyclerviewdatabasewelfare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recyclerviewdatabasewelfare.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference();
        binding.btnSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendDataBtn();

            }
        });
        binding.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                startActivity(intent);
            }
        });
    }

    private void SendDataBtn() {
        String username = binding.edtName.getText().toString();
        String useremail = binding.edtEmail.getText().toString();
        String userage = binding.edtAge.getText().toString();
        String id = databaseReference.push().getKey();

        SampleModal sampleModal = new SampleModal(username, useremail,userage );
        databaseReference.child("Users").child(id).setValue(sampleModal)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "User details Inserted", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(MainActivity.this, "User details Fails", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}