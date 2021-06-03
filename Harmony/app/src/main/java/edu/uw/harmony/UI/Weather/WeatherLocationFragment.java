package edu.uw.harmony.UI.Weather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
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
import edu.uw.harmony.databinding.FragmentWeatherLocationBinding;

/**
 *
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

    private Marker currentMarker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.binding = FragmentWeatherLocationBinding.bind(getView());
        binding.textViewErrorMessage.setVisibility(View.INVISIBLE);

        mWeatherModel = new ViewModelProvider(getActivity())
                .get(WeatherViewModel.class);
        mWeatherModel.setWeatherLocationBinding(this.binding);
        mWeatherModel.setWeatherLocationFragment(this);

        mLocationModel = new ViewModelProvider(getActivity())
                .get(LocationViewModel.class);
        mLocationModel.addLocationObserver(getViewLifecycleOwner(),
                location -> {

                });
                        //binding.textLatLong.setText(location.toString()))

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        //add this fragment as the OnMapReadyCallback -> See onMapReady()
        mapFragment.getMapAsync(this);

        //set button listeners
        binding.buttonSelectCurrentLocation.setOnClickListener(this::onUseCurrentLocation);
        binding.buttonSelectZipLocation.setOnClickListener(this::onUseZipCode);
        binding.buttonSelectMapLocation.setOnClickListener(this::onUseMapLocation);
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
        Log.d("LAT/LONG", latLng.toString());

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

    public void onUseCurrentLocation(View v) {
        //Get current location
        mWeatherModel.useCurrentLocation();
    }

    public void onUseZipCode(View v) {
        //Get zip code from text field
        String zipCode = this.binding.editTextZipInput.getText().toString();
        if(!zipCode.matches("[0-9]+")) {
            binding.textViewErrorMessage.setText(getString(R.string.weather_location_zip_invalid));
            binding.textViewErrorMessage.setVisibility(View.VISIBLE);
            return;
        }
        mWeatherModel.useZipLocation(zipCode);
    }

    public void onUseMapLocation(View v) {
        //Get zip code from text field
        if(currentMarker != null) {
            mWeatherModel.useMapLocation(
                    currentMarker.getPosition().latitude, currentMarker.getPosition().longitude);
        } else {
            binding.textViewErrorMessage.setText(getString(R.string.weather_location_map_location_not_selected));
            binding.textViewErrorMessage.setVisibility(View.VISIBLE);
        }
    }

    public void afterServerResponse() {
        if(mWeatherModel.getLocationIsValid()) {
            Navigation.findNavController(getView())
                    .navigate(WeatherContainerFragmentDirections.actionNavigationWeatherContainerSelf());
        } else {
            if(mWeatherModel.getWeatherLocationSource() == WeatherViewModel.WeatherLocationSource.LAT_LONG) {
                binding.textViewErrorMessage.setText(
                        getString(R.string.weather_location_current_location_not_found));
            } else if(mWeatherModel.getWeatherLocationSource() == WeatherViewModel.WeatherLocationSource.ZIP) {
                binding.textViewErrorMessage.setText(
                        getString(R.string.weather_location_zip_location_not_found));
            } else if(mWeatherModel.getWeatherLocationSource() == WeatherViewModel.WeatherLocationSource.MAP) {
                binding.textViewErrorMessage.setText(
                        getString(R.string.weather_location_map_location_not_found));
            } else {
                binding.textViewErrorMessage.setText("An error occurred.");
            }
            binding.textViewErrorMessage.setVisibility(View.VISIBLE);
        }
    }
}