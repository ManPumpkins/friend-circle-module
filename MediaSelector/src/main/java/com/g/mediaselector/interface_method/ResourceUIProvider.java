package com.g.mediaselector.interface_method;

import android.view.View;
import com.g.mediaselector.activity.ResourcePickerActivity;
import com.g.mediaselector.databinding.ItemRibBinding;
import com.g.mediaselector.databinding.ToolbarBinding;
import com.g.mediaselector.model.ResourceItem;

public interface ResourceUIProvider {
    void bindItemView(ItemRibBinding itemView, ResourceItem item, boolean selected);
    View getCustomToolbar(ToolbarBinding binding, ResourcePickerActivity picker);
}
