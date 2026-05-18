package com.example.streetfix;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class IssueDetailActivity extends AppCompatActivity {

    TextView titleText, locationText, descriptionText, statusText, timelineText;
    ImageView detailImage;
    Button fixButton;
    MapView mapView;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_detail);

        titleText = findViewById(R.id.titleText);
        locationText = findViewById(R.id.locationText);
        descriptionText = findViewById(R.id.descriptionText);
        statusText = findViewById(R.id.statusText);
        timelineText = findViewById(R.id.timelineText);
        detailImage = findViewById(R.id.detailImage);
        fixButton = findViewById(R.id.fixButton);
        mapView = findViewById(R.id.detailMapView);

        Configuration.getInstance().setUserAgentValue(getPackageName());
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        index = getIntent().getIntExtra("issueIndex", -1);
        String role = getIntent().getStringExtra("role");

        // Hide button for users
        if(role != null && role.equals("user")){
            fixButton.setVisibility(Button.GONE);
        }

        if (index != -1 && index < IssueData.issues.size()) {
            Issue issue = IssueData.issues.get(index);

            titleText.setText(issue.title);
            locationText.setText(issue.locationString);
            descriptionText.setText(issue.description);
            statusText.setText("Status: " + issue.status);

            if (issue.imageUri != null) {
                detailImage.setImageURI(issue.imageUri);
            }

            updateTimelineText(issue);

            // Show issue location on map
            GeoPoint point = new GeoPoint(issue.latitude, issue.longitude);
            mapView.getController().setZoom(16.0);
            mapView.getController().setCenter(point);

            Marker startMarker = new Marker(mapView);
            startMarker.setPosition(point);
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            startMarker.setTitle(issue.title + " - " + issue.locationString);
            mapView.getOverlays().add(startMarker);
        }

        fixButton.setOnClickListener(v -> {
            Issue issue = IssueData.issues.get(index);

            // Ask officer name
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter Officer Name");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                String officerName = input.getText().toString().trim();
                if(!officerName.isEmpty()){
                    issue.status = "Fixed";
                    issue.fixedBy = officerName;
                    issue.timeline.add("Fixed by " + officerName + " at " +
                            java.text.DateFormat.getDateTimeInstance().format(new java.util.Date()));

                    statusText.setText("Status: Fixed by " + officerName);
                    updateTimelineText(issue);
                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });
    }

    private void updateTimelineText(Issue issue){
        StringBuilder sb = new StringBuilder();
        for(String event : issue.timeline){
            sb.append(event).append("\n");
        }
        timelineText.setText(sb.toString());
    }
}