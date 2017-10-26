package jinye.ch.flexibleloadmapview.utils;

import android.os.Handler;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;

import jinye.ch.flexibleloadmapview.baseControl.MDefApplication;

/**
 * Created by 今夜犬吠 on 2017/8/3.
 * 百度地图定位-封装
 */

public class MLocationHelper {

  /*自己*/private volatile static MLocationHelper mLocationHelper;
  /*回调接口*/private MLocationCallBack mCallBack;
  /*定位客户端*/private LocationClient mLocationClient;
  /*定位监听*/private BDLocationListener mBdLocationListener = new MBDLoca();

  /*地图客户端*/private BaiduMap mBaiduMap;
  /*Handler*/private Handler mHandlerUtils;

  /**
   * 你不能New我
   */
  private MLocationHelper() {

    /**初始化定位客户端并配置定位参数*/
    mLocationClient = new LocationClient(MDefApplication.mContext, getLocOption());

    /**设置定位回调函数*/
    mLocationClient.registerLocationListener(mBdLocationListener);
  }


  /**
   * 传递回调参数
   */
  public void sendCallBack(MLocationCallBack mCallBack, BaiduMap mBaiduMap) {
    this.mCallBack = mCallBack;
    this.mBaiduMap = mBaiduMap;
  }

  /**
   * 单例获取实例
   */
  public static MLocationHelper getInstance() {
    if (mLocationHelper == null) {
      synchronized (MLocationHelper.class) {
        if (mLocationHelper == null) {
          mLocationHelper = new MLocationHelper();
        } else {
          return mLocationHelper;
        }
      }
    }
    return mLocationHelper;
  }

  /**
   * 获取定位配置实例
   */
  private LocationClientOption getLocOption() {

    LocationClientOption mOption = new LocationClientOption();

    /**初始定位参数*/
    mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

    mOption.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系

    mOption.setScanSpan(1000);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

    mOption.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要

    mOption.setOpenGps(true);// 可选，默认false,设置是否使用gps

    mOption.setLocationNotify(true);// 可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

    mOption.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

    mOption.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

    mOption.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

    mOption.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集

    mOption.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

    // mOption.setOpenAutoNotifyMode(1000,1,LocationClientOption.LOC_SENSITIVITY_HIGHT);//设置自动回调 1秒 1米

    return mOption;
  }


  /**
   * 定位回调接口
   */
  public interface MLocationCallBack {
    void okCallBack(int locType
        , double latitude
        , double longitude
        , float mAccuracy
        , String mNetWorkLocType);

    void failCallBack();
  }

  /**
   * 开始定位
   */
  public void startLoc() {
    if (mLocationClient != null) {
      if (!mLocationClient.isStarted()) {
        mLocationClient.start();
      }
    }
  }

  /**
   * 请求定位-异步-不阻塞
   */
  public void requestLoc() {
    if (mLocationClient != null) {
      mLocationClient.requestLocation();
    }
  }

  /**
   * 停止定位
   */
  public void stopLoc() {
    if (mLocationClient != null) {
      if (mLocationClient.isStarted()) {
        mLocationClient.stop();
      }
    }
  }

  /**
   * 销毁
   */
  public void destoryLoc() {
    if (mLocationClient != null) {
      /**停止定位*/mLocationClient.stop();
      if (mBdLocationListener != null) {
        /**注销定位监听*/mLocationClient.unRegisterLocationListener(mBdLocationListener);
      }
      /**回收*/mLocationClient = null;
      /**回收自己*/mLocationHelper = null;
      /***/mHandlerUtils = null;
    }
  }

  /**
   * 定位回调函数
   */
  public class MBDLoca implements BDLocationListener {

    /**
     * 定位结果回调
     *
     * @param bdLocation
     */
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
      if (bdLocation != null) {
        if (bdLocation.getLocType() == BDLocation.TypeGpsLocation
            || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation
            || bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {

          if (mCallBack != null) {
            mCallBack.okCallBack(bdLocation.getLocType()
                , bdLocation.getLatitude()
                , bdLocation.getLongitude()
                , bdLocation.getRadius()
                , bdLocation.getNetworkLocationType());
          }
        } else {
          mCallBack.failCallBack();
        }

      }
    }

  }

}
