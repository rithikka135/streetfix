package com.example.streetfix;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import android.location.Location;

public class ReportIssueActivity extends AppCompatActivity {

    EditText title, description;
    TextView locationText;
    Button uploadBtn, submitBtn;
    ImageView imagePreview;
    Uri imageUri;
    int IMAGE_REQUEST = 1;

    FusedLocationProviderClient fusedLocationClient;
    double currentLatitude = 0.0;
    double currentLongitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_issue);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        locationText = findViewById(R.id.location); // TextView
        uploadBtn = findViewById(R.id.uploadImage);
        submitBtn = findViewById(R.id.submitBtn);
        imagePreview = findViewById(R.id.imagePreview);

        // Initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation();

        // Image upload
        uploadBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_REQUEST);
        });

        // Submit issue
        submitBtn.setOnClickListener(v -> {
            String t = title.getText().toString().trim();
            String d = description.getText().toString().trim();
            String locString = locationText.getText().toString().trim();

            if (t.isEmpty() || d.isEmpty() || locString.isEmpty()) {
                Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create Issue with 6 parameters: title, desc, imageUri, latitude, longitude, location string
            Issue issue = new Issue(t, d, imageUri, currentLatitude, currentLongitude, locString);
            IssueData.issues.add(issue);

            Toast.makeText(this, "Issue Submitted! Notifying officers...", Toast.LENGTH_SHORT).show();

            // Clear form
            title.setText("");
            description.setText("");
            imagePreview.setImageDrawable(null);
            getCurrentLocation(); // refresh location for next report
        });
    }

    private void getCurrentLocation() {
        // Check location permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();
                locationText.setText("Lat: " + currentLatitude + ", Lng: " + currentLongitude);
            } else {
                locationText.setText("Unable to detect location");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            imagePreview.setImageURI(imageUri);
        }
    }
}