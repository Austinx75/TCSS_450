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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Contacts.ContactCard;

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

    private final MutableLiveData<Integer> cAvatar;

    public AvatarViewModel(@NonNull Application application){
        super(application);
        mAvatarList = new MutableLiveData<>();
        mAvatarList.setValue(new ArrayList<>());
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
        cAvatar = new MutableLiveData<>();
    }

    /**
     * Observer method to listen for changes made to avatar list
     * @param owner Lifecycle owner
     * @param observer Observer List<Avatar>
     */
    public void addAvatarListObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Avatar>> observer){
        mAvatarList.observe(owner, observer);
    }

    public void addAvatarObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super Integer> observer){
        cAvatar.observe(owner, observer);
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
    private void handleResult(final JSONObject result) { }

    private void handleGet(final JSONObject result){
        IntFunction<String> getString = getApplication().getResources()::getString;
        try {
            Log.d("Result", result.getJSONArray("avatar").getJSONObject(0).toString());
            cAvatar.setValue(Integer.parseInt(result.getJSONArray("avatar").getJSONObject(0).get("avatar").toString()));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
    }

    public void connectGet (final String jwt){
        String url = "https://team-9-tcss450-backend.herokuapp.com/avatar";
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                this::handleGet,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", jwt);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    public void connectAdd (final String jwt, int avatar){
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
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", jwt);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }


}
