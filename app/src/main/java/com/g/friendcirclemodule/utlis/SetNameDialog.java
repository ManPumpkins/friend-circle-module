package com.g.friendcirclemodule.utlis;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.g.friendcirclemodule.R;
import com.g.friendcirclemodule.databinding.SetNameDialogBinding;
import com.g.friendcirclemodule.dp.DMEntryUseInfoBase;
import com.g.friendcirclemodule.dp.FeedManager;
import com.g.friendcirclemodule.model.BaseModel;

public class SetNameDialog extends BaseDialog<SetNameDialogBinding, BaseModel> {
    int uId = 1;
    public SetNameDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void initView() {
        super.initView();
        viewbinding.setNameBtnCancel.setOnClickListener(v -> {
            cancel();
        });
        viewbinding.setNameBtnEnsure.setOnClickListener(v -> {
            String name = String.valueOf(viewbinding.setNameEt.getText());
            Log.i("dddddd", name);
            DMEntryUseInfoBase dmEntryBase = new DMEntryUseInfoBase(1, uId,name, "");
            FeedManager.InsertItemToUserInfo(dmEntryBase);
            cancel();
        });
    }

    // 获取输入数据方法
    public String getEditText() {
        return viewbinding.setNameEt.getText().toString().trim();
    }

    //设置尺寸
    public void setDialogSize() {

        Window window = getWindow();

        WindowManager.LayoutParams wlp = window.getAttributes();

        WindowMetrics windowMetrics = window.getWindowManager().getCurrentWindowMetrics();
        Rect bounds = windowMetrics.getBounds();
        int width = bounds.width();  // 屏幕宽度

        if (width > (int) (410 * getContext().getResources().getDisplayMetrics().density)) {
            width = (int) (410 * getContext().getResources().getDisplayMetrics().density);
        }

        wlp.width = width;

        wlp.gravity = Gravity.BOTTOM;

        window.setBackgroundDrawableResource(android.R.color.transparent);

        window.setAttributes(wlp);

        handler.sendEmptyMessageDelayed(1,100);
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };
}