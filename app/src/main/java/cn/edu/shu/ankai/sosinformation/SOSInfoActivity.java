package cn.edu.shu.ankai.sosinformation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cn.edu.shu.ankai.R;


public class SOSInfoActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    public static final String TYPE = "TYPE";
    private SOSDataSource mDataSource;
    private ListView mListView;
private String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sos_activity_main);

        str=(String) getBaseContext().getResources().getText(R.string.soshelp6);



        mListView = (ListView) findViewById(R.id.listView);
        mDataSource = new SOSDataSource(this);
        mListView.setAdapter(new SampleAdapter());
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SOSDataItem item = (SOSDataItem) mListView.getItemAtPosition(position);

        // if navigation is supported, open the next activity
        if (item.getNavigationInfo() != SOSDataSource.NO_NAVIGATION) {

            if (position == 0) {


                Intent intent = new Intent(this, SOSList1Activity.class);
                intent.putExtra(TYPE, item.getNavigationInfo());
                startActivity(intent);
            }
            if (position == 1) {


                Intent intent = new Intent(this, SOSList2Activity.class);
                intent.putExtra(TYPE, item.getNavigationInfo());
                startActivity(intent);
            }
            if (position == 2) {


                Intent intent = new Intent(this, SOSList3Activity.class);
                intent.putExtra(TYPE, item.getNavigationInfo());
                startActivity(intent);
            }
            if (position == 3) {


                Intent intent = new Intent(this, SOSList4Activity.class);
                intent.putExtra(TYPE, item.getNavigationInfo());
                startActivity(intent);
            }
            if (position == 4) {


                Intent intent = new Intent(this, SOSList5Activity.class);
                intent.putExtra(TYPE, item.getNavigationInfo());
                startActivity(intent);
            }
            if (position == 5) {


                Intent intent = new Intent(this, SOSList6Activity.class);
                intent.putExtra(TYPE, item.getNavigationInfo());
                startActivity(intent);
            }


        }

        else{
            if (position == 6) {

                Intent intent = new Intent(SOSInfoActivity.this, SOSContentActivity.class);
                SharedPreferences.Editor editor = getSharedPreferences("soshelp", Context.MODE_WORLD_WRITEABLE).edit();
                editor.putString("help", str);
                editor.putString("help_name", "应急处理");
                editor.commit();
                startActivity(intent);
            }
            if (position == 8) {
                Uri uri = Uri.parse("http://cms.shu.edu.cn/Default.aspx?tabid=11907");
                 Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);

            }

            if (position == 7) {
                Intent it=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+66133501));
                startActivity(it);
            }

        }








    }














    private class SampleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDataSource.getCount();
        }

        @Override
        public SOSDataItem getItem(int position) {
            return mDataSource.getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(SOSInfoActivity.this, R.layout.sos_list_item_layout, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            SOSDataItem item = getItem(position);

            final Drawable drawable = item.getDrawable();
            holder.imageView.setImageDrawable(drawable);
            holder.textView.setText(item.getLabel());

            // if navigation is supported, show the ">" navigation icon
            if (item.getNavigationInfo() != SOSDataSource.NO_NAVIGATION) {
                holder.textView.setCompoundDrawablesWithIntrinsicBounds(null,
                        null,
                        getResources().getDrawable(R.drawable.ic_action_next_item),
                        null);
            }
            else {
                holder.textView.setCompoundDrawablesWithIntrinsicBounds(null,
                        null,
                        null,
                        null);
            }

            // fix for animation not playing for some below 4.4 devices
            if (drawable instanceof AnimationDrawable) {
                holder.imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        ((AnimationDrawable) drawable).stop();
                        ((AnimationDrawable) drawable).start();
                    }
                });
            }

            return convertView;
        }
    }

    private static class ViewHolder {

        private ImageView imageView;

        private TextView textView;

        private ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.imageView);
            textView = (TextView) view.findViewById(R.id.textView);
        }
    }
}
