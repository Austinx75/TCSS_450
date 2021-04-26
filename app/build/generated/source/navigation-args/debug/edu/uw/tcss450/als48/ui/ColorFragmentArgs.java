package edu.uw.tcss450.als48.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class ColorFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private ColorFragmentArgs() {
  }

  private ColorFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ColorFragmentArgs fromBundle(@NonNull Bundle bundle) {
    ColorFragmentArgs __result = new ColorFragmentArgs();
    bundle.setClassLoader(ColorFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("Color")) {
      int Color;
      Color = bundle.getInt("Color");
      __result.arguments.put("Color", Color);
    } else {
      throw new IllegalArgumentException("Required argument \"Color\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  public int getColor() {
    return (int) arguments.get("Color");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("Color")) {
      int Color = (int) arguments.get("Color");
      __result.putInt("Color", Color);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    ColorFragmentArgs that = (ColorFragmentArgs) object;
    if (arguments.containsKey("Color") != that.arguments.containsKey("Color")) {
      return false;
    }
    if (getColor() != that.getColor()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + getColor();
    return result;
  }

  @Override
  public String toString() {
    return "ColorFragmentArgs{"
        + "Color=" + getColor()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(ColorFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(int Color) {
      this.arguments.put("Color", Color);
    }

    @NonNull
    public ColorFragmentArgs build() {
      ColorFragmentArgs result = new ColorFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setColor(int Color) {
      this.arguments.put("Color", Color);
      return this;
    }

    @SuppressWarnings("unchecked")
    public int getColor() {
      return (int) arguments.get("Color");
    }
  }
}
