package com.baosight.brightfish.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.baosight.brightfish.BasicActivity;
import com.baosight.brightfish.R;
import com.baosight.brightfish.model.Checkin;
import com.baosight.brightfish.model.ChecklistNote;
import com.baosight.brightfish.model.Goods;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/12/13.
 */

public class ChecklistNoteAdapter extends ArrayAdapter<ChecklistNote>{
    private int resourceId;
    public ChecklistNoteAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        resourceId=resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ChecklistNote checklistNote=getItem(position);
        Goods goods= DataSupport.find(Goods.class,checklistNote.getId());
        View view= LayoutInflater.from(parent.getContext()).inflate(resourceId,parent,false);
        TextView noteIcon=(TextView) view.findViewById(R.id.note_icon);
        TextView checkName=(TextView) view.findViewById(R.id.choose_name);
        TextView checkSku=(TextView) view.findViewById(R.id.choose_sku);
        TextView checkAmount=(TextView) view.findViewById(R.id.check_amount);
        TextView checkChange=(TextView) view.findViewById(R.id.check_change);
        TextView checkDate=(TextView) view.findViewById(R.id.check_date);
        noteIcon.setBackground(parent.getContext().getResources().getDrawable(R.drawable.rect_green));
        char firstName=goods.getName().charAt(0);
        noteIcon.setText(firstName+"");
        checkName.setText(goods.getName());
        checkSku.setText(goods.getSku());
        checkAmount.setText(checklistNote.getCurrAmount()+"");
        checkChange.setText(checklistNote.getChange().split(" ")[1]);
        checkDate.setText(checklistNote.getNoteDate());
        return view;
    }
}
