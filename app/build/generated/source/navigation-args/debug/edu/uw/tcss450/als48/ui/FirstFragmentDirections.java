package edu.uw.tcss450.als48.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import edu.uw.tcss450.als48.R;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class FirstFragmentDirections {
  private FirstFragmentDirections() {
  }

  @NonNull
  public static ActionFirstFragmentToColorFragment actionFirstFragmentToColorFragment(int Color) {
    return new ActionFirstFragmentToColorFragment(Color);
  }

  public static class ActionFirstFragmentToColorFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionFirstFragmentToColorFragment(int Color) {
      this.arguments.put("Color", Color);
    }

    @NonNull
    public ActionFirstFragmentToColorFragment setColor(int Color) {
      this.arguments.put("Color", Color);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("Color")) {
        int Color = (int) arguments.get("Color");
        __result.putInt("Color", Color);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_firstFragment_to_colorFragment;
    }

    @SuppressWarnings("unchecked")
    public int getColor() {
      return (int) arguments.get("Color");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionFirstFragmentToColorFragment that = (ActionFirstFragmentToColorFragment) object;
      if (arguments.containsKey("Color") != that.arguments.containsKey("Color")) {
        return false;
      }
      if (getColor() != that.getColor()) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + getColor();
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionFirstFragmentToColorFragment(actionId=" + getActionId() + "){"
          + "Color=" + getColor()
          + "}";
    }
  }
}
