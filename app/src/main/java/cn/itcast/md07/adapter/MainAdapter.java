package cn.itcast.md07.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/4/29.
 */
public class MainAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private String[] titles;

    public MainAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    // 返回选项卡显示的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
