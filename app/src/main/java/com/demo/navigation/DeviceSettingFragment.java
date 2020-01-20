package com.demo.navigation;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeviceSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeviceSettingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentManager mFm;
    private FragmentTransaction mFt;
    private Fragment mPhoneSetting;
    private Fragment mSMSSetting;
    private IotSharedViewModel mViewModel;

    public DeviceSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeviceSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeviceSettingFragment newInstance(String param1, String param2) {
        DeviceSettingFragment fragment = new DeviceSettingFragment();
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
        initBack();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(IotSharedViewModel.class);
    }

    private void initBack(){
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Navigation.findNavController(getView()).navigate(R.id.action_global_devicesFragment);

            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_device_setting, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        int function = mViewModel.getCurrentDeviceFunction();
        mFm = getChildFragmentManager();
        mFt = mFm.beginTransaction();

        mPhoneSetting = new PhoneSettingFragment();
        mSMSSetting = new SMSSettingFragment();

        //根据不同的id 加载不通的fragment
        if (function == IotSharedViewModel.PHONE) {
            mFt.replace(R.id.content_host,mPhoneSetting).commit();
        } else if (function == IotSharedViewModel.SMS) {
            // Inflate the layout for this fragment
            mFt.replace(R.id.content_host,mSMSSetting).commit();
        }
    }
}
