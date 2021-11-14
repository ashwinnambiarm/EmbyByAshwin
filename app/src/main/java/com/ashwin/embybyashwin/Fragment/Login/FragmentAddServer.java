package com.ashwin.embybyashwin.Fragment.Login;

import android.content.Context;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ashwin.embybyashwin.R;


public class FragmentAddServer extends Fragment {


    private FragmentAddServer.FragmentAddServerListener listener;

    public interface FragmentAddServerListener{
        void OnClickFragmentAddServer(String serverAddress, String serverPort);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_server, container, false);

        final EditText txtServerAddress = v.findViewById(R.id.txt_add_server_address);
        final EditText txtServerPort = v.findViewById(R.id.txt_add_server_port);
        final Button btnConnect = v.findViewById(R.id.btn_add_server_connect);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String _address = txtServerAddress.getText().toString();
                String _port = txtServerPort.getText().toString();

                listener.OnClickFragmentAddServer(_address, _port);

                // Go back to the previous Fragment
//                getFragmentManager().popBackStackImmediate();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentAddServer.FragmentAddServerListener){
            listener = (FragmentAddServer.FragmentAddServerListener) context;
        }else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentAddServerListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}