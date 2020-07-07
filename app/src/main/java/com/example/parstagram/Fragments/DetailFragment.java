package com.example.parstagram.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.parstagram.Post;
import com.example.parstagram.R;
import com.example.parstagram.databinding.FragmentDetailBinding;

import org.parceler.Parcels;


public class DetailFragment extends Fragment {

    FragmentDetailBinding binding;
    TextView tvUsername;
    TextView tvDescription;
    TextView tvCaption;
    TextView tvTimestamp;
    ImageView ivImage;
    Bundle bundle;


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(getLayoutInflater());
        bundle = this.getArguments();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvUsername = binding.tvUsername;
        tvDescription = binding.tvDescription;
        tvCaption = binding.tvCaption;
        tvTimestamp = binding.tvTimestamp;
        ivImage = binding.ivImage;
        tvUsername.setText(bundle.getString("username"));
        tvDescription.setText(bundle.getString("description"));
        Glide.with(getContext()).load(bundle.getString("imageUrl")).into(ivImage);
        tvTimestamp.setText(bundle.getString("createdAt"));

    }
}