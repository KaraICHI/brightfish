package com.baosight.brightfish.ui;

import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.baosight.brightfish.model.AlbumItem;
import com.baosight.brightfish.R;

import java.util.List;

/**
 * Created by saitama on 2017/11/24.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private static final String TAG = "AlbumAdapter";
    private List<AlbumItem> mAlbumItemList;

    public AlbumAdapter(List<AlbumItem> albumItemList){
        mAlbumItemList = albumItemList;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ==================");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ablum, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
            AlbumItem albumItem = mAlbumItemList.get(position);
            holder.ablumPhoto.setImageBitmap(albumItem.getAblumPhoto());
            holder.ablumPhotoChoose.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(((RadioButton)v).isChecked() ){
                        ((RadioButton) v).setChecked(false);
                        return true;
                    }else {
                        return false;
                    }


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
