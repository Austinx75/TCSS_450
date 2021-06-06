package edu.uw.harmony.UI.Weather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.TimeUnit;

import edu.uw.harmony.MainActivity;
import edu.uw.harmony.R;
import edu.uw.harmony.UI.Auth.LogIn.LogInFragmentDirections;
import edu.uw.harmony.UI.model.LocationViewModel;
import edu.uw.harmony.UI.settings.SettingsViewModel;
import edu.uw.harmony.databinding.FragmentWeatherLocationBinding;

/**
 * A fragment that contains options to change the location to get weather information about. Can select
 * from the user's current location, a zip code, or a spot on a map.
 *
 *
 * @author  Gary Kono
 * @version 1.2
 */
public class WeatherLocationFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private FragmentWeatherLocationBinding binding;

    private WeatherViewModel mWeatherModel;
    private LocationViewModel mLocationModel;
    private GoogleMap mMap;
    private SettingsViewModel settingsViewModel;

    private Marker currentMarker;

    private boolean mButtonPressed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeatherModel = new ViewModelProvider(getActivity())
                .get(WeatherViewModel.class);

        mLocationModel = new ViewModelProvider(getActivity())
                .get(LocationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButtonPressed = false;

        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);

        mWeatherModel.addServerRespondedObserver(getViewLifecycleOwner(), this::afterServerResponse);

        this.binding = FragmentWeatherLocationBinding.bind(getView());
        binding.textViewErrorMessage.setVisibility(View.INVISIBLE);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        //add this fragment as the OnMapReadyCallback -> See onMapReady()
        mapFragment.getMapAsync(this);

        //set button listeners
        binding.buttonSelectCurrentLocation.setOnClickListener(this::onUseCurrentLocation);
        binding.buttonSelectZipLocation.setOnClickListener(this::onUseZipCode);
        binding.buttonSelectMapLocation.setOnClickListener(this::onUseMapLocation);
        if(settingsViewModel.getCurrentThemeID() == R.style.Theme_1_Harmony){
            binding.editTextZipInput.setTextColor(Color.BLACK);
            binding.textViewCurrentLocation.setTextColor(Color.BLACK);
            binding.textViewMapLocation.setTextColor(Color.BLACK);
            binding.textViewZipLocation.setTextColor(Color.BLACK);
            binding.textViewErrorMessage.setTextColor(Color.BLACK);
        } else {
            binding.editTextZipInput.setTextColor(Color.WHITE);
            binding.textViewCurrentLocation.setTextColor(Color.WHITE);
            binding.textViewMapLocation.setTextColor(Color.WHITE);
            binding.textViewZipLocation.setTextColor(Color.WHITE);
            binding.textViewErrorMessage.setTextColor(Color.WHITE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationViewModel model = new ViewModelProvider(getActivity())
                .get(LocationViewModel.class);
        model.addLocationObserver(getViewLifecycleOwner(), location -> {
            if (location != null) {
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                final LatLng c = new LatLng(location.getLatitude(),
                        location.getLongitude());
                //Zoom levels are from 2.0f (zoomed out) to 21.f (zoomed in)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(c, 15.0f));
            }
        });

        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(currentMarker != null) {
            currentMarker.remove();
        }
        currentMarker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("New Marker"));

        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                        latLng, mMap.getCameraPosition().zoom));
    }

    /**
     * Update the weather information in the weather model based on the user's location.
     */
    public void onUseCurrentLocation(View v) {
        mButtonPressed = true;
        mWeatherModel.useCurrentLocation();
    }

    /**
     * Update the weather information in the weather model based on a zip code.
     */
    public void onUseZipCode(View v) {
        mButtonPressed = true;
        //Get zip code from text field
        String zipCode = this.binding.editTextZipInput.getText().toString();
        if(!zipCode.matches("[0-9]+")) {
            binding.textViewErrorMessage.setText(getString(R.string.weather_location_zip_invalid));
            binding.textViewErrorMessage.setVisibility(View.VISIBLE);
            mButtonPressed = false;
            return;
        }
        mWeatherModel.useZipLocation(zipCode);
    }

    /**
     * Update the weather information in the weather model based a location selected on the map.
     */
    public void onUseMapLocation(View v) {
        mButtonPressed = true;
        //Get zip code from text field
        if(currentMarker != null) {
            mWeatherModel.useMapLocation(
                    currentMarker.getPosition().latitude, currentMarker.getPosition().longitude);
        } else {
            binding.textViewErrorMessage.setText(getString(R.string.weather_location_map_location_not_selected));
            binding.textViewErrorMessage.setVisibility(View.VISIBLE);
            mButtonPressed = false;
        }
    }

    /**
     * Post an error message if the location provided is not valid. Otherwise, navigate to the weather report
     * fragment.
     */
    public void afterServerResponse(boolean serverHasResponded) {
        if(mButtonPressed && serverHasResponded && mWeatherModel.getLocationIsValid()) {
            mButtonPressed = false;
            binding.textViewErrorMessage.setText(
                    getString(R.string.weather_location_navigating_to_report));
            mWeatherModel.setNavigatingFromWeatherLocation(true);
            Navigation.findNavController(getView())
                    .navigate(WeatherContainerFragmentDirections.actionNavigationWeatherContainerSelf());
        } else {
            if(!mButtonPressed || !serverHasResponded) {
                binding.textViewErrorMessage.setVisibility(View.INVISIBLE);
            } else {
                if (mWeatherModel.getWeatherLocationSourceRequest() == WeatherViewModel.WeatherLocationSource.CURRENT) {
                    binding.textViewErrorMessage.setText(
                            getString(R.string.weather_location_current_location_not_found));
                } else if (mWeatherModel.getWeatherLocationSourceRequest() == WeatherViewModel.WeatherLocationSource.ZIP) {
                    binding.textViewErrorMessage.setText(
                            getString(R.string.weather_location_zip_location_not_found));
                } else if (mWeatherModel.getWeatherLocationSourceRequest() == WeatherViewModel.WeatherLocationSource.MAP) {
                    binding.textViewErrorMessage.setText(
                            getString(R.string.weather_location_map_location_not_found));
                } else {
                    binding.textViewErrorMessage.setText("An error occurred.");
                }
                binding.textViewErrorMessage.setVisibility(View.VISIBLE);
            }
        }
    }
}