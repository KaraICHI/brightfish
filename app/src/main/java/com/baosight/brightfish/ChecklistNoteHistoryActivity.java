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
        listView.setMenuCreator(getSlideMenuCreator());
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        ChecklistNoteActivity.startChecklistNoteActivity(ChecklistNoteHistoryActivity.this,checklistNoteList.get(position));
                        break;
                    case 1:
                        DataSupport.delete(ChecklistNote.class,checklistNoteList.remove(position).getId());
                        adapter=new ChecklistNoteAdapter(ChecklistNoteHistoryActivity.this,R.layout.item_checklist_note,checklistNoteList);
                        listView.setAdapter(adapter);
                        break;
                }
                return false;
            }
        });

    }
}
