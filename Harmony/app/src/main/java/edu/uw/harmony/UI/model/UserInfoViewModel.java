package edu.uw.harmony.UI.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * A view model that stores information about the currently logged in user that will be used throughout
 * the app
 *
 * @author  Gary Kono
 * @version 1.1
 */
public class UserInfoViewModel extends ViewModel {

    private final String mEmail;
    private final String mJwt;

    /**
     * Default constructor
     *
     * @param email Currently logged in user's email
     * @param jwt A JWT used for authentication between the client and server
     */
    private UserInfoViewModel(String email, String jwt) {
        mEmail = email;
        mJwt = jwt;
    }

    /**
     *
     * @return Currently logged in user's email
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     *
     * @return A JWT used for authentication between the client and server
     */
    public String getJwt() {
        return mJwt;
    }

    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {

        private final String email;
        private final String jwt;

        public UserInfoViewModelFactory(String email, String jwt) {
            this.email = email;
            this.jwt = jwt;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(email, jwt);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }


}
