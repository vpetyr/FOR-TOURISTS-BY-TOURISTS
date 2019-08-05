package com.example.fortouristsbytourists;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URL;

/**
 *  uploading picture in Firebase Storage
 */
public class Activity_11_Upload_Picture extends AppCompatActivity {

    public static final int REQUEST = 1;
    public static final String URL = "URL";
    Button photo, upload;
    ImageView iv;
    Uri url;    // URL in Firebase storage
    StorageReference sref;
    String attrName, userName;
    EditText et1102; //Attraction name - for File name
    boolean success = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_11__upload__picture);
        photo = findViewById(R.id.button1101);
        upload = findViewById(R.id.button1102);
        iv = findViewById(R.id.imageView1101);
        et1102 = findViewById(R.id.editText1102);
        userName = getIntent().getStringExtra("uName3");  // getting userName from repository

        sref = FirebaseStorage.getInstance().getReference("images");

        upload.setOnClickListener(new View.OnClickListener() {  // button "upload" selected for the uploading selected picture
            @Override
            public void onClick(View v) {
                success = true;
                try {
                        final StorageReference reference = sref.child(attrName + "." + getExt(url)); //setting a name of a Firebase Storage element

                        reference.putFile(url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String downloadURL = uri.toString();

                                        Intent intent = new Intent(Activity_11_Upload_Picture.this, Activity_09_Add_A_New_Attraction.class);
                                        intent.putExtra("URL", downloadURL);
                                        intent.putExtra("attrName", attrName);
                                        intent.putExtra("userName", userName);
                                        startActivity(intent);
                                    }
                                });

                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast myToast1 = Toast.makeText(getApplicationContext(), "Select a Photo", Toast.LENGTH_LONG);
                                myToast1.setGravity(Gravity.CENTER,0,650);
                                myToast1.show();
                            }
                        });

                } catch (NullPointerException ex) {
                    Toast myToast1 = Toast.makeText(getApplicationContext(), "Select a Photo", Toast.LENGTH_LONG);
                    myToast1.setGravity(Gravity.CENTER,0,650);
                    myToast1.show();
                }

            }
        });

        photo.setOnClickListener(new View.OnClickListener() {  // button "photo" selected, for the picture selection from SD
            @Override
            public void onClick(View v) {
                attrName = et1102.getText().toString();  // obligated to fill the attraction title first; it WILL be used as a key later
                if (attrName.length() == 0){
                    Toast myToast1 = Toast.makeText(getApplicationContext(),"Write Attraction Name", Toast.LENGTH_LONG );
                    myToast1.setGravity(Gravity.CENTER,0,650);
                    myToast1.show();
                    return;
                }
                Intent i = new Intent();
                i.setType("image/*");       // all images type
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, REQUEST); // getting a photo from the device storage
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_OK && data.getData() != null){
            url = data.getData();
            Picasso.get().load(url).into(iv);
        }
    }

    private String getExt(Uri uri){     //get the extension of the image file
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }

//    private String getFileName(Uri uri) {
//        ContentResolver resolver = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(resolver.getType(uri));
//    }
}