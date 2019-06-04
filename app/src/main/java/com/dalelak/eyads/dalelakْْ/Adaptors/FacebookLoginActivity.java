package com.dalelak.eyads.dalelakْْ.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dalelak.eyads.dalelakْْ.DataListUser;
import com.dalelak.eyads.dalelakْْ.MainActivity;
import com.dalelak.eyads.dalelakْْ.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;


public class FacebookLoginActivity extends AppCompatActivity {

    private static final String TAG = "FacebookLogin";
    private AccessTokenTracker accessTokenTracker;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private CallbackManager mCallbackManager;
    private static final String EMAIL = "email";
    private ProfileTracker profileTracker;

    private ImageView profile;
    private FirebaseDatabase database;
    private DataListUser dataListUser;
    private StorageReference mStorageRef;
    private FirebaseUser user;
    private Bitmap x;
    private ProgressBar progressBar;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        final LoginButton loginButton = findViewById(R.id.login_button);
        profile = (ImageView) findViewById(R.id.imageface);
        progressBar = (ProgressBar) findViewById(R.id.progrssfacebook);
        if(!isNetworkAvailable()){
            Toast.makeText(FacebookLoginActivity.this, R.string.internet_connection,Toast.LENGTH_LONG).show();
        }

        mAuth  = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        progressBar.setVisibility(View.INVISIBLE);

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
           profileTracker = new ProfileTracker() {
               @Override
               protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
               nextActivity(currentProfile);
               }
           };
           profileTracker.startTracking();
           accessTokenTracker.startTracking();
            }
        };

        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginButton.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                Profile profile = Profile.getCurrentProfile();

                nextActivity(profile);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {


            }

            @Override
            public void onError(FacebookException error) {

            }
        });




    }

    private void nextActivity(Profile currentProfile) {
        if(currentProfile!=null){
            Picasso.with(this).load(currentProfile.getProfilePictureUri(100,100)).into(profile);

            dataListUser = new DataListUser("1",currentProfile.getId(),currentProfile.getFirstName(),currentProfile.getLinkUri().toString(),currentProfile.getLastName()
         ,currentProfile.getProfilePictureUri(750,750).toString(),"l","yes","yes","yes","It is better to light a candle than to curse the darkness" );
        }

    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                             user = mAuth.getCurrentUser();
                            dataListUser.setId(user.getUid());
                            database.getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.getValue(DataListUser.class)==null){
                                        uploaddata();
                                    }
                                    Intent i = new Intent(FacebookLoginActivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);


    }
   public void uploaddata() {
        x = ((BitmapDrawable)profile.getDrawable()).getBitmap();



       ByteArrayOutputStream baos;
       baos = new ByteArrayOutputStream();
       x.compress(Bitmap.CompressFormat.JPEG, 100, baos);
       byte[] datax = baos.toByteArray();

       StorageReference mountainsRef = mStorageRef.child("Users/"+user.getUid()+"/thumb.jpg");
       UploadTask uploadTask = mountainsRef.putBytes(datax);


       uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                dataListUser.setImageUrlThumb(task.getResult().getDownloadUrl().toString());


                database.getReference("Users").child(user.getUid()).setValue(dataListUser);


            }
        });



   };
    @Override
    protected void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        if(profileTracker!=null){
        profileTracker.stopTracking();}
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}