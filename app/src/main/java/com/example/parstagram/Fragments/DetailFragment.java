package com.example.parstagram.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.parstagram.Comment;
import com.example.parstagram.CommentsAdapter;
import com.example.parstagram.Post;
import com.example.parstagram.PostsAdapter;
import com.example.parstagram.R;
import com.example.parstagram.databinding.FragmentDetailBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class DetailFragment extends Fragment {

    public static final String TAG = "DetailFragment";

    FragmentDetailBinding binding;
    TextView tvUsername;
    TextView tvDescription;
    TextView tvTimestamp;
    ImageView ivImage;
    EditText etComment;
    Button btnSubmit;
    RecyclerView rvComments;
    Bundle bundle;
    Post post;
    TextView tvNumLikes;
    ImageButton ibLike;
    CommentsAdapter adapter;
    List<Comment> commentList;


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
        post = Parcels.unwrap(bundle.getParcelable("post"));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvUsername = binding.tvUsername;
        tvDescription = binding.tvDescription;
        tvTimestamp = binding.tvTimestamp;
        ivImage = binding.ivImage;
        rvComments = binding.rvComments;
        tvNumLikes = binding.tvNumLikes;
        tvNumLikes.setText(Integer.toString(post.getLikes().size()));
        ibLike = binding.ibLike;
        ArrayList<String> likes = post.getLikes();
        for (int i = 0; i < likes.size();i++) {
            if (likes.get(i).equals(ParseUser.getCurrentUser().getUsername())) {
                ibLike.setImageResource(R.mipmap.ic_heart_filled_foreground);
            }
        }
        commentList = new ArrayList<>();
        adapter = new CommentsAdapter(getContext(), commentList);
        rvComments.setAdapter(adapter);
        rvComments.setLayoutManager(new LinearLayoutManager(getContext()));
        queryComments();
        //TODO fix comment recycler view
        tvUsername.setText(post.getUser().getUsername());
        tvDescription.setText(post.getDescription());
        Glide.with(getContext()).load(post.getImage().getUrl()).into(ivImage);
        tvTimestamp.setText(post.getCreatedAt().toString());

        etComment = binding.etComment;
        btnSubmit = binding.btnSubmit;
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = etComment.getText().toString();
                if (comment.isEmpty()) {
                    Toast.makeText(getContext(), "Comment can't be empty", Toast.LENGTH_SHORT).show();
                } else if (comment.length() > 500) {
                    Toast.makeText(getContext(), "Comment is too long", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Posting Comment", Toast.LENGTH_SHORT).show();
                    saveComment(comment,ParseUser.getCurrentUser());
                }
            }
        });

        ibLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean addedLike = post.changeLike();
                tvNumLikes.setText(Integer.toString(post.getLikes().size()));
                if (addedLike) {
                    ibLike.setImageResource(R.mipmap.ic_heart_filled_foreground);
                } else {
                    ibLike.setImageResource(R.mipmap.ic_heart_clear_foreground);
                }
                post.saveInBackground();
            }
        });

    }

    private void saveComment(String text, ParseUser currentUser) {
        final Comment comment = new Comment();
        comment.setPost(post);
        comment.setText(text);
        comment.setUser(currentUser);
        comment.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG,"error while saving" + e);
                    return;
                }
                Log.i(TAG,"comment saved");
                etComment.setText("");
                commentList.add(0,comment);
                adapter.notifyDataSetChanged();
            }
        });

    }
    protected void queryComments() {
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.include(Comment.KEY_POST);
        query.setLimit(20);
        query.whereEqualTo(Comment.KEY_POST, post);
        query.addDescendingOrder(Comment.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"issue");
                    return;
                }
                for (Comment comment : comments) {
                    Log.i(TAG,post.getDescription());
                }
                commentList.addAll(comments);
                adapter.notifyDataSetChanged();
            }
        });
    }

}