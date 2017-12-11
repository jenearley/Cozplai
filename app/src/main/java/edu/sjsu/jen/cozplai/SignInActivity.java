package edu.sjsu.jen.cozplai;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;


public class SignInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String LOG_TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private LinearLayout profileSection;
    private LinearLayout loginSection;
    private SignInButton signInButton;
    public static GoogleApiClient mGoogleApiClient;
    private TextView name;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        profileSection = (LinearLayout) findViewById(R.id.profile_section);
        profileSection.setVisibility(View.GONE);

        loginSection = (LinearLayout) findViewById(R.id.login_section);
        loginSection.setVisibility(View.VISIBLE);

        signInButton = (SignInButton) findViewById(R.id.signin_button);
        signInButton.setOnClickListener(this);

        name = (TextView) findViewById(R.id.user_name_textview);
        email = (TextView) findViewById(R.id.user_email_textview);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin_button:
                signIn();
                break;
        }
    }

    private void handleResult(GoogleSignInResult result){
        if(result.isSuccess()){
            Log.d(LOG_TAG, "handleResult: Success");
            GoogleSignInAccount account = result.getSignInAccount();
            name.setText(account.getDisplayName());
            email.setText(account.getEmail());
            continueMain(null);
        } else {
            Log.d(LOG_TAG, "handleResult: FAIL: " + result.getStatus());
            updateUI(false);
        }
    }

    private void updateUI(boolean isLogin){
        if(isLogin){
            profileSection.setVisibility(View.VISIBLE);
            loginSection.setVisibility(View.GONE);
        } else {
            profileSection.setVisibility(View.GONE);
            loginSection.setVisibility(View.VISIBLE);
        }
    }

    private void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(intent, RC_SIGN_IN);
    }

    public void continueMain(View view){
        Intent intent = new Intent(this, CharacterListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //TODO: HANDLE THIS
        Log.e(LOG_TAG, "onConnectionFailed: ");
    }
}
