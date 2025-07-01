package com.g.friendcirclemodule.utlis;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

import com.g.friendcirclemodule.interface_method.BaseBindingInterface;

import java.lang.reflect.Field;

public class BaseDialog<VB extends ViewBinding,VM extends ViewModel> extends Dialog implements BaseBindingInterface<VB, VM> {
    public BaseDialog(@NonNull Context context) {
        super(context);
    }
    final  String[] bv = {"viewbinding", "viewmodel"};
    public VB viewbinding;
    public VM viewmodel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewBinding(this, this.getContext());
        try {
            for (String key : bv) {
                Field field = getClass().getDeclaredField(key);
                if (key.equals("viewbinding")) {
                    viewbinding = (VB) field.get(this);
                } else {
                    viewmodel = (VM) field.get(this);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
        if (viewbinding != null) {
            setContentView(viewbinding.getRoot());
        }
        initData();
        initView();
    }
    protected void initView() {}

    protected void initData(){}
}
