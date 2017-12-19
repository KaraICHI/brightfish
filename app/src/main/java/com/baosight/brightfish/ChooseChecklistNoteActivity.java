package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.baosight.brightfish.model.ChecklistNote;
import com.baosight.brightfish.ui.ChecklistNoteAdapter;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ChooseChecklistNoteActivity extends BasicActivity {
    List<ChecklistNote> checklistNoteList= DataSupport.findAll(ChecklistNote.class);
    SwipeMenuListView listView;
    ChecklistNoteAdapter adapter;
    public static void startChecklistNoteHistoryActivity(Context context){
        Intent intent=new Intent(context,ChooseChecklistNoteActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_checklist_note);
        initControls();

    }

    private void initControls() {
        initToolbar(R.color.colorOrange);
        listView = (SwipeMenuListView) findViewById(R.id.check_list);
        adapter=new ChecklistNoteAdapter(ChooseChecklistNoteActivity.this,R.layout.item_checklist_note,checklistNoteList);
        listView.setAdapter(adapter);
        listView.setMenuCreator(getSlideMenuCreator());
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        ChecklistNoteActivity.startChecklistNoteActivity(ChooseChecklistNoteActivity.this,checklistNoteList.get(position));
                        break;
                    case 1:
                        DataSupport.delete(ChecklistNote.class,checklistNoteList.remove(position).getId());
                        adapter=new ChecklistNoteAdapter(ChooseChecklistNoteActivity.this,R.layout.item_checklist_note,checklistNoteList);
                        listView.setAdapter(adapter);
                        break;
                }
                return false;
            }
        });

    }
}
