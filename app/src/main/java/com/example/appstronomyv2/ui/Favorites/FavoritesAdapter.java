package com.example.appstronomyv2.ui.Favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appstronomyv2.R;
import com.example.appstronomyv2.data.model.SavedApod;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<SavedApod> apodList;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(SavedApod apod);
    }

    public FavoritesAdapter(List<SavedApod> apodList, OnDeleteClickListener onDeleteClickListener) {
        this.apodList = apodList;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SavedApod apod = apodList.get(position);
        holder.title.setText(apod.getTitle());
        holder.explanation.setText(apod.getExplanation());
        Glide.with(holder.image.getContext()).load(apod.getUrl()).into(holder.image);

        // Configurar el botón de eliminar
        holder.deleteButton.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(apod);
            }
        });
    }

    @Override
    public int getItemCount() {
        return apodList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView explanation;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_apod);
            title = itemView.findViewById(R.id.text_title);
            explanation = itemView.findViewById(R.id.text_explanation);
            deleteButton = itemView.findViewById(R.id.button_delete);
        }
    }
}

