package com.example.poryectochat.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poryectochat.R;
import com.example.poryectochat.models.Post;
import com.example.poryectochat.providers.AuthProvider;
import com.example.poryectochat.providers.ImageProvider;
import com.example.poryectochat.providers.PostProvider;
import com.example.poryectochat.utilies.FileUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import dmax.dialog.SpotsDialog;

public class PostActivity extends AppCompatActivity {
    AlertDialog dialog;
    File mimage;
    File mimage2;
  Button btnpost;
  ImageProvider imgpro;
  PostProvider pspro;
  ImageView imageupl2;
    ImageView imageupl1;
    TextInputEditText nombregame;
    TextInputEditText description;
    TextView categoritext;
    ImageView pc;
    int CODE_1 =1;
    int CODE_2=2;
    String titulo;
    String descripcion;
    AuthProvider mauth;
    ImageView xbox;
    ImageView playstation;
    ImageView nintendo;
    String mcat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        imgpro = new ImageProvider();

        mauth = new AuthProvider();
        imageupl2 = findViewById(R.id.imageupload2);
        imageupl1 = findViewById(R.id.imageupload1);
        btnpost = findViewById(R.id.btnpostpub);
        nombregame = findViewById(R.id.nmjuego);
        description = findViewById(R.id.descripcion);
        categoritext = findViewById(R.id.categoritext);
        pc = findViewById(R.id.imgpc);
        xbox= findViewById(R.id.imgxbox);
        dialog = new SpotsDialog.Builder().setContext(this).setMessage("Espere unos momentos ...").setCancelable(false).build();
        playstation = findViewById(R.id.imgps4);
        nintendo = findViewById(R.id.imgnin);
        pspro = new PostProvider();

        imageupl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(CODE_1);
            }
        });
        imageupl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(CODE_2);
            }
        });
        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickpost();
            }
        });
        pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcat= "PC";
                categoritext.setText("PC");
            }
        });
        xbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcat= "XBOX";
                categoritext.setText("XBOX");
            }
        });
        playstation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcat= "PS4"; categoritext.setText("PS4");
            }
        });
        nintendo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcat= "NINTENDO";
                categoritext.setText("NINTENDO");
            }
        });
    }

    private void clickpost() {
         titulo = nombregame.getText().toString();
         descripcion = description.getText().toString();
        if (!titulo.isEmpty() && !descripcion.isEmpty() && !mcat.isEmpty()){
            if(mimage != null){
                saveimage();
            }else{
                Toast.makeText(this, "Debe escoger una imagen", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Debe rellenar todos los campos ", Toast.LENGTH_LONG).show();

        }

    }

    private void saveimage() {
        dialog.show();
        imgpro.save(PostActivity.this,mimage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    imgpro.getstorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Post pos = new Post();
                            final String url = uri.toString();
                            imgpro.save(PostActivity.this,mimage2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task2) {
                                    if(task2.isSuccessful()){
                                        imgpro.getstorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri2) {
                                                String url2 = uri2.toString();
                                                pos.setImg1(url);
                                                pos.setImg2(url2);
                                                pos.setCategory(mcat);
                                                pos.setDescripcion(descripcion);
                                                pos.setName(titulo);
                                                pos.setIduser(mauth.getUid());
                                                pspro.save(pos).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> tasksave) {
                                                        dialog.dismiss();
                                                        if(tasksave.isSuccessful()){
                                                            clearform();
                                                            Toast.makeText(PostActivity.this, "La info se alamceno correctamente", Toast.LENGTH_SHORT).show();
                                                        }else{
                                                            Toast.makeText(PostActivity.this, "No se pudo almacenar n la BD we", Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                });
                                            }
                                        });

                                    }else{
                                        dialog.dismiss();
                                        Toast.makeText(PostActivity.this, "La imagen 2 no se pudo amacenar", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });




                        }
                    });
                }else{
                    dialog.dismiss();
                    Toast.makeText(PostActivity.this, "La imagen 1 no se pudo amacenar", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void clearform() {
        mcat = "";
        titulo ="";
        descripcion="";
        imageupl1.setImageResource(R.drawable.cmrapng2);
        imageupl2.setImageResource(R.drawable.cmrapng2);
        nombregame.setText("");
        description.setText("");
        mimage = null;
        mimage2= null;
        categoritext.setText("CATEGORIA");

    }

    private void openGallery(int code) {
        Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imageIntent.setType("image/*");
        startActivityForResult(imageIntent,code);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_1 && resultCode== RESULT_OK){
            try {
                //Transformar uri en archivo
                mimage= FileUtil.from(this,data.getData());
                //para poner la imagen en el imageview
                imageupl1.setImageBitmap(BitmapFactory.decodeFile(mimage.getAbsolutePath()));


            }catch (Exception e){
                Log.d("Error","Se producjo un error" + e.getMessage());
                Toast.makeText(this, "Se produjo un error" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }
        if(requestCode == CODE_2  && resultCode== RESULT_OK){
            try {
                //Transformar uri en archivo
                mimage2= FileUtil.from(this,data.getData());
                //para poner la imagen en el imageview
                imageupl2.setImageBitmap(BitmapFactory.decodeFile(mimage2.getAbsolutePath()));


            }catch (Exception e){
                Log.d("Error","Se producjo un error" + e.getMessage());
                Toast.makeText(this, "Se produjo un error" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }
    }
}
