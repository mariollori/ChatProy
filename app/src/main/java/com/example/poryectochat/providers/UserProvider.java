package com.example.poryectochat.providers;

import com.example.poryectochat.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserProvider {

    CollectionReference mFirestore;

    public UserProvider(){
        mFirestore = FirebaseFirestore.getInstance().collection("Users");
    }

    public Task<Void> registraruser(User user){
       return  mFirestore.document(user.getId()).set(user);
    }
    public Task<DocumentSnapshot> getuser(String id){
         return mFirestore.document(id).get();
    }
    public Task<Void> update(User user){
        Map<String,Object> map = new HashMap<>();
        map.put("username",user.getUsername());
        return  mFirestore.document(user.getId()).update(map);
    }



}
