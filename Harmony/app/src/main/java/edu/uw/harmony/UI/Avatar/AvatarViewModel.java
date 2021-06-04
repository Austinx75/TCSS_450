package edu.uw.harmony.UI.Avatar;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * This is a view model for the AvatarListFragment.
 *
 * @author Jack Lin
 * @version 1.0
 */
public class AvatarViewModel extends AndroidViewModel {
    /** live data for the list of contacts */
    private MutableLiveData<List<Avatar>> mAvatarList;
    /** view model for the contact list class */
    private final MutableLiveData<JSONObject> mResponse;

    public AvatarViewModel(@NonNull Application application){
        super(application);
        mAvatarList = new MutableLiveData<>();
        mAvatarList.setValue(new ArrayList<>());
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Observer method to listen for changes made to avatar list
     * @param owner Lifecycle owner
     * @param observer Observer List<Avatar>
     */
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
        Log.d("AvatarHandleResult", "Success");
    }

    public void connectAdd (int avatar){
        String url = "https://team-9-tcss450-backend.herokuapp.com/avatar";
        JSONObject body = new JSONObject();
        try {
            body.put("Avatar", avatar);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                body,
                this::handleResult,
                this::handleError) {
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }


}
