package edu.uw.harmony.UI.Chat.update;

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

/**
 * View model to store the information of the list of chats the user has
 */
public class UpdateChatViewModel extends AndroidViewModel {

    /**
     * The response
     */
    private final MutableLiveData<JSONObject> mResponseGet;
    /**
     * The response
     */
    private final MutableLiveData<JSONObject> mResponseDelete;
    /**
     * The response
     */
    private final MutableLiveData<JSONObject> mResponse;

    /**
     * Constructor for the chat list view model
     * @param application the application
     */
    public UpdateChatViewModel(@NonNull Application application) {
        super(application);
        mResponse=new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
        mResponseGet=new MutableLiveData<>();
        mResponseGet.setValue(new JSONObject());
        mResponseDelete=new MutableLiveData<>();
        mResponseDelete.setValue(new JSONObject());
    }


    /**
     * Adds observers to the response
     * @param owner the owner
     * @param observer the observer
     */
    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }

    /**
     * Adds observers to the response
     * @param owner the owner
     * @param observer the observer
     */
    public void addResponseGetObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponseGet.observe(owner, observer);
    }

    /**
     * Handles the error from Volley
     * @param error The error from Volley
     */
    private void handleError(final VolleyError error) {
        //you should add much better error handling in a production release.
        //i.e. YOUR PROJECT
        Log.e("ERROR","Something went wrong when creating the chat room");
    }
    /**
     * Handles the error from Volley
     * @param error The error from Volley
     */
    private void handleDelete(final VolleyError error) {
        //you should add much better error handling in a production release.
        //i.e. YOUR PROJECT
        Log.e("ERROR","SOmething went wrong");
    }

    /**
     * If the user recieves a good response, then we begin filling the page with chatposts
     * @param result the result from the web service
     */
    private void handleResult(final JSONObject result) {
        Log.e("PASSED", "Chatroom was created");
    }

    public void done() {
        mResponseGet.setValue(new JSONObject());
        mResponseDelete.setValue(new JSONObject());
        mResponse.setValue(new JSONObject());
    }

    /**
     * Connects to the web service to get all the chatrooms associated with the users email.
     * @param jwt the users jwt
     * @param email the users email
     */
    public void connectGet(final String jwt, final String email, int chatID) {

        String url =
                getApplication().getResources().getString(R.string.base_url) + "chatroom/" + email + "/" + chatID;
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                mResponseGet::setValue, // we get a response but do nothing with it
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization",jwt);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    /**
     * Connects to the web service to get all the chatrooms associated with the users email.
     * @param jwt the users jwt
     * @param email the users email
     */
    public void connectPost(final String jwt, final String email, int chatId, String members) {
        JSONObject body = new JSONObject();

        try{
            body.put("chatid", chatId);
            body.put("members", members);
        } catch (JSONException e){
            e.printStackTrace();
        }
        String url =
                getApplication().getResources().getString(R.string.base_url) + "chatroom";
        Request request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                body,
                mResponse::setValue, // we get a response but do nothing with it
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization",jwt);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    /**
     * Connects to the web service to get all the chatrooms associated with the users email.
     * @param jwt the users jwt
     * @param email the users email
     */
    public void connectDelete(final String jwt, final String email, final int chatId) {
        JSONObject body = new JSONObject();
        try {
            body.put("chatid", chatId);
            body.put("email", email);
        } catch (JSONException err){
            Log.e("Delete error", err.toString());
        }
        String url =
                getApplication().getResources().getString(R.string.base_url) + "chatroom/" + email + "/" + chatId;
        Request request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                body,
                mResponseDelete::setValue, // we get a response but do nothing with it
                this::handleDelete) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization",jwt);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }


}
