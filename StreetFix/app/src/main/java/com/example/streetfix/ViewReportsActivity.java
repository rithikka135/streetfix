package com.example.streetfix;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewReportsActivity extends AppCompatActivity {

    ListView issuesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        issuesList = findViewById(R.id.issuesList);

        updateList();

        String role = getIntent().getStringExtra("role");

        issuesList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(ViewReportsActivity.this, IssueDetailActivity.class);
            intent.putExtra("issueIndex", position);
            if(role != null){
                intent.putExtra("role", role);
            }
            startActivity(intent);
        });
    }

    private void updateList(){
        ArrayList<String> titles = new ArrayList<>();

        for(Issue i : IssueData.issues){
            String title = i.title + " - " + i.locationString + " [" + i.status + "]";
            if(!i.fixedBy.isEmpty()){
                title += " by " + i.fixedBy;
            }
            titles.add(title);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                titles
        );

        issuesList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList(); // refresh list when returning from detail
    }
}