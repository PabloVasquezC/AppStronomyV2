package com.example.appstronomyv2.ui.Favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appstronomyv2.R;
import com.example.appstronomyv2.data.model.SavedApod;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private List<SavedApod> apodList;

    // Constructor
    public FavoritesAdapter(List<SavedApod> apodList) {
        this.apodList = apodList;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        SavedApod apod = apodList.get(position);

        holder.titleTextView.setText(apod.getTitle());
        holder.explanationTextView.setText(apod.getExplanation());

        // Usar Glide para cargar la imagen
        Glide.with(holder.itemView.getContext())
                .load(apod.getUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return apodList.size();
    }

    // ViewHolder interno
    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView explanationTextView;
        ImageView imageView;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.text_title);
            explanationTextView = itemView.findViewById(R.id.text_explanation);
            imageView = itemView.findViewById(R.id.image_apod);
        }
    }
}
