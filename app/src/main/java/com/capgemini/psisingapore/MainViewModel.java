package com.capgemini.psisingapore;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.capgemini.psisingapore.data.Location;

import java.util.List;

/**
 * View Model class to get all the Location data.
 */
public class MainViewModel extends AndroidViewModel {
    private LocationRepo locationRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        locationRepository = new LocationRepo(application);
    }

    public LiveData<List<Location>> getAllLocation() {
        return locationRepository.getMutableLiveData();
    }

}
