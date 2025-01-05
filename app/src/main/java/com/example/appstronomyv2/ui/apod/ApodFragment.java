package com.example.appstronomyv2.ui.apod;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appstronomyv2.databinding.FragmentApodBinding;

import java.util.Calendar;

public class ApodFragment extends Fragment {

    private FragmentApodBinding binding;
    private ApodViewModel apodViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        apodViewModel = new ViewModelProvider(this).get(ApodViewModel.class);
        binding = FragmentApodBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar el botón para seleccionar la fecha
        binding.btnSelectDate.setOnClickListener(v -> showDatePickerDialog());

        // Observar el LiveData en el ViewModel para actualizar el texto
        apodViewModel.getText().observe(getViewLifecycleOwner(), binding.textApod::setText);

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
                    apodViewModel.setText("Fecha Seleccionada: " + selectedDate); // Usar setText
                },
                year,
                month,
                day
        );
        datePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
