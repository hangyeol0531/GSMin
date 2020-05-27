package com.example.gsmin.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.example.gsmin.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.BlockingDeque;

public class MainActivity extends AppCompatActivity {


    private FragmentManager fragmentManager = getSupportFragmentManager();
    public static HomeFragment homeFragment;
//    public static BookmarkFragment bookmarkFragment;
//    private ProfileFragment profileFragment;
//    public static FragmentTransaction transaction;
    private ViewPager viewPager;
    private MenuItem prevMenuItem;
    private ViewPagerAdapter adapter;
    private ImageButton drawwr_btn, search, menu;
    private ImageView gsmin;



    //    private String[] strData = new String[]{
//            "전체",
//            "HOT 게시판",
//            "Best 게시판",
//            "자유",
//            "홍보",
//            "장터",
//            "1학년",
//            "2학년",
//            "3학년",
//            "졸업생",
//            "코딩"
//    };



    private boolean mSlideState = false, searchActivity= true;
    private EditText mainEdit;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        search = findViewById(R.id.searchBtn);
        search.setVisibility(View.INVISIBLE);
        gsmin = findViewById(R.id.gsmin);

        mainEdit = findViewById(R.id.mainEdit);

        menu = findViewById(R.id.menuBtn);
        menu.setBackgroundResource(R.drawable.mask);

//        for(int i = 0; i < IB_LEN; i++) {
//            final int finalI = i;
//            ibArr[i].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                Log.d("finalData_ib", "onClick: "+finalI);
//
//
//                }
//            });
//        }

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(searchActivity){
                isTextChange();
                gsmin.setVisibility(View.GONE);
                mainEdit.setVisibility(View.VISIBLE);
                searchActivity=false;
            }else {
                isTextChange();
                gsmin.setVisibility(View.VISIBLE);
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

            }
            @Override
            public void onPageSelected(int position) {
//                if (prevMenuItem != null) {
//                    prevMenuItem.setChecked(false);
//                } else {
//                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
//                }
////                adapter.notifyDataSetChanged();
//                Log.d("page", "onPageSelected: " + position);
////                if (position == 1){
////                    transaction = fragmentManager.beginTransaction();
////                    transaction.detach(bookmarkFragment).attach(bookmarkFragment).commit();
////                }
//                bottomNavigationView.getMenu().getItem(position).setChecked(true);
//                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
             }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
//        bookmarkFragment = new BookmarkFragment();
//        profileFragment = new ProfileFragment();

        adapter.addFragment(homeFragment);
//        adapter.addFragment(bookmarkFragment);
//        adapter.addFragment(profileFragment);
        viewPager.setAdapter(adapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    private void init() {
//
//    }

}
