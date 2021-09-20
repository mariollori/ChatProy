package com.example.poryectochat.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthProvider {

    private FirebaseAuth mAuth;
    public AuthProvider(){
        mAuth = FirebaseAuth.getInstance();

    }
    public Task<AuthResult> login(String email, String password){
       return mAuth.signInWithEmailAndPassword(email,password);
    }

    public Task<AuthResult> googlelogin(String token){
         AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
        return mAuth.signInWithCredential(credential);

    }

    public String getUid(){
        if(mAuth.getCurrentUser() != null){
            return mAuth.getCurrentUser().getUid();
        }else{
            return null;
        }

    }
    public String getemail(){
        if(mAuth.getCurrentUser() != null) {
            return mAuth.getCurrentUser().getEmail();
        }else{
            return null;
        }
    }
    public Task<AuthResult> registeremailpass(String email, String password){
        return  mAuth.createUserWithEmailAndPassword(email,password);
    }
}
