package edu.uw.harmony.UI.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

/**
 * View Model for the new messages from other users
 */
public class NewMessageCountViewModel extends ViewModel {
    /** The number of messages mapped to each chat for the user*/
    private MutableLiveData<Map<Integer, Integer>> mNewMessageCount;
    /** The current room the user is in. -1 if they aren't in any*/
    private MutableLiveData<Integer> mCurrentChatRoom;


    /**
     * Constructor for the View Model
     */
    public NewMessageCountViewModel() {
        mNewMessageCount = new MutableLiveData<>();
        mCurrentChatRoom = new MutableLiveData<>();
        mCurrentChatRoom.setValue(-1);
        mNewMessageCount.setValue(new HashMap<Integer, Integer>());
    }

    /**
     * Adds a message count observer
     * @param owner the owner
     * @param observer the observer
     */
    public void addMessageCountObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super Map<Integer, Integer>> observer) {
        mNewMessageCount.observe(owner, observer);
    }

    /**
     * Gets the chat room that the current user is in
     * @return the current chat room
     */
    public int getCurrentChatRoom(){
        return mCurrentChatRoom.getValue();
    }

    /**
     * Sets the chat room the current user is in
     * @param id the id of the room the user is in
     */
    public void setCurrentChatRoom(int id) {
        mCurrentChatRoom.setValue(id);
    }

    /**
     * Increments the number of new messages based on the chat room id
     * @param id the id of the chat room the message is in
     */
    public void increment(int id) {
        if (id < 0) return;
        int value = mNewMessageCount.getValue().containsKey(id) ?  mNewMessageCount.getValue().get(id) : 0;
        Map<Integer, Integer> map = mNewMessageCount.getValue();
        map.put(id, value + 1);
        mNewMessageCount.setValue(map);
    }

    /**
     * Resets the new messages that the user has seen.
     */
    public void reset() {
        Map<Integer, Integer> map = mNewMessageCount.getValue();
        map.put(mCurrentChatRoom.getValue(), 0);
        mNewMessageCount.setValue(map);
    }
}
