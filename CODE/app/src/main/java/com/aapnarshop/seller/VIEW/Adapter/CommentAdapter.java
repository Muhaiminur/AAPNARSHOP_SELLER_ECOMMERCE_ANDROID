package com.aapnarshop.seller.VIEW.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aapnarshop.seller.Model.GET.Comment;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.databinding.RecyclerviewCommentBinding;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context context;
    private List<Comment> commentList;

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewCommentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recyclerview_comment,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Comment comment = commentList.get(position);
        holder.binding.recyclerviewCommentBuyerName.setText(comment.getBuyerName());
        holder.binding.recyclerviewCommentBuyerComment.setText(comment.getBuyerComment());


    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerviewCommentBinding binding;
        public ViewHolder(RecyclerviewCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
