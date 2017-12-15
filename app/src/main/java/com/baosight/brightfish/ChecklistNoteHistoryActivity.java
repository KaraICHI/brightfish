package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import com.baosight.brightfish.model.ChecklistNote;
import com.baosight.brightfish.ui.ChecklistNoteAdapter;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ChecklistNoteHistoryActivity extends BasicActivity {
    private static final String TAG = "ChecklistNoteHistoryAct";
    List<ChecklistNote> checklistNoteList= DataSupport.findAll(ChecklistNote.class);
    SwipeMenuListView listView;
    ChecklistNoteAdapter adapter;
    public static void startChecklistNoteHistoryActivity(Context context){
        Intent intent=new Intent(context,ChecklistNoteHistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_history_note);
        initControls();

    }

    private void initControls() {
        initToolbar(R.color.colorOrange);
        listView = (SwipeMenuListView) findViewById(R.id.check_list);
        adapter=new ChecklistNoteAdapter(ChecklistNoteHistoryActivity.this,R.layout.item_checklist_note,checklistNoteList);
        listView.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(180);
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(180);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete_white_24dp);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        ChecklistNoteActivity.startChecklistNoteActivity(ChecklistNoteHistoryActivity.this,checklistNoteList.get(position));
                        break;
                    case 1:
                        checklistNoteList.remove(position);
                        DataSupport.delete(ChecklistNote.class,position+1);
                        Log.d(TAG, "onMenuItemClick: ========="+position);
                        Log.d(TAG, "onMenuItemClick: =====size"+DataSupport.findAll(ChecklistNote.class).size());
                        adapter.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

    }
}
