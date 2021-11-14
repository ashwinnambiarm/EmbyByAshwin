package com.ashwin.embybyashwin.Fragment.Login.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashwin.embybyashwin.Fragment.Login.Model.User;
import com.ashwin.embybyashwin.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private final List<User> userList;
    private static UserAdapter.UserSelectClickListener listener;

    public interface UserSelectClickListener{
        void OnClickUserItem(String username);
    }

    public void setOnClickUserItem(UserAdapter.UserSelectClickListener listener){
        UserAdapter.listener = listener;
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
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        User user = userList.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.txtUserName;
        textView.setText(user.getUserName());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClickUserItem(user.getUserName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public UserAdapter(List<User> users){
        userList = users;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtUserName;
        private final LinearLayout linearLayout;

        public  ViewHolder(View itemView){
            super(itemView);
            txtUserName = (TextView) itemView.findViewById(R.id.txt_server_name);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.ly_login_grid_item);
        }
    }


    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        UserAdapter.listener = null;
    }

}
