package com.jdhd.qynovels.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.bookcase.CaseBean;
import com.jdhd.qynovels.module.bookcase.DelBookRackBean;
import com.jdhd.qynovels.persenter.impl.bookcase.ICasePresenterImpl;
import com.jdhd.qynovels.persenter.impl.bookcase.IDelBookRankPresenterImpl;
import com.jdhd.qynovels.ui.activity.MainActivity;
import com.jdhd.qynovels.ui.fragment.CaseFragment;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.view.bookcase.ICaseView;
import com.jdhd.qynovels.view.bookcase.IDelBookRankView;

import java.util.ArrayList;
import java.util.List;

public class CaseContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IDelBookRankView, ICaseView {
    private static final int TYPE_LIST=0;
    private static final int TYPE_FOOT=1;
    private IDelBookRankPresenterImpl delBookRankPresenter;
    private DbUtils dbUtils;
    private SQLiteDatabase database;
    private ICasePresenterImpl casePresenter;
    private String token;
    private String islogin;
    /**
     * 是否显示ｃｈｅｃｋｂｏｘ
     */
    private boolean isShowCheck;
    /**
     * 记录选中的ｃｈｅｃｋｂｏｘ
     */
    private List<String> checkList=new ArrayList<>();
    private ListAdapter adapter;
    private Activity context;
    private FragmentActivity activity;
    private List<CaseBean.DataBean.ListBean> list=new ArrayList<>();
    public void refresh(List<CaseBean.DataBean.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    private List<TTNativeExpressAd> feedlist=new ArrayList<>();
    public void refreshfeed(List<TTNativeExpressAd> feedlist){
        this.feedlist=feedlist;
        notifyDataSetChanged();
    }

    public CaseContentAdapter(Activity context, FragmentActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        delBookRankPresenter=new IDelBookRankPresenterImpl(this,context);
        casePresenter=new ICasePresenterImpl(this,context);
        SharedPreferences sharedPreferences=context.getSharedPreferences("token",Context.MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        islogin=sharedPreferences.getString("islogin","");
        RecyclerView.ViewHolder viewHolder=null;
        dbUtils=new DbUtils(context);
        if(viewType==TYPE_LIST){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
            viewHolder=new ListViewHolder(view);
        }
        else if(viewType==TYPE_FOOT){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot, parent, false);
            viewHolder=new FootViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ListViewHolder){
           GridLayoutManager manager=new GridLayoutManager(context, 3);
            ((ListViewHolder) holder).rv.setLayoutManager(manager);
            ((ListViewHolder) holder).rv.setOnTouchListener(
                    new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (CaseFragment.sr.isRefreshing()) {
                                return true;
                            }
                            else {
                                return false;
                            }
                        }
                    });
            adapter=new ListAdapter(context,activity);
            adapter.refresh(list);
            Log.e("listname",list.size()+"---");
            adapter.refreshfeed(feedlist);
            initListener();
            ((ListViewHolder) holder).rv.setAdapter(adapter);
        }
        else if(holder instanceof FootViewHolder){
           ((FootViewHolder) holder).xh.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   toFragment(1);
                   MainActivity.rb_shop.setChecked(true);
               }
           });
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_LIST;
        }
        else if(position==1){
            return TYPE_FOOT;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onSuccess(DelBookRackBean delBookRackBean) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(delBookRackBean.getCode()==200){
                    for(int i=0;i<checkList.size();i++){
                        String name=list.get(Integer.parseInt(checkList.get(i))).getName();
                        list.remove(Integer.parseInt(checkList.get(i)));
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                database.execSQL("delete from usercase where user='user' and name='"+name+"'");
                            }
                        }).start();
                    }
                    MainActivity.ll.setVisibility(View.GONE);
                    adapter.setShowCheckBox(false);
                    adapter.setType(0);
                    checkList.clear();
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public void onAddError(String error) {
       Log.e("delbookerror",error);
    }

    @Override
    public void onSuccess(CaseBean caseBean) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.refresh(caseBean.getData().getList());
                for(int i=0;i<caseBean.getData().getList().size();i++){
                    Log.e("casebeanlist",caseBean.getData().getList().get(i).getName()+"--");
                }
            }
        });

    }

    @Override
    public void onError(String error) {

    }

    class ListViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView rv;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            rv=itemView.findViewById(R.id.rv);
        }
    }
    class FootViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout xh;
        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
            xh=itemView.findViewById(R.id.xh);
        }
    }
    private void toFragment(final int i) {
        final MainActivity mainActivity = (MainActivity) activity;
        mainActivity.setFragment2Fragment(new MainActivity.Fragment2Fragment() {
            @Override
            public void gotoFragment(ViewPager viewPager) {
                viewPager.setCurrentItem(i);
            }
        });
        mainActivity.forSkip();
    }

    /**
     * 点击监听
     */
    private void initListener() {

        MainActivity.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database=dbUtils.getWritableDatabase();
                //用户
                if(islogin.equals("1")){
                   if(checkList.size()==1){
                       delBookRankPresenter.setType(10);
                       delBookRankPresenter.setId(list.get(Integer.parseInt(checkList.get(0))).getId()+"");
                       delBookRankPresenter.loadData();
                   }
                   else{
                       delBookRankPresenter.setType(20);
                       StringBuffer id=new StringBuffer();
                       for(int i=0;i<checkList.size();i++){
                           if(i==checkList.size()-1){
                               id.append(list.get(Integer.parseInt(checkList.get(i))).getId());
                           }
                           else{
                               id.append(list.get(Integer.parseInt(checkList.get(i))).getId()+",");
                           }
                       }
                       delBookRankPresenter.setId(id.toString());
                       delBookRankPresenter.loadData();
                   }
                }
                //游客
                else{
                   for(int i=0;i<checkList.size();i++){
                       Log.e("index",checkList.get(i));
                       String name=list.get(Integer.parseInt(checkList.get(i))).getName();
                       list.remove(Integer.parseInt(checkList.get(i)));
                       //adapter.notifyItemRemoved(Integer.parseInt(checkList.get(i)));

                       Log.e("bookname",name+"----");
                       new Thread(new Runnable() {
                           @Override
                           public void run() {
                               database.execSQL("delete from usercase where user='visitor' and name='"+name+"'");
                           }
                       }).start();
                   }
                }
                MainActivity.ll.setVisibility(View.GONE);
                adapter.setShowCheckBox(false);
                adapter.setType(0);
                checkList.clear();
                adapter.notifyDataSetChanged();
            }
        });
        MainActivity.cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.ll.setVisibility(View.GONE);
                adapter.setShowCheckBox(false);
                adapter.setType(0);
                adapter.notifyDataSetChanged();

            }
        });
        //ａｄａｐｔｅｒ中定义的监听事件　可以根据isShowCheck判断当前状态，设置点击Ｉｔｅｍ之后是查看大图（未实现　跳到下一个Ａｃｔｉｖｉｔｙ即可）还是选中ｃｈｅｃｋｂｏｘ*/
        adapter.setOnItemClickListener(new ListAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                Log.e("clickpos",pos+"");
                if (checkList.contains(String.valueOf(pos))) {
                    checkList.remove(String.valueOf(pos));
                    MainActivity.delete.setText("删除("+checkList.size()+")");

                } else {
                    checkList.add(String.valueOf(pos));
                    MainActivity.delete.setText("删除("+checkList.size()+")");
                }

            }
            @Override
            public boolean onLongClick(View view, int pos) {
                Log.e("longpos",pos+"");
                isShowCheck=false;
                if (isShowCheck) {
                    MainActivity.ll.setVisibility(View.GONE);
                    adapter.setShowCheckBox(false);
                    checkList.clear();
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.setShowCheckBox(true);
                    adapter.notifyDataSetChanged();
                    MainActivity.ll.setVisibility(View.VISIBLE);
                }
                isShowCheck = !isShowCheck;
                return false;
            }

        });

    }
}
