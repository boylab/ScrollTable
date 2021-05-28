# ScrollTable
ScrollTable 是一款好用的TableView，以下是具体使用。

## Usage

### 1、库依赖

步骤1：存储库末尾的 `build.gradle`中：
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

步骤2：添加依赖库：
```
dependencies {
        implementation 'com.github.boylab:ScrollTable:Tag'
}
```
### 2、使用

步骤1：xml布局文件添加自定义控件：
```
<com.boylab.scrolltable.TableView
    android:id="@+id/tableView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:contentGravity="center"
    app:rowWidths="@array/WithList" />
```

步骤2：视图页面初始化TableView：
```
private ArrayList<Student> mTableDatas = new ArrayList<Student>();
/**
* 查询所得数据
*/
for (int i = 0; i < 30; i++) {
    mTableDatas.add(new Student(i));
}

TableView tableView = findViewById(R.id.tableView);
/**
* 可设置一些参数...
*/
tableView.setTableData(new StudentLabel(), mTableDatas);
```
步骤3：对StudentLabel.class、Student.class说明：StudentLabel作为TableView的头部数据类、Student为TableView展示数据类，都必须实现ItemRow接口
```
public class StudentLabel implements ItemRow {

    public static final int SIZE = 13;
    private final List<String> label = new ArrayList<String>(){{
        add("序号");
        add("姓名");
        add("年龄");
        add("性别");
        add("身高");
        add("体重");
        add("语文");
        add("数学");
        add("英语");
        add("语文老师");
        add("数学老师");
        add("英语老师");
        add("备注");
    }};

    @Override
    public int size() {
        return SIZE;
    }

    @Override
    public String get(int position) {
        if (position < size()){
            return label.get(position);
        }
        return "N/A";
    }

}
```

```
public class Student implements ItemRow {

    private long id;
    private properties...

    @Override
    public int size() {
        return StudentLabel.SIZE;
    }

    @Override
    public String get(int position) {
        /**
         * position 对应的值 与 Label的标签对应上即可
         */
        if (position == 0) {
            return String.valueOf(id);
        } else if (position == 1) {
            .......

        } else if (position == 12) {
            return remark;
        }
        return "N/A";
    }
```
### 3、监听

item点击监听
```
tableView.setmOnItemClickListenter(new TableView.OnItemClickListenter() {
    @Override
    public void onItemClick(View item, int position) {
        Toast.makeText(MainActivity.this, "onItemClick: "+position, Toast.LENGTH_SHORT).show();
    }
});
```
下拉刷新监听（用的少）
```
tableView.setOnRefreshListener(new TableView.OnTableRefreshListener() {
    @Override
    public void onRefresh(TableView mTableView) {
        mTableDatas.clear();

        for (int i = 0; i < 30; i++) {
            mTableDatas.add(new Student(i));
        }

        //刷新TableView即可
        mTableView.notifyDataSetChanged();

        //完成刷新
        mTableView.finishRefresh();

        if (mTableDatas.size() >= 30){
                //放开上拉加载
            mTableView.setEnableLoadMore(true);
        }
    }
});
```
上拉加载监听

```
tableView.setOnLoadMoreListener(new TableView.OnTableLoadMoreListener() {
    @Override
    public void onLoadMore(TableView mTableView) {

        int updateSize = (mTableDatas.size() < 100) ? 30 : 20;
        for (int i = 0; i < updateSize; i++) {
            mTableDatas.add(new Student(i));
        }

        //刷新TableView即可
        mTableView.notifyDataSetChanged();

        //完成上拉加载
        mTableView.finishLoadMore();

        if (updateSize < 30){
            //关闭上拉加载
            mTableView.setEnableLoadMore(false);
        }
    }
});
```


### 4、扩展属性
You can add attributes to customize the view. Available attributes:
| name                  | type      | info                                      |
|-----------------------|-----------|-------------------------------------------|
| rowWidths             | reference | sets the with on the column               |
| rowHeight             | dimension | sets the height on the row                |
| rowDivider            | color     | sets the color on the table divider       |
| rowFoucsColor         | color     | sets the color on the foucs row           |
| **firstrow 参数**     |           |                                           |
| headTextSize          | dimension | sets the size on the  firstrow            |
| headTextColor         | color     | sets the color on the firstrow            |
| headBackgroundColor   | color     | sets the backgroundColor on the firstrow  |
| headPaddingTop        | dimension | sets the paddingTop on the firstrow       |
| headPaddingLeft       | dimension | sets the paddingLeft on the firstrow      |
| headPaddingBottom     | dimension | sets the paddingBottom on the firstrow    |
| headPaddingRight      | dimension | sets the paddingRight on the firstrow     |
| headGravity           | enum      | sets the gravity on the firstrow          |
| **firstcolumn 参数**  |           |                                           |
| leftTextSize          | dimension | sets the size on the firstcolumn          |
| leftTextColor         | color     | sets the color on the firstcolumn         |
| leftBackgroundColor   | color     | sets the backgroundColor on the firstcolumn|
| leftPaddingTop        | dimension | sets the paddingTop on the firstcolumn    |
| leftPaddingLeft       | dimension | sets the paddingLeft on the firstcolumn   |
| leftPaddingBottom     | dimension | sets the paddingBottom on the firstcolumn |
| leftPaddingRight      | dimension | sets the paddingRight on the firstcolumn  |
| leftGravity           | enum      | sets the gravity on the firstcolumn       |
| **contentrow 参数**   |           |                                           |
| contentTextSize       | dimension | sets the size on the contentrow           |
| contentTextColor      | color     | sets the color on the contentrow          |
| contentBackgroundColor| color     | sets the backgroundColor on contentrow    |
| contentPaddingTop     | dimension | sets the paddingTop on the contentrow     |
| contentPaddingLeft    | dimension | sets the paddingLeft on the contentrow    |
| contentPaddingBottom  | dimension | sets the paddingBottom on the contentrow  |
| contentPaddingRight   | dimension | sets the paddingRight on the contentrow   |
| contentGravity        | enum      | sets the gravity on the contentrow        |

### 5、you also declare it into your `pom.xml`:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.boylab</groupId>
    <artifactId>SmartSpinner</artifactId>
    <version>1.0.0</version>
</dependency>
```
