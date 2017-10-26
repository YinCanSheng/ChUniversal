package jinye.ch.flexibleloadmapview.baseControl;
import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import com.baidu.mapapi.SDKInitializer;
import java.util.Stack;
import es.dmoral.toasty.MyToast;


/**
 * Activity管理器
 * ChenHong
 */
public class MDefApplication extends Application {

  /*上下文*/public static Context mContext = null;
  /*activity堆*/private static Stack<AppCompatActivity> activityStack;
  /*自己*/private static MDefApplication singleton;

  @Override
  public void onCreate() {
    super.onCreate();

    //installCrash();

    /**地图初始化配置*/SDKInitializer.initialize(this);

    /**初始化MyToast*/MyToast.init(this, true, true);

    mContext = getApplicationContext();

    singleton = this;

  }

  public static MDefApplication getInstance() {
    return singleton;
  }

  /**
   * add Activity 添加Activity到栈
   */
  public void addActivity(AppCompatActivity activity) {
    if (activityStack == null) {
      activityStack = new Stack<AppCompatActivity>();
    }
    activityStack.add(activity);
  }

  /**
   * get current Activity 获取当前Activity（栈中最后一个压入的）
   */
  public AppCompatActivity currentActivity() {
    AppCompatActivity activity = activityStack.lastElement();
    return activity;
  }

  /**
   * 结束当前Activity（栈中最后一个压入的）
   */
  public void finishActivity() {
    AppCompatActivity activity = activityStack.lastElement();
    finishActivity(activity);
  }

  /**
   * 结束指定的Activity
   */
  public void finishActivity(AppCompatActivity activity) {
    if (activity != null) {
      activityStack.remove(activity);
      activity.finish();
      activity = null;
    }
  }

  /**
   * 結束指定Activity
   * 自定義退出动画
   */
  public void finishDefActivity(int enterAni, int outAni) {
    AppCompatActivity activity = activityStack.lastElement();
    finishDefActivity(activity, enterAni, outAni);
  }

  /**
   * 结束指定的Activity
   */
  public void finishDefActivity(AppCompatActivity activity, int enterAni, int outAni) {
    if (activity != null) {
      activityStack.remove(activity);
      activity.finish();
      activity.overridePendingTransition(enterAni, outAni);
      activity = null;
    }
  }


  /**
   * 结束指定类名的Activity
   */
  public void finishActivity(Class<?> cls) {
    for (AppCompatActivity activity : activityStack) {
      if (activity.getClass().equals(cls)) {
        finishActivity(activity);
      }
    }
  }

  /**
   * 结束所有Activity
   */
  public void finishAllActivity() {
    for (int i = 0, size = activityStack.size(); i < size; i++) {
      if (null != activityStack.get(i)) {
        activityStack.get(i).finish();
      }
    }
    activityStack.clear();
  }

  /**
   * 退出应用程序
   */
  public void AppExit() {
    try {

      finishAllActivity();

    } catch (Exception e) {

    }
  }


//  /**
//   * 注册崩溃捕获器
//   */
//  private void installCrash() {
//    try {
//      Cockroach.install(new Cockroach.ExceptionHandler() {
//        @Override
//        public void handlerException(final Thread thread, final Throwable throwable) {
//          new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//              try {
//                MLogUtils.e("AndroidRuntime", "--->异常捕获器:" + thread + "<---" + throwable);
//                //MyToast.error("捕获到异常\n" + thread + "\n" + throwable.toString());
//              } catch (Throwable e) {
//
//              }
//            }
//          });
//        }
//      });
//    }catch (Exception e){
//
//    }
//
//  }
}
