package com.ashwin.embybyashwin.Fragment.Login;

import android.content.Context;
import android.os.Bundle;

import android.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashwin.embybyashwin.Fragment.Login.Adapter.UserAdapter;
import com.ashwin.embybyashwin.Fragment.Login.Model.User;
import com.ashwin.embybyashwin.R;

import java.util.ArrayList;

public class FragmentUsers extends Fragment {
    private FragmentUsersListener listener;
    ArrayList<User> user;

    public interface FragmentUsersListener{
        void OnClickFragmentUser(String username);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_users, container, false);

        RecyclerView rvUser = (RecyclerView) v.findViewById(R.id.recyclerViewUserSelect);

        if (user == null){
            user = new ArrayList<User>();
        }

        UserAdapter adapter = new UserAdapter(user);

        adapter.setOnClickUserItem(new UserAdapter.UserSelectClickListener() {
            @Override
            public void OnClickUserItem(String username) {
                listener.OnClickFragmentUser(username);
            }
        });

        rvUser.setAdapter(adapter);
        rvUser.setLayoutManager(new GridLayoutManager(getContext(),2));
//        rvServer.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentUsers.FragmentUsersListener){
            listener = (FragmentUsers.FragmentUsersListener) context;
        }else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentUsersListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void setUserList (ArrayList<User> userListList){
        user = userListList;
    }
}