package cn.itcast.md07.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.itcast.md07.DetailActivity;
import cn.itcast.md07.R;
import cn.itcast.md07.bean.ItemBean;
import cn.itcast.md07.fragment.ItemFragment;

/**
 * Created by Administrator on 2016/4/29.
 */
public class ItemAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ItemBean> listDatas;

    private int mLayoutType;

    public ItemAdapter(Context context, List<ItemBean> listDatas, int layoutType) {
        this.context = context;
        this.listDatas = listDatas;
        this.mLayoutType = layoutType;
    }

    // 创建ViewHolder：封装了列表项布局
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 创建列表项布局
        View item = LayoutInflater.from(context).inflate(getLayoutRes(), parent, false);
        // View item =  View.inflate(context, R.layout.item_list_view, null); // 此方式有问题

        // 创建ViewHolder
        MyViewHolder holder = new MyViewHolder(item);

        item.setTag(holder);

        return holder;
    }

    /**
     * 根据类型返回item布局文件
     *
     * @return
     */
    private int getLayoutRes() {
        int layout = 0;
        switch (mLayoutType) {
            case ItemFragment.TYPE_LIST_VIEW:
                layout = R.layout.item_list_view;
                break;
            case ItemFragment.TYPE_GRID_VIEW:
                layout = R.layout.item_grid_view;
                break;
            case ItemFragment.TYPE_STAGGERED:
                layout = R.layout.item_staggered_grid;
                break;
        }
        return layout;
    }

    // 显示列表项中的子控件
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // 获取holder对象
        final MyViewHolder myholder = (MyViewHolder) holder;

        // 获取列表项实体对象
        final ItemBean bean = listDatas.get(position);

        // 显示列表项中的子控件
        myholder.ivImage.setImageResource(bean.imageId);
        myholder.tvTitle.setText(bean.title);

        // 设置列表项点击事件
        myholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(myholder, bean);
            }
        });
    }

    /**
     * 列表项点击事件
     *
     * @param myholder Holder对象
     * @param bean item对应的实体对象
     */
    private void onItemClick(MyViewHolder myholder, ItemBean bean) {
        // Toast.makeText(context, "" + bean.title, Toast.LENGTH_SHORT).show();
        // 进入详情界面
        Activity activity = (Activity) context;

        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra("bean", bean);

        if (Build.VERSION.SDK_INT >= 21) {// 高版本支持转场动画
            Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, myholder.ivImage, "tran_01").toBundle();
            activity.startActivity(intent, bundle);
        } else {
            activity.startActivity(intent);
        }

    }

    // 返回item的个数
    @Override
    public int getItemCount() {
        return listDatas == null ? 0 : listDatas.size();
    }

    /**
     * 设置新数据，刷新列表数据显示
     * @param listDatas 要显示的新数据
     */
    public void setDatas(List<ItemBean> listDatas) {
        this.listDatas = listDatas;
        notifyDataSetChanged(); // 刷新数据显示
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private TextView tvTitle;

        /**
         * @param itemView item布局
         */
        public MyViewHolder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

}
