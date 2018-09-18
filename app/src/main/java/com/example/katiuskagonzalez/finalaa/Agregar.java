package com.example.katiuskagonzalez.finalaa;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.katiuskagonzalez.finalaa.models.agregar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class Agregar extends AppCompatActivity {


    private EditText Nombre;
    private EditText Descripcion;
    private EditText Ubicacion;
    private TextView subirImage;
    private StorageReference mStorage;
    private ImageView cargarImage;
    DatabaseReference mDataRe;
    private ProgressDialog progressDialog;
    private Uri mImageUri;
    private TextView txtClose;
    private StorageTask mTask;
    private static final int Gallery_Intent = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        progressDialog = new ProgressDialog(this);

        //Datos/Historia

        Nombre = findViewById(R.id.nombreAgre);
        Descripcion = findViewById(R.id.descripcionAgre);
        Ubicacion = findViewById(R.id.ubicacionAgre);
        cargarImage = findViewById(R.id.imgSubir);


        findViewById(R.id.txtclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Agregar.this, Home.class);
                startActivity(i);
            }
        });
        findViewById(R.id.btnPublicarHis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubirImg();
            }
        });

        //Abrir Galeria

        mStorage = FirebaseStorage.getInstance().getReference();


        findViewById(R.id.btnSubirImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbreGaleria();
            }
        });
    }

    public void AbreGaleria() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Gallery_Intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Intent && resultCode == RESULT_OK) {

            mImageUri = data.getData();
            Glide.with(Agregar.this).load(mImageUri).into(cargarImage);


        }

    }


    private  void SubirImg(){
        if(mImageUri != null){
            progressDialog.setMessage("Publicando");
            progressDialog.show();

            final StorageReference storageReference = mStorage.child("Fotos").child(mImageUri.getLastPathSegment());
            storageReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {

                                    Uri descargar = task.getResult();

                                   try {

                                       FirebaseDatabase database = FirebaseDatabase.getInstance();
                                       DatabaseReference mDataRef = database.getReference();


                                       Map<String, Object> Datos = new HashMap<>();
                                       Datos.put("Nombre", Nombre.getText().toString());
                                       Datos.put("Descripcion", Descripcion.getText().toString());
                                       Datos.put("Ubicacion", Ubicacion.getText().toString());
                                       Datos.put("Imagen", descargar.toString());


                                       mDataRef.child("Historia").push().setValue(Datos);
                                       Toast.makeText(Agregar.this, "Historia publicada", Toast.LENGTH_LONG).show();
                                   } catch(Exception e){
                                       System.out.print(e.getMessage());
                                   }
                                }
                            });
                        }
                    });
        }
    }
    }

