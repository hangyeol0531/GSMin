package com.example.gsmin.Main;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.gsmin.Adapter.ViewPagerAdapter;
import com.example.gsmin.Fragment.HomeFragment;
import com.example.gsmin.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    private FragmentManager fragmentManager = getSupportFragmentManager();
    public static HomeFragment homeFragment;
//    public static BookmarkFragment bookmarkFragment;
//    private ProfileFragment profileFragment;
    public static FragmentTransaction transaction;
    private ViewPager viewPager;
    private MenuItem prevMenuItem;
    private ViewPagerAdapter adapter;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager_main);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                transaction = fragmentManager.beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.menu_home:
                        // To-Do
//                        bottomNavigationView.setItemIconTintList(new ColorStateList("", ""));
                        viewPager.setCurrentItem(0);
                        transaction.detach(homeFragment).attach(homeFragment).commit();
                        break;
//                    case R.id.menu_Bookmark:
//                        viewPager.setCurrentItem(1);
//                        transaction.detach(homeFragment).attach(homeFragment).commit();
//                        break;
//                    case R.id.menu_profile:
//                        viewPager.setCurrentItem(2);
//                        transaction.detach(homeFragment).attach(homeFragment).commit();
//                        break;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
//                adapter.notifyDataSetChanged();
                Log.d("page", "onPageSelected: " + position);
//                if (position == 1){
//                    transaction = fragmentManager.beginTransaction();
//                    transaction.detach(bookmarkFragment).attach(bookmarkFragment).commit();
//                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);
//        heart = (ImageView)findViewById(R.id.heart);
//        heart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ck == true){
//                    heart.setImageResource(R.drawable.binheart);
//                    ck = false;
//                }else{
//                    heart.setImageResource(R.drawable.fillheart);
//                    ck = true;
//                }
//            }
//        });
      /*  init();
        getData();*/
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
