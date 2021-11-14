package com.ashwin.embybyashwin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.ashwin.embybyashwin.Fragment.Login.FragmentAddServer;
import com.ashwin.embybyashwin.Fragment.Login.FragmentServers;
import com.ashwin.embybyashwin.Fragment.Login.FragmentSignIn;
import com.ashwin.embybyashwin.Fragment.Login.FragmentUsers;
import com.ashwin.embybyashwin.Fragment.Login.Model.Server;
import com.ashwin.embybyashwin.Fragment.Login.Model.User;
import com.ashwin.embybyashwin.emby.EmbyConnection;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import mediabrowser.apiinteraction.ApiClient;
import mediabrowser.apiinteraction.ConnectionResult;
import mediabrowser.apiinteraction.Response;
import mediabrowser.apiinteraction.android.AndroidApiClient;
import mediabrowser.model.apiclient.ServerInfo;
import mediabrowser.model.dto.UserDto;
import mediabrowser.model.users.AuthenticationResult;

public class ActivityLogin extends AppCompatActivity implements FragmentSignIn.FragmentSignInListener,
        FragmentServers.FragmentServersListener,
        FragmentAddServer.FragmentAddServerListener,
        FragmentUsers.FragmentUsersListener{

    String TAG = "ActivityLogin";

    private EmbyConnection embyConnection;
    private ApiClient apiClient;
    private String currentServerID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Intent intent = getIntent();

        embyConnection = new EmbyConnection(getApplicationContext());

        embyConnection.getConnection().Connect(new Response<ConnectionResult>() {
            @Override
            public void onResponse(ConnectionResult result) {

                Log.e(TAG, "ConnectionState " + result.getState());

                Integer serverCount = result.getServers().size();
                Log.e(TAG, "Server Count ->" + serverCount);


                switch (result.getState())
                {
                    case ConnectSignIn:
                        // Connect sign in screen should be presented
                        // Authenticate using LoginToConnect, then call Connect again to start over
                        ShowServerSelection(null);
                        break;
                    case ServerSignIn:
                        // A server was found and the user needs to login.
                        // Display a login screen and authenticate with the server using result.ApiClient
                        embyConnection.setApiClient((AndroidApiClient) result.getApiClient());

                        ArrayList<User> users = new ArrayList<User>();

                        embyConnection.getApiClient().GetPublicUsersAsync(new Response<UserDto[]>(){
                            @Override
                            public void onResponse(UserDto[] response) {
                                Log.e(TAG, "Public Users number  ->" + response.length);
                                for (UserDto user: response) {
                                    Log.e(TAG, "Public Users  ->" + user.getName());
                                    users.add(new User(user.getName()));
                                }
                                ShowUserSelection(users);
                            }

                            @Override
                            public void onError(Exception exception) {
                                Log.e(TAG, "Users Error ->" + exception.getLocalizedMessage());
                                ShowUserSelection(null);
                            }
                        });
                        break;
                    case ServerSelection:
                        // Multiple servers available
                        // Display a selection screen by calling GetAvailableServers
                        // When a server is chosen, call the Connect overload that accept either a ServerInfo object or a String url.
                        ShowServerSelection(result.getServers());
                        break;
                    case SignedIn:
                        // A server was found and the user has been signed in using previously saved credentials.
                        // Ready to browse using result.ApiClient
                        ShowMainActivity();
                        break;
                }
            }
        });


    }

    private void ShowUserSelection(ArrayList<User> users){
        FragmentUsers fragmentUsers = new FragmentUsers();

        if (users == null) users = new ArrayList<User>();

        users.add(new User("Manual Login"));
        users.add(new User("Change Server"));
        fragmentUsers.setUserList(users);
        loadFragment(fragmentUsers, "USERS");
    }

    private void ShowServerSelection(ArrayList<ServerInfo> serverInfo){
        FragmentServers fragmentServers = new FragmentServers();

        ArrayList<Server> server = new ArrayList<Server>();

        if (serverInfo != null){
            for(ServerInfo _server : serverInfo){
                server.add(new Server(_server.getName() ,_server.getLocalAddress()));
            }
        }

        server.add(new Server("Add Server", "ASHWIN"));
        fragmentServers.setServerList(server);
        loadFragment(fragmentServers, "SERVERS");
    }

    private void ShowServerSignIn(){
        ShowServerSignIn(null);
    }
    private void ShowServerSignIn(String username){
        FragmentSignIn fragmentSignIn = new FragmentSignIn();
        fragmentSignIn.setUsername(username);
        loadFragment(fragmentSignIn, "SIGN_IN");
    }

    private void ShowMainActivity(){
        Intent intent = new Intent(this, ActivityMain.class);
        startActivity(intent);
        finish();
    }

    private void loadFragment(Fragment fragment, String tag){
        if (fragment !=null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction()
                    .replace(R.id.frameLayoutSignIn, fragment)
                    .addToBackStack(null);
            fragmentTransaction.commit();
        }else {
            Log.e(TAG, "fragment is null");
        }
    }

    @Override
    public void OnClickFragmentSignIn(String username, String password) {

        if (apiClient ==null){
            Log.e(TAG, "data from FragmentSignIn " + username + " " + password);
        }

        try {
            if ( embyConnection.getApiClient() != null){
                embyConnection.getApiClient()
                .AuthenticateUserAsync(username, password,new Response<AuthenticationResult>(){
                    @Override
                    public void onResponse(AuthenticationResult response) {

                        Log.e(TAG, "Login Success, token=" + response.getAccessToken());
                        ShowMainActivity();
                    }
                });
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnClickFragmentSignInChangeServer() {
        embyConnection.getConnection().Connect(new Response<ConnectionResult>() {
           @Override
           public void onResponse(ConnectionResult response) {
               ShowServerSelection(response.getServers());
           }
       });

    }

    @Override
    public void OnClickFragmentServers(String serverName, String serverAddress) {
        Log.e(TAG, "Server item=> " + serverName + " " + serverAddress);

        if (serverName == "Add Server" && serverAddress == "ASHWIN"){
            //Open Add Server Screen
            Log.e(TAG, "Open Add Server Screen");

            loadFragment(new FragmentAddServer(), "ADD_SERVER");
        }else{
            //Open Sign in Screen
            Log.e(TAG, "Open Sign in Screen");

            embyConnection.getConnection().Connect(serverAddress, new Response<ConnectionResult>() {

                @Override
                public void onResponse(ConnectionResult response) {
                    switch (response.getState()) {
                        case Unavailable:
                            // Server unreachable
                            Log.e(TAG, "Server unreachable " + serverAddress);
                            break;
                        case ServerSignIn:
                            // A server was found and the user needs to login.
                            // Display a login screen and authenticate with the server using result.ApiClient
                            Log.e(TAG, "A server was found and the user needs to login. " + serverAddress);
                            loadFragment(new FragmentSignIn(), "SIGN_IN");
                            break;
                        case SignedIn:
                            // A server was found and the user has been signed in using previously saved credentials.
                            // Ready to browse using result.ApiClient
                            Log.e(TAG, "A server was found and the user has been signed in using previously saved credentials. " + serverAddress);
                            break;
                    }

                }

            });
        }
    }

    @Override
    public void OnClickFragmentAddServer(String serverAddress, String serverPort) {

        String address = "http://" + serverAddress + ":" + serverPort;

        Log.e(TAG, "Connecting to Server " + address);

        embyConnection.getConnection().Connect(address, new Response<ConnectionResult>(){

            @Override
            public void onResponse(ConnectionResult response) {
                switch (response.getState())
                {
                    case Unavailable:
                        // Server unreachable
                        Log.e(TAG, "Server unreachable " + address);
                        break;
                    case ServerSignIn:
                        // A server was found and the user needs to login.
                        // Display a login screen and authenticate with the server using result.ApiClient
                        Log.e(TAG, "A server was found and the user needs to login. " + address);
                        break;
                    case SignedIn:
                        // A server was found and the user has been signed in using previously saved credentials.
                        // Ready to browse using result.ApiClient
                        Log.e(TAG, "A server was found and the user has been signed in using previously saved credentials. " + address);
                        break;
                }

            }

            @Override
            public void onError(Exception exception) {
                super.onError(exception);
                Log.e(TAG, "Failed connecting to Server " + address);
            }
        });
    }

    @Override
    public void OnClickFragmentUser(String username) {
        Log.e(TAG, "Username " + username);
        if (username == "Manual Login") {
            ShowServerSignIn();
        }else if (username == "Change Server"){
            OnClickFragmentSignInChangeServer();
        }else{
            ShowServerSignIn(username);
        }

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
