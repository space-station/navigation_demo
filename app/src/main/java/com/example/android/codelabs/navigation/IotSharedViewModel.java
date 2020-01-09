package com.example.android.codelabs.navigation;

import androidx.lifecycle.ViewModel;

public class IotSharedViewModel extends ViewModel {

    public static final int SMS = 1;
    public static final int PHONE = 2;

    private int mTargetDeviceFunction = 1;
    private int mCurrentDeviceFunction = 1;

    public int getTargetDeviceFunction() {
        return mTargetDeviceFunction;
    }

    public void setTargetDeviceFunction(int function) {
        mTargetDeviceFunction = function;
    }

    public int getCurrentDeviceFunction() {
        return mCurrentDeviceFunction;
    }

    public void setCurrentDeviceFunction(int function) {
        mCurrentDeviceFunction = function;
    }

}
