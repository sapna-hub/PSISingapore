package com.capgemini.psisingapore;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.capgemini.psisingapore.data.Location;
import com.capgemini.psisingapore.network.NetworkHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.when;

//@RunWith(PowerMockRunner.class)
public class LocationRepoTest {

    private Location location;

    private LocationRepo locationRepo;

    @Mock
    Application application;

    @Mock
    private MutableLiveData<List<Location>> mutableLiveData;

    @Mock
    private NetworkHandler networkHandler;

    @Before
    public void setUp() throws Exception {
        locationRepo = new LocationRepo(application);
    }

    @After
    public void tearDown() throws Exception {
        location = null;
        locationRepo = null;
    }

    @Test
    public void getMutableLiveDataTest() throws Exception {
      //  PowerMockito.when(NetworkHandler.getInstance(application)).thenReturn(networkHandler);
        mutableLiveData = locationRepo.getMutableLiveData();

    }
}
