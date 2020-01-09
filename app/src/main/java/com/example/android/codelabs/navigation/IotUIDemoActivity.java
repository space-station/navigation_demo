package com.example.android.codelabs.navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class IotUIDemoActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private BottomNavigationView mainBottom;
    private BottomNavigationView deviceBottom;
    private FloatingActionButton floatingActionBtn;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iot_ui_activity);

        navController = Navigation.findNavController(this, R.id.iot_nav_host_fragment);//navHostFragment.getNavController();

        //toolbar绑定
        toolbar = findViewById(R.id.toolbar);

        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .setDrawerLayout(drawerLayout)
                        .build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        //main bottom bar绑定
        mainBottom = findViewById(R.id.iot_ui_bottom_nav);
        NavigationUI.setupWithNavController(mainBottom, navController);

        //device bottom绑定
        deviceBottom = findViewById(R.id.iot_ui_device_bottom_nav);
        NavigationUI.setupWithNavController(deviceBottom, navController);

        //drawer layout抽屉绑定导航
        drawerLayout = findViewById(R.id.iot_ui_drawer_layout);
        NavigationView navView = findViewById(R.id.iot_ui_nav_view);
        NavigationUI.setupWithNavController(navView, navController);


        //点击可拖动的悬浮按钮,可以打开左边抽屉
        floatingActionBtn = findViewById(R.id.connect_chk_btn);
        floatingActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IotUIDemoActivity.this.onClick(v);
            }
        });


        //在这里控制fragment时,底层bottom view的切换
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.deviceDetailFragment
                        || destination.getId() == R.id.deviceFunctionFragment
                        || destination.getId() == R.id.deviceSettingFragment) {
//                    toolbar.setVisibility(View.GONE);
                    changeToDeviceBottom();
                    if (destination.getId() == R.id.deviceDetailFragment) {
                        setDeviceBottomBottomMain(1);
                    } else {
                        setDeviceBottomBottomMain(3);
                    }

                } else if (destination.getId() == R.id.mainFragment
                        || destination.getId() == R.id.profileFragment
                        || destination.getId() == R.id.devicesFragment
                        || destination.getId() == R.id.connectionsFragment) {
//                    toolbar.setVisibility(View.VISIBLE);
                    changeToMainBottom();
                }
            }
        });

//可以给导航按钮设置点击事件
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //打开抽屉
//                drawerLayout.openDrawer(Gravity.LEFT);
//            }
//        });

    }

    public void changeToDeviceBottom() {
        Log.d("iot", "change bottom...." + mainBottom.getVisibility() + "," + deviceBottom.getVisibility());
        mainBottom.clearAnimation();
        mainBottom.setVisibility(View.GONE);
        deviceBottom.setVisibility(View.VISIBLE);

    }

    public void setDeviceBottomBottomMain(int count) {
        if (count == 1) {
            MenuItem deviceDetailItem = deviceBottom.getMenu().findItem(R.id.deviceDetailFragment);
            deviceDetailItem.setEnabled(true);
            MenuItem deviceFunctionItem = deviceBottom.getMenu().findItem(R.id.deviceFunctionFragment);
            deviceFunctionItem.setEnabled(false);
            MenuItem deviceSettingItem = deviceBottom.getMenu().findItem(R.id.deviceSettingFragment);
            deviceSettingItem.setEnabled(false);
        }

        if (count == 3) {
            MenuItem deviceFunctionItem = deviceBottom.getMenu().findItem(R.id.deviceFunctionFragment);
            deviceFunctionItem.setEnabled(true);
            MenuItem deviceSettingItem = deviceBottom.getMenu().findItem(R.id.deviceSettingFragment);
            deviceSettingItem.setEnabled(true);
        }
    }

    public void changeToMainBottom() {
        Log.d("iot", "change bottom...." + mainBottom.getVisibility() + "," + deviceBottom.getVisibility());
        mainBottom.setVisibility(View.VISIBLE);
        deviceBottom.setVisibility(View.GONE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.connect_chk_btn:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
            default:
                break;
        }

    }

}
