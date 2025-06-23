package com.g.friendcirclemodule;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<VB extends ViewBinding,VM extends ViewModel> extends AppCompatActivity {

    final static Class[] classArray = {LayoutInflater.class};
    final  String[] bv = {"binding", "viewModel"};
    protected VB viewbinding;
    protected VM viewmodel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewBinding();
        setContentView(viewbinding.getRoot());
        initView();
    }

    @SuppressWarnings("unchecked")
    public void initViewBinding() {
        try {
            for (String key : bv) {
                Type type = getClass().getGenericSuperclass();
                ParameterizedType pt = (ParameterizedType) type;
                if (key.equals("binding")) {
                    Class<VB> vbClass = null;
                    if (pt != null) {
                        if (pt.getActualTypeArguments()[0] instanceof Class) {
                            vbClass = (Class<VB>) pt.getActualTypeArguments()[0];
                        }
                    }
                    Method inflate = null;
                    if (vbClass != null) {
                        inflate = vbClass.getMethod("inflate", classArray);
                    }
                    if (inflate != null) {
                        if (inflate.invoke(null, getLayoutInflater()) instanceof ViewBinding) {
                            viewbinding = (VB) inflate.invoke(null, getLayoutInflater());
                        }
                    }
                } else {
                    Class<VM> vmClass = null;
                    if (pt != null) {
                        vmClass = (Class<VM>) pt.getActualTypeArguments()[1];
                        if (pt.getActualTypeArguments()[0] instanceof Class) {
                            vmClass = (Class<VM>) pt.getActualTypeArguments()[1];
                        }
                    }
                    if (vmClass != null) {
                        viewmodel = new ViewModelProvider(this).get(vmClass);
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    protected void initView() {

    }
}
