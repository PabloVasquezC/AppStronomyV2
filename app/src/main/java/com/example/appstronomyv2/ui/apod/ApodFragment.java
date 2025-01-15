package com.example.appstronomyv2.ui.apod;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.appstronomyv2.api.ApodResponse;
import com.example.appstronomyv2.api.ApodService;
import com.example.appstronomyv2.api.RetrofitInstance;
import com.example.appstronomyv2.data.database.AppDatabase;
import com.example.appstronomyv2.data.database.DatabaseClient;
import com.example.appstronomyv2.data.model.SavedApod;
import com.example.appstronomyv2.databinding.FragmentApodBinding;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApodFragment extends Fragment {

    private AppDatabase appDatabase;
    private FragmentApodBinding binding;
    private ApodViewModel apodViewModel;
    private ApodResponse currentApodResponse;
    private static final String API_KEY = "gsmdmboriTgUlxWQQPEJ22YuitgZpqsvS6seAd9O";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        apodViewModel = new ViewModelProvider(this).get(ApodViewModel.class);
        binding = FragmentApodBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        appDatabase = DatabaseClient.getInstance(requireContext()).getAppDatabase();

        binding.fab.setOnClickListener(v -> {
            if (currentApodResponse != null) {
                String userEmail = getArguments() != null ? getArguments().getString("USER_EMAIL") : null;
                if (userEmail != null) {
                    saveApodToDatabase(currentApodResponse, userEmail);
                } else {
                    Toast.makeText(requireContext(), "No se pudo obtener el email del usuario.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "No hay APOD para guardar.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnSelectDate.setOnClickListener(v -> showDatePickerDialog());

        fetchApodData(null);

        return root;
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    String selectedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
                    fetchApodData(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void fetchApodData(@Nullable String date) {
        ApodService service = RetrofitInstance.getRetrofitInstance().create(ApodService.class);
        Call<ApodResponse> call = service.getApod(API_KEY, date);

        call.enqueue(new Callback<ApodResponse>() {
            @Override
            public void onResponse(Call<ApodResponse> call, Response<ApodResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApodResponse apodResponse = response.body();
                    currentApodResponse = apodResponse;

                    binding.apodTitle.setText(apodResponse.getTitle());
                    binding.apodExplanation.setText(apodResponse.getExplanation());
                    Glide.with(requireContext())
                            .load(apodResponse.getUrl())
                            .into(binding.imageView);
                } else {
                    Toast.makeText(requireContext(), "Error al obtener el APOD.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApodResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveApodToDatabase(ApodResponse apodResponse, String userEmail) {
        SavedApod savedApod = new SavedApod();
        savedApod.setTitle(apodResponse.getTitle());
        savedApod.setExplanation(apodResponse.getExplanation());
        savedApod.setUrl(apodResponse.getUrl());
        savedApod.setUser_email(userEmail);

        // Guardar el APOD en la base de datos usando un hilo separado
        new Thread(() -> {
            appDatabase.savedApodDao().insert(savedApod);
            requireActivity().runOnUiThread(() ->
                    Toast.makeText(requireContext(), "🚀 Foto Astronómica del dia guardada correctamente.", Toast.LENGTH_SHORT).show()
            );
        }).start();
    }
}
