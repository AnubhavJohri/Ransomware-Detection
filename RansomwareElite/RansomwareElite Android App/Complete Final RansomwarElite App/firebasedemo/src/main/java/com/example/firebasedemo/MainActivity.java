package com.example.firebasedemo;
//LOGIN ACTIVITY
//Has:-
//1.)Google Sign-In
//2.)Manual Firebase Sign-In
//3.)Missing Facebook Sign-In

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 1;
    EditText pass,email;
    TextView tv1,tv2;
    Button btn;
    SignInButton signinbtn;
    String st1,st2;
    private ProgressDialog pd;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth.AuthStateListener authListener;
    private CallbackManager mCallbackManager;
    //private GoogleSignInAccount mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //These two lines hide the STATUS BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

       /* View decorView=getWindow().getDecorView();
        int uioptions=View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uioptions);*/

        initviews();
        mAuth=FirebaseAuth.getInstance();
        googleSignininfo();// Configure Google Sign In
        initializeFBbutton();// Initialize Facebook Login button
    }

    // Initialize Facebook Login button
    //called in onCreate Method
    private void initializeFBbutton() {
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.fblogin_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this, "facebook:onSuccess:" + loginResult, Toast.LENGTH_SHORT).show();
                //Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                //Log.d(TAG, "facebook:onCancel");
                // ...
            }
            @Override
            public void onError(FacebookException error) {
                //Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });
    }

    // Configure Google Sign In
    // called in onCreate method
    // Builds the Google Sign-In API
    private void googleSignininfo() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this, "Something Went Wrong,Couldn't Connect!", Toast.LENGTH_SHORT).show();

                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API).build();//Google Sign-in API is being Built
    }


    //Initializes all the views in the activity
    private void initviews() {
        email=(EditText)findViewById(R.id.edt1_main);
        pass=(EditText)findViewById(R.id.edt2_main);
        //st1=email.getText().toString();
        //st2=pass.getText().toString();
        pd=new ProgressDialog(this);

        btn=(Button)findViewById(R.id.btnmain1);
        btn.setOnClickListener(this);

        signinbtn=(SignInButton)findViewById(R.id.googlesigninbtnmain);
        signinbtn.setOnClickListener(this);

        tv1=(TextView)findViewById(R.id.tvmain1);
        tv1.setOnClickListener(this);

        tv2=(TextView)findViewById(R.id.tvmain2);
        tv2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) //Method Deals with all the Click Events
    {
        switch (view.getId())//1.)Login Button Click-Authenticates from Firebase and Goes to Dashboard.java
        {
            case R.id.btnmain1:
                loginUser();
                break;

            case R.id.tvmain1://2.)If not registered click here text view-Goes to Register.java
                Intent intent=new Intent(MainActivity.this,Register.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.tvmain2://3.)Forgot Password text view-Goes to ForgotPassword.java
                Intent i=new Intent(MainActivity.this,ForgotPassword.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                break;

            case R.id.googlesigninbtnmain://4.)Google Sign-in Button-Authenticates then goes to Dashboard.java
                signIn();
        }

    }



    //1.)Called on clicking Login Button
    //Used for Manual Firebase Login
    private void loginUser()
    {
        st1=email.getText().toString().trim();
        st2=pass.getText().toString().trim();
        if(st1.isEmpty())//VALIDATION
        {
            email.setError("Email-Id can't Be left Blank");
            email.requestFocus();
            return;
        }
        if(st2.isEmpty())
        {
            pass.setError("Password can't be left blank");
            pass.requestFocus();
            return;
        }//VALIDATION
        pd.setMessage("User Logging-In...");
        pd.show();

        mAuth.signInWithEmailAndPassword(st1, st2)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pd.dismiss();//////
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "User Successfully Logged-In!", Toast.LENGTH_SHORT).show();
                            if(st1.equals("admin@gmail.com")&&st2.equals("123456"))
                            {
                                Intent in=new Intent(getApplicationContext(),Admin.class);
                                startActivity(in);
                                finish();
                            }
                            else
                            {
                                Intent in=new Intent(getApplicationContext(),Menu.class);
                                //in.putExtra("a",st1);
                                startActivity(in);
                                finish();
                            }
                            //in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        } else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                            pd.dismiss();//////
                            Toast.makeText(getApplicationContext(),"Your Password/Email-Id is Wrong",Toast.LENGTH_SHORT).show();
                        }else if(task.getException() instanceof FirebaseAuthInvalidUserException){
                            pd.dismiss();//////
                            Toast.makeText(getApplicationContext(),"You are not a registered User!",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            pd.dismiss();//////
                            Toast.makeText(getApplicationContext(),"Authentication Failed! "+task.getException(),Toast.LENGTH_SHORT).show();
                        }// ...
                    }
                });
    }

    //2.)Used for FB Login
    private void handleFacebookAccessToken(AccessToken token) {
       // Log.d(TAG, "handleFacebookAccessToken:" + token);

        pd.setMessage("Signing-In User...");
        pd.show();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(MainActivity.this, "Sign-In Success!", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent in=new Intent(getApplicationContext(),Menu.class);
                            //in.putExtra("a",st1);
                            startActivity(in);
                            pd.dismiss();
                            finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                           // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Sign-In Failed!"+task.getException(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                            pd.dismiss();
                        }

                        // ...
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
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                //Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show();
                // ...
            }
        }

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        //Toast.makeText(this, "firebaseAuthWithGoogle:" + acct.getId(), Toast.LENGTH_SHORT).show();
        pd.setMessage("Signing-In User...");
        pd.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "User Successfully Signed-In", Toast.LENGTH_SHORT).show();
                            Intent in=new Intent(getApplicationContext(),Menu.class);
                            //in.putExtra("a",st1);
                            //in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            pd.dismiss();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Sign-in Failed!"+task.getException(), Toast.LENGTH_SHORT).show();
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                            pd.dismiss();
                        }

                        // ...
                    }
                });
    }
}
