package com.baosight.brightfish.ui.album;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.baosight.brightfish.model.AlbumItem;
import com.baosight.brightfish.ui.CameraBasicActivity;

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


}
