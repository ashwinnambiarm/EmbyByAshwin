package com.ashwin.embybyashwin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ashwin.embybyashwin.Fragment.Details.FragmentView;
import com.ashwin.embybyashwin.Fragment.Home.Model.MyMedia;
import com.ashwin.embybyashwin.emby.GlobalClass;

import java.util.ArrayList;

import mediabrowser.apiinteraction.Response;
import mediabrowser.apiinteraction.android.AndroidApiClient;
import mediabrowser.model.drawing.ImageFormat;
import mediabrowser.model.dto.BaseItemDto;
import mediabrowser.model.dto.ImageOptions;
import mediabrowser.model.entities.ImageType;
import mediabrowser.model.entities.SortOrder;
import mediabrowser.model.querying.ItemQuery;
import mediabrowser.model.querying.ItemsResult;

public class ActivityDetails extends AppCompatActivity {

    private String TAG = "ActivityDetails";
    private AndroidApiClient apiClient;
    private FragmentView fragmentGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        apiClient = GlobalClass.getInstance().getApiClient();

//        Intent intent = getIntent();
//        String view = intent.getStringExtra("VIEW");
//        String parentID = intent.getStringExtra("PARENT_ID");
//        Log.e(TAG, view);
//        Log.e(TAG, parentID);
    }
}