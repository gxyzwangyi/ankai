package cn.edu.shu.ankai.widget;

import android.widget.ListAdapter;

import java.util.List;

import cn.edu.shu.ankai.model.DemoItem;

public interface DemoAdapter extends ListAdapter {

  void appendItems(List<DemoItem> newItems);

  void setItems(List<DemoItem> moreItems);
}
