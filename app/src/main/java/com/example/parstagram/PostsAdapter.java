package com.example.parstagram;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram.Fragments.ComposeFragment;
import com.example.parstagram.Fragments.DetailFragment;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> allPosts) {
        this.context = context;
        this.posts = allPosts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        try {
            holder.bind(post);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private ImageView ivProfilePicture;
        private TextView tvCreatedDate;
        private TextView tvNumLikes;
        private ImageButton ibLike;
        public Post currentPost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivProfilePicture = itemView.findViewById(R.id.ivProfilePicture);
            tvCreatedDate = itemView.findViewById(R.id.tvCreatedDate);
            tvNumLikes = itemView.findViewById(R.id.tvNumLikes);
            ibLike = itemView.findViewById(R.id.ibLike);
            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("spqqrf",Integer.toString(getAdapterPosition()));
                    FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    //prepBundle(bundle);
                    bundle.putParcelable("post", Parcels.wrap(currentPost));
                    DetailFragment fragInfo = new DetailFragment();
                    fragInfo.setArguments(bundle);
                    FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.flContainer,fragInfo);
                    transaction.commit();
                }
            });
            ibLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boolean addedLike = currentPost.changeLike();
                    if (addedLike) {
                        tvNumLikes.setText(Integer.toString(currentPost.getLikes().size()));
                        ibLike.setImageResource(R.mipmap.ic_heart_filled_foreground);
                    } else {
                        tvNumLikes.setText(Integer.toString(currentPost.getLikes().size()));
                        ibLike.setImageResource(R.mipmap.ic_heart_clear_foreground);
                    }
                    currentPost.saveInBackground();
                }
            });
        }

        public void bind(Post post) throws ParseException {
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            currentPost = post;
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
            }
            String imageUrl = post.getUser().getParseFile("Picture").getUrl();
            Glide.with(context).load(imageUrl).into(ivProfilePicture);
            tvCreatedDate.setText(post.getCreatedAt().toString());
            ArrayList<String> likes = post.getLikes();
            for (int i = 0; i < likes.size();i++) {
                if (likes.get(i).equals(ParseUser.getCurrentUser().getUsername())) {
                    ibLike.setImageResource(R.mipmap.ic_heart_filled_foreground);
                }
            }
            tvNumLikes.setText(Integer.toString(likes.size()));
        }
    }
}
