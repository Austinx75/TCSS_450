package edu.uw.harmony.UI.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserInfoViewModel extends ViewModel {

    private final String mEmail;
    private final String mJwt;
    private  int mVerified;

    private UserInfoViewModel(String email, String jwt, int verified) {
        mEmail = email;
        mJwt = jwt;
        mVerified = verified;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getJwt() {
        return mJwt;
    }

    public int getVerified() {return mVerified;}

    public void setVerified(int i) {this.mVerified = i;}

    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {

        private final String email;
        private final String jwt;
        private  int verified;

        public UserInfoViewModelFactory(String email, String jwt, int verified) {
            this.email = email;
            this.jwt = jwt;
            this.verified =verified;
        }

        public void setVerified(int i ) {this.verified=i;}

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(email, jwt, verified);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }


}
