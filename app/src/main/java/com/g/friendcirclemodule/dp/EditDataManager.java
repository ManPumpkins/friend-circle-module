package com.g.friendcirclemodule.dp;

import com.g.mediaselector.model.ResourceItem;
import java.util.ArrayList;
import java.util.List;

public class EditDataManager {
    public static List<ResourceItem> list = new ArrayList<>();

    public static List<ResourceItem> getList() {
        return list;
    }

    public static void setList(List<ResourceItem> l) {
        list = l;
    }
}
