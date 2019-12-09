package com.capgemini.psisingapore;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import com.capgemini.psisingapore.model.Location;
import com.capgemini.psisingapore.repository.LocationRepo;
import com.capgemini.psisingapore.viewmodel.MainViewModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.List;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
public class MainViewModelTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private LocationRepo locationRepository;

    private MainViewModel mainViewModel;

    @Mock
    Application application;

    @Before
    public void setUp() throws Exception {
        mainViewModel = spy(new MainViewModel(application));
        doNothing().when(mainViewModel).initLocationRepo(Matchers.any(Application.class));
    }

    @Test
    public void getAllLocationsTest() throws Exception {
        MutableLiveData<List<Location>> liveData = mock(MutableLiveData.class);
        doReturn(liveData).when(locationRepository).getMutableLiveData();
        doReturn(liveData).when(mainViewModel).getAllLocation();
        mainViewModel.getAllLocation();
        verify(locationRepository, times(0)).getMutableLiveData();
    }

    @After
    public void tearDown() throws Exception {
        mainViewModel = null;
    }
}
