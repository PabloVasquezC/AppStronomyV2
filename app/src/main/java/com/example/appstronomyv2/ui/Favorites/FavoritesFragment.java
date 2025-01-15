package com.example.appstronomyv2.ui.Favorites;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appstronomyv2.R;
import com.example.appstronomyv2.data.database.AppDatabase;
import com.example.appstronomyv2.data.database.DatabaseClient;
import com.example.appstronomyv2.data.model.SavedApod;
import com.example.appstronomyv2.databinding.FragmentFavoritesBinding;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment implements FavoritesAdapter.OnDeleteClickListener {

    private FavoritesViewModel mViewModel;
    private AppDatabase appDatabase;
    private FragmentFavoritesBinding binding;
    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private List<SavedApod> apodList = new ArrayList<>();
    private String userEmail;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);

        // Configuración del RecyclerView
        recyclerView = binding.recyclerViewFavorites;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener la instancia de la base de datos
        appDatabase = DatabaseClient.getInstance(requireContext()).getAppDatabase();

        // Obtener el email del usuario desde los argumentos
        if (getArguments() != null) {
            userEmail = getArguments().getString("USER_EMAIL");
        }

        // Cargar datos en el RecyclerView
        loadApods();

        return binding.getRoot();
    }

    private void loadApods() {
        new Thread(() -> {
            // Consultar las fotos guardadas del usuario
            apodList = appDatabase.savedApodDao().getApodsByUser(userEmail);

            // Actualizar el RecyclerView en el hilo principal
            requireActivity().runOnUiThread(() -> {
                // Pasar la lógica de eliminación al adapter
                adapter = new FavoritesAdapter(apodList, this);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }

    @Override
    public void onDeleteClick(SavedApod apod) {
        // Llamar a la función para eliminar el apod de la base de datos
        deleteSavedApod(apod);
    }

    // Función de eliminación
    private void deleteSavedApod(SavedApod apod) {
        new Thread(() -> {
            // Eliminar el apod de la base de datos
            appDatabase.savedApodDao().deleteApodsByUser(apod.getUser_email());

            // Actualizar la UI en el hilo principal
            requireActivity().runOnUiThread(() -> {
                // Eliminar el apod de la lista y actualizar el RecyclerView
                apodList.remove(apod);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

