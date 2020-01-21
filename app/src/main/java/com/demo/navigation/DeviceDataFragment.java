package com.demo.navigation;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeviceDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeviceDataFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private IotSharedViewModel mViewModel;
    private FragmentManager mFm;
    private FragmentTransaction mFt;
    private Fragment mPhone;
    private Fragment mSMS;

    public DeviceDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeviceDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeviceDataFragment newInstance(String param1, String param2) {
        DeviceDataFragment fragment = new DeviceDataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_device_function, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(IotSharedViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        int function = mViewModel.getCurrentDeviceFunction();

        mFm = getChildFragmentManager();
        mFt = mFm.beginTransaction();

        mPhone = new PhoneFragment();
        mSMS = new SMSFragment();

        //根据不同的id 加载不通的功能界面
        if (function == IotSharedViewModel.PHONE) {
            mFt.replace(R.id.content_host,mPhone).commit();
        } else if (function == IotSharedViewModel.SMS) {
            // Inflate the layout for this fragment
            mFt.replace(R.id.content_host,mSMS).commit();
        }
        Log.d("iot","fun ===="+function);
    }


}
