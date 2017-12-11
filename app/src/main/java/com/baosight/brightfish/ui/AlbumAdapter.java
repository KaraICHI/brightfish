package com.baosight.brightfish.ui;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.baosight.brightfish.R;
import com.baosight.brightfish.model.AlbumItem;

import java.util.List;


public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private List<AlbumItem> mAlbumItemList;
    static RadioButton selected;

    public AlbumAdapter(List<AlbumItem> albumItemList){
        mAlbumItemList = albumItemList;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ablum, viewGroup, false);
        return new ViewHolder(view);

    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
            AlbumItem albumItem = mAlbumItemList.get(position);
            holder.ablumPhoto.setImageBitmap(albumItem.getAblumPhoto());
            holder.ablumPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.ablumPhotoChoose.setChecked(true);
                    if(selected!=null){
                        selected.setChecked(false);
                    }
                    selected=holder.ablumPhotoChoose;
                }
            });
    }

    @Override
    public int getItemCount() {
        return mAlbumItemList.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ablumPhoto;
        RadioButton ablumPhotoChoose;
        public ViewHolder(View itemView) {
            super(itemView);
                ablumPhoto = (ImageView) itemView.findViewById(R.id.ablum_item_photo);
                ablumPhotoChoose=(RadioButton) itemView.findViewById(R.id.ablum_item_photo_choose);

        }
    }


}
