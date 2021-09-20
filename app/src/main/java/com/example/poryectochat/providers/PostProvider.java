package com.example.poryectochat.providers;

import com.example.poryectochat.models.Post;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostProvider {
    CollectionReference mcollection;

    public PostProvider() {
        mcollection = FirebaseFirestore.getInstance().collection("Posts");
    }
    public Task<Void> save(Post ps){
        //En document() Antes le asignabamos el id de la sesion del usuario pero como ahora son varias subidas deimagenes
        // necesitamos que firebase genere automaticamente los ids.
        return mcollection.document().set(ps);
    }
}
