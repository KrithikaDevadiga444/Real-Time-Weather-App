package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText cityInput;
    Button getWeatherBtn, getLocationBtn;
    TextView resultText, aqiText;
    ImageView weatherIcon;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private FusedLocationProviderClient fusedLocationClient;

    // ⚠ Replace with your OpenWeatherMap API key
    private final String API_KEY = "fe6ebdb7f891f0c43e6f88bbfe09b43f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityInput = findViewById(R.id.cityInput);
        getWeatherBtn = findViewById(R.id.getWeatherBtn);
        getLocationBtn = findViewById(R.id.getLocationBtn);
        resultText = findViewById(R.id.resultText);
        weatherIcon = findViewById(R.id.weatherIcon);
        aqiText = findViewById(R.id.aqiText);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getWeatherBtn.setOnClickListener(view -> {
            String city = cityInput.getText().toString().trim();
            if (!city.isEmpty()) {
                try {
                    String encodedCity = URLEncoder.encode(city, "UTF-8");
                    new GetWeatherTask().execute(encodedCity);
                } catch (Exception e) {
                    resultText.setText("Invalid city name.");
                }
            } else {
                resultText.setText("Please enter a city name.");
            }
        });

        getLocationBtn.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            } else {
                getCurrentLocationAndFetchWeather();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocationAndFetchWeather();
            } else {
                resultText.setText("Location permission denied.");
            }
        }
    }

    private void getCurrentLocationAndFetchWeather() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            resultText.setText("Location permission not granted.");
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(0);
        locationRequest.setFastestInterval(0);
        locationRequest.setNumUpdates(1);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();

                    runOnUiThread(() ->
                            resultText.setText("Fetching weather for Lat: " + lat + ", Lon: " + lon));

                    String latLongQuery = "lat=" + lat + "&lon=" + lon;
                    new GetWeatherByLatLongTask().execute(latLongQuery);

                } else {
                    resultText.setText("Couldn't get location. Try again outside.");
                }
                fusedLocationClient.removeLocationUpdates(this);
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    // ---------------- WEATHER TASKS ----------------
    class GetWeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String city = strings[0];
            try {
                String urlStr = "https://api.openweathermap.org/data/2.5/weather?q=" +
                        city + "&appid=" + API_KEY + "&units=metric";
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) response.append(line);
                return response.toString();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            displayWeather(result);
            fetchAqiFromWeather(result);
        }
    }

    class GetWeatherByLatLongTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String latLong = strings[0];
            try {
                String urlStr = "https://api.openweathermap.org/data/2.5/weather?" +
                        latLong + "&appid=" + API_KEY + "&units=metric";
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) response.append(line);
                return response.toString();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            displayWeather(result);
            fetchAqiFromWeather(result);
        }
    }

    // ---------------- AQI FETCH TASK ----------------
    class GetAqiTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String latLong = strings[0];
            try {
                String urlStr = "https://api.openweathermap.org/data/2.5/air_pollution?" +
                        latLong + "&appid=" + API_KEY;
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) response.append(line);
                return response.toString();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            displayAqi(result);
        }
    }

    // ---------------- DISPLAY HELPERS ----------------
    private void displayWeather(String result) {
        try {
            if (result != null) {
                JSONObject json = new JSONObject(result);
                JSONObject main = json.getJSONObject("main");
                String temperature = main.getString("temp");
                String humidity = main.getString("humidity");
                JSONObject weather = json.getJSONArray("weather").getJSONObject(0);
                String description = weather.getString("description");
                String windSpeed = json.getJSONObject("wind").getString("speed");
                String cityName = json.getString("name");
                String country = json.getJSONObject("sys").getString("country");

                // Set icon based on weather condition
                String condition = description.toLowerCase();
                if (condition.contains("cloud")) {
                    weatherIcon.setImageResource(R.drawable.cloudy);
                } else if (condition.contains("rain")) {
                    weatherIcon.setImageResource(android.R.drawable.ic_menu_report_image);
                } else if (condition.contains("clear")) {
                    weatherIcon.setImageResource(R.drawable.sun);
                } else if (condition.contains("snow")) {
                    weatherIcon.setImageResource(R.drawable.snowy);
                } else {
                    weatherIcon.setImageResource(R.drawable.default_icon);
                }

                String weatherInfo = "City: " + cityName + ", " + country + "\n"
                        + "Condition: " + description + "\n"
                        + "Temperature: " + temperature + "°C\n"
                        + "Humidity: " + humidity + "%\n"
                        + "Wind Speed: " + windSpeed + " m/s";

                resultText.setText(weatherInfo);
            } else {
                resultText.setText("Couldn't fetch weather. Try again.");
            }
        } catch (Exception e) {
            resultText.setText("Error parsing weather data.");
        }
    }

    private void fetchAqiFromWeather(String result) {
        try {
            if (result != null) {
                JSONObject json = new JSONObject(result);
                double lat = json.getJSONObject("coord").getDouble("lat");
                double lon = json.getJSONObject("coord").getDouble("lon");
                new GetAqiTask().execute("lat=" + lat + "&lon=" + lon);
            }
        } catch (Exception ignored) {}
    }

    private void displayAqi(String result) {
        try {
            if (result != null) {
                JSONObject json = new JSONObject(result);
                int aqi = json.getJSONArray("list").getJSONObject(0)
                        .getJSONObject("main").getInt("aqi");

                String quality;
                int color;

                switch (aqi) {
                    case 1: quality = "Good"; color = 0xFF4CAF50; break;
                    case 2: quality = "Fair"; color = 0xFF8BC34A; break;
                    case 3: quality = "Moderate"; color = 0xFFFFC107; break;
                    case 4: quality = "Poor"; color = 0xFFFF5722; break;
                    case 5: quality = "Very Poor"; color = 0xFFF44336; break;
                    default: quality = "Unknown"; color = 0xFF000000;
                }

                aqiText.setText("Air Quality: " + quality + " (AQI " + aqi + ")");
                aqiText.setTextColor(color);
            } else {
                aqiText.setText("Couldn't fetch AQI");
            }
        } catch (Exception e) {
            aqiText.setText("Error parsing AQI");
        }
    }
}