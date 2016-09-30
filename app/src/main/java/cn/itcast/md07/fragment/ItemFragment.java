package cn.itcast.md07.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.md07.R;
import cn.itcast.md07.adapter.ItemAdapter;
import cn.itcast.md07.bean.ItemBean;

/**
 * Created by Administrator on 2016/4/29.
 */
public class ItemFragment extends Fragment {

    /** 布局类型：列表 */
    public static final int TYPE_LIST_VIEW = 0;
    /** 布局类型：网格 */
    public static final int TYPE_GRID_VIEW = 1;
    /** 布局类型：瀑布流 */
    public static final int TYPE_STAGGERED = 2;

    /** ItemFragment的布局类型 */
    private int mLayoutType = TYPE_LIST_VIEW;

    private Activity mActivity;

    /** Fragment的根布局 */
    private View root;

    /** 列表显示的数据 */
    private List<ItemBean> listDatas;
    private ItemAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 接收Activity传递过来的参数
        mLayoutType = getArguments().getInt("type", TYPE_LIST_VIEW);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
        @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // System.out.println("----------------onCreateView: ");

        if (root == null) {
            root = LayoutInflater.from(mActivity).inflate(R.layout.fragment_item, container, false);

            initSwipeRefreshLayout();
            initRecyclerView();

        } else {
            // 解除父子控件关系
            unbindWidthParent(root);
        }

        return root;
    }

    /**
     * 初始化可回收视图
     */
    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);

        // 设置可回收视图显示的样式
        recyclerView.setLayoutManager(getLayoutManager());

        // 创建数据集合
        // listDatas = createDatas();

        // 显示可回收视图
        mAdapter = new ItemAdapter(mActivity, null, mLayoutType);
        recyclerView.setAdapter(mAdapter);

        // 加载数据
        loadDatas();
    }

    /**
     * 获取布局的样式
     * @return
     */
    private RecyclerView.LayoutManager getLayoutManager() {
        RecyclerView.LayoutManager manager = null;
        switch (mLayoutType) {
            case TYPE_LIST_VIEW:
                manager = new LinearLayoutManager(mActivity);
                break;
            case TYPE_GRID_VIEW:
                manager = new GridLayoutManager(mActivity, 3);
                break;
            case TYPE_STAGGERED:
                manager = new StaggeredGridLayoutManager(2,
                        StaggeredGridLayoutManager.VERTICAL);
                break;
        }
        return manager;
    }

    /**
     *  模拟列表显示的数据
     * @return
     */
    private List<ItemBean> createDatas() {
        List<ItemBean> listDatas = new ArrayList<>();

        for (int i = 0; i < 40; i ++) {
            ItemBean bean = new ItemBean();
            bean.imageId = R.drawable.p1 + i;
            bean.title = "选项 " + (i+1);
            listDatas.add(bean);
        }

        return listDatas;
    }

    /**
     * 初始化下拉刷新控件
     */
    private void initSwipeRefreshLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout)
                root.findViewById(R.id.swipe_refresh_layout);

        // 设置下拉刷新控件显示的颜色
        // swipeRefreshLayout.setColorSchemeColors();
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light
        );

        // 参数1： 下拉时是否缩放
        // 参数2： 下拉时开始显示的位置
        // 参数2： 下拉手势结束后，下拉刷新控件显示的位置
        swipeRefreshLayout.setProgressViewOffset(false, 10, 50);

        // 显示下拉刷新控件
        swipeRefreshLayout.setRefreshing(true);

        // 设置下拉监听事件
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDatas();
            }
        });
    }

    /**
     * 模拟加载新数据
     */
    private void loadDatas() {
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(1500);

                // 获取新数据
                listDatas = createDatas();

                // 设置新数据，刷新列表数据显示
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setDatas(listDatas);

                        // 隐藏下拉控件
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }.start();
    }

    /**
     * 解除父子控件关系
     * @param view
     */
    private void unbindWidthParent(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
    }
}












