package com.jdhd.qynovels.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.DownloadStatusController;
import com.bytedance.sdk.openadsdk.FilterWord;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTDislikeDialogAbstract;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.bytedance.sdk.openadsdk.TTInteractionAd;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.glong.reader.activities.ExtendReaderActivity;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.BookListBean;
import com.jdhd.qynovels.module.bookcase.CaseBean;
import com.jdhd.qynovels.module.bookcase.DelBookRackBean;
import com.jdhd.qynovels.persenter.impl.bookcase.IBookListPresenterImpl;
import com.jdhd.qynovels.persenter.impl.bookcase.IDelBookRankPresenterImpl;
import com.jdhd.qynovels.ui.activity.MainActivity;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.utils.TransformationUtils;
import com.jdhd.qynovels.view.bookcase.IBookListView;
import com.jdhd.qynovels.view.bookcase.IDelBookRankView;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import rxhttp.wrapper.param.RequestBuilder;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IDelBookRankView {

    private Context context;
    private FragmentActivity activity;
    private List<CaseBean.DataBean.ListBean> list=new ArrayList<>();
    private DbUtils dbUtils;
    private SQLiteDatabase database;
    private IBookListPresenterImpl bookListPresenter;
    private String token="";
    private String time;
    private BookListBean bookBean=new BookListBean();
    public static final int TYPE_FEED=1;
    public static final int TYPE_LIST=0;
    private int shownum;
    private int getnum;
    private int count=0;
    private int listsize;
    private int type=0;

    public void setType(int type) {
        this.type = type;
    }

    /**
     * 控制是否显示Checkbox
     */
    private boolean showCheckBox;
    public boolean isShowCheckBox() {
        return showCheckBox;
    }
    public void setShowCheckBox(boolean showCheckBox) {
        this.showCheckBox = showCheckBox;
    }
    /**
     * 防止Checkbox错乱 做setTag  getTag操作
     */
    private SparseBooleanArray mCheckStates = new SparseBooleanArray();

    private List<TTFeedAd> feedlist=new ArrayList<>();
    private List<TTFeedAd> newfeedlist=new ArrayList<>();
    public void refreshfeed(List<TTFeedAd> feedlist){
        this.feedlist=feedlist;
        notifyDataSetChanged();
    }


    private IDelBookRankPresenterImpl delBookRankPresenter;
    public void refresh(List<CaseBean.DataBean.ListBean> list){
        this.list.clear();
        this.list=list;
        notifyDataSetChanged();
    }

    public ListAdapter(Context context, FragmentActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(list.size()>5){
            shownum=list.size()/5;
        }
        else{
           shownum=1;
        }
        if(feedlist.size()>0){
            newfeedlist.clear();
            for(int i=0;i<30;i++){
                int num = (int) (Math.random() * (feedlist.size() - 0)) + 0;
                newfeedlist.add(feedlist.get(num));
            }
        }
        if(count==0){
            for(int i=0;i<list.size();i++){
                Log.e("title",list.get(i).getName()+"000");
                if(list.get(i).getName()==null){
                    list.remove(i);

                }
                listsize=list.size();
            }
            if(feedlist.size()!=0){
                for(int i=0;i<listsize;i++){
                    if(list.size()==0||list.size()==1||list.size()==2||list.size()==3||list.size()==4||list.size()==5){
                        list.add(new CaseBean.DataBean.ListBean());
                        break;
                    }
                    else if(list.size()>5&&i==5){
                        Log.e("123","1234"+"--"+list.size()+"--"+i);
                        list.add(i,new CaseBean.DataBean.ListBean());
                    }
                    else if(list.size()>5&&i%8==0&&i>5){
                        Log.e("123","1234"+"--"+list.size()+"--"+i);
                        list.add(i,new CaseBean.DataBean.ListBean());
                    }
                    else if(list.size()>5&&i%11==0&&i>8){
                        Log.e("123","1234"+"--"+list.size()+"--"+i);
                        list.add(i,new CaseBean.DataBean.ListBean());
                    }
                    else if(list.size()>5&&i%14==0&&i>11){
                        Log.e("123","1234"+"--"+list.size()+"--"+i);
                        list.add(i,new CaseBean.DataBean.ListBean());
                    }
                    else if(list.size()>5&&i%17==0&&i>14){
                        Log.e("123","1234"+"--"+list.size()+"--"+i);
                        list.add(i,new CaseBean.DataBean.ListBean());
                    }
                    else if(list.size()>5&&i%20==0&&i>17){
                        Log.e("123","1234"+"--"+list.size()+"--"+i);
                        list.add(i,new CaseBean.DataBean.ListBean());
                    }
                }
            }

        }
        count=1;
        SharedPreferences preferences=context.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        time= DeviceInfoUtils.getTime()+"";
        RecyclerView.ViewHolder viewHolder=null;
        delBookRankPresenter=new IDelBookRankPresenterImpl(this,context);
        if(viewType==TYPE_FEED){
            View view= LayoutInflater.from(context).inflate(R.layout.item_case,parent,false);
            viewHolder=new FeedViewHolder(view);
        }
        else if(viewType==TYPE_LIST){
            View view= LayoutInflater.from(context).inflate(R.layout.item_case,parent,false);
            viewHolder=new ListViewHolder(view);
        }

        dbUtils=new DbUtils(context);
        Log.e("feedlist",feedlist.size()+"--");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof ListViewHolder){
            Log.e("list",list.size()+"--");
            ListViewHolder viewHolder= (ListViewHolder) holder;
            if(list.size()!=0){
                if(list.get(position).getName()!=null){
                    if(list.get(position).getImage()!=null){
                        Log.e("images",list.get(position).getImage());
                        GlideUrl url = DeviceInfoUtils.getUrl(list.get(position).getImage());
                        Glide.with(context)
                                .load(url)
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(MyApp.raduis)))
                                .apply(new RequestOptions().error(R.mipmap.book_100))
                                .apply(new RequestOptions().placeholder(R.mipmap.book_100))
                                .into(viewHolder.book);
                    }
                    viewHolder.name.setText(list.get(position).getName());
                    if(list.get(position).getReadContent().equals("")){
                        viewHolder.zj.setText("未开始");
                    }
                    else{
                        viewHolder.zj.setText("读到："+list.get(position).getReadContent());
                    }
                    //防止复用导致的checkbox显示错乱
                    viewHolder.select.setTag(position);
                    //防止复用导致的checkbox显示错乱
                    viewHolder.select.setTag(position);
                    //判断当前checkbox的状态
                    if (showCheckBox) {
                        viewHolder.select.setVisibility(View.VISIBLE);
                        //防止显示错乱
                        viewHolder.select.setChecked(mCheckStates.get(position, false));
                    } else {
                        viewHolder.select.setVisibility(View.GONE);
                        //取消掉Checkbox后不再保存当前选择的状态
                        viewHolder.select.setChecked(false);
                        mCheckStates.clear();
                    }
                    //对checkbox的监听 保存选择状态 防止checkbox显示错乱
                    viewHolder.select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            onItemClickListener.onClick(compoundButton, position);
                            int pos = (int) compoundButton.getTag();
                            if (b) {
                                mCheckStates.put(pos, true);
                            } else {
                                mCheckStates.delete(pos);
                            }

                        }
                    });
                    //长按监听
                    viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            onItemClickListener.onLongClick(view, position);
                            type=1;
                            return false;
                        }
                    });

                    viewHolder.ll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(type==1){
                                //判断当前checkbox的状态
                                if (showCheckBox) {
                                    viewHolder.select.setVisibility(View.VISIBLE);
                                    //防止显示错乱
                                    viewHolder.select.setChecked(mCheckStates.get(position, false));
                                } else {
                                    viewHolder.select.setVisibility(View.GONE);
                                    //取消掉Checkbox后不再保存当前选择的状态
                                    viewHolder.select.setChecked(false);
                                    mCheckStates.clear();
                                }
                            }
                            else{
                                bookListPresenter=new IBookListPresenterImpl(new IBookListView() {
                                    @Override
                                    public void onSuccess(BookListBean bookListBean) {
                                        bookBean=bookListBean;
                                        database=dbUtils.getWritableDatabase();
                                        if(!token.equals("")){
                                            database.execSQL("delete from readhistory where user='user'and name='"+list.get(position).getName()+"'");
                                            database.execSQL("insert into readhistory(user,name,image,author,readContent,readStatus,bookStatus,bookid,backlistPercent,lastTime,backlistId)" +
                                                    "values('user'," +
                                                    "'"+list.get(position).getName()+"'," +
                                                    "'"+list.get(position).getImage()+"'," +
                                                    "'"+list.get(position).getAuthor()+"'," +
                                                    "'"+bookListBean.getData().getList().get(0).getName()+"'," +
                                                    "10," + "10," + "'"+list.get(position).getBookId()+"'," +
                                                    "0," + "'"+DeviceInfoUtils.changeData(time)+"日"+"'," + "'')");

                                        }
                                        else{
                                            database.execSQL("delete from readhistory where user='visitor'and name='"+list.get(position).getName()+"'");
                                            database.execSQL("insert into readhistory(user,name,image,author,readContent,readStatus,bookStatus,bookid,backlistPercent,lastTime,backlistId)" +
                                                    "values('visitor'," +
                                                    "'"+list.get(position).getName()+"'," +
                                                    "'"+list.get(position).getImage()+"'," +
                                                    "'"+list.get(position).getAuthor()+"'," +
                                                    "'"+bookListBean.getData().getList().get(0).getName()+"'," +
                                                    "10," + "10," + "'"+list.get(position).getBookId()+"'," +
                                                    "0," + "'"+DeviceInfoUtils.changeData(time)+"日"+"'," + "'')");
                                        }
                                    }

                                    @Override
                                    public void onError(String error) {
                                        Log.e("booklisterror",error);
                                    }
                                }, context);
                                bookListPresenter.setId(list.get(position).getBookId());
                                bookListPresenter.loadData();
                                Intent intent=new Intent(context, ExtendReaderActivity.class);
                                intent.putExtra("token",token);
                                intent.putExtra("id",list.get(position).getBookId());
                                intent.putExtra("img",list.get(position).getImage());
                                intent.putExtra("name",list.get(position).getName());
                                intent.putExtra("author",list.get(position).getAuthor());
                                context.startActivity(intent);
                            }

                        }
                    });

                }
            }

        }
        else if(holder instanceof FeedViewHolder){
            Log.e("position",position+"--");
            FeedViewHolder viewHolder= (FeedViewHolder) holder;
            if(newfeedlist.size()!=0){
                //Log.e("newtitle",newfeedlist.get(position).getTitle());
                Glide.with(context)
                        .load(newfeedlist.get(position).getImageList().get(0).getImageUrl())
                        .apply(new RequestOptions().error(R.mipmap.book_100))
                        .apply(new RequestOptions().placeholder(R.mipmap.book_100))
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(MyApp.raduis)))
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                viewHolder.book.setImageDrawable(resource);
                            }
                        });
                viewHolder.name.setText(newfeedlist.get(position).getDescription());
                viewHolder.zj.setText(newfeedlist.get(position).getTitle());
                //防止复用导致的checkbox显示错乱
                viewHolder.select.setTag(position);
                //判断当前checkbox的状态
                if (showCheckBox) {
                    viewHolder.select.setVisibility(View.VISIBLE);
                    //防止显示错乱
                    viewHolder.select.setChecked(mCheckStates.get(position, false));
                } else {
                    viewHolder.select.setVisibility(View.GONE);
                    //取消掉Checkbox后不再保存当前选择的状态
                    viewHolder.select.setChecked(false);
                    mCheckStates.clear();
                }

                //长按监听
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return onItemClickListener.onLongClick(view, position);
                    }
                });
                //对checkbox的监听 保存选择状态 防止checkbox显示错乱
                viewHolder.select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        onItemClickListener.onClick(compoundButton, position);
                        int pos = (int) compoundButton.getTag();
                        if (b) {
                            mCheckStates.put(pos, true);
                        } else {
                            mCheckStates.delete(pos);
                        }

                    }
                });
                newfeedlist.get(position).setVideoAdListener(new TTFeedAd.VideoAdListener() {
                    @Override
                    public void onVideoLoad(TTFeedAd ttFeedAd) {

                    }

                    @Override
                    public void onVideoError(int i, int i1) {

                    }

                    @Override
                    public void onVideoAdStartPlay(TTFeedAd ttFeedAd) {
                      //Toast.makeText(context,"播放",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVideoAdPaused(TTFeedAd ttFeedAd) {

                    }

                    @Override
                    public void onVideoAdContinuePlay(TTFeedAd ttFeedAd) {

                    }
                });
                bindData(viewHolder,newfeedlist.get(position));
            }
        }


    }

    @Override
    public int getItemCount() {
        Log.e("num",list.size()+"--");

            return list.size();


    }

    @Override
    public int getItemViewType(int position) {
        if(feedlist.size()==0){
            return TYPE_LIST;
        }
        else{
            if(list.size()==1){
                return TYPE_FEED;
            }
            if(list.size()==2&&position==1){
                return TYPE_FEED;
            }
            else if(list.size()==3&&position==2){
                return TYPE_FEED;
            }
            else if(list.size()==4&&position==3){
                return TYPE_FEED;
            }
            else if(list.size()==5&&position==4){
                return TYPE_FEED;
            }
            else if(list.size()>5&&position==5){
                return TYPE_FEED;
            }
            else if(list.size()>5&&position%8==0&&position>5){
                return TYPE_FEED;
            }
            else if(list.size()>5&&position%11==0&&position>8){
                return TYPE_FEED;
            }
            else if(list.size()>5&&position%14==0&&position>11){
                return TYPE_FEED;
            }
            else if(list.size()>5&&position%17==0&&position>14){
                return TYPE_FEED;
            }
            else if(list.size()>5&&position%20==0&&position>17){
                return TYPE_FEED;
            }
            else{
                return TYPE_LIST;
            }
        }


    }

    @Override
    public void onSuccess(DelBookRackBean delBookRackBean) {
        Log.e("del","删除成功");


    }

    @Override
    public void onAddError(String error) {
       Log.e("delbookerror",error);
    }

    class ListViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private CheckBox select;
        private TextView name,zj;
        private RelativeLayout ll;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.case_book);
            select=itemView.findViewById(R.id.select);
            name=itemView.findViewById(R.id.case_name);
            zj=itemView.findViewById(R.id.case_zj);
            ll=itemView.findViewById(R.id.ll);
        }
    }

    class FeedViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private CheckBox select;
        private TextView name,zj;
        private Button del;
        private SwipeMenuLayout sml;
        private RelativeLayout ll;
        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.case_book);
            select=itemView.findViewById(R.id.select);
            name=itemView.findViewById(R.id.case_name);
            zj=itemView.findViewById(R.id.case_zj);
            ll=itemView.findViewById(R.id.ll);
        }
    }

   private void bindData(final FeedViewHolder adViewHolder, TTFeedAd ad){
       List<View> clickViewList = new ArrayList<>();
       clickViewList.add(adViewHolder.ll);
       ad.registerViewForInteraction((ViewGroup) adViewHolder.itemView,clickViewList, clickViewList, new TTNativeAd.AdInteractionListener(){

           @Override
           public void onAdClicked(View view, TTNativeAd ttNativeAd) {
               //Toast.makeText(context,"被点击",Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onAdCreativeClick(View view, TTNativeAd ttNativeAd) {

           }

           @Override
           public void onAdShow(TTNativeAd ttNativeAd) {
              // Toast.makeText(context,"展示",Toast.LENGTH_SHORT).show();
           }
       });
       bindDownloadListener(adViewHolder, ad);
   }
    private Map<FeedViewHolder, TTAppDownloadListener> mTTAppDownloadListenerMap = new WeakHashMap<>();
    private void bindDownloadListener(final FeedViewHolder adViewHolder, TTFeedAd ad) {
        TTAppDownloadListener downloadListener = new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                if (!isValid()) {
                    return;
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                if (!isValid()) {
                    return;
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                if (!isValid()) {
                    return;
                }
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                if (!isValid()) {
                    return;
                }

            }

            @Override
            public void onInstalled(String fileName, String appName) {
                if (!isValid()) {
                    return;
                }

            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                if (!isValid()) {
                    return;
                }

            }

            @SuppressWarnings("BooleanMethodIsAlwaysInverted")
            private boolean isValid() {
                return mTTAppDownloadListenerMap.get(adViewHolder) == this;
            }
        };
        //一个ViewHolder对应一个downloadListener, isValid判断当前ViewHolder绑定的listener是不是自己
        ad.setDownloadListener(downloadListener); // 注册下载监听器
        mTTAppDownloadListenerMap.put(adViewHolder, downloadListener);
    }
    /**
     * 自己写接口，实现点击和长按监听
     */
    public interface onItemClickListener {
        void onClick(View view, int pos);

        boolean onLongClick(View view, int pos);
    }

    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(ListAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
