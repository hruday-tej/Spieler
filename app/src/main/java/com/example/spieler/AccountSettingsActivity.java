package com.example.spieler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
//import id.zelory.compressor.Compressor;

public class AccountSettingsActivity extends AppCompatActivity {

    private DatabaseReference muserdb;
    private FirebaseUser muser;

    private CircleImageView mdispimg;
    private TextView mname;
    private TextView mstatus;
    private Button mb;
    private Button mimage;
//    private Button changename;

    private StorageReference mimagestorage;

    private ProgressDialog mprog;

    private static final int GalleryPick = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

//        changename = (Button)findViewById(R.id.accnt_settings_username);
        mdispimg = (CircleImageView)findViewById(R.id.circleImageView);
        mname = (TextView)findViewById(R.id.settings_display_name);
        mstatus = (TextView)findViewById(R.id.settings_status);
        mb = (Button)findViewById(R.id.settings_status_button);
        muser = FirebaseAuth.getInstance().getCurrentUser();
        mimage = (Button)findViewById(R.id.settings_change_image);
        String curruser = muser.getUid();
        muserdb = FirebaseDatabase.getInstance().getReference().child("Users").child(curruser);
        mimagestorage = FirebaseStorage.getInstance().getReference();

//        changename.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        muserdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String Name = dataSnapshot.child("Name").getValue().toString();
                String img = dataSnapshot.child("Image").getValue().toString();
                String status = dataSnapshot.child("Status").getValue().toString();
                String thumb_img = dataSnapshot.child("Thumb_img").getValue().toString();

                mname.setText(Name.toUpperCase());
                mstatus.setText(status);
                Picasso.get().load(img).into(mdispimg);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status_value = mstatus.getText().toString();
                Intent statuschng= new Intent(AccountSettingsActivity.this,StatusActivity.class);
                statuschng.putExtra("status_value",status_value);
                startActivity(statuschng);
            }
        });
        mimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryintent = new Intent();
                galleryintent.setType("image/*");
                galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryintent, "SELECT DP"),GalleryPick);
//                CropImage.activity()
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .start(AccountSettingsActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GalleryPick && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mprog = new ProgressDialog(AccountSettingsActivity.this);
                mprog.setTitle("Changing your DP");
                mprog.setMessage("Hold on to your BUTTS while we upload your DP");
                mprog.setCanceledOnTouchOutside(false);
                mprog.show();



                Uri resultUri = result.getUri();
                String user = muser.getUid();
                File thumb_filePath = new File(resultUri.getPath());

//                Bitmap thumb_bitmap = new Compressor(this)
//                        .setMaxWidth(200)
//                        .setMaxHeight(200)
//                        .setQuality(75)
//                        .compressToBitmap(thumb_filePath);
//
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
//                byte[] thumb_byte = baos.toByteArray();

                StorageReference filepath = mimagestorage.child("profile_images").child(user+".jpg");

//                StorageReference thumb_filepath = mimagestorage.child("profile_images").child("thumbs").child(mus)

                filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> downloaduri = taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()){
                                    mprog.dismiss();
                                    Uri download = task.getResult();
                                    muserdb.child("Image").setValue(download.toString());
                                    Toast.makeText(AccountSettingsActivity.this,"You'r new DP looks LITT!!",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(AccountSettingsActivity.this,"Uh Oh! Looks like an Error",Toast.LENGTH_LONG).show();

                                }
                            }
                        });


                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
