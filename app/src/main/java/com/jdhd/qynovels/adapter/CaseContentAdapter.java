package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.jdhd.qynovels.persenter.impl.bookcase.IDelBookRankPresenterImpl;
import com.jdhd.qynovels.ui.activity.MainActivity;
import com.jdhd.qynovels.ui.fragment.CaseFragment;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.view.bookcase.IDelBookRankView;

import java.util.ArrayList;
import java.util.List;

public class CaseContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IDelBookRankView {
    private static final int TYPE_LIST=0;
    private static final int TYPE_FOOT=1;
    private IDelBookRankPresenterImpl delBookRankPresenter;
    private DbUtils dbUtils;
    private SQLiteDatabase database;
    /**
     * 是否显示ｃｈｅｃｋｂｏｘ
     */
    private boolean isShowCheck;
    /**
     * 记录选中的ｃｈｅｃｋｂｏｘ
     */
    private List<String> checkList=new ArrayList<>();
    private ListAdapter adapter;
    private Context context;
    private FragmentActivity activity;
    private List<CaseBean.DataBean.ListBean> list=new ArrayList<>();
    public void refresh(List<CaseBean.DataBean.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    private List<TTFeedAd> feedlist=new ArrayList<>();
    public void refreshfeed(List<TTFeedAd> feedlist){
        this.feedlist=feedlist;
        notifyDataSetChanged();
    }

    public CaseContentAdapter(Context context, FragmentActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        delBookRankPresenter=new IDelBookRankPresenterImpl(this,context);
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
            adapter=new ListAdapter(context,activity);
            adapter.refresh(list);
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
                adapter.notifyDataSetChanged();
                Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAddError(String error) {
       Log.e("delbookerror",error);
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
                if(checkList.size()==1){
                    delBookRankPresenter.setType(10);
                    delBookRankPresenter.setId(list.get(Integer.parseInt(checkList.get(0))).getId()+"");
                    delBookRankPresenter.loadData();
                }
                else if(checkList.size()>1){
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
                database=dbUtils.getWritableDatabase();
                SharedPreferences sharedPreferences=context.getSharedPreferences("token",Context.MODE_PRIVATE);
                String token=sharedPreferences.getString("token","");
                for(int i=0;i<checkList.size();i++){
                    Log.e("checklist",checkList.get(i)+"");
                    if(token.equals("")){
                        database.execSQL("delete from usercase where user='visitor' and name='"+list.get(Integer.parseInt(checkList.get(i))).getName()+"'");
                    }
                    else{
                        database.execSQL("delete from usercase where user='user' and name='"+list.get(Integer.parseInt(checkList.get(i))).getName()+"'");
                    }
                    list.remove(Integer.parseInt(checkList.get(i)));
                }

                MainActivity.ll.setVisibility(View.GONE);
                adapter.setShowCheckBox(false);
                adapter.setType(0);
                adapter.notifyDataSetChanged();
               //Toast.makeText(context, checkList.toString(), Toast.LENGTH_SHORT).show();
                delBookRankPresenter.loadData();

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
                    adapter.notifyDataSetChanged();
                    checkList.clear();
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
