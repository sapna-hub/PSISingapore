package com.capgemini.psisingapore.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.capgemini.psisingapore.model.Location;
import com.capgemini.psisingapore.network.NetworkHandler;
import com.capgemini.psisingapore.network.ResponseListener;
import java.util.List;

/**
 * Repository class to fetch mutable data through network api.
 */
public class LocationRepo {

    private MutableLiveData<List<Location>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public LocationRepo(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Location>> getMutableLiveData() {
        NetworkHandler.getInstance(application).getPSI(new ResponseListener<List<Location>>() {

            @Override
            public void getResult(List<Location> object) {
                mutableLiveData.setValue(object);
            }

            @Override
            public void onErrorResponse(String errMsg) {
            }
        });

        return mutableLiveData;
    }
}
