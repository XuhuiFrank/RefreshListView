# RefreshListView

## 项目简介

	1.根据网络上的开源项目PullToRefresh改造而来。
	2.修复原来项目中上拉加载更多数据时，回调接口调用两次的问题。
	3.根据服务器返回数据的有无，进行不同的处理。

## 项目截图

![image text](https://raw.githubusercontent.com/MakeDeath/RefreshListView/master/Screenshot/0.png)

![image text](https://raw.githubusercontent.com/MakeDeath/RefreshListView/master/Screenshot/1.png)

![image text](https://raw.githubusercontent.com/MakeDeath/RefreshListView/master/Screenshot/2.png)

## RefreshListView项目使用示例

初始化设置相关属性：
```java
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


//        //禁用加载更多  默认开启
//        mListView.setLoadMoreEnable(false);
//        //禁用下拉刷新  默认开启
//        mListView.setRefreshEnable(false);

    }
```

下拉刷新以及上拉加载更多接口回掉：
```java
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
```

## 模拟服务器是否有数据

实际开发中，需判断服务器返回数据的有无，这里仅仅是模拟服务器
```java
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
```









