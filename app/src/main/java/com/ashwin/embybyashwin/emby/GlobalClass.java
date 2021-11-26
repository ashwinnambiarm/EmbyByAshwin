package com.ashwin.embybyashwin.emby;

import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import mediabrowser.apiinteraction.android.AndroidApiClient;

public final class GlobalClass {
    private static GlobalClass INSTANCE = null;
    private AndroidApiClient m_apiClient;

    private GlobalClass() {};

    public static GlobalClass getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GlobalClass();
        }
        return(INSTANCE);
    }

    public void setApiClient(final AndroidApiClient apiClient){
        INSTANCE.m_apiClient = apiClient;
    }

    public AndroidApiClient getApiClient(){
        return INSTANCE.m_apiClient;
    }

    public ImageLoader getImageLoader(){
        return INSTANCE.m_apiClient.getImageLoader();
    }

    public void LoadImagetoView(String url, ImageView imageView){
        INSTANCE.m_apiClient.getImageLoader().get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                imageView.setImageBitmap(imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
    }


}
