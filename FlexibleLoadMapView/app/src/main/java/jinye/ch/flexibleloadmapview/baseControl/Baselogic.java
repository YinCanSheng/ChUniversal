package jinye.ch.flexibleloadmapview.baseControl;

/**
 * BaseActivity、BaseFragment的基本逻辑
 * @author ChenHong
 */

public interface Baselogic {

    /**
     * 给实现类用 返回布局资源ID加载界面布局
     *
     * @return 布局Id
     */
    abstract int getLayoutResID();

    /**
     * 初始化控件
     *
     */
    abstract void findViews();

    /**
     * 配置控件初始化状态
     */
    abstract void configureView();
    /**
     * 初始化对象
     */
    abstract void initObj();
    /**
     * 设置监听
     */
    abstract void setListener();

    /**
     * 数据操作
     */
    abstract void initData();

    /**
     * 单击监听响应
     *
     * @param mWeigetId 视图Id
     */
    abstract void responseClick(int mWeigetId);

}
