package com.baosight.brightfish;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baosight.brightfish.model.AlbumItem;
import com.baosight.brightfish.ui.AlbumAdapter;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */

public class AlbumBasicActivity extends CameraBasicActivity {

    List<AlbumItem> albumItemList;
    Toolbar toolbar;
    RelativeLayout addPhoto;
    RecyclerView recyclerView;
    AlbumAdapter adapter;

}
