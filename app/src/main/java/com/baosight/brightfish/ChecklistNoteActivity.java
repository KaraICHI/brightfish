package com.baosight.brightfish;

import android.os.Bundle;
import android.widget.ListView;

import com.baosight.brightfish.model.ChecklistNote;
import com.baosight.brightfish.ui.ChecklistNoteAdapter;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ChecklistNoteActivity extends BasicActivity {
    List<ChecklistNote> checklistNoteList= DataSupport.findAll(ChecklistNote.class);
    ListView listView;
    ChecklistNoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_note);
        initControls();
    }

    private void initControls() {
        initToolbar(R.color.colorOrange);
        listView = (ListView) findViewById(R.id.check_list);

    }
}
