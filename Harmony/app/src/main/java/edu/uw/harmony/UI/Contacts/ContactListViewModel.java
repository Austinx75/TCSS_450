package edu.uw.harmony.UI.Contacts;

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
 * This is a view model for the ContactListFragment.
 *
 * @author Jack Lin
 * @version 1.1
 */

public class ContactListViewModel extends AndroidViewModel {

    /** live data for the list of contacts */
    private MutableLiveData<List<ContactCard>> mContactList;

    /** view model for the contact list class */
    private final MutableLiveData<JSONObject> mResponse;

    public ContactListViewModel(@NonNull Application application){
        super(application);
        mContactList = new MutableLiveData<>();
        mContactList.setValue(new ArrayList<>());
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Observer method to listen for changes made to contact list
     * @param owner Lifecycle owner
     * @param observer Observer List<ContactCard>
     */
    public void addContactListObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<ContactCard>> observer){
        mContactList.observe(owner, observer);
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
        List<ContactCard> list = new ArrayList<>();
        IntFunction<String> getString = getApplication().getResources()::getString;
        try {
            // get/read contacts
            if (Integer.parseInt(result.getString("rowCount")) > 0) {
                JSONArray contacts = result.getJSONArray(getString.apply(
                        R.string.keys_json_contacts_list));
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject contactList = contacts.getJSONObject(i);
                    if (Integer.parseInt(contactList.get("verified").toString()) == 1)  {
                        ContactCard contact = new ContactCard.Builder(
                                contactList.getString(getString.apply(R.string.keys_json_contacts_firstname)) + " " + contactList.getString(getString.apply(R.string.keys_json_contacts_lastname)),
                                contactList.getString(getString.apply(R.string.keys_json_contacts_memberid))).addUsername(contactList.getString(getString.apply(R.string.keys_json_contacts_username)))
                                .build();
                        list.add(contact);
                    }
                }
                mContactList.setValue(list);
            } else {
                Log.e("ERROR!", "No contact list array in contacts endpoint response");
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        mContactList.setValue(mContactList.getValue());
    }

    /**
     * Get method for grabbing contacts from the user table
     * @param jwt String jwt
     */
    public void connectGet(final String jwt) {
        String url = "https://team-9-tcss450-backend.herokuapp.com/contacts";
        //String url = "https://localhost:5000/contacts";
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
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
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
    }

    /**
     * Post method to add contacts to the user table
     * @param jwt String jwt
     * @param id Int member ID
     */
    public void contactAdd (final String jwt, int id){
//        String url = "https://localhost:5000/contacts";
        String url = "https://team-9-tcss450-backend.herokuapp.com/contacts";
        JSONObject body = new JSONObject();
        try {
            body.put("MemberId", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                this::handleResult,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
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

    /**
     * Delete method to delete existing contacts from the user table
     * @param jwt String jwt
     * @param id Int Member ID
     */
    public void contactDelete (final String jwt, int id){
        Log.d("Contact Delete",  "Trying method");
        String url = "https://team-9-tcss450-backend.herokuapp.com/contacts/"+ id;

        Request request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
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
