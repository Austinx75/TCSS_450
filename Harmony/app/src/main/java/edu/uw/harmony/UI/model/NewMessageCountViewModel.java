package edu.uw.harmony.UI.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

public class NewMessageCountViewModel extends ViewModel {
    private MutableLiveData<Map<Integer, Integer>> mNewMessageCount;


    public NewMessageCountViewModel() {
        mNewMessageCount = new MutableLiveData<>();
        mNewMessageCount.setValue(new HashMap<Integer, Integer>());
    }

    public void addMessageCountObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super Map<Integer, Integer>> observer) {
        mNewMessageCount.observe(owner, observer);
    }

    public void increment(int chatId) {
        mNewMessageCount.getValue().replace(chatId, mNewMessageCount.getValue().get(chatId) + 1);
    }

    public void reset(int chatId) {
        mNewMessageCount.getValue().replace(chatId, 0);
    }
}
