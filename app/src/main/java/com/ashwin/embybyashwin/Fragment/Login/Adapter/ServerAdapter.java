package com.ashwin.embybyashwin.Fragment.Login.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashwin.embybyashwin.Fragment.Login.Model.Server;
import com.ashwin.embybyashwin.R;

import java.util.List;

public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.ViewHolder> {

    private final List<Server> serverList;
    private static ServerAdapter.ServerSelectClickListener listener;

    public interface ServerSelectClickListener{
        void OnClickServerItem(String serverName, String serverAddress);
    }

    public void setOnClickServerItem(ServerSelectClickListener listener){
        ServerAdapter.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_item_login, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ServerAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Server server = serverList.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.txtServerName;
        textView.setText(server.getServerName());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClickServerItem(server.getServerName(), server.getServerAddress());
//                Log.e("ServerAdapter", "Clcked");
            }
        });
    }

    @Override
    public int getItemCount() {
        return serverList.size();
    }

    public ServerAdapter(List<Server> servers){
        serverList = servers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtServerName;
        private final LinearLayout linearLayout;

        public  ViewHolder(View itemView){
            super(itemView);
            txtServerName = (TextView) itemView.findViewById(R.id.txt_server_name);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.ly_login_grid_item);
        }
    }


    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        ServerAdapter.listener = null;
    }
}
