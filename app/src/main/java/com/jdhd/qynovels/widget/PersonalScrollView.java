package com.jdhd.qynovels.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;

public class PersonalScrollView extends ScrollView {
    private final String TAG = PersonalScrollView.class.getSimpleName();
    private View inner;// 孩子View
    private float touchY;// 点击时Y坐标
    private float deltaY;// Y轴滑动的距离
    private float initTouchY;// 初次点击的Y坐标
    private boolean shutTouch = false;// 是不是关闭ScrollView的滑动.
    private Rect normal = new Rect();// 矩形(这里只是个形式，只是用于判断是不是须要动画.)
    private boolean isMoveing = false;// 是不是开始挪动.
    private ImageView imageView;// 背景图控件.
    private View line_up;// 上线
    private int line_up_top;// 上线的top
    private int line_up_bottom;// 上线的bottom
    private int initTop, initBottom;// 初始高度
    private int current_Top, current_Bottom;// 拖动时时高度。
    private int lineUp_current_Top, lineUp_current_Bottom;// 上线
    private onTurnListener turnListener;
    OnScrollViewListener onScrollViewListener;
    boolean isTop = false;
    boolean isBottom = false;
    private boolean isScrolledToTop = true;// 初始化的时候设置一下值
    private boolean isScrolledToBottom = false;
    // 状态：上部，下部，默认
    private enum State {
        UP, DOWN, NOMAL
    };
    // 默认状态
    private State state = State.NOMAL;
    public void setTurnListener(onTurnListener turnListener) {
        this.turnListener = turnListener;
    }
    public void setLine_up(View line_up) {
        this.line_up = line_up;
    }
    // 注入背景图
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
    /***
     * 构造方法
     *
     * @param context
     * @param attrs
     */
    public PersonalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /***
     * 根据 XML 生成视图工作实现.该函数在生成视图的最后调用，在所有子视图添加完以后. 即使子类覆盖了 onFinishInflate
     * 方法，也应当调用父类的方法，使该方法得以执行.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            inner = getChildAt(0);
        }
    }
    /** touch 事件处理 **/
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner != null) {
            commOnTouchEvent(ev);
        }
        // ture：禁止控件本身的滑动.
        if (shutTouch)
            return true;
        else
            return super.onTouchEvent(ev);
    }
    /***
     * 触摸事件
     *
     * @param ev
     */
    public void commOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                initTouchY = ev.getY();
                current_Top = initTop = imageView.getTop();
                current_Bottom = initBottom = imageView.getBottom();
                if (line_up_top == 0) {
                    lineUp_current_Top = line_up_top = line_up.getTop();
                    lineUp_current_Bottom = line_up_bottom = line_up.getBottom();
                }
                break;
            case MotionEvent.ACTION_UP:
                /** 回缩动画 **/
                if (isNeedAnimation()) {
                    animation();
                }
                if (getScrollY() == 0) {
                    state = State.NOMAL;
                }
                isMoveing = false;
                touchY = 0;
                shutTouch = false;
                break;
            /***
             * 消除出第一次挪动计算，因为第一次无法得知deltaY的高度， 然而我们也要进行初始化，就是第一次挪动的时候让滑动距离归0.
             * 以后记载精确了就正常执行.
             */
            case MotionEvent.ACTION_MOVE:
                touchY = ev.getY();
                Log.e("asd",touchY+"");
                deltaY = touchY - initTouchY;// 滑动距离
                /** 对于初次Touch操作要判断方位：UP OR DOWN **/
                if (deltaY < 0 && state == state.NOMAL) {
                    state = State.UP;
                } else if (deltaY > 0 && state == state.NOMAL) {
                    state = State.DOWN;
                }
                if (state == State.UP) {
                    deltaY = deltaY < 0 ? deltaY : 0;
                    isMoveing = false;
                    shutTouch = false;
                    /** line_up **/
                    lineUp_current_Top = (int) (line_up_top - getScrollY());
                    lineUp_current_Bottom = (int) (line_up_bottom - getScrollY());
                    Log.e(TAG, "top=" + getScrollY());
                    line_up.layout(line_up.getLeft(), lineUp_current_Top,
                            line_up.getRight(), lineUp_current_Bottom);
                } else if (state == state.DOWN) {
                    if (getScrollY() <= deltaY) {
                        shutTouch = true;
                        isMoveing = true;
                    }
                    deltaY = deltaY < 0 ? 0 : deltaY;
                }
                if (isMoveing) {
                    // 初始化头部矩形
                    if (normal.isEmpty()) {
                        // 保存正常的布局位置
                        normal.set(inner.getLeft(), inner.getTop(),
                                inner.getRight(), inner.getBottom());
                    }
                    // 挪动布局(手势挪动的1/3)
                    float inner_move_H = deltaY / 5;
                    inner.layout(normal.left, (int) (normal.top + inner_move_H),
                            normal.right, (int) (normal.bottom + inner_move_H));
                    /** image_bg **/
                    float image_move_H = deltaY / 10;
                    current_Top = (int) (initTop + image_move_H);
                    current_Bottom = (int) (initBottom + image_move_H);
                    imageView.layout(imageView.getLeft(), current_Top,
                            imageView.getRight(), current_Bottom);
                    /** line_up **/
                    lineUp_current_Top = (int) (line_up_top + inner_move_H);
                    lineUp_current_Bottom = (int) (line_up_bottom + inner_move_H);
                    line_up.layout(line_up.getLeft(), lineUp_current_Top,
                            line_up.getRight(), lineUp_current_Bottom);
                }
                break;
            default:
                break;
        }
    }
    /***
     * 回缩动画
     */
    public void animation() {
        TranslateAnimation image_Anim = new TranslateAnimation(0, 0,
                Math.abs(initTop - current_Top), 0);
        image_Anim.setDuration(200);
        imageView.startAnimation(image_Anim);
        imageView.layout(imageView.getLeft(), (int) initTop,
                imageView.getRight(), (int) initBottom);
        // 开启挪动动画
        TranslateAnimation inner_Anim = new TranslateAnimation(0, 0,
                inner.getTop(), normal.top);
        inner_Anim.setDuration(200);
        inner.startAnimation(inner_Anim);
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);
        /** line_up **/
        TranslateAnimation line_up_Anim = new TranslateAnimation(0, 0,
                Math.abs(line_up_top - lineUp_current_Top), 0);
        line_up_Anim.setDuration(200);
        line_up.startAnimation(line_up_Anim);
        line_up.layout(line_up.getLeft(), line_up_top, line_up.getRight(),
                line_up_bottom);
        normal.setEmpty();
        /** 动画执行 **/
        if (current_Top > initTop + 50 && turnListener != null)
            turnListener.onTurn();
    }
    /** 是不是须要开启动画 **/
    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }
    /***
     * 执行翻转
     *
     * @author jia
     *
     */
    public interface onTurnListener {
        /** 必须到达必定水平才执行 **/
        void onTurn();
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    isTop = false;
                    break;
                case 1:
                    isBottom = false;
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 自定义滑动事件的监听接口
     */
    public interface OnScrollViewListener {

        void onTop(); // 滑动到顶部


        void onBottom();// 滑动到底部
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollViewListener != null) {
            if (getScrollY() == 0) {
                if (!isTop) {
                    isTop = true;
                    mHandler.sendEmptyMessageDelayed(0, 200);
                    onScrollViewListener.onTop();
                }
            } else {
                View contentView = getChildAt(0);
                if (contentView != null && contentView.getMeasuredHeight() == (getScrollY() + getHeight())) {
                    if (!isBottom) {
                        isBottom = true;
                        mHandler.sendEmptyMessageDelayed(1, 200);
                        onScrollViewListener.onBottom();
                    }

                }
            }
        }
        if (android.os.Build.VERSION.SDK_INT < 9) {  // API 9及之后走onOverScrolled方法监听
            if (getScrollY() == 0) {    // 小心踩坑1: 这里不能是getScrollY() <= 0
                isScrolledToTop = true;
                isScrolledToBottom = false;
            } else if (getScrollY() + getHeight() - getPaddingTop()-getPaddingBottom() == getChildAt(0).getHeight()) {
                // 小心踩坑2: 这里不能是 >=
                // 小心踩坑3（可能忽视的细节2）：这里最容易忽视的就是ScrollView上下的padding　
                isScrolledToBottom = true;
                isScrolledToTop = false;
            } else {
                isScrolledToTop = false;
                isScrolledToBottom = false;
            }
            notifyScrollChangedListeners();
        }

    }


    public void setOnScrollViewListener(OnScrollViewListener onScrollViewListener) {
        this.onScrollViewListener = onScrollViewListener;
    }
    private ISmartScrollChangedListener mSmartScrollChangedListener;

    /** 定义监听接口 */
    public interface ISmartScrollChangedListener {
        void onScrolledToBottom();
        void onScrolledToTop();
    }

    public void setScanScrollChangedListener(ISmartScrollChangedListener smartScrollChangedListener) {
        mSmartScrollChangedListener = smartScrollChangedListener;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY == 0) {
            isScrolledToTop = clampedY;
            isScrolledToBottom = false;
        } else {
            isScrolledToTop = false;
            isScrolledToBottom = clampedY;
        }
        notifyScrollChangedListeners();
    }



    private void notifyScrollChangedListeners() {
        if (isScrolledToTop) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToTop();
            }
        } else if (isScrolledToBottom) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToBottom();
            }
        }
    }

    public boolean isScrolledToTop() {
        return isScrolledToTop;
    }

    public boolean isScrolledToBottom() {
        return isScrolledToBottom;
    }


}
