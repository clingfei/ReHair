package com.example.app;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShowImageAdapter extends RecyclerView.Adapter<ShowImageAdapter.ViewHolder> {
    private List<ShowImage> mShowImageList;
    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView showImage;
        TextView showNum;
        public ViewHolder(@NonNull View view){
            super(view);

            showImage=(ImageView) view.findViewById(R.id.show_image);
            showNum=(TextView) view.findViewById(R.id.show_num);
        }
    }
    public ShowImageAdapter(List<ShowImage>imageList) {
        mShowImageList= imageList;

            }
            @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.show_item,parent,false);
         ViewHolder holder = new ViewHolder(view);

        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ShowImage showi = mShowImageList.get(position);
        holder.showImage.setImageResource(showi.getShowImage());
        holder.showNum.setText(showi.getShowName());
    }
    @Override
    public int getItemCount(){
        return mShowImageList.size();
    }
    }


