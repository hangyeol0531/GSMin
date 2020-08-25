package com.example.gsmin.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.gsmin.Adapter.ViewPagerAdapter;
//import com.example.gsmin.Fragment.HomeFragment;
import com.example.gsmin.Fragment.HomeFragment;
import com.example.gsmin.Fragment.MyChatFragment;
import com.example.gsmin.Fragment.MyWriteFragment;
import com.example.gsmin.Fragment.NoticeFragment;
import com.example.gsmin.Fragment.SettingFragment;
import com.example.gsmin.Model.Data;
import com.example.gsmin.R;
import com.example.gsmin.Splash.SplashActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.BlockingDeque;

public class MainActivity extends AppCompatActivity {


    private FragmentManager fragmentManager = getSupportFragmentManager();
    public static HomeFragment homeFragment;
    public static NoticeFragment noticeFragment;
    public static SettingFragment settingFragment;
    public static MyWriteFragment myWriteFragment;
    public static MyChatFragment myChatFragment;
    public static FragmentTransaction transaction;
    public static ImageView gsmin;
    public static TextView mainText;
    public static TextView navName, navEmail;
    public int pagePosition = 0;

    private ViewPager viewPager;
    private MenuItem prevMenuItem;
    private ViewPagerAdapter adapter;
    private ImageButton drawwr_btn, search, menu;
    private ImageView navImg;
    private boolean mSlideState = false, searchActivity= true;
    private EditText mainEdit;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        navImg = hView.findViewById(R.id.navImg);
        navName = hView.findViewById(R.id.navName);
        navEmail = hView.findViewById(R.id.navEmail);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                transaction = fragmentManager.beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.navBoard:
                        viewPager.setCurrentItem(0);
                        transaction.detach(homeFragment).attach(homeFragment).commit();
                        drawer.closeDrawer(Gravity.LEFT);
                        mSlideState = false;
                        break;
                    case R.id.navNoti:
                        viewPager.setCurrentItem(1);
                        transaction.detach(noticeFragment).attach(noticeFragment).commit();
                        drawer.closeDrawer(Gravity.LEFT);
                        mSlideState = false;
                        break;

                    case R.id.navMywrite:
                        viewPager.setCurrentItem(2);
                        transaction.detach(myWriteFragment).attach(myWriteFragment).commit();
                        drawer.closeDrawer(Gravity.LEFT);
                        mSlideState = false;
                        break;

                    case R.id.navMychat:
                        viewPager.setCurrentItem(3);
                        transaction.detach(myChatFragment).attach(myChatFragment).commit();
                        drawer.closeDrawer(Gravity.LEFT);
                        mSlideState = false;
                        break;

                    case R.id.navSetting:
                        viewPager.setCurrentItem(4);
                        transaction.detach(settingFragment).attach(settingFragment).commit();
                        drawer.closeDrawer(Gravity.LEFT);
                        mSlideState = false;
                        break;
                    case R.id.logout:
                        SharedPreferences sharedPreferences = getSharedPreferences("login_data",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", "");
                        editor.putString("pw", "");
                        editor.commit();

                        Toast.makeText(getApplicationContext(), Data.UserName+"님! 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();

                        Data.clear();

                        startActivity(new Intent(getApplication(), LoginActivity.class));
                        MainActivity.this.finish();
                        break;
                }
                return false;
            }
        });

        search = findViewById(R.id.searchBtn);
        search.setVisibility(View.INVISIBLE);
        gsmin = findViewById(R.id.gsmin);

        mainEdit = findViewById(R.id.mainEdit);
        mainText = findViewById(R.id.mainText);

        menu = findViewById(R.id.menuBtn);
        menu.setBackgroundResource(R.drawable.mask);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(searchActivity){
                isTextChange();
                if (pagePosition == 0) {
                    gsmin.setVisibility(View.GONE);
                }else{
                    mainText.setVisibility(View.GONE);
                }
                mainEdit.setVisibility(View.VISIBLE);
                searchActivity=false;
            }else {
                isTextChange();
                if (pagePosition == 0){
                    gsmin.setVisibility(View.VISIBLE);
                }else{
                    mainText.setVisibility(View.VISIBLE);
                }
                mainEdit.setVisibility(View.GONE);
                searchActivity=true;
            }
            }
        });

        mainEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isTextChange();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        mainEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//                if(!b) {
