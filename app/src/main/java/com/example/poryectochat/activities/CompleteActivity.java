package com.example.poryectochat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.poryectochat.R;
import com.example.poryectochat.models.User;
import com.example.poryectochat.providers.AuthProvider;
import com.example.poryectochat.providers.UserProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class CompleteActivity extends AppCompatActivity {

    TextInputEditText user;
    Button btnconf;
    AuthProvider mAuth;
    UserProvider mfire;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        user = findViewById(R.id.usuarioconf);
        btnconf = findViewById(R.id.btnconf);
        mfire = new UserProvider();
        mAuth = new AuthProvider();
        dialog = new SpotsDialog.Builder().setContext(this).setMessage("Espere unos momentos ...").setCancelable(false).build();

        btnconf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register();
            }
        });



    }

    private void register() {
        String usuario = user.getText().toString();
        if(!usuario.isEmpty()){
            guardarenBD(usuario);
        }else{
            Toast.makeText(this, "El campo no se completo", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarenBD(String username) {
        String id = mAuth.getUid();
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        dialog.show();
       mfire.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                if(task.isSuccessful()){
                    Intent intent2 = new Intent(CompleteActivity.this,MainActivity2.class);
                    startActivity(intent2);
                }else{
                    Toast.makeText(CompleteActivity.this, "No se pudo guardar en BD", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}