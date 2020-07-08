package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parstagram.databinding.ActivitySignUpBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    EditText etConfirmPassword;
    Button btnSignUp;
    ActivitySignUpBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        etUsername = binding.etUsername;
        etPassword = binding.etPassword;
        etConfirmPassword = binding.etConfirmPassword;
        btnSignUp = binding.btnSignUp;
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                if (password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Password can't be empty", Toast.LENGTH_SHORT).show();
                }
                else if (username.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Username can't be empty", Toast.LENGTH_SHORT).show();
                }
                else if (!confirmPassword.equals(password)) {
                    Toast.makeText(SignUpActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                }
                else {
                    SignUpUser(username,password);
                }
            }
        });
    }

    private void SignUpUser(final String username, final String password) {
        ParseUser user = new ParseUser();
// Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                    Intent data = new Intent();
                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, "Error signing up", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}