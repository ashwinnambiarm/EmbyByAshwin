package com.ashwin.embybyashwin.Fragment.Login;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ashwin.embybyashwin.R;

import mediabrowser.apiinteraction.ConnectionResult;


public class FragmentSignIn extends Fragment {
    ConnectionResult connectionResult;
    private FragmentSignInListener listener;
    private String userName;

    public interface FragmentSignInListener{
        void OnClickFragmentSignIn(String username, String password);
        void OnClickFragmentSignInChangeServer();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);

        final EditText txtUsername = v.findViewById(R.id.txt_signin_username);
        final EditText txtPassword = v.findViewById(R.id.txt_signin_password);
        final Button btnSignIn = v.findViewById(R.id.btn_signin_login);
        final Button btnChangeServer = v.findViewById(R.id.btn_signin_change_server);

        if (userName != null){
            txtUsername.setText(userName);
        }

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _username = txtUsername.getText().toString();
                String _password = txtPassword.getText().toString();

                listener.OnClickFragmentSignIn(_username, _password);
            }
        });

        btnChangeServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClickFragmentSignInChangeServer();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentSignInListener){
            listener = (FragmentSignInListener) context;
        }else {
            throw new RuntimeException(context.toString()
            + " must implement FragmentSignInListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void setUsername (String username){
        userName = username;
    }
}