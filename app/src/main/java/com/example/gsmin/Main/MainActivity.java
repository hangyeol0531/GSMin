package com.example.gsmin.Main;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
    private ImageButton drawwr_btn;
    private boolean mSlideState = false;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        if (savedInstanceState == null){
//            MainFragment mainFragment = new MainFragment();
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.mainFragment, mainFragment, "MAIN")
//                    .commit();
//        }
//        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation_main);

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

}
