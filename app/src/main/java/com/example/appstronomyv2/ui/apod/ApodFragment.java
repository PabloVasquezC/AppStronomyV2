package com.example.appstronomyv2.ui.apod;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appstronomyv2.databinding.FragmentApodBinding;

public class ApodFragment extends Fragment {

    private FragmentApodBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ApodViewModel apodViewModel =
                new ViewModelProvider(this).get(ApodViewModel.class);

        binding = FragmentApodBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textApod;
        apodViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}