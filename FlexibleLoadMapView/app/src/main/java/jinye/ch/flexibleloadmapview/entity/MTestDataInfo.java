package jinye.ch.flexibleloadmapview.entity;

/**
 * Created by ChenHong on 2017/10/26.
 * 测试数据对象
 * 用于保存和百度地图相关的数据
 * 可替换成自己的模型
 */

public class MTestDataInfo {

  /*Latitude-纬度*/private String mLteLatitude;
  /*Longitude-经度*/private String mLteLongitude;

  /*额外数据*/private String mExtraData;


  public String getmLteLatitude() {
    return mLteLatitude;
  }

  public void setmLteLatitude(String mLteLatitude) {
    this.mLteLatitude = mLteLatitude;
  }

  public String getmLteLongitude() {
    return mLteLongitude;
  }

  public void setmLteLongitude(String mLteLongitude) {
    this.mLteLongitude = mLteLongitude;
  }

  public String getmExtraData() {
    return mExtraData;
  }

  public void setmExtraData(String mExtraData) {
    this.mExtraData = mExtraData;
  }
}
