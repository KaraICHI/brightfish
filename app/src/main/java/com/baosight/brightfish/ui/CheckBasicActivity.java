package com.baosight.brightfish.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baosight.brightfish.R;
import com.baosight.brightfish.model.Goods;
import com.baosight.brightfish.ui.search.choose.ChooseGoodsdDialogAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;


public class CheckBasicActivity extends BasicActivity implements View.OnClickListener{
    protected String content = "";
    protected boolean isFocus;
    TextView currentTime;
    Button commit;
    static EditText goodsSku;
    static EditText goodsName;
    EditText price;
    EditText amount;
    EditText description;
    Dialog dialog;
    ImageButton goodsMenu, goodsRefesh, goodsPhoto;
    List<EditText> editTexts = new ArrayList<>();



    protected void initControls() {

        currentTime = (TextView) findViewById(R.id.current_time);

        commit = (Button) findViewById(R.id.checkin_commit);
        assert commit != null;
        goodsSku = (EditText) findViewById(R.id.goods_sku_checkin);
        goodsName = (EditText) findViewById(R.id.goods_name_checkin);
        price = (EditText) findViewById(R.id.price_checkin);
        amount = (EditText) findViewById(R.id.amount_checkin);
        description = (EditText) findViewById(R.id.descripe_checkin);
        goodsMenu = (ImageButton) findViewById(R.id.check_good_menu);
        goodsRefesh = (ImageButton) findViewById(R.id.check_good_refesh);
        goodsPhoto = (ImageButton) findViewById(R.id.photo_goods);

    }


    protected void setCheckLisener(){

        editTexts.add(goodsSku);
        editTexts.add(price);
        editTexts.add(amount);
        editTexts.add(description);
        editTexts.add(goodsName);
        commit.setOnClickListener(this);
        goodsPhoto.setOnClickListener(this);
        goodsMenu.setOnClickListener(this);
        goodsRefesh.setOnClickListener(this);

        for (EditText editText : editTexts) {
            editText.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

    }

    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkin_bar_menu, menu);
        return true;
    }



    protected boolean isEditTextBlank() {

        if (CheckinActivity.goods == null && TextUtils.isEmpty(goodsSku.getText())) {
            showAlertDialog("内容不能为空");
            return true;
        } else if (!selectGoods(goodsSku.getText().toString())) {
            showNoThatGoodsDialog();
            return true;
        } else if (TextUtils.isEmpty(price.getText())) {
            showAlertDialog("价格不能为空");
            return true;
        } else if (TextUtils.isEmpty(amount.getText())) {
            showAlertDialog("数量不能为空");
            return true;
        }else {
            return false;
        }
    }
    protected void showNoThatGoodsDialog() {
        dialog = new Dialog(CheckBasicActivity.this);
        dialog.setTitle("没有该货品！");
        dialog.setContentView(R.layout.dialog_no_goods);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.dialog_rec);
        Button addNew=(Button) dialog.findViewById(R.id.add_new_goods);
        Button addCancel=(Button) dialog.findViewById(R.id.add_cancel);
        addCancel.setOnClickListener(this);
        addNew.setOnClickListener(this);
        ChooseGoodsdDialogAdapter adapter = new ChooseGoodsdDialogAdapter(DataSupport.findAll(Goods.class),dialog);
        LinearLayoutManager layoutManager=new LinearLayoutManager(CheckBasicActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        dialog.show();

    }



    protected void showAlertDialog(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(CheckBasicActivity.this);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setMessage(message);
        dialog.show();

    }
    protected Boolean selectGoods(String sku) {
        List<Goods> goodsList = DataSupport.where("sku = ?", sku).find(Goods.class);
        if (goodsList.size() >= 1) {
            CheckinActivity.goods = goodsList.get(0);
            return true;
        } else {

            return false;
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!isFocus) {
            menu.findItem(R.id.check_mark).setVisible(false);
        } else {
            menu.findItem(R.id.check_mark).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }
    public void clearEditText(){
        goodsName.setText("");
        goodsSku.setText("");
        price.setText("");
        amount.setText("");
        description.setText("");
    }




}
