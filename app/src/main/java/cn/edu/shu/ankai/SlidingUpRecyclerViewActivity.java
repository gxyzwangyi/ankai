/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.edu.shu.ankai;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;

import java.util.List;

import cn.edu.shu.ankai.model.Device;

public class SlidingUpRecyclerViewActivity extends SlidingUpBaseActivity<ObservableRecyclerView> implements ObservableScrollViewCallbacks {
    public String json;
    private static final int COMPLETED = 0;
    @Override
    protected int getLayoutResId() {
        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window=SlidingUpRecyclerViewActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);

        return R.layout.activity_slidinguprecyclerview;
    }

    @Override
    protected ObservableRecyclerView createScrollable() {
        ObservableRecyclerView recyclerView = (ObservableRecyclerView) findViewById(R.id.scroll);
        recyclerView.setScrollViewCallbacks(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new CustomAdapter(this, devicedata));
        return recyclerView;
    }


    public static class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private Context mContext;
        private LayoutInflater mInflater;

        List<Device> contents;

        public CustomAdapter(Context context, List<Device> contents) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
            this.contents = contents;
        }

        @Override
        public int getItemCount() {
            return contents.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(mContext, mInflater.inflate(R.layout.sliding_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder Holder, int position) {


            ViewHolder viewHolder = (ViewHolder) Holder;
            Device object = contents.get(position);

            String id = object.getid();
            String serialno = object.getserialno();
            String name  = object.getname();
            String eng_name = object.geteng_name();
            String location_id = object.getlocation_id();
            String device_model = object.getdevice_model();
            String device_metric = object.getdevice_metric();
            String application_code = object.getapplication_code();
            String use_department_id = object.getuse_department_id();
            String director_id = object.getdirector_id();
            String contact_id = object.getcontact_id();

            viewHolder.textView1.setText(serialno);
            viewHolder.textView2.setText(name+eng_name);
            viewHolder.textView3.setText(location_id);
            viewHolder.textView4.setText(device_model+device_metric+"("+application_code+")");
            viewHolder.textView5.setText(use_department_id);
            viewHolder.textView6.setText(director_id+"("+contact_id+")");


        }



        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView1;
            TextView textView2;
            TextView textView3;
            TextView textView4;
            TextView textView5;
            TextView textView6;
            Context context;

            public ViewHolder(Context context, View view) {
                super(view);
                this.context = context;
                this.textView1 = (TextView) view.findViewById(R.id.item_id);
                this.textView2 = (TextView) view.findViewById(R.id.item_name);
                this.textView3 = (TextView) view.findViewById(R.id.item_local);
                this.textView4 = (TextView) view.findViewById(R.id.item_status);
                this.textView5 = (TextView) view.findViewById(R.id.item_school);
                this.textView6 = (TextView) view.findViewById(R.id.item_contact);



                this.textView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click(getPosition() + 1);
                    }
                });
            }

            private void click(int i) {
                Toast.makeText(context, textView2.getText(), Toast.LENGTH_SHORT).show();
            }
        }
    }




}
