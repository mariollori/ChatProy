package com.example.poryectochat.providers;

import android.content.Context;

import com.example.poryectochat.utilies.CompressorBitmapImage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;

public class ImageProvider {
    StorageReference mstorage;

    public ImageProvider(){
        mstorage = FirebaseStorage.getInstance().getReference();
    }
    public UploadTask save(Context context, File file){
        byte[] imagebyte = CompressorBitmapImage.getImage(context,file.getPath(),500,500);
        //Nombre de la imagen
        StorageReference storage = FirebaseStorage.getInstance().getReference().child(new Date() +".jgp");
        // le asignamos el storage
        mstorage = storage;
        UploadTask upl = storage.putBytes(imagebyte);
        return  upl;

    }

    //este objeto nos ayudara  a obtener la referencia de la imagen
    public StorageReference getstorage(){
        return mstorage;
    }
}
