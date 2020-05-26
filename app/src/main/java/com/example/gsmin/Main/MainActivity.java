package com.example.gsmin.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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

    private static int[] menuBtn = new int[]{
            R.id.b1, R.id.b2, R.id.b3,
            R.id.b4, R.id.b5, R.id.b6,
            R.id.b7, R.id.b8, R.id.b9,
            R.id.b10, R.id.b11};
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
    private int IB_LEN = menuBtn.length;
    private ImageButton[] ibArr = new ImageButton[IB_LEN];


    private boolean mSlideState = false;
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

        menu = findViewById(R.id.menuBtn);
        menu.setBackgroundResource(R.drawable.mask);

        init();

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

        drawwr_btn = findViewById(R.id.drawer_btn);

        drawwr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.mainMenu:
                Toast.makeText(this, "첫번째", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.mainMenu2:
                Toast.makeText(this, "두번째", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

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

    private void init() {
        for(int i = 0; i<IB_LEN;i++){
            ibArr[i] = findViewById(menuBtn[i]);
        }
    }

}
