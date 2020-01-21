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

        //main bottom bar绑定
        mainBottom = findViewById(R.id.iot_ui_bottom_nav);
        NavigationUI.setupWithNavController(mainBottom, navController);

        //device bottom绑定
        deviceBottom = findViewById(R.id.iot_ui_device_bottom_nav);
        NavigationUI.setupWithNavController(deviceBottom, navController);
        deviceBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                boolean ret = true;
                switch (id) {
                    case R.id.deviceFunctionFragment:
                        navController.navigate(R.id.action_to_deviceFunctionFragment);
                        break;
                    case R.id.deviceSettingFragment:
                        navController.navigate(R.id.action_to_deviceSettingFragment);
                        break;
                    case R.id.deviceDetailFragment:
                        navController.navigate(R.id.action_to_deviceDetailFragment);
                        break;
                    default:
                        ret = false;
                        break;
                }
                return ret;
            }
        });

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

    public void showDeviceBottomMenu(int funcId) {
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

//    Handle drawer in onBackPressed
//    if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
//        drawerLayout.closeDrawer(Gravity.LEFT);
//    }else{
//        drawerLayout.openDrawer(Gravity.LEFT);
//    }

}
