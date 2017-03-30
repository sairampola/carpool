package com.androidbelieve.drawerwithswipetabs;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ratan on 7/29/2015.
 */
public class SendLocationFragment extends Fragment {

    EditText et;
    Button bt;
    Switch sw;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sendlocation_layout,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et= (EditText) view.findViewById(R.id.editText);
        bt= (Button) view.findViewById(R.id.button2);
        sw = (Switch) view.findViewById(R.id.switch1);
        final SharedPreferences prefs = getContext().getSharedPreferences("carpool", MODE_PRIVATE);
        String kw = prefs.getString("keyword","get location");
        et.setText(kw);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getContext().getSharedPreferences("carpool", MODE_PRIVATE).edit();
                editor.putString("keyword", et.getText().toString());
                editor.commit();
                Toast.makeText(getContext(), "Keyword updated Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        boolean ck = prefs.getBoolean("switch",true);
        sw.setChecked(ck);
        if(ck)
            sw.setText("On");
        else
            sw.setText("Off");
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getContext().getSharedPreferences("carpool", MODE_PRIVATE).edit();
                editor.putBoolean("switch", isChecked);
                editor.commit();
                if(isChecked){
                    PackageManager pm  = getActivity().getPackageManager();
                    ComponentName componentName = new ComponentName(getActivity(), SmsListener.class);
                    pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);
                    Toast.makeText(getContext(), "Activated!", Toast.LENGTH_LONG).show();
                    sw.setText("On");
                }
                else{
                    PackageManager pm  = getActivity().getPackageManager();
                    ComponentName componentName = new ComponentName(getActivity(), SmsListener.class);
                    pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);
                    Toast.makeText(getContext(), "Deactivated!", Toast.LENGTH_LONG).show();
                    sw.setText("Off");
                }
            }
        });

    }
}
