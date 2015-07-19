package cn.edu.shu.ankai.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.edu.shu.ankai.R;
import cn.edu.shu.ankai.model.History;


/**
 * Created by florentchampigny on 24/04/15.
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {




    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;



    public static interface OnRecyclerViewListener {
        void onItemClick(int position);
        boolean onItemLongClick(int position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }




    List<History> contents;
    public TestRecyclerViewAdapter(List<History> contents) {
        this.contents = contents;
    }



    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.main_item_big, parent, false);
                return new ThingViewHolder(view) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.main_item_small, parent, false);
                return new ThingViewHolder(view) {
                };
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ThingViewHolder holder1 = (ThingViewHolder) viewholder;
                holder1.position = position;
                History history = contents.get(position);
                Log.e("看看1", history.getName());
                holder1.nameTv.setText(history.getName());
                holder1.titleTv.setText(history.getTitle());
                holder1.timeTv.setText(history.getTime());
                holder1.localTv.setText(history.getLocal());
                holder1.LLTv.setText(history.getLL());
                break;
            case TYPE_CELL:
                ThingViewHolder holder = (ThingViewHolder) viewholder;
                holder.position = position;
                History history1 = contents.get(position);

                holder.nameTv.setText(history1.getName());
                holder.titleTv.setText(history1.getTitle());
                holder.timeTv.setText(history1.getTime());
                holder.localTv.setText(history1.getLocal());
                holder.LLTv.setText(history1.getLL());
                break;
        }
    }

    class ThingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        public View rootView;
        public TextView nameTv;
        public TextView titleTv;
        public TextView timeTv;
        public TextView localTv;
        public TextView LLTv;



        public int position;

        public ThingViewHolder (View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.item_name);
            titleTv = (TextView) itemView.findViewById(R.id.item_title);
            timeTv = (TextView) itemView.findViewById(R.id.item_time);
            localTv = (TextView) itemView.findViewById(R.id.item_local);
            LLTv = (TextView) itemView.findViewById(R.id.item_status);

            rootView = itemView.findViewById(R.id.item_small);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);

        }

        @Override
             public void onClick(View v) {
        if (null != onRecyclerViewListener) {
            onRecyclerViewListener.onItemClick(position);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(null != onRecyclerViewListener){
            return onRecyclerViewListener.onItemLongClick(position);
        }
        return false;
    }
}


    }






