package com.g.friendcirclemodule.interface_method;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface BaseBindingInterface<VB extends ViewBinding,VM extends ViewModel> {
    final static Class[] classArray = {LayoutInflater.class};
    final  String[] bv = {"viewbinding", "viewModel"};
    default void initViewBinding(Object instance, Context context) {
        try {
            Context mContext = null;
            if (instance instanceof Fragment) {
                mContext = ((Fragment) instance).getContext();
            } else {
                mContext = (Context) instance;
            }
            for (String key : bv) {
                Type type = instance.getClass().getGenericSuperclass();
                ParameterizedType pt = (ParameterizedType) type;
                Object result = null;
                Field binding = instance.getClass().getField(key);
                if (key.equals("viewbinding")) {
                    Class<VB> vbClass = null;
                    if (pt != null) {
                        if (pt.getActualTypeArguments()[0] instanceof Class) {
                            vbClass = (Class<VB>) pt.getActualTypeArguments()[0];
                        }
                    }
                    Method inflate = null;
                    if (vbClass != null) {
                        inflate = vbClass.getMethod("inflate", classArray);
                        result = inflate.invoke(null, LayoutInflater.from(context));
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
                        FragmentActivity mActivity = null;
                        if (instance instanceof Fragment) {
                            mActivity = ((Fragment) instance).getActivity();
                        } else {
                            mActivity = (FragmentActivity) mContext;
                        }
                        if (mActivity != null) {
                            result = new ViewModelProvider(mActivity).get(vmClass);
                        }
                    }
                }
                Log.i("222222", String.valueOf(result));
                if (result != null) {
                    binding.setAccessible(true);
                    binding.set(instance, result);
                }
            }
        } catch (Exception ignored) {
        }
    }
}
