package com.baosight.brightfish.ui.album;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.AlbumItem;

import java.util.List;


public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private List<AlbumItem> mAlbumItemList;
    private Context context;

    public AlbumAdapter(List<AlbumItem> albumItemList){
        mAlbumItemList = albumItemList;

    }
    
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ablum, viewGroup, false);
        context=viewGroup.getContext();
        return new ViewHolder(view);

    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
            final AlbumItem albumItem = mAlbumItemList.get(position);
            holder.ablumPhoto.setImageBitmap(albumItem.getAblumPhoto());
            holder.ablumPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(albumItem.getChecked()){
                        albumItem.setChecked(false);
                        holder.ablumPhotoChoose.setVisibility(View.INVISIBLE);
                    }else{
                        albumItem.setChecked(true);
                        holder.ablumPhotoChoose.setVisibility(View.VISIBLE);
                    }

                }
            });
            holder.ablumPhoto.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setMessage("是否删除照片");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAlbumItemList.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                    return true;
                }
            });
    }

    @Override
    public int getItemCount() {
        return mAlbumItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ablumPhoto;
        ImageView ablumPhotoChoose;
        public ViewHolder(View itemView) {
            super(itemView);
                ablumPhoto = (ImageView) itemView.findViewById(R.id.ablum_item_photo);
                        ablumPhotoChoose= (ImageView) itemView.findViewById(R.id.ablum_item_photo_choose);
                        }
                        }

                        }
