package edu.uw.harmony.UI.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

/**
 * @version 1.4
 * @author Austin Scott
 * This increments and resets the notification badge numbers.
 */
public class NewContactCountViewModel extends ViewModel{

    /** This keeps track of the badge number*/
    private MutableLiveData<Integer> mNewContactCount;

    public NewContactCountViewModel () {
        mNewContactCount = new MutableLiveData<>();
        mNewContactCount.setValue(0);
    }

    /**
     * adds an observer to increment the number visually whenever there is an increment change.
     * @param owner
     * @param observer
     */
    public void addContactCountObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super Integer> observer) {
        mNewContactCount.observe(owner, observer);
    }

    /**
     * increases the number
     */
    public void increment() {
        mNewContactCount.setValue(mNewContactCount.getValue() + 1);
    }

    /**
     * resets it to zero
     */
    public void reset() {
        mNewContactCount.setValue(0);
    }
}