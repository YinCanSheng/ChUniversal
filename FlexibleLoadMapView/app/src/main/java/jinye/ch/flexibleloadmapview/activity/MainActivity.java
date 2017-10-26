package jinye.ch.flexibleloadmapview.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import jinye.ch.flexibleloadmapview.R;
import jinye.ch.flexibleloadmapview.baseControl.BaseActivity;
import jinye.ch.flexibleloadmapview.entity.MTestDataInfo;
import jinye.ch.flexibleloadmapview.utils.MLocationHelper;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * 主页-MapView-手势滑动地图-加载5000范围内的Marker
 *
 * @author ChenHong
 */
public class MainActivity extends BaseActivity implements MLocationHelper.MLocationCallBack {

  /*toolbar*/
  @BindView(R.id.Toolbar_ManinActivity_head)
  Toolbar mHeadToolbar;

  /*地图*/
  @BindView(R.id.MapView_MainActivity_map)
  MapView mapView;

  /*定位*/
  @BindView(R.id.FloatingActionButton_MainActivity_location)
  FloatingActionButton mLocationFAB;

  /*地图操作*/private BaiduMap mBaiduMap;

  /*是否开启定位*/private boolean mOpenLocation = true;

  /*定位-管理器*/private MLocationHelper mLocationHelper;

  /*数据源*/private List<MTestDataInfo> mTestDataInfoList;

  /**
   * 给实现类用 返回布局资源ID加载界面布局
   */
  @Override
  public int getLayoutResID() {
    return R.layout.activity_main;
  }

  /**
   * 初始化控件
   */
  @Override
  public void findViews() {
    /*定位*/
    RxView.clicks(mLocationFAB)
        .compose(this.<Void>bindToLifecycle())
        .subscribe(new Action1<Void>() {
          @Override
          public void call(Void aVoid) {
            if (mOpenLocation) {
              Snackbar
                  .make(mLocationFAB, "准备定位"
                      , Snackbar.LENGTH_LONG)
                  .show();
            } else {
              Snackbar
                  .make(mLocationFAB, "关闭定位"
                      , Snackbar.LENGTH_LONG)
                  .show();
            }
            /**定时一秒-开启或关闭定位*/
            Observable.timer(1, TimeUnit.SECONDS)
                .compose(MainActivity.this.<Long>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                  @Override
                  public void call(Long aLong) {
                    if (isNullOfObject(mBaiduMap)) {
                      if (mOpenLocation) {
                        /**显示定位图层*/mBaiduMap.setMyLocationEnabled(true);
                        /**跟随定位*/mBaiduMap.setMyLocationConfiguration(
                            new MyLocationConfiguration(
                                MyLocationConfiguration.LocationMode.FOLLOWING
                                , true, null));
                        /*更改定位标识*/mOpenLocation=false;
                      } else {
                        /**显示定位图层*/mBaiduMap.setMyLocationEnabled(false);
                        /**普通定位*/mBaiduMap.setMyLocationConfiguration(
                            new MyLocationConfiguration(
                                MyLocationConfiguration.LocationMode.NORMAL
                                , true, null));
                        /**更改定位标识*/mOpenLocation=true;
                      }

                    }
                  }
                });
          }
        });
  }

  /**
   * 配置控件初始化状态
   */
  @Override
  public void configureView() {
    /*配置ToolBar*/
    /**替换ActionBar*/setSupportActionBar(mHeadToolbar);
    /**置空标题*/getSupportActionBar().setDisplayShowTitleEnabled(true);
    /**给左上角回退图标*/getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    /**图标可点击*/getSupportActionBar().setHomeButtonEnabled(true);
    /**显示图标*/getSupportActionBar().setDisplayShowHomeEnabled(true);
    /**配置MapView*//**隐藏地图缩放控件*/mapView.showZoomControls(false);

    /**获取BaiduMap*/mBaiduMap = mapView.getMap();
  }

  /**
   * 初始化对象
   */
  @Override
  public void initObj() {
    /*初始化定位管理器*/mLocationHelper=MLocationHelper.getInstance();
    /*初始化数据源*/mTestDataInfoList=new ArrayList<MTestDataInfo>();
  }

  /**
   * 设置监听
   */
  @Override
  public void setListener() {
    /*地图加载完成回调*/
    mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
      @Override
      public void onMapLoaded() {

      }
    });

    /*地图改变回调-移动-缩放-旋转*/
    mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
      /*状态开始改变-调用一次*/
      @Override
      public void onMapStatusChangeStart(MapStatus mapStatus) {

      }
      /*状态开始改变-调用一次-可判断地图改变原因（i）*/
      @Override
      public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

      }

      /*状态改变中*/
      @Override
      public void onMapStatusChange(MapStatus mapStatus) {

      }

      /*状态改变结束-调用一次*/
      @Override
      public void onMapStatusChangeFinish(MapStatus mapStatus) {

      }
    });
  }

  /**
   * 数据操作
   */
  @Override
  public void initData() {

  }

  /**
   * 单击监听响应
   *
   * @param mWeigetId
   */
  @Override
  public void responseClick(int mWeigetId) {

  }

  /**
   * Menu
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    return super.onCreateOptionsMenu(menu);
  }


  /**
   * 定位成功-UI线程
   *
   * @param locType
   * @param latitude
   * @param longitude
   * @param mAccuracy
   * @param mNetWorkLocType
   */
  @Override
  public void okCallBack(int locType, double latitude, double longitude, float mAccuracy, String mNetWorkLocType) {
    /**初始化定位数据*/
    MyLocationData myLocationData = new MyLocationData.Builder()
        .accuracy(10)
        .latitude(latitude)
        .longitude(longitude)
        .build();

    /*设置定位数据*/
    if (isNullOfObject(mBaiduMap)) {
      mBaiduMap.setMyLocationData(myLocationData);
    }

  }

  /**
   * 定位失败
   */
  @Override
  public void failCallBack() {

  }

  /**
   * 获取数据（本地文件读取、网络请求等）
   * 此Demo使用自定义测试数据
   */
  private void toolGetData(){

  }
}
