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
import com.bumptech.glide.request.RequestOptions;
import com.example.appstronomyv2.MainActivity;
import com.example.appstronomyv2.R;
import com.example.appstronomyv2.api.ApodService;
import com.example.appstronomyv2.api.RetrofitInstance;
import com.example.appstronomyv2.api.ApodResponse;
import com.example.appstronomyv2.data.database.AppDatabase;
import com.example.appstronomyv2.data.database.DatabaseClient;
import com.example.appstronomyv2.data.model.UserPreference;
import com.example.appstronomyv2.databinding.FragmentApodBinding;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApodFragment extends Fragment {

    private AppDatabase appDatabase;
    private FragmentApodBinding binding;
    private ApodViewModel apodViewModel;
    private static final String API_KEY = "gsmdmboriTgUlxWQQPEJ22YuitgZpqsvS6seAd9O"; // Reemplaza con tu API Key

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        apodViewModel = new ViewModelProvider(this).get(ApodViewModel.class);
        binding = FragmentApodBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar la base de datos
        appDatabase = DatabaseClient.getInstance(requireContext()).getAppDatabase();

        setupFloatingActionButton();

        // Configurar el botón para seleccionar la fecha
        binding.btnSelectDate.setOnClickListener(v -> showDatePickerDialog());

        // Llamar a la API para obtener la imagen del día
        fetchApodData(null);

        return root;
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    fetchApodData(selectedDate);
                },
                year,
                month,
                day
        );

        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void setupFloatingActionButton() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UserPreference userPreference = new UserPreference();
                        userPreference.setItemId("item123");
                        userPreference.setLiked(true);

                        // Inserta el objeto en la base de datos
                        appDatabase.userPreferenceDao().insert(userPreference);

                        // Consulta todos los elementos de la base de datos
                        List<UserPreference> allPreferences = appDatabase.userPreferenceDao().getAllPreferences();
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Actualiza la UI con los resultados de la consulta
                                Toast.makeText(requireContext(), "Número de preferencias: " + allPreferences.size(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });
    }

    private void fetchApodData(@Nullable String date) {
        ApodService apiService = RetrofitInstance.getRetrofitInstance().create(ApodService.class);
        Call<ApodResponse> call = apiService.getApod(API_KEY, date);

        call.enqueue(new Callback<ApodResponse>() {
            @Override
            public void onResponse(Call<ApodResponse> call, Response<ApodResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApodResponse apod = response.body();

                    if ("image".equals(apod.getMediaType())) {
                        // Cargar la imagen con Glide
                        Glide.with(ApodFragment.this)
                                .load(apod.getUrl())
                                .into(binding.imageView);

                        // Mostrar el título y la descripción
                        binding.apodTitle.setText(apod.getTitle());
                        binding.apodExplanation.setText(apod.getExplanation());
                    } else {
                        Toast.makeText(requireContext(), "El contenido no es una imagen", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApodResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
