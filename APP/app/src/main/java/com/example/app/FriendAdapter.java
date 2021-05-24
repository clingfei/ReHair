package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FriendAdapter extends ArrayAdapter<com.example.app.Friend> {
    private int resourceId;
    public FriendAdapter(Context context, int textViewResourceId, List<com.example.app.Friend>objects) {
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        com.example.app.Friend friend = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ImageView friendImage = (ImageView) view.findViewById(R.id.friend_image);
        TextView friendName = (TextView) view.findViewById(R.id.friend_name);
        friendImage.setImageResource(friend.getImageID());
        friendName.setText(friend.getUsername());
        return view;
    }

}
