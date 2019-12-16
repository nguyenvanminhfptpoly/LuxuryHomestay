package com.minhnv.luxuryhomestay.ui.main.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Comment;

public class CommentViewHolder extends RecyclerView.ViewHolder {
    private TextView tvUserName, tvComment, tvRating;
    private CommentViewHolder(@NonNull View itemView) {
        super(itemView);
        tvComment = itemView.findViewById(R.id.tvCmtHomestay);
        tvRating = itemView.findViewById(R.id.tvRatingHomestay);
        tvUserName = itemView.findViewById(R.id.tvUserNameCmt);
    }

    public void bind(Comment comment){
        tvUserName.setText(comment.getUsername());
        tvRating.setText(comment.getRating());
        tvComment.setText(comment.getComment());
    }

    public static CommentViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_homestay,parent,false);
        return new CommentViewHolder(view);
    }
}
