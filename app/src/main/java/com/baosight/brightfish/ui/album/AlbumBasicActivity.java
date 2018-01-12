package com.baosight.brightfish.ui.album;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.AlbumItem;
import com.baosight.brightfish.ui.CameraBasicActivity;

import java.util.ArrayList;
import java.util.List;

public class AlbumBasicActivity extends CameraBasicActivity {

    List<AlbumItem> albumItemList;
    RelativeLayout addPhoto;
    RecyclerView recyclerView;
    AlbumAdapter adapter;

    protected void setCropSize(){
        aspectX=1;
        aspectY=1;
    }
    protected void initControls() {
        recyclerView = (RecyclerView) findViewById(R.id.add_Ablum_rec);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        initAblum();
        adapter = new AlbumAdapter(albumItemList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        addPhoto = (RelativeLayout) findViewById(R.id.add_photo);
        initCameraControls(addPhoto, null, null);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }

        });

    }
    private void initAblum() {
        albumItemList = new ArrayList<>();
        AlbumItem album = new AlbumItem();
        albumItemList.add(album);

    }



}
