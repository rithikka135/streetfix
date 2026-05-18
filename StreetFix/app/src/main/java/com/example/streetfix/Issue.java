package com.example.streetfix;

import android.net.Uri;
import java.util.ArrayList;
import java.text.DateFormat;
import java.util.Date;

public class Issue {

    public String title;
    public String description;
    public Uri imageUri;

    public double latitude;
    public double longitude;
    public String locationString; // new: formatted location

    public String status; // Pending / Fixed
    public String fixedBy; // Officer name
    public ArrayList<String> timeline; // Events timeline

    // Constructor with location
    public Issue(String title, String description, Uri imageUri, double latitude, double longitude, String locationString) {
        this.title = title;
        this.description = description;
        this.imageUri = imageUri;

        this.latitude = latitude;
        this.longitude = longitude;
        this.locationString = locationString;

        this.status = "Pending";
        this.fixedBy = "";
        this.timeline = new ArrayList<>();
        this.timeline.add("Created at " + DateFormat.getDateTimeInstance().format(new Date()));
    }
}