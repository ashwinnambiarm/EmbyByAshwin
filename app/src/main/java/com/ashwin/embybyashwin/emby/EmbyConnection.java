package com.ashwin.embybyashwin.emby;

import android.content.Context;
import android.util.Log;

import com.ashwin.embybyashwin.emby.Logger;

import java.util.ArrayList;

import mediabrowser.apiinteraction.ApiEventListener;
import mediabrowser.apiinteraction.IConnectionManager;
import mediabrowser.apiinteraction.ICredentialProvider;
import mediabrowser.apiinteraction.Response;
import mediabrowser.apiinteraction.android.AndroidApiClient;
import mediabrowser.apiinteraction.android.AndroidConnectionManager;
import mediabrowser.apiinteraction.android.AndroidCredentialProvider;
import mediabrowser.apiinteraction.android.AndroidDevice;
import mediabrowser.apiinteraction.android.AndroidNetworkConnection;
import mediabrowser.apiinteraction.android.GsonJsonSerializer;
import mediabrowser.apiinteraction.android.VolleyHttpClient;
import mediabrowser.apiinteraction.device.IDevice;
import mediabrowser.apiinteraction.discovery.IServerLocator;
import mediabrowser.apiinteraction.discovery.ServerLocator;
import mediabrowser.apiinteraction.http.IAsyncHttpClient;
import mediabrowser.apiinteraction.network.INetworkConnection;
import mediabrowser.model.apiclient.ServerInfo;
import mediabrowser.model.logging.ILogger;
import mediabrowser.model.serialization.IJsonSerializer;
import mediabrowser.model.session.ClientCapabilities;

public class EmbyConnection  {

    private final Context context;
    private AndroidApiClient m_apiClient;
    private static IConnectionManager INSTANCE = null;
    String TAG = "EmbyConnection";

    public EmbyConnection(Context context){

        this.context = context;
        this.m_apiClient = null;
    }
    public IConnectionManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = getConnection();
        }
        return(INSTANCE);
    }

    private IConnectionManager getConnection(){
        // Developers are encouraged to create their own ILogger implementation
        ILogger logger = new Logger();

        // Android developers should use GsonJsonSerializer
        IJsonSerializer jsonSerializer = new GsonJsonSerializer();

        // Android developer should use AndroidCredentialProvider
//        ICredentialProvider credentialProvider = new AndroidCredentialProvider(jsonSerializer, context,logger);

//        INetworkConnection networkConnection = new AndroidNetworkConnection(context,logger);

//        IServerLocator serverLocator = new ServerLocator(logger, jsonSerializer);

        // The underlying http stack. Developers can inject their own if desired
        IAsyncHttpClient httpClient = new VolleyHttpClient(logger, context);

        // Android developers should use AndroidDevice
        IDevice device = new AndroidDevice(context);

        // This describes the device capabilities
        ClientCapabilities capabilities = new ClientCapabilities();

        ApiEventListener eventListener = new ApiEventListener();

//        IConnectionManager connectionManager = new ConnectionManager(credentialProvider,
//                networkConnection,
//                jsonSerializer,
//                logger,
//                serverLocator,
//                httpClient,
//                "My App",
//                "1.0.0",
//                device,
//                capabilities,eventListener);

       //  Android developers should use AndroidConnectionManager
        IConnectionManager connectionManager = new AndroidConnectionManager(context,
                jsonSerializer,
                logger,
                httpClient,
                "My app name",
                "1.0.0.0",
                device,
                capabilities,
                eventListener);

        return connectionManager;
    }

    public void setApiClient(final AndroidApiClient apiClient){
        m_apiClient = apiClient;
    }

    public AndroidApiClient getApiClient(){
        return m_apiClient;
    }


}
