package com.demo.navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class SMSFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //改成对应的layout
        return inflater.inflate(R.layout.fragment_device_function_sms, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d("iot","sms fragment.");
    }
}
