package com.ashwin.embybyashwin;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ashwin.embybyashwin.Fragment.Home.FragmentHome;
import com.ashwin.embybyashwin.Fragment.Home.FragmentSearch;
import com.ashwin.embybyashwin.emby.EmbyConnection;
import com.ashwin.embybyashwin.emby.GlobalClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import mediabrowser.apiinteraction.EmptyResponse;
import mediabrowser.apiinteraction.android.AndroidApiClient;
import mediabrowser.model.logging.NullLogger;

public class ActivityMain extends AppCompatActivity {

    private Boolean isLogin = false;
    private String TAG = "ActivityMain";
    private NullLogger logger;
    private EmbyConnection embyConnection;
    private AndroidApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogout = findViewById(R.id.btn_Logout);

        apiClient = GlobalClass.getInstance().getApiClient();


//        LoadLatest();

        LoadFragment(FragmentHome.newInstance());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogout();
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_bottom_activity_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.nav_bottom_activity_main_home:
                        fragment = FragmentHome.newInstance();
                        break;
                    case R.id.nav_bottom_activity_main_search:
                        fragment = FragmentSearch.newInstance();
                        break;
                    case R.id.nav_bottom_activity_main_movies:
                        fragment = FragmentHome.newInstance();
                        break;
                }
                LoadFragment(fragment);
                return true;
            }
        });
    }

    private void LoadFragment(Fragment fragment){
        getFragmentManager().beginTransaction().replace(R.id.sv_main, fragment).commit();
    }

    private void userLogout(){
        apiClient.Logout(new EmptyResponse());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        apiClient = null;
    }


}
