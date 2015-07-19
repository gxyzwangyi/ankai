package cn.edu.shu.ankai.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.ThemeSingleton;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.avos.avoscloud.AVUser;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.shu.ankai.FindLabActivity;
import cn.edu.shu.ankai.MainActivity;
import cn.edu.shu.ankai.ProgressActivity1;
import cn.edu.shu.ankai.R;
import cn.edu.shu.ankai.db.LabroomData;
import cn.edu.shu.ankai.model.Labroom;
import cn.edu.shu.ankai.ui.adapter.HomeCardRecyclerAdapter;
import cn.edu.shu.ankai.ui.adapter.MyRecyclerViewAdapter;
import cn.edu.shu.ankai.utils.App;
import cn.edu.shu.ankai.utils.PreferenceUtils;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class LabFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerViewMaterialAdapter mAdapter;

    private LabroomData LD;
    AVUser currentUser = AVUser.getCurrentUser();
    private EditText passwordInput;
    public TextView BoolLocal;
    private Handler mUiHandler = new Handler();
    private App app;
    private ArrayList<HashMap<String, Object>> labroomlist = new ArrayList<HashMap<String, Object>>();
    private View positiveAction;
    private List<FloatingActionMenu> menus = new ArrayList<>();
    private FloatingActionButton fab12;
    private FloatingActionButton fab22;
    private FloatingActionButton fab32;
    private FloatingActionButton fab42;
    // private List<Thing> historylist = new ArrayList<>();

    public static LabFragment newInstance() {
        return new LabFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_lab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        LD = new LabroomData(getActivity());
        if(((App) getActivity().getApplication()).code==0) {
            labroomlist = LD.getUserList();
        }
        else if(((App) getActivity().getApplication()).code==1){

          String factor =   PreferenceUtils.getPrefString(getActivity(), "find", null);
            labroomlist = LD.getSpecialrList(factor);
            Log.e("数据库1",labroomlist.size()+"");
        }
        else if(((App) getActivity().getApplication()).code==2){
            //String factor =   PreferenceUtils.getPrefString(getActivity(), "find", null);
            labroomlist = LD.getRandomrList();
            Log.e("数据库2",labroomlist.size()+"");
        }
        else if(((App) getActivity().getApplication()).code==3){
            String factor =   PreferenceUtils.getPrefString(getActivity(), "find_parent", null);
            String factor1 =   PreferenceUtils.getPrefString(getActivity(), "find_level", null);
            String factor2 =   PreferenceUtils.getPrefString(getActivity(), "find_type", null);
            Log.e("数据库2",factor+factor1+factor2);
            labroomlist = LD.getshaiList(factor, factor1, factor2);
            Log.e("数据库2",labroomlist.size()+"");
        }




        final FloatingActionMenu menu2 = (FloatingActionMenu) view.findViewById(R.id.menu2);
        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }


        menu2.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                String text = "";
                if (opened) {
                    text = "开始查找";
                } else {
                    text = "关闭查找";
                }
                Toast.makeText(getActivity(),text, Toast.LENGTH_SHORT).show();
            }
        });

        fab12 = (FloatingActionButton) view.findViewById(R.id.fab12);
        fab22 = (FloatingActionButton) view.findViewById(R.id.fab22);
        fab32 = (FloatingActionButton) view.findViewById(R.id.fab32);
        fab42 = (FloatingActionButton) view.findViewById(R.id.fab42);

        fab12.setOnClickListener(clickListener);
        fab22.setOnClickListener(clickListener);
        fab32.setOnClickListener(clickListener);
        fab42.setOnClickListener(clickListener);



        ((App) getActivity().getApplication()).locationClient.requestLocation();
        app = (App) getActivity().getApplication();


          mAdapter = new RecyclerViewMaterialAdapter(new HomeCardRecyclerAdapter(getActivity(),getData1()));

        mRecyclerView.setAdapter(mAdapter);
        setUpAdapterListener();

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }



    private List<Labroom> getData1() {
        List<Labroom> list = new ArrayList();

        Labroom labroom=new Labroom();
        labroom.setid("实验室id");
        labroom.setserialno("实验室编号");
        labroom.setname("实验室名称");
        labroom.setparent_id("所属单位");
        labroom.setimage("实验室图片");
        labroom.setlab_level("实验室等级");
        labroom.setlab_type("实验室类型");
        labroom.setopen_flag("是否开放");
        labroom.setsecrect_flag("是否保密");
        labroom.setdescription("实验室简介");
        labroom.setresearch_fields("研究方向");
        labroom.setdirector_id("主任");
        labroom.setcontact_id("联系人");
        labroom.setcontact_phone("联系电话");
        labroom.setweb_url("实验室网站");
        labroom.setsince_date("成立时间");
        list.add(labroom);

        if (labroomlist.size()>0) {
            for (int i = 0; i<labroomlist.size() ; i++) {
                labroom = new Labroom();
                labroom.setid(labroomlist.get(i).get("id").toString());
                labroom.setserialno(labroomlist.get(i).get("serialno").toString());
                labroom.setname(labroomlist.get(i).get("name").toString());
                labroom.setparent_id(labroomlist.get(i).get("parent_id").toString());
                labroom.setimage(labroomlist.get(i).get("image").toString());
                labroom.setlab_level(labroomlist.get(i).get("lab_level").toString());
                labroom.setlab_type(labroomlist.get(i).get("lab_type").toString());
                labroom.setopen_flag(labroomlist.get(i).get("open_flag").toString());
                labroom.setsecrect_flag(labroomlist.get(i).get("secrect_flag").toString());
                labroom.setdescription(labroomlist.get(i).get("description").toString());
                labroom.setresearch_fields(labroomlist.get(i).get("research_fields").toString());
                labroom.setdirector_id(labroomlist.get(i).get("director_id").toString());
                labroom.setcontact_id(labroomlist.get(i).get("contact_id").toString());
                labroom.setcontact_phone(labroomlist.get(i).get("contact_phone").toString());
                labroom.setweb_url(labroomlist.get(i).get("web_url").toString());
                labroom.setsince_date(labroomlist.get(i).get("since_date").toString());
                list.add(labroom);
            }
        }

        return list;
    }


    //浮动按钮
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = "";

            switch (v.getId()) {
                case R.id.fab12:
                    text = fab12.getLabelText();
                    Intent intent = new Intent(getActivity(),FindLabActivity.class);
                    startActivity(intent);
                    break;
                case R.id.fab22:
                    text = fab22.getLabelText();
                    MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                            .title("查询")
                            .customView(R.layout.dialog_find_customview, true)
                            .positiveText(R.string.connect)
                            .negativeText(android.R.string.cancel)
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {


                                    PreferenceUtils.setPrefString(getActivity(),"find",passwordInput.getText().toString());
                                    ((App) getActivity().getApplication()).code=1;
                                    Log.e("空的", "1");
                                    Intent intent = new Intent(getActivity(),MainActivity.class);
                                    startActivity(intent);



                                }
                                @Override
                                public void onNegative(MaterialDialog dialog) {
                                }
                            }).build();

                    passwordInput = (EditText) dialog.getCustomView().findViewById(R.id.password);
                    passwordInput.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            positiveAction.setEnabled(s.toString().trim().length() > 0);
                        }
                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                    positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
                    int widgetColor = ThemeSingleton.get().widgetColor;
                    MDTintHelper.setTint(passwordInput,
                            widgetColor == 0 ? getResources().getColor(R.color.material_teal_500) : widgetColor);
                    dialog.show();
                    positiveAction.setEnabled(false); // disabled by default
                    break;
                case R.id.fab32:
                    text = fab32.getLabelText();
                    MaterialDialog dialog1 = new MaterialDialog.Builder(getActivity())
                            .title("查询")
                            .customView(R.layout.dialog_random_customview, true)
                            .positiveText(R.string.connect)
                            .negativeText(android.R.string.cancel)
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {


                                    ((App) getActivity().getApplication()).code=2;
                                    Intent intent = new Intent(getActivity(),MainActivity.class);
                                    startActivity(intent);


                                }
                                @Override
                                public void onNegative(MaterialDialog dialog) {
                                }
                            }).build();

                    positiveAction = dialog1.getActionButton(DialogAction.POSITIVE);
                    //noinspection ConstantConditions
                    dialog1.show();
                    break;
                case R.id.fab42:
                    ((App) getActivity().getApplication()).code=0;
                    Intent intent1 = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent1);
                    break;



            }






        }
    };








    protected void setUpAdapterListener() {
        MyRecyclerViewAdapter adapter = (MyRecyclerViewAdapter) mAdapter.get_adapter();
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                if (position == 0) return;
                HomeCardRecyclerAdapter adapter =
                        (HomeCardRecyclerAdapter)mAdapter.get_adapter();
                    String id  = adapter.getItem(position).getid();
                Intent intent=new Intent();
                intent.putExtra("lab_id",id);
                intent.setClass(getActivity(), ProgressActivity1.class);
                startActivity(intent);

            }
        });
        adapter.setOnItemLongClickListener(new MyRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(int position) {
                if (position == 0) return false;

                HomeCardRecyclerAdapter adapter =
                        (HomeCardRecyclerAdapter) mAdapter.get_adapter();

                return true;
            }
        });
    }

}
