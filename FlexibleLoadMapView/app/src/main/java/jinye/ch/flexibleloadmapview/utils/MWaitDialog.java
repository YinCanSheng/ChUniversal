package jinye.ch.flexibleloadmapview.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import jinye.ch.flexibleloadmapview.R;
import jinye.ch.flexibleloadmapview.baseControl.BaseDialog;

/**
 * 等待框
 * @author ChenHong
 */

public class MWaitDialog extends BaseDialog implements View.OnClickListener{

  /*自己*/ private  static MWaitDialog mWaitDialog;
  /*得到顶级容器*/ Window mWindow;
  /* 完全自定义对话框 */ Dialog mDialog;


  /**
   * 构造初始化
   *
   * @param mContext
   */
  private MWaitDialog(Context mContext) {
    /** 实例化对话框 */
    mDialog = new Dialog(mContext, R.style.AppLoginTheme);

    /**设置回退关闭对话框*/
    mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

      @Override
      public boolean onKey(DialogInterface dialog, int keyCode,
                           KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
         close();
        }
        return true;
      }
    });

    /**设置失去焦点*/mDialog.setCancelable(false);

    LayoutInflater mInflater = LayoutInflater.from(mContext);

    View mView = mInflater.inflate(R.layout.util_waitbox_loading, null);

    mWindow = mDialog.getWindow();

    /**设置顶部对齐*/mWindow.setGravity(Gravity.TOP);

   // mWindow.setWindowAnimations(R.style.DialogTop);

    ///**设置背景为空-*/mWindow.setBackgroundDrawable(null);

    /** 往容器里装入视图 */mWindow.setContentView(mView);

  }

  /**
   * 单例获取对象
   *
   * @return
   */
  public static MWaitDialog getDialogUtils(Context mContext) {
    if (mWaitDialog == null) {
      synchronized (MWaitDialog.class) {
        if (mWaitDialog == null) {
          mWaitDialog = new MWaitDialog(mContext);
        } else {
          return mWaitDialog;
        }
      }
    }
    return mWaitDialog;
  }

  /**
   * 设置等待框文字
   */
  public MWaitDialog setLoadTex(String mTex) {
    if (mWindow != null) {
      TextView mTitleTex = (TextView) mWindow.findViewById(R.id.TextView_waitBox_title);
      if (mTitleTex != null&&mTex!=null) {
        mTitleTex.setText(mTex);
      }
    }
    return this;
  }

  /**
   * 设置等待框位置在中部
   */
  public MWaitDialog setLoadOfCenter() {
    if (mWindow != null) {
      /**设置顶部对齐*/mWindow.setGravity(Gravity.CENTER);
    }
    return this;
  }

  /**
   * 隐藏进度条
   */
  public MWaitDialog hideProgressBar() {
    if (mWindow != null) {
      ProgressBar mProgressBar = (ProgressBar) mWindow.findViewById(R.id.ProgressBar_waitBox_wait);
      if (mProgressBar != null) {
        mProgressBar.setVisibility(View.GONE);
      }
    }
    return this;
  }

  /**
   * 显示操作成功图标
   */
  public MWaitDialog showOkIcon() {
    if (mWindow != null) {
      ProgressBar mProgressBar = (ProgressBar) mWindow.findViewById(R.id.ProgressBar_waitBox_wait);
      if (mProgressBar.getVisibility() == View.GONE) {
        ImageView imageView = (ImageView) mWindow.findViewById(R.id.ImageView_waitBox_ok);
        if (imageView != null) {
          imageView.setVisibility(View.VISIBLE);
        }
      }
    }
    return this;
  }

  /**
   * 显示弹框
   */
  @Override
  public void show() {
    if (mDialog != null&&mWindow!=null) {
      mDialog.show();
    }
  }

  /**
   * 关闭弹框
   */
  @Override
  public void close() {
    if (mDialog != null && mDialog.isShowing()) {
      //mDialog.cancel();
      mDialog.dismiss();//反复开启会造成内存泄露-慎用
      mWaitDialog = null;
      mDialog=null;
      mWindow=null;
    }
  }

  /**
   * Dialog是否关闭
   * @return
   */
  public boolean isClose(){
    if(mDialog.isShowing()){
      return false;
    }else{
      return true;
    }
  }

  /**
   * 单击回调
   * @param v
   */
  @Override
  public void onClick(View v) {

  }
}
