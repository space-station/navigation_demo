package com.demo.navigation;

import androidx.lifecycle.ViewModel;

public class IotSharedViewModel extends ViewModel {

    public static final int SMS = 1;
    public static final int PHONE = 2;

    private int mCurrentDeviceFunction = 1;

    public int getCurrentDeviceFunction() {
//        Log.d("iot","get fun==="+mCurrentDeviceFunction);
        return mCurrentDeviceFunction;
    }

    public void setCurrentDeviceFunction(int function) {
//        Log.d("iot","set fun==="+function);
        mCurrentDeviceFunction = function;
    }

}
