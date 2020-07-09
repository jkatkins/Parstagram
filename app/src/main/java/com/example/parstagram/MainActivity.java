package com.example.parstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parstagram.Fragments.ComposeFragment;
import com.example.parstagram.Fragments.PostsFragment;
import com.example.parstagram.Fragments.ProfileFragment;
import com.example.parstagram.Fragments.ProfileTopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final FragmentManager fragmentManager = getSupportFragmentManager();
    public static final String TAG = "MainActivity";
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_logout:
                        ParseUser.logOut();
                        fragment = null;
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                        break;
                    case R.id.action_home:
                        Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                        fragment = new PostsFragment();
                        if (fragmentManager.findFragmentById(R.id.flContainerTop) != null) {
                            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.flContainerTop)).commit();
                        }
                        break;
                    case R.id.action_compose:
                        Toast.makeText(MainActivity.this, "compose", Toast.LENGTH_SHORT).show();
                        fragment = new ComposeFragment();
                        if (fragmentManager.findFragmentById(R.id.flContainerTop) != null) {
                            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.flContainerTop)).commit();
                        }
                        break;
                    case R.id.action_profile:
                    default:
                        Toast.makeText(MainActivity.this, "profile", Toast.LENGTH_SHORT).show();
                        fragment = new ProfileFragment();
                        fragmentManager.beginTransaction().replace(R.id.flContainerTop,new ProfileTopFragment()).commit();
                        break;
                }
                if (fragment != null){
                    fragmentManager.beginTransaction().replace(R.id.flContainer,fragment).commit();
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}