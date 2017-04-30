package findhosp.com.publichealth.Map;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import findhosp.com.publichealth.R;

import static findhosp.com.publichealth.R.id.map;
import static findhosp.com.publichealth.R.id.mapanimal;

public class MapsAnimalAll extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


        private ProgressDialog progressDialog;
        private GoogleMap mMap;
        private GoogleApiClient mGoogleApiClient;
        private static final int REQUEST_LOCATION_PER = 103;
        protected Boolean mRequestingLocationUpdates = false;
        LocationRequest mLocationRequest;
        JSONArray jsonArray;
        ArrayList<HashMap<String, String>> location = new ArrayList<>();
        HashMap<String, String> map;

        String str = "";

        //Latitude&Longitude
        private Double Latitude = 0.00;
        private Double Longitude = 0.00;
        private Double currentLatitude = 0.00;
        private Double currentLongitude = 0.00;
        LatLng latLng;
        Marker markerCurrent;
        Circle circle;


        String getUrl,getType;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps_animal_all);

            Intent intent = getIntent();
            getUrl = intent.getStringExtra("GET_URL");
            getType = intent.getStringExtra("GET_TYPE");
            new MapsAnimalAll.GetDataApi().execute();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkLocationPermission();
            }
            SupportMapFragment mapFragemnt = (SupportMapFragment) getSupportFragmentManager().findFragmentById(mapanimal);
            mapFragemnt.getMapAsync(this);




        }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_PER);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_PER);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onLocationChanged(Location loc) {
        currentLatitude = loc.getLatitude();
        currentLongitude = loc.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        markerCurrent = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.locathuman)).title("คุณอยู่ที่นี่").snippet("You are here"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 10));
        circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(currentLatitude, currentLongitude))
                .radius(20000)
                .strokeWidth(4)
                .strokeColor(Color.RED)
                .fillColor(Color.TRANSPARENT));

    }


    private void drawMarker(Location location) {
        if(circle != null) {
            circle.remove();
        }
        circle = mMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(20000)
                .strokeColor(Color.RED)
                .fillColor(Color.TRANSPARENT));
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, (LocationListener) this);
            mRequestingLocationUpdates = true;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PER: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    // GET DATA API
    class GetDataApi extends AsyncTask<Void, Void, Void> {
        private String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MapsAnimalAll.this);
            progressDialog.setMessage("กำลังดึงข้อูมล โปรดรอสักครู่...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(getUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                result = readInputStreamToString(connection);
                str = result;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            progressDialog.dismiss();
            //Display Google Map
            jsonParse();

            latLng = new LatLng(currentLatitude, currentLongitude);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

            //***Marker(Loop)
            for (int i = 0; i < location.size(); i++) {
                Latitude = Double.parseDouble(location.get(i).get("LAT"));
                Longitude = Double.parseDouble(location.get(i).get("LON"));
                String name = location.get(i).get("AHOS_NAME");
                String addr = location.get(i).get("ADDR");
                MarkerOptions marker = new MarkerOptions().position(new LatLng(Latitude, Longitude)).title(name);

                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.animalhos));

                mMap.addMarker(marker);

               /*
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital));
                mMap.addMarker(marker);*/
            }

        }

        private String readInputStreamToString(HttpURLConnection connection) {
            String result = null;
            StringBuffer sb = new StringBuffer();
            InputStream is = null;

            try {
                is = new BufferedInputStream(connection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String inputLine = "";
                while ((inputLine = br.readLine()) != null) {
                    sb.append(inputLine);
                }
                result = sb.toString();
            } catch (Exception e) {
                Log.i("TAG", "Error reading InputStream");
                result = null;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        Log.i("TAG", "Error closing InputStream");
                    }
                }
            }

            return result;
        }
    }

    private void jsonParse() {
        //*JSON Parse
        try {
            jsonArray = new JSONArray(str);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);

                map = new HashMap<>();
                map.put("AHOS_NAME", c.getString("AHOS_NAME"));
                map.put("LAT", c.getString("LAT"));
                map.put("LON", c.getString("LON"));
                location.add(map);
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
