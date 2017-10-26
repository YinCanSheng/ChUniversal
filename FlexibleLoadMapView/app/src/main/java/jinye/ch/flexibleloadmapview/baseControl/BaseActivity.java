package jinye.ch.flexibleloadmapview.baseControl;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import jinye.ch.flexibleloadmapview.utils.MRxManager;

/**
 * activity基类-管理各Activity生命周期
 *
 * @author ChenHong
 */
public abstract class BaseActivity extends RxAppCompatActivity
    implements OnClickListener, Baselogic {

  /*緩存控件-集合*/private SparseArray<View> mViewsSparesA;

  /*Rx-订阅统一管理*/public MRxManager mRxManager = new MRxManager();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    /*把每一个Activity添加到栈 */
    MDefApplication.getInstance().addActivity(this);

    /**加载布局*/setContentView(getLayoutResID());

    /**绑定子V*/ButterKnife.bind(this);

    /*初始化控件集合-用于緩存*/
    mViewsSparesA = new SparseArray<View>();

    /*初始化控件*/
    findViews();

    /*配置控件初始状态*/
    configureView();

    /*初始化对象*/
    initObj();

    /*设置监听*/
    setListener();

    /*数据操作*/
    initData();
  }

  /**
   * 单击监听响应
   */
  @Override
  public void onClick(View mView) {
    responseClick(mView.getId());

  }

  /**
   * 通过控件ID得到控件对象
   */
  public <E extends View> E getViewObjectOfId(int mViewId) {
           /*拿取已加载过的控件*/
    E mView = (E) mViewsSparesA.get(mViewId);
            /*没有加载过则加载控件*/
    if (mView == null) {
                /*初始化控件*/
      mView = (E) findViewById(mViewId);
                /*把控件添加到集合*/
      mViewsSparesA.put(mViewId, mView);
    }
    return mView;
  }

  /**
   * 判断对象是否为空
   */
  public boolean isNullOfObject(Object mObject) {
    if (mObject != null) {
      return true;
    }
    return false;
  }

  /**
   * 退订所有Rx
   */
  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mRxManager != null) {
      mRxManager.clearAllSubscription();
    }
  }
}
