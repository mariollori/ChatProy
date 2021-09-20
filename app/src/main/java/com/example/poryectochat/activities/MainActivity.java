package com.example.poryectochat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poryectochat.R;
import com.example.poryectochat.models.User;
import com.example.poryectochat.providers.AuthProvider;
import com.example.poryectochat.providers.UserProvider;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
    //declaramos la variable de tipo textview
    TextView mtextregister;
    TextInputEditText loginem;
    TextInputEditText loginpass;
    Button logbutton;
    AuthProvider mAuth;
    private static final int RC_SIGN_IN = 1;
    private GoogleSignInClient mGoogleSignInClient;
    SignInButton googlebutton;
    UserProvider mfire;
    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // la variable le asiganmos el valor del textview de la vista
        mtextregister = findViewById(R.id.textregister);
        loginem = findViewById(R.id.loginemail);
        loginpass = findViewById(R.id.loginpassword);
        logbutton =  findViewById(R.id.login);
        mAuth = new AuthProvider();
        googlebutton = findViewById(R.id.googlebtn);
        mfire= new UserProvider();
         dialog = new SpotsDialog.Builder().setContext(this).setMessage("Espere unos momentos ...").setCancelable(false).build();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1030130327041-1d5rcuservqno772kefod00m8k1to03u.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googlebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


//le asignamos una funcion para que cuando den click suceda algo
        mtextregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Una Intent es un objeto de mensajería que puedes usar para solicitar una acción
                // de otro componente de una app. Si bien las intents facilitan la comunicación
                // entre componentes de varias formas
                Intent intent = new Intent(MainActivity.this,Register.class);
                //iniciamos el intent para que funcione
                // el intent recive 2 parametros, el contexto que indica en que parte de la aplicacion
                // estamos y la pagina a donde redigira.
                startActivity(intent);

            }
        });
        logbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("Error al acceder", "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        dialog.show();
        mAuth.googlelogin(idToken)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id = mAuth.getUid();
                            checkuser(id);


                        } else {
                            dialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w("Error 102", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "No se pudo acceder con google", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void checkuser(String id) {
        mfire.getuser(id).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    dialog.dismiss();
                    Intent intent3 = new Intent(MainActivity.this,MainActivity2.class);
                    intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent3);
                }else{
                    User user = new User();
                    String email = mAuth.getemail();
                    user.setEmail(email);
                    user.setId(id);


                    mfire.registraruser(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialog.dismiss();
                            if(task.isSuccessful()){
                                Intent intent3 = new Intent(MainActivity.this,CompleteActivity.class);
                                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent3);
                            }else{
                                Toast.makeText(MainActivity.this,"Error al guardar en BD",Toast.LENGTH_LONG).show();

                            }

                        }
                    });
                }
            }
        });

    }

    private void login(){
        String email = loginem.getText().toString();
        String password = loginpass.getText().toString();
        dialog.show();
        mAuth.login(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dialog.dismiss();
                if(task.isSuccessful()){
                    Intent intent2 = new Intent(MainActivity.this,MainActivity2.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);

                }else{
                    Toast.makeText(MainActivity.this, "Email o password incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Log.d("Campo","email:" + email);
        Log.d("Campo","Password:" + password);

    }
}