package com.ashwin.embybyashwin.Fragment.Login;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.ashwin.embybyashwin.Fragment.Login.Adapter.ServerAdapter;
import com.ashwin.embybyashwin.Fragment.Login.Model.Server;
import com.ashwin.embybyashwin.R;

import java.util.ArrayList;

public class FragmentServers extends Fragment {

    ArrayList<Server> server;
    private FragmentServers.FragmentServersListener listener;
    private ImageLoader imageLoader;

    public interface FragmentServersListener{
        void OnClickFragmentServers(String serverName, String serverAddress);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_servers, container, false);

        RecyclerView rvServer = (RecyclerView) v.findViewById(R.id.recyclerViewServerSelect);

        if (server == null){
            server = new ArrayList<Server>();
        }

        ServerAdapter adapter = new ServerAdapter(server);

        adapter.setOnClickServerItem(new ServerAdapter.ServerSelectClickListener() {
            @Override
            public void OnClickServerItem(String serverName, String serverAddress) {
                listener.OnClickFragmentServers(serverName, serverAddress);
//                Log.e("OnClickServerItem", "Clcked");
            }
        });

        rvServer.setAdapter(adapter);
        rvServer.setLayoutManager(new GridLayoutManager(getContext(),2));
//        rvServer.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentServers.FragmentServersListener){
            listener = (FragmentServers.FragmentServersListener) context;
        }else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentServersListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void setServerList (ArrayList<Server> serverList){
        server = serverList;
    }
}