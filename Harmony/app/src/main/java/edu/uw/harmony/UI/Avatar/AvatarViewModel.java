package edu.uw.harmony.UI.Avatar;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AvatarViewModel extends AndroidViewModel {
    private MutableLiveData<List<Avatar>> mAvatarList;
    private final MutableLiveData<JSONObject> mResponse;

    public AvatarViewModel(@NonNull Application application){
        super(application);
        mAvatarList = new MutableLiveData<>();
        mAvatarList.setValue(new ArrayList<>());
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    public void addAvatarListObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Avatar>> observer){
        mAvatarList.observe(owner, observer);
    }

    /**
     * Error handler when connecting to endpoint fails
     * @param error VolleyError
     */
    private void handleError(final VolleyError error){
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException((error.getMessage()));
    }

    /**
     * Result handler when successfully connecting to endpoint
     * @param result JSONObject
     */
    private void handleResult(final JSONObject result) {

    }


}
