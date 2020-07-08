package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parstagram.databinding.ActivityLoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    Button btnSignUp;
    ActivityLoginBinding binding;
    public static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (ParseUser.getCurrentUser() != null)  {
            goMainActivity();
        }

        etUsername = binding.etUsername;
        etPassword = binding.etPassword;
        btnLogin = binding.btnLogin;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"login pressed");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                LoginUser(username,password);
            }
        });
        btnSignUp = binding.btnSignUp;
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Signup Pressed");
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivityForResult(i,42); //42 is hardcoded request code
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 42) { //42 is hardcoded request code
            if (resultCode == RESULT_OK) {
                Log.i(TAG,"got result");
                if (ParseUser.getCurrentUser() != null)  {
                    Log.i(TAG,"instant login");
                    goMainActivity();
                }
            }
        }
    }

    private void LoginUser(String username, String password) {
        Log.i(TAG,"attempting login");
        //TODO
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"login issue");
                    return;
                }
                goMainActivity();
                Toast.makeText(LoginActivity.this, "login success!", Toast.LENGTH_SHORT).show();

            }

        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}