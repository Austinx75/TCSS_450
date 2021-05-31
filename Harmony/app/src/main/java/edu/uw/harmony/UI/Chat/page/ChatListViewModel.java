package edu.uw.harmony.UI.Chat.page;

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
public class ChatListViewModel extends AndroidViewModel {

    /** The list of chats that the user is currently in*/
    private MutableLiveData<List<ChatPost>> mChatList;

    /**
     * Constructor for the chat list view model
     * @param application the application
     */
    public ChatListViewModel(@NonNull Application application) {
        super(application);
        mChatList = new MutableLiveData<>();
        mChatList.setValue(new ArrayList<>());
    }

    /**
     * Adds a blog list observer
     * @param owner the owner
     * @param observer the observer
     */
    public void addBlogListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<ChatPost>> observer) {
        mChatList.observe(owner, observer);
    }

    /**
     * Handles the error from Volley
     * @param error The error from Volley
     */
    private void handleError(final VolleyError error) {
        //you should add much better error handling in a production release.
        //i.e. YOUR PROJECT
        Log.e("ERROR","USER HAS NOT STARTED ANY CHATS");
    }

    /**
     * If the user recieves a good response, then we begin filling the page with chatposts
     * @param result the result from the web service
     */
    private void handleResult(final JSONObject result) {
        IntFunction<String> getString =
                getApplication().getResources()::getString;
        try {
            mChatList.setValue(new ArrayList<>());
            JSONObject root = result;
            JSONArray chats = root.getJSONArray("chats");
            Log.e("-----------------------", chats.get(0).toString());
            for (int i = 0; i < chats.length(); i++) {
                JSONObject room = chats.getJSONObject(i);

                List<Integer> members = new ArrayList<>();

                ChatPost post = new ChatPost
                        .Builder(
                                (int)room.get("chatid"),
                                members,
                            "" + room.get("name"),
                        ""+room.get("username") + ": " +room.get("message")).build();
                boolean contains = false;
                List <ChatPost> list = mChatList.getValue();
                for (ChatPost chat: list) {
                    if ((int)room.get("chatid") == chat.getChatId()) {
                        contains = true;
                    }
                }
                if (!contains) {
                    mChatList.getValue().add(post); }
            }
        } catch (JSONException e){
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        mChatList.setValue(mChatList.getValue());
    }

    /**
     * Connects to the web service to get all the chatrooms associated with the users email.
     * @param jwt the users jwt
     * @param email the users email
     */
    public void connectGet(final String jwt, final String email) {
        String url =
                getApplication().getResources().getString(R.string.base_url) + "chatroom/" + email;
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                this::handleResult,
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


}
