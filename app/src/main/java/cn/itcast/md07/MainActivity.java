package cn.itcast.md07;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.md07.adapter.MainAdapter;
import cn.itcast.md07.fragment.ItemFragment;

public class MainActivity extends AppCompatActivity {

        private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();
        initDrawerLayout();
        initNavigationView();
        initFAB();
        initViewPager();
        Intent intent = getIntent();


    }

    /**
     * 初始化ViewPager与选项卡
     */
    private void initViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        List<Fragment> fragments = new ArrayList<>();
        String[] titles = {
                "列表", "网格","瀑布流"
        };

        int[] layoutTypes =  {
                ItemFragment.TYPE_LIST_VIEW,
                ItemFragment.TYPE_GRID_VIEW,
                ItemFragment.TYPE_STAGGERED
        };

        for (int i = 0; i < titles.length; i++) {
            ItemFragment fragment = new ItemFragment();

            // activity传递参数给Fragment
            Bundle bundle = new Bundle();
            bundle.putInt("type", layoutTypes[i]);
            fragment.setArguments(bundle);

            fragments.add(fragment);
        }

        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager(), fragments, titles));
        // 关联选项卡控件与ViewPager
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 初始化浮动操作按钮
     */
    private void initFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 初始化侧滑菜单
     */
    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        // 设置菜单项点击事件
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.nav_camera) {

                        }
                        // 关闭抽屉
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
        });
    }

    /**
     * 初始化抽屉控件
     */
    private void initDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // 创建开关控件： 用来关联DrawerLayout与ActionBar(ToolBar)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        // 设置监听器
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState(); // 同步DrawerLayout与ActionBar状态
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        // 按下返回键时，如果抽屉打开了则关闭它
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else { // 按下返回键时，退出当前activity界面
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
