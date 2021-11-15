package com.ashwin.embybyashwin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ashwin.embybyashwin.Fragment.Home.FragmentMyMedia;
import com.ashwin.embybyashwin.Fragment.Home.Model.MyMedia;
import com.ashwin.embybyashwin.Fragment.Login.Model.User;
import com.ashwin.embybyashwin.emby.EmbyConnection;
import com.ashwin.embybyashwin.emby.GlobalClass;

import java.util.ArrayList;

import mediabrowser.apiinteraction.ApiClient;
import mediabrowser.apiinteraction.ConnectionResult;
import mediabrowser.apiinteraction.EmptyResponse;
import mediabrowser.apiinteraction.Response;
import mediabrowser.apiinteraction.android.AndroidApiClient;
import mediabrowser.model.drawing.ImageFormat;
import mediabrowser.model.dto.BaseItemDto;
import mediabrowser.model.dto.ImageOptions;
import mediabrowser.model.dto.UserDto;
import mediabrowser.model.entities.ImageType;
import mediabrowser.model.logging.NullLogger;
import mediabrowser.model.querying.ItemsResult;

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

        FragmentMyMedia fragmentMyMedia = new FragmentMyMedia();

        ArrayList<MyMedia> myMediaList = new ArrayList<MyMedia>();

        apiClient.GetUserViews(apiClient.getCurrentUserId(), new Response<ItemsResult>(){
            @Override
            public void onResponse(ItemsResult response) {
                ImageOptions options = new ImageOptions();
                options.setImageType(ImageType.Primary);
                options.setFormat(ImageFormat.Png);
                options.setMaxHeight(107);

                for (BaseItemDto item: response.getItems()) {
                    Log.e(TAG, "Library  ->" + item.getName()
                            + " Library image url ->" + apiClient.GetImageUrl(item,options));
                    MyMedia myMedia = new MyMedia();
                    myMedia.setMediaName(item.getName());
                    myMedia.setThumbanilUrl(apiClient.GetImageUrl(item,options));

                    myMediaList.add(myMedia);
                }

                fragmentMyMedia.setMyMediaList(myMediaList);

                loadFragment(fragmentMyMedia, R.id.fl_home_section_1);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogout();
            }
        });

    }

    private void loadFragment(Fragment fragment, Integer layoutID){
        if (fragment !=null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction()
                    .replace(layoutID, fragment)
                    .addToBackStack(null);
            fragmentTransaction.commit();
            Log.e(TAG, "fragment loaded");
        }else {
            Log.e(TAG, "fragment is null");
        }
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
