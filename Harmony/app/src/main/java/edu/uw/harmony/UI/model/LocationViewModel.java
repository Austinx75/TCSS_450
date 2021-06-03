package edu.uw.harmony.UI.model;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {
    private MutableLiveData<Location> mLocation;
    private boolean mLoadedFirstLocation;

    public LocationViewModel() {
        mLocation = new MediatorLiveData<>();

        mLoadedFirstLocation = false;
    }

    public void addLocationObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super Location> observer) {
        mLocation.observe(owner, observer);
    }

    public void setLocation(final Location location) {
        if (!location.equals(mLocation.getValue())) {
            mLocation.setValue(location);
        }
        mLoadedFirstLocation = true;
    }

    public Location getCurrentLocation() {
        return new Location(mLocation.getValue());
    }

    public boolean getLoadedFirstLocation() {
        return mLoadedFirstLocation;
    }
}
