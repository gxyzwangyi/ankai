package cn.edu.shu.ankai;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rey.material.widget.Switch;

import cn.edu.shu.ankai.utils.PreferenceConstants;
import cn.edu.shu.ankai.utils.PreferenceUtils;


/**
 *
 * Created by Administrator on 2015/3/1.
 */
public class SettingFragment extends Fragment implements
    Switch.OnCheckedChangeListener {

    private static Switch mNotifyRunBackgroundSwitch;
    private static Switch mNewMsgSoundSwitch;
    private static Switch mNewMsgVibratorSwitch;
    private static Switch mVisiableNewMsgSwitch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setting2fragment, container,
                false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNotifyRunBackgroundSwitch = (Switch) view.findViewById(R.id.notify_run_background_switch);
        mNotifyRunBackgroundSwitch.setOnCheckedChangeListener(this);
        mNewMsgSoundSwitch = (Switch) view.findViewById(R.id.new_msg_sound_switch);
        mNewMsgSoundSwitch.setOnCheckedChangeListener(this);
        mNewMsgVibratorSwitch = (Switch) view.findViewById(R.id.new_msg_vibrator_switch);
        mNewMsgVibratorSwitch.setOnCheckedChangeListener(this);
        mVisiableNewMsgSwitch = (Switch) view.findViewById(R.id.visiable_new_msg_switch);
        mVisiableNewMsgSwitch.setOnCheckedChangeListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        readData();
    }

    public void readData() {

        mNotifyRunBackgroundSwitch.setChecked(PreferenceUtils.getPrefBoolean(
                getActivity(), PreferenceConstants.FOREGROUND, true));
        mNewMsgSoundSwitch.setChecked(PreferenceUtils.getPrefBoolean(
                getActivity(), PreferenceConstants.SCLIENTNOTIFY, false));
        mNewMsgVibratorSwitch.setChecked(PreferenceUtils.getPrefBoolean(
                getActivity(), PreferenceConstants.VIBRATIONNOTIFY, true));
        mVisiableNewMsgSwitch.setChecked(PreferenceUtils.getPrefBoolean(
                getActivity(), PreferenceConstants.TICKER, true));
    }

    @Override
    public void onCheckedChanged(Switch buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.notify_run_background_switch:
                PreferenceUtils.setPrefBoolean(getActivity(),
                        PreferenceConstants.FOREGROUND, isChecked);
                break;
            case R.id.new_msg_sound_switch:
                PreferenceUtils.setPrefBoolean(getActivity(),
                        PreferenceConstants.SCLIENTNOTIFY, isChecked);
                break;
            case R.id.new_msg_vibrator_switch:
                PreferenceUtils.setPrefBoolean(getActivity(),
                        PreferenceConstants.VIBRATIONNOTIFY, isChecked);
                break;

            case R.id.visiable_new_msg_switch:
                PreferenceUtils.setPrefBoolean(getActivity(),
                        PreferenceConstants.TICKER, isChecked);
                break;
            default:
                break;
        }
    }


    public boolean onPreferenceClick(Preference preference) {

        return true;
    }


}