package cn.edu.shu.ankai.ui.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.edu.shu.ankai.R;
import cn.edu.shu.ankai.model.Labroom;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeCardRecyclerAdapter extends MyRecyclerViewAdapter {


	private static final int VIEW_TYPE_HEADER = 0;
	private static final int VIEW_TYPE_ITEM = 1;


	private int type;
	private View headerView;

	private int[] defaultColors;

	public static interface OnRecyclerViewListener {
		void onItemClick(int position);
		boolean onItemLongClick(int position);
	}

	private OnRecyclerViewListener onRecyclerViewListener;

	public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
		this.onRecyclerViewListener = onRecyclerViewListener;
	}




	List<Labroom> contents;
	public HomeCardRecyclerAdapter(Context context,List<Labroom> contents) {
		super(true);
		this.defaultColors = context.getResources().getIntArray(R.array.statusColor);
		this.contents = contents;
	}


	@Override
	public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		switch (viewType) {
			case VIEW_TYPE_HEADER:
				bindContext(parent.getContext());
				View v1 = LayoutInflater.from(getContext())
						.inflate(R.layout.card_express_item, parent, false);
				return new ViewHolder(v1);
			case VIEW_TYPE_ITEM:
				bindContext(parent.getContext());
				View v = LayoutInflater.from(getContext())
						.inflate(R.layout.card_express_item, parent, false);
				return new ViewHolder(v);
			default:
				return null;
		}
	}


	public Labroom getItem(int i) {
			return contents.get(i);
	}



	@Override
	public void onBindViewHolder(ClickableViewHolder viewHolder, final int position) {
		super.onBindViewHolder(viewHolder, position);
		//Express item = getItem(position + (headerView != null ? -1 : 0));
		ViewHolder holder = (ViewHolder) viewHolder;
		Labroom labroom = contents.get(position);
		ColorDrawable drawable = new ColorDrawable(defaultColors[1]);
		holder.iv_round.setImageDrawable(drawable);


		holder.tv_title.setText(labroom.getname());
		holder.tv_desp.setText(labroom.getserialno());
		holder.tv_time.setText(labroom.getdirector_id());
		holder.tv_center_round.setText(labroom.getname().substring(0,1));



	}


	public int getExpressCount() {
		return contents.size();
	}

	@Override
	public int getItemCount() {
		int result = getExpressCount();
		if (headerView != null) result++;
		return result;
	}

	@Override
	public int getItemViewType(int position) {
		return VIEW_TYPE_ITEM;
	}



	public class ViewHolder extends ClickableViewHolder {
		public CircleImageView iv_round;
		public TextView tv_title, tv_desp, tv_time, tv_center_round;
		public ViewHolder(View itemView) {
			super(itemView);
			this.iv_round = (CircleImageView) itemView.findViewById(R.id.iv_round);
			this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
			this.tv_desp = (TextView) itemView.findViewById(R.id.tv_desp);
			this.tv_time = (TextView) itemView.findViewById(R.id.tv_time);
			this.tv_center_round = (TextView) itemView.findViewById(R.id.center_text);
		}
	}


	public class HeaderViewHolder extends ClickableViewHolder {
		public HeaderViewHolder(View view) {
			super(view);
		}
	}




}