package com.ashwin.embybyashwin;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;

import com.ashwin.embybyashwin.Fragment.Details.FragmentView;
import com.ashwin.embybyashwin.Fragment.Home.FragmentHome;
import com.ashwin.embybyashwin.Fragment.Home.FragmentLatest;
import com.ashwin.embybyashwin.Fragment.Home.FragmentSearch;
import com.ashwin.embybyashwin.emby.EmbyConnection;
import com.ashwin.embybyashwin.emby.GlobalClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

import mediabrowser.apiinteraction.EmptyResponse;
import mediabrowser.apiinteraction.Response;
import mediabrowser.apiinteraction.android.AndroidApiClient;
import mediabrowser.model.dto.BaseItemDto;
import mediabrowser.model.logging.NullLogger;
import mediabrowser.model.querying.ItemsResult;

public class ActivityMain extends AppCompatActivity implements FragmentLatest.FragmentLatestOnClickListener {

    private Boolean isLogin = false;
    private String TAG = ActivityMain.class.getSimpleName();
    private NullLogger logger;
    private EmbyConnection embyConnection;
    private AndroidApiClient apiClient;
    private HashMap<Integer, String> navbarItems = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogout = findViewById(R.id.btn_Logout);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_bottom_activity_main);

        apiClient = GlobalClass.getInstance().getApiClient();

        apiClient.GetUserViews(apiClient.getCurrentUserId(), new Response<ItemsResult>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(ItemsResult response) {
                Integer count = bottomNavigationView.getMenu().size();
                for (BaseItemDto item: response.getItems()) {

                    MenuItem menuItem = bottomNavigationView.getMenu().add(0,count, 0, item.getName());
                    navbarItems.put(count, item.getId());

                    if (item.getMediaType() == "Movie"){
                        menuItem.setIcon(R.drawable.ic_baseline_local_movies_24);
                    }else {
                        menuItem.setIcon(R.drawable.ic_baseline_tv_24);
                    }
                    count +=1;
                }
            }
        });

        LoadFragment(FragmentHome.newInstance());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogout();
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                if (item.getItemId() == R.id.nav_bottom_activity_main_home){
                    fragment = FragmentHome.newInstance();
                }else if (item.getItemId() == R.id.nav_bottom_activity_main_search){
                    fragment = FragmentSearch.newInstance();
                }else{
                    Log.e(TAG, navbarItems.get(item.getItemId()));
                    fragment = FragmentView.newInstance("GRID",navbarItems.get(item.getItemId()));
                }

                LoadFragment(fragment);
                return true;
            }
        });
    }

    private void LoadFragment(Fragment fragment){
        getFragmentManager().beginTransaction().replace(R.id.sv_main, fragment).addToBackStack(null).commit();
    }

    private void userLogout(){
        apiClient.Logout(new EmptyResponse());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        apiClient = null;
    }


    @Override
    public void OnClickFragmentLatestViewAll(String itemID) {
        FragmentView fragmentView = FragmentView.newInstance("GRID", itemID);
        LoadFragment(fragmentView);
    }
}
