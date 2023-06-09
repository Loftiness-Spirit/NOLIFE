package com.example.nolife;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;

import java.util.Objects;

public class FavouritesAdapter extends ListAdapter<Article, FavouriteViewHolder> {
    public FavouritesAdapter(@NonNull DiffUtil.ItemCallback<Article> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return FavouriteViewHolder.create(parent);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        Article current = getItem(position);
        holder.getTitle().setText(current.getTitle());
        holder.getDescription().setText(current.getDescription());
        holder.getContributorDate().setText(current.getAuthor()+
                " | "+ current.getPublishedAt().substring(0,10));
        holder.getImage().setDefaultImageResId(R.drawable.ic_launcher_background);
        holder.getImage().setErrorImageResId(R.drawable.ic_launcher_foreground);
        holder.getImage().setImageUrl(current.getUrlToImage());
        holder.getImage().setContentDescription(current.getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("url", current.getUrl());
                Navigation.findNavController(v).navigate(R.id.action_favourites_to_article2, bundle);
            }
        });
    }
    static class ArticleDiff extends DiffUtil.ItemCallback<Article> {

        @Override
        public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return Objects.equals(oldItem.getPublishedAt(), newItem.getPublishedAt());
        }
    }

}

class FavouriteViewHolder extends RecyclerView.ViewHolder{

    private TextView title, description, contributorDate;
    private ANImageView image;
    private FavouriteViewHolder(View itemView){
        super(itemView);
        title = itemView.findViewById(R.id.title_id);
        description=itemView.findViewById(R.id.description_id);
        image=itemView.findViewById(R.id.image_id);
        contributorDate=itemView.findViewById(R.id.contributordate_id);
    }

    public ANImageView getImage() {
        return image;
    }

    public TextView getContributorDate() {
        return contributorDate;
    }

    public TextView getDescription() {
        return description;
    }

    public TextView getTitle() {
        return title;
    }

    static FavouriteViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new FavouriteViewHolder(view);
    }
}
