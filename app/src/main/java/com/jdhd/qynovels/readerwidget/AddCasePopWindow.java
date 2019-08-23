package com.jdhd.qynovels.readerwidget;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.bookcase.AddBookBean;
import com.jdhd.qynovels.module.bookcase.BookInfoBean;
import com.jdhd.qynovels.module.bookcase.BookListBean;
import com.jdhd.qynovels.persenter.impl.bookcase.IAddBookRankPresenterImpl;
import com.jdhd.qynovels.persenter.impl.bookcase.IBookInfoPresenterImpl;
import com.jdhd.qynovels.persenter.impl.bookcase.IBookListPresenterImpl;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.view.bookcase.IAddBookRankView;
import com.jdhd.qynovels.view.bookcase.IBookInfoView;
import com.jdhd.qynovels.view.bookcase.IBookListView;

import org.greenrobot.eventbus.EventBus;


public class AddCasePopWindow extends PopupWindow implements IAddBookRankView, IBookInfoView, IBookListView {
    private Activity context;
    private View.OnClickListener itemClick;
    private View view;
    private TextView add,notadd;
    private int type;
    private int id;
    private DbUtils dbUtils;
    private SQLiteDatabase database;
    private IAddBookRankPresenterImpl addBookRankPresenter;
    private IBookInfoPresenterImpl bookInfoPresenter;
    private BookInfoBean bookBean=new BookInfoBean();
    private IBookListPresenterImpl iBookListPresenter;
    private BookListBean listBean=new BookListBean();


    public AddCasePopWindow(Activity context, View.OnClickListener itemClick,int type,int id) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.item_addcase,null);
        dbUtils=new DbUtils(context);
        addBookRankPresenter=new IAddBookRankPresenterImpl(this,context);
        bookInfoPresenter=new IBookInfoPresenterImpl(this,context);
        iBookListPresenter=new IBookListPresenterImpl(this,context);
        iBookListPresenter.setId(id);
        iBookListPresenter.loadData();
        bookInfoPresenter.setId(id);
        bookInfoPresenter.loadData();
        this.context = context;
        this.itemClick = itemClick;
        this.type=type;
        this.id=id;
        initView();
        initPopWindow();

    }

    private void initView() {
        add=view.findViewById(R.id.add);
        notadd=view.findViewById(R.id.notadd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBookRankPresenter.setId(id);
                addBookRankPresenter.loadData();


            }
        });

        notadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });
    }

    private void initPopWindow() {
        this.setContentView(view);
        // 设置弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击()
        this.setFocusable(true);
        //this.setOutsideTouchable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00FFFFFF);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        backgroundAlpha(context, 0.6f);//0.0-1.0

    }
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    @Override
    public void onSuccess(AddBookBean addBookBean) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("addbookbean",addBookBean.getMsg()+"--"+addBookBean.getCode());
                if(addBookBean.getCode()==9005){
                    Toast.makeText(context,"加入书架",Toast.LENGTH_SHORT).show();
                    XqActivity.jr.setText("已加入书架");
                    XqActivity.jrsj.setClickable(false);
                    XqActivity.jr.setTextColor(Color.parseColor("#999999"));
                    XqActivity.icon.setImageResource(R.mipmap.xqy_jrsjon);
                    database=dbUtils.getWritableDatabase();
                    if(bookBean.getData().getBook()!=null&&listBean.getData().getList()!=null){
                        Log.e("addbookid",bookBean.getData().getBook().getBookId()+"==="+bookBean.getData().getBook().getName());
                        database.execSQL("delete from usercase where name='"+bookBean.getData().getBook().getName()+"'");
                        database.execSQL("insert into usercase(user,name,image,author,readContent,readStatus,bookStatus,bookid,backlistPercent,lastTime,backlistId) " +
                                "values('visitor'," +
                                "'"+bookBean.getData().getBook().getName()+"'," +
                                "'"+bookBean.getData().getBook().getImage()+"'," +
                                "'"+bookBean.getData().getBook().getAuthor()+"'," +
                                "'"+listBean.getData().getList().get(0).getName()+"'," +
                                "10,+10,+'"+bookBean.getData().getBook().getBookId()+"'," +
                                "0,'',+'"+bookBean.getData().getBook().getBacklistNum()+"')");
                    }
                    EventBus.getDefault().post(addBookBean);
                }
                else if(addBookBean.getCode()!=200){
                    Toast.makeText(context,addBookBean.getMsg(),Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"加入书架",Toast.LENGTH_SHORT).show();
                    XqActivity.jr.setText("已加入书架");
                    XqActivity.jrsj.setClickable(false);
                    XqActivity.jr.setTextColor(Color.parseColor("#999999"));
                    XqActivity.icon.setImageResource(R.mipmap.xqy_jrsjon);
                    database=dbUtils.getWritableDatabase();
                    EventBus.getDefault().post(addBookBean);
                    if(bookBean.getData().getBook()!=null&&listBean.getData().getList()!=null){
                        database.execSQL("insert into usercase(user,name,image,author,readContent,readStatus,bookStatus,bookid,backlistPercent,lastTime,backlistId) " +
                                "values('user'," +
                                "'"+bookBean.getData().getBook().getName()+"'," +
                                "'"+bookBean.getData().getBook().getImage()+"'," +
                                "'"+bookBean.getData().getBook().getAuthor()+"'," +
                                "'"+listBean.getData().getList().get(0).getName()+"'," +
                                "10,+10,+'"+bookBean.getData().getBook().getBookId()+"'," +
                                "0,'',+'"+bookBean.getData().getBook().getBacklistNum()+"')");
                    }

                }
                context.finish();

            }
        });
    }

    @Override
    public void onAddError(String error) {

    }

    @Override
    public void onBookinfoSuccess(BookInfoBean bookInfoBean) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bookBean=bookInfoBean;
            }
        });
    }

    @Override
    public void onBookinfoError(String error) {

    }

    @Override
    public void onSuccess(BookListBean bookListBean) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listBean=bookListBean;
            }
        });
    }

    @Override
    public void onError(String error) {

    }
}
