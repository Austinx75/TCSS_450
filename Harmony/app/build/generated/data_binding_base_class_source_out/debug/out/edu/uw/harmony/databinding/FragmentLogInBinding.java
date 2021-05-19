// Generated by view binder compiler. Do not edit!
package edu.uw.harmony.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import edu.uw.harmony.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentLogInBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button buttonLoginFragmentForgotPassword;

  @NonNull
  public final Button buttonLoginFragmentLogin;

  @NonNull
  public final Button buttonLoginFragmentRegister;

  @NonNull
  public final EditText editTextEmail;

  @NonNull
  public final EditText editTextPassword;

  @NonNull
  public final ConstraintLayout frameLayout;

  @NonNull
  public final ImageView loginHarmonyLogo;

  private FragmentLogInBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button buttonLoginFragmentForgotPassword, @NonNull Button buttonLoginFragmentLogin,
      @NonNull Button buttonLoginFragmentRegister, @NonNull EditText editTextEmail,
      @NonNull EditText editTextPassword, @NonNull ConstraintLayout frameLayout,
      @NonNull ImageView loginHarmonyLogo) {
    this.rootView = rootView;
    this.buttonLoginFragmentForgotPassword = buttonLoginFragmentForgotPassword;
    this.buttonLoginFragmentLogin = buttonLoginFragmentLogin;
    this.buttonLoginFragmentRegister = buttonLoginFragmentRegister;
    this.editTextEmail = editTextEmail;
    this.editTextPassword = editTextPassword;
    this.frameLayout = frameLayout;
    this.loginHarmonyLogo = loginHarmonyLogo;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentLogInBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentLogInBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_log_in, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentLogInBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button_loginFragment_forgot_password;
      Button buttonLoginFragmentForgotPassword = rootView.findViewById(id);
      if (buttonLoginFragmentForgotPassword == null) {
        break missingId;
      }

      id = R.id.button_login_fragment_login;
      Button buttonLoginFragmentLogin = rootView.findViewById(id);
      if (buttonLoginFragmentLogin == null) {
        break missingId;
      }

      id = R.id.button_loginFragment_register;
      Button buttonLoginFragmentRegister = rootView.findViewById(id);
      if (buttonLoginFragmentRegister == null) {
        break missingId;
      }

      id = R.id.editTextEmail;
      EditText editTextEmail = rootView.findViewById(id);
      if (editTextEmail == null) {
        break missingId;
      }

      id = R.id.editTextPassword;
      EditText editTextPassword = rootView.findViewById(id);
      if (editTextPassword == null) {
        break missingId;
      }

      ConstraintLayout frameLayout = (ConstraintLayout) rootView;

      id = R.id.login_harmony_logo;
      ImageView loginHarmonyLogo = rootView.findViewById(id);
      if (loginHarmonyLogo == null) {
        break missingId;
      }

      return new FragmentLogInBinding((ConstraintLayout) rootView,
          buttonLoginFragmentForgotPassword, buttonLoginFragmentLogin, buttonLoginFragmentRegister,
          editTextEmail, editTextPassword, frameLayout, loginHarmonyLogo);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
