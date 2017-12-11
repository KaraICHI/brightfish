package com.baosight.brightfish;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.baosight.brightfish.model.AlbumItem;
import com.baosight.brightfish.ui.AlbumAdapter;

import java.util.List;

public class AlbumBasicActivity extends CameraBasicActivity {

    List<AlbumItem> albumItemList;
    Toolbar toolbar;
    RelativeLayout addPhoto;
    RecyclerView recyclerView;
    AlbumAdapter adapter;

}
