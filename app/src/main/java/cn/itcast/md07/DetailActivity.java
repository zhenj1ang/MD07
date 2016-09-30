package cn.itcast.md07;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import cn.itcast.md07.bean.ItemBean;

public class DetailActivity extends AppCompatActivity {

    private ItemBean bean;
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivImage = (ImageView) findViewById(R.id.iv_image);

        bean = (ItemBean) getIntent().getSerializableExtra("bean");

        ivImage.setImageResource(bean.imageId);

        initToolBar();
        initFAB();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // 显示界面标题
        toolbar.setTitle(bean.title);
        setSupportActionBar(toolbar);
    }


    private void initFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
