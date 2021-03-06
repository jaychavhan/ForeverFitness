package com.gayatri.foreverfitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private String Name;
    private boolean isImperial;
    private NavigationAdapter navigationAdapter;
    private ViewPager Pager = null;
    private BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Pager = (ViewPager) findViewById(R.id.viewPager);

        navView = (BottomNavigationView) findViewById(R.id.nav_view);
        Intent intent = getIntent();
        this.Name = getIntent().getStringExtra("Name");
        this.isImperial = getIntent().getBooleanExtra("IsImperial",false);
        navigationAdapter = new NavigationAdapter(this, this.Name, this.isImperial);

        Pager.setAdapter(navigationAdapter);

        Pager.setCurrentItem(2);

        navView.setSelectedItemId(R.id.navigation_settings);

        Pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0){
                    navView.setSelectedItemId(R.id.navigation_home);
                }else if (i == 1){
                    navView.setSelectedItemId(R.id.navigation_dashboard);
                }else if(i == 2){
                    navView.setSelectedItemId(R.id.navigation_settings);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        navView.setOnNavigationItemSelectedListener (new BottomNavigationView.OnNavigationItemSelectedListener() {
             @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home:
                        Pager.setCurrentItem(0);
                        break;
                    case R.id.navigation_dashboard:
                        Pager.setCurrentItem(1);
                        break;
                    case R.id.navigation_settings:
                        Pager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });

        navigationAdapter.setCustomObjectListener(new NavigationAdapter.MyCustomObjectListener() {
            @Override
            public void onObjectReady(String title) {
                if (title == "btnDeleteAccount") {
                    finish();
                    Intent WelcomePage = new Intent(MainActivity.this, WelcomeActivity.class);
                    SqlLiteManager sql = new SqlLiteManager(MainActivity.this);
                    sql.getUserId(MainActivity.this.Name);
                    sql.deleteUser();
                    finish();
                    startActivity(WelcomePage);
                }
            }
        });
    }
}