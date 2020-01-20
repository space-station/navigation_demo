package com.demo.navigation;

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
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class IotUIDemoActivity extends AppCompatActivity {
    public static final String TAG = IotUIDemoActivity.class.getSimpleName();

    DrawerLayout drawerLayout;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private BottomNavigationView mainBottom;
    private BottomNavigationView deviceBottom;
    private FloatingActionButton floatingActionBtn;
    private Toolbar toolbar;

    private IotSharedViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iot_ui_activity);
        navController = Navigation.findNavController(this, R.id.iot_nav_host_fragment);
        //toolbar
        toolbar = findViewById(R.id.toolbar);
        //替换android原生的actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //drawer layout
        drawerLayout = findViewById(R.id.iot_ui_drawer_layout);



        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .setDrawerLayout(drawerLayout)
                        .build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        //NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);

        //main bottom bar绑定
        mainBottom = findViewById(R.id.iot_ui_bottom_nav);
        NavigationUI.setupWithNavController(mainBottom, navController);

        //device bottom绑定
        deviceBottom = findViewById(R.id.iot_ui_device_bottom_nav);
        NavigationUI.setupWithNavController(deviceBottom, navController);

        //关联导航图
        NavigationView navView = findViewById(R.id.iot_ui_nav_view);
        NavigationUI.setupWithNavController(navView, navController);


        //点击可拖动的悬浮按钮,可以打开左边抽屉
        floatingActionBtn = findViewById(R.id.connect_chk_btn);
        floatingActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });

        mViewModel = ViewModelProviders.of(this).get(IotSharedViewModel.class);
        //在这里控制fragment时,底层bottom view的切换
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                int destId = destination.getId();
                switch(destId){
                    case R.id.mainFragment:
                    case R.id.devicesFragment:
                    case R.id.connectionsFragment:
                    case R.id.profileFragment:
                    default:
                        showMainBottomMenu();
                        break;
                    case R.id.deviceDetailFragment:
                        showDeviceBottomMenu(mViewModel.getCurrentDeviceFunction());
                        enableDeviceBottom(false);
                        break;
                    case R.id.deviceFunctionFragment:
                    case R.id.deviceSettingFragment:
                        showDeviceBottomMenu(mViewModel.getCurrentDeviceFunction());
                        enableDeviceBottom(true);
                        break;
                }

            }
        });

        setBackBtnOnClickNav();
    }

    public void showMainBottomMenu() {

        mainBottom.setVisibility(View.VISIBLE);
        deviceBottom.setVisibility(View.GONE);

        mViewModel.setCurrentDeviceFunction(0);
    }

    private void enableDeviceBottom(boolean enable){
        View function = findViewById(R.id.deviceFunctionFragment);
        View setting = findViewById(R.id.deviceSettingFragment);
        function.setEnabled(enable);
        setting.setEnabled(enable);
    }

    int lastFunctionId = -1;
    public void showDeviceBottomMenu(int funcId) {
        if(lastFunctionId != funcId) {
            Log.d(TAG, "showDeviceBottomMenu, funcId == " + funcId);
            lastFunctionId = funcId;
        }

        if(deviceBottom.getVisibility() != View.VISIBLE) {
            deviceBottom.setVisibility(View.VISIBLE);
            mainBottom.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onNavigateUp() {
        Log.d("iot","on navigation up ss================ ");
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onNavigateUp();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.d("iot","on support navigation up ================ ");
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("iot","select id="+item.getItemId());
        switch (item.getItemId()){
            case R.id.deviceDetailFragment:
                Navigation.findNavController(item.getActionView()).navigate(R.id.action_global_deviceDetailFragment);
                break;
            case R.id.deviceFunctionFragment:
            Navigation.findNavController(item.getActionView()).navigate(R.id.action_global_deviceFunctionFragment);
                break;
            case R.id.deviceSettingFragment:
                Navigation.findNavController(item.getActionView()).navigate(R.id.action_global_deviceSettingFragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setBackBtnOnClickNav(){
        //可以给导航按钮设置点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("iot","v == "+v.getId());
                if(navController.getCurrentDestination() != null
                && (navController.getCurrentDestination().getId() == R.id.deviceFunctionFragment
                    ||navController.getCurrentDestination().getId() == R.id.deviceSettingFragment
                    ||navController.getCurrentDestination().getId() == R.id.deviceDetailFragment)){
                    Log.d("iot","else navigation......go devices");
                    navController.navigate(R.id.action_global_devicesFragment);
                }else if(navController.getCurrentDestination() != null
                        && (navController.getCurrentDestination().getId() == R.id.devicesFragment
                        ||navController.getCurrentDestination().getId() == R.id.profileFragment
                        ||navController.getCurrentDestination().getId() == R.id.connectionsFragment)){
                    Log.d("iot","else navigation......go main");
                    navController.navigate(R.id.action_global_mainFragment);
                }else if(navController.getCurrentDestination() != null
                        && (navController.getCurrentDestination().getId() == R.id.mainFragment)){
                    Log.d("iot","else navigation......open drawer.");
                    if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        drawerLayout.closeDrawer(Gravity.LEFT);
                    }else{
                        drawerLayout.openDrawer(Gravity.LEFT);
                    }
                }else{
                    Log.d("iot","pop back stack.");
                    navController.popBackStack();
                }

            }
        });
    }


}
