package com.example.streetfix;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class IssueAdapter extends ArrayAdapter<Issue> {

    private final Context context;
    private final List<Issue> issues;
    private final String role; // "user" or "officer"

    public IssueAdapter(@NonNull Context context, @NonNull List<Issue> issues, @NonNull String role) {
        super(context, 0, issues);
        this.context = context;
        this.issues = issues;
        this.role = role;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.item_issue, parent, false);
        }

        Issue currentIssue = issues.get(position);

        TextView title = listItem.findViewById(R.id.itemTitle);
        TextView location = listItem.findViewById(R.id.itemLocation);
        TextView status = listItem.findViewById(R.id.itemStatus);

        title.setText(currentIssue.title);
        location.setText(currentIssue.locationString);
        status.setText(currentIssue.status);

        // Status color
        if (currentIssue.status.equalsIgnoreCase("Fixed")) {
            status.setTextColor(Color.parseColor("#4CAF50")); // Green
        } else {
            status.setTextColor(Color.parseColor("#FF5722")); // Orange
        }

        return listItem;
    }
}