package com.xx.base.project.refresh.util;

import org.tangtown.com.lib_project.R;

public enum ListType { // 列表类型
        ListView(R.layout.refresh_listview),
        RecylerView(R.layout.refresh_recylerview);
//        GridView(R.layout.refresh_gridview);
        public int contentId;
        ListType(int contentId) {
            this.contentId = contentId;
        }
    }