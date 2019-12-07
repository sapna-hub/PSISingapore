package com.capgemini.psisingapore;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.capgemini.psisingapore.model.Location;
import com.capgemini.psisingapore.network.NetworkHandler;
import com.capgemini.psisingapore.repository.LocationRepo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.List;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class LocationRepoTest {

    private Location location;

    private LocationRepo locationRepo;

    @Mock
    Application application;

    @Mock
    RequestQueue requestQueue;

    @Mock
    Context context;

    @Mock
    private MutableLiveData<List<Location>> mutableLiveData;

    @Mock
    private NetworkHandler networkHandler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(NetworkHandler.class);
        when(context.getApplicationContext()).thenReturn(context);
        PowerMockito.when(Volley.newRequestQueue(context.getApplicationContext())).thenReturn(requestQueue);
        locationRepo = new LocationRepo(application);
    }

    @After
    public void tearDown() throws Exception {
        location = null;
        locationRepo = null;
    }

    @Test
    public void getMutableLiveDataTest() throws Exception {
        PowerMockito.when(NetworkHandler.getInstance(application)).thenReturn(networkHandler);
        mutableLiveData = locationRepo.getMutableLiveData();
    }
}