//                    mainEdit.setText("");
//                    isTextChange();
//                    gsmin.setVisibility(View.VISIBLE);
//                    mainEdit.setVisibility(View.GONE);
//                }
//            }
//        });

        drawwr_btn = findViewById(R.id.drawer_btn);
        drawwr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("t", "onClick: ");
                if(mSlideState){
                    drawer.closeDrawer(Gravity.LEFT);
                    mSlideState = false;
                }else{
                    drawer.openDrawer(Gravity.LEFT);
                    mSlideState = true;
                }
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager_main);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                return;
            }
            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    nav_view.getMenu().getItem(0).setChecked(false);
                }
                pagePosition = position;
                adapter.notifyDataSetChanged();
                isTextChange();
                gsmin.setVisibility(View.VISIBLE);
                mainEdit.setVisibility(View.GONE);
                searchActivity=true;
                switch (position){
                    case 0:
                        menu.setVisibility(View.VISIBLE);
                        MainActivity.gsmin.setVisibility(View.VISIBLE);
                        MainActivity.mainText.setVisibility(View.GONE);
                        break;
                    case 1:
                        menu.setVisibility(View.VISIBLE);
                        MainActivity.gsmin.setVisibility(View.GONE);
                        MainActivity.mainText.setVisibility(View.VISIBLE);
                        MainActivity.mainText.setText("알림");
                        break;
                    case 2:
                        menu.setVisibility(View.VISIBLE);
                        MainActivity.gsmin.setVisibility(View.GONE);
                        MainActivity.mainText.setVisibility(View.VISIBLE);
                        MainActivity.mainText.setText("내가 쓴 글");
                        break;
                    case 3:
                        menu.setVisibility(View.VISIBLE);
                        MainActivity.gsmin.setVisibility(View.GONE);
                        MainActivity.mainText.setVisibility(View.VISIBLE);
                        MainActivity.mainText.setText("내가 쓴 댓글");
                        break;
                    case 4:
                        menu.setVisibility(View.GONE);
                        MainActivity.gsmin.setVisibility(View.GONE);
                        MainActivity.mainText.setVisibility(View.VISIBLE);
                        MainActivity.mainText.setText("설정");
                        break;
                }
                Log.d("page", "onPageSelected: " + position);

                nav_view.getMenu().getItem(position).setChecked(true);
                prevMenuItem = nav_view.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Handler hd = new Handler();
        hd.postDelayed(new MainActivity.splashhandler(), 1800);
        setupViewPager(viewPager);
    }

    private void isTextChange() {
        for(int j = 0; j < HomeFragment.TV_LEN; j++) {
            final int finalI = j;
            if (!(HomeFragment.tvArr[finalI].getText().toString()).contains(mainEdit.getText().toString())) {
                HomeFragment.slArr[0].setVisibility(View.GONE);
                HomeFragment.slArr[finalI + 1].setVisibility(View.GONE);
            }else{
                HomeFragment.slArr[0].setVisibility(View.VISIBLE);
                HomeFragment.slArr[finalI + 1].setVisibility(View.VISIBLE);
            }
        }
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId()){
//            case R.id.mainMenu:
//                Toast.makeText(this, "첫번째", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.mainMenu2:
//                Toast.makeText(this, "두번째", Toast.LENGTH_SHORT).show();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
    }
    public boolean onContextItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.mainMenu:
                Log.d("add", "onContextItemSelected: ");
        }

        return super.onContextItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        noticeFragment = new NoticeFragment();
        myWriteFragment = new MyWriteFragment();
        myChatFragment = new MyChatFragment();
        settingFragment = new SettingFragment();

        adapter.addFragment(homeFragment);
        adapter.addFragment(noticeFragment);
        adapter.addFragment(myWriteFragment);
        adapter.addFragment(myChatFragment);
        adapter.addFragment(settingFragment);
        viewPager.setAdapter(adapter);
    }

    public class splashhandler implements Runnable {
        public void run(){
            switch (Data.UserGrade){
                case 1 : navImg.setImageResource(R.drawable.one_icon);break;
                case 2 :navImg.setImageResource(R.drawable.two_icon);break;
                case 3 :navImg.setImageResource(R.drawable.three_icon);break;
                default: navImg.setImageResource(R.drawable.grad_icon);break;
            }
            navName.setText(Data.UserName);
            navEmail.setText(Data.UserEmail);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
//        return true;
//    }

//    private void init() {
//
//    }

}
