package cn.edu.shu.ankai.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.shu.ankai.R;
import cn.edu.shu.ankai.model.DemoItem;
import cn.edu.shu.ankai.widget.DefaultCursorAdapter;
import cn.edu.shu.ankai.widget.DefaultListAdapter;
import cn.edu.shu.ankai.widget.DemoAdapter;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class ScrollFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ObservableScrollView mScrollView;
    private static final String TAG = "MainActivity";
    private AsymmetricGridView listView;
    private DemoAdapter adapter;
    private int currentOffset;
    private static final boolean USE_CURSOR_ADAPTER = false;

    public static ScrollFragment newInstance() {
        return new ScrollFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scroll, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        listView = (AsymmetricGridView) view.findViewById(R.id.listView);

        if (USE_CURSOR_ADAPTER) {
            if (savedInstanceState == null) {
                adapter = new DefaultCursorAdapter(getActivity(), getMoreItems(50));
            } else {
                adapter = new DefaultCursorAdapter(getActivity());
            }
        } else {
            if (savedInstanceState == null) {
                adapter = new DefaultListAdapter(getActivity(), getMoreItems(50));
            } else {
                adapter = new DefaultListAdapter(getActivity());
            }
        }

        listView.setRequestedColumnCount(5);
        listView.setRequestedHorizontalSpacing(Utils.dpToPx(getActivity(), 3));
        listView.setAdapter(getNewAdapter());
        listView.setDebugging(true);
        //listView.setOnItemClickListener(getActivity());




        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
    }


    private AsymmetricGridViewAdapter<?> getNewAdapter() {
        return new AsymmetricGridViewAdapter<>(getActivity(), listView, adapter);
    }

    private List<DemoItem> getMoreItems(int qty) {
        List<DemoItem> items = new ArrayList<>();

        for (int i = 0; i < qty; i++) {
            int colSpan = Math.random() < 0.2f ? 2 : 1;
            // Swap the next 2 lines to have items with variable
            // column/row span.
            // int rowSpan = Math.random() < 0.2f ? 2 : 1;
            int rowSpan = colSpan;
            DemoItem item = new DemoItem(colSpan, rowSpan, currentOffset + i);
            items.add(item);
        }

        currentOffset += qty;

        return items;
    }






    private void setNumColumns(int numColumns) {
        listView.setRequestedColumnCount(numColumns);
        listView.determineColumns();
        listView.setAdapter(getNewAdapter());
    }

    private void setColumnWidth(int columnWidth) {
        listView.setRequestedColumnWidth(Utils.dpToPx(getActivity(), columnWidth));
        listView.determineColumns();
        listView.setAdapter(getNewAdapter());
    }

    @Override public void onItemClick( AdapterView<?> parent, View view,
                                      int position, long id) {
        Toast.makeText(getActivity(), "Item " + position + " clicked", Toast.LENGTH_SHORT).show();
    }

}


