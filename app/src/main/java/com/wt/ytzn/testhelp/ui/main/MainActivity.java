package com.wt.ytzn.testhelp.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.wt.ytzn.testhelp.database.ErrorDBOperate;
import com.wt.ytzn.testhelp.database.NetworkDBOperate;
import com.wt.ytzn.testhelp.databinding.ActivityMainBinding;
import com.wt.ytzn.testhelp.ui.error.ErrorLogActivity;
import com.wt.ytzn.testhelp.ui.net.LogActivity;
import com.wt.ytzn.testhelp.util.ButtonDelayUtil;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.error.setOnClickListener(v -> {
            if (ButtonDelayUtil.isFastClick()) {
                Intent intent = new Intent(this, ErrorLogActivity.class);
                startActivity(intent);
            }
        });
        mBinding.network.setOnClickListener(v -> {
            if (ButtonDelayUtil.isFastClick()) {
                Intent intent = new Intent(this, LogActivity.class);
                startActivity(intent);
            }

        });
        mBinding.delete.setOnClickListener(v -> {
            showDeleteDialog();
        });
    }

    private void showDeleteDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("是否删除全部数据")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NetworkDBOperate.getInstance().deleteAll();
                        ErrorDBOperate.getInstance().deleteAll();
                        Toast.makeText(MainActivity.this, "全部清理完毕", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

}