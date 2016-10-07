package com.p8p5.refreshlistview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.p8p5.refreshlistview.widget.RefreshListView;
import com.p8p5.refreshlistview.widget.RefreshListViewListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements RefreshListViewListener {

    private static final long DURATION_TIME = 2000;
    private RefreshListView mListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> datas;
    boolean hasRefreshDatas = true;
    boolean hasMoreDatas = true;
    private Random random = new Random();
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (RefreshListView) findViewById(R.id.refresh_listview);
        mListView.setOnRefreshListViewListener(this);

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
        initData();

        mListView.setAdapter(mAdapter);
        mAdapter.addAll(datas);


//        //禁用加载更多
//        mListView.setLoadMoreEnable(false);
//        //禁用下拉刷新
//        mListView.setRefreshEnable(false);

    }

    private void initData() {
        datas = new ArrayList<String>();
        for (int i = 0; i < 15; i++) {
            datas.add("Content: " + random.nextInt(100));
        }
    }

    @Override
    public void onLoad() {
        if (hasMoreDatas) {
            getLoadMoreData_HasDatas();
        } else {
            getLoadMoreData_HasNoDatas();
        }
    }

    @Override
    public void onRefresh() {
        if (hasRefreshDatas) {
            getRefreshMoreData_HasDatas();
        } else {
            getRefreshData_HasNoDatas();
        }
    }

    public void getLoadMoreData_HasDatas() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                datas.clear();
                for (int i = 0; i < 10; i++) {
                    datas.add("LoadMoreDatas: " + random.nextInt(100));
                }
                mAdapter.addAll(datas);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mListView.stopLoadMore();
                    }
                });

            }
        }, DURATION_TIME);
    }

    public void getLoadMoreData_HasNoDatas() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mListView.stopLoadMoreForNoDatas();
                    }
                });

            }
        }, DURATION_TIME);
    }


    public void getRefreshMoreData_HasDatas() {
        //下拉刷新有数据，说明上拉加载更多可能有数据，因此激活上拉加载
        mListView.setLoadMoreEnable(true);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.clear();
                datas.clear();
                for (int i = 0; i < 20; i++) {
                    datas.add("RefreshDatas:  " + random.nextInt(100));
                }
                mAdapter.addAll(datas);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mListView.stopRefresh();
                    }
                });

            }
        }, DURATION_TIME);
    }

    public void getRefreshData_HasNoDatas() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mListView.stopRefresh();
                    }
                });

            }
        }, DURATION_TIME);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.refresh_has_data:
                hasRefreshDatas = true;
                break;
            case R.id.refresh_has_no_data:
                hasRefreshDatas = false;
                break;
            case R.id.load_more:
                hasMoreDatas = true;
                break;
            case R.id.has_no_more:
                hasMoreDatas = false;
                break;
        }

        Toast.makeText(this, "下拉刷新： " +
                (hasRefreshDatas == true ? "有数据" : "无数据") +
                "\r\n" +
                "上拉加载： " +
                (hasMoreDatas == true ? "有数据" : "无数据"), Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh_menu, menu);
        getMenuInflater().inflate(R.menu.load_more_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
