package com.example.appstronomyv2.ui.apod;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ApodViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ApodViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is apod fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    // Nuevo método para actualizar el texto
    public void setText(String text) {
        mText.setValue(text);
    }
}
