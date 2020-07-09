package com.example.parstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parstagram.databinding.CommentBinding;
import com.parse.ParseException;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {


    private Context context;
    private List<Comment> comments;

    public CommentsAdapter(Context context, List<Comment> allComments) {
        this.context = context;
        this.comments = allComments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment,parent,false);
        return new CommentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        try {
            holder.bind(comment);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvUsername;
        TextView tvText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tvText);
            tvUsername = itemView.findViewById(R.id.tvUsername);
        }


        public void bind(Comment comment) throws ParseException {
            tvUsername.setText(comment.getUser().getUsername());
            tvText.setText(comment.getText());
        }
    }
}
