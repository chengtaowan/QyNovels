package com.jdhd.qynovels.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.DownloadStatusController;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTFeedAd;
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
        if(list.size()>3){
            shownum=list.size()/3;
        }
        else{
           shownum=1;
        }
        if(feedlist.size()!=0){
            newfeedlist.clear();
            for(int i=0;i<30;i++){
                int num = (int) (Math.random() * (2 - 0)) + 0;
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
            for(int i=0;i<listsize;i++){
                if(list.size()==0||list.size()==1||list.size()==2||list.size()==3){
                    list.add(new CaseBean.DataBean.ListBean());
                    break;
                }
                else if(list.size()>3&&i%3==0&&i!=0){
                    Log.e("123","1234"+"--"+list.size()+"--"+i);
                    list.add(i,new CaseBean.DataBean.ListBean());
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
                        GlideUrl url = DeviceInfoUtils.getUrl(list.get(position).getImage());
                        Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new RoundedCorners(MyApp.raduis))).into(viewHolder.book);
                    }
                    viewHolder.name.setText(list.get(position).getName());
                    viewHolder.zj.setText("读到："+list.get(position).getReadContent());
                    viewHolder.ll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
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
                            context.startActivity(intent);
                        }
                    });
                    viewHolder.del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            database=dbUtils.getWritableDatabase();
                            database.execSQL("delete from usercase where name='"+list.get(position).getName()+"'");
                            delBookRankPresenter.setId(list.get(position).getId());
                            delBookRankPresenter.loadData();
                            list.remove(position);
                            notifyDataSetChanged();
                            viewHolder.sml.quickClose();
                        }
                    });
                }
            }
            else{
                if(newfeedlist.size()!=0){
                    Log.e("newtitle",newfeedlist.get(position).getTitle());
                    Glide.with(context).load(newfeedlist.get(position).getImageList().get(0).getImageUrl())
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(MyApp.raduis)))
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                    viewHolder.book.setImageDrawable(resource);
                                }
                            });
                    viewHolder.name.setText(newfeedlist.get(position).getTitle());
                    List<View> viewList=new ArrayList<>();
                    viewList.add(viewHolder.itemView);
                    newfeedlist.get(position).registerViewForInteraction((ViewGroup)viewHolder.itemView,viewList,viewList,new TTNativeAd.AdInteractionListener() {
                        @Override
                        public void onAdClicked(View view, TTNativeAd ad) {
                            if (ad != null) {
                                //TToast.show(mContext, "广告" + ad.getTitle() + "被点击");
                            }
                        }

                        @Override
                        public void onAdCreativeClick(View view, TTNativeAd ad) {
                            if (ad != null) {
                               // TToast.show(mContext, "广告" + ad.getTitle() + "被创意按钮被点击");
                            }
                        }

                        @Override
                        public void onAdShow(TTNativeAd ad) {
                            if (ad != null) {
                               // TToast.show(mContext, "广告" + ad.getTitle() + "展示");
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
                Log.e("newtitle",newfeedlist.get(position).getTitle());
                Glide.with(context).load(newfeedlist.get(position).getImageList().get(0).getImageUrl())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(MyApp.raduis)))
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                viewHolder.book.setImageDrawable(resource);
                            }
                        });
                viewHolder.name.setText(newfeedlist.get(position).getTitle());
                viewHolder.zj.setText(newfeedlist.get(position).getDescription());
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
                viewHolder.del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        list.remove(position);
                        list.add(new CaseBean.DataBean.ListBean());
                        int random=(int) (Math.random() * (30 - 0)) + 0;
                        Glide.with(context).load(newfeedlist.get(random).getImageList().get(0).getImageUrl())
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(MyApp.raduis)))
                                .into(new SimpleTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                        viewHolder.book.setImageDrawable(resource);
                                    }
                                });
                        viewHolder.name.setText(newfeedlist.get(random).getTitle());
                        viewHolder.zj.setText(newfeedlist.get(random).getDescription());
                        notifyDataSetChanged();
                        viewHolder.sml.quickClose();
                    }
                });
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
        if(list.size()==1){
            return TYPE_FEED;
        }
        if(list.size()==2&&position==1){
            return TYPE_FEED;
        }
        else if(list.size()==3&&position==2){
            return TYPE_FEED;
        }
        else if(list.size()>=4&&position%3==0&&position!=0){
            return TYPE_FEED;
        }
        else{
            return TYPE_LIST;
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
        private TextView name,zj,yd,tg,xh;
        private Button del;
        private SwipeMenuLayout sml;
        private LinearLayout ll;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.case_book);
            name=itemView.findViewById(R.id.case_name);
            zj=itemView.findViewById(R.id.case_zj);
            tg=itemView.findViewById(R.id.case_tg);
            xh=itemView.findViewById(R.id.case_xh);
            del=itemView.findViewById(R.id.case_del);
            sml=itemView.findViewById(R.id.case_sml);
            ll=itemView.findViewById(R.id.case_ll);
        }
    }

    class FeedViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name,zj,yd,tg,xh;
        private Button del;
        private SwipeMenuLayout sml;
        private LinearLayout ll;
        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.case_book);
            name=itemView.findViewById(R.id.case_name);
            zj=itemView.findViewById(R.id.case_zj);
            tg=itemView.findViewById(R.id.case_tg);
            xh=itemView.findViewById(R.id.case_xh);
            del=itemView.findViewById(R.id.case_del);
            sml=itemView.findViewById(R.id.case_sml);
            ll=itemView.findViewById(R.id.case_ll);
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


}
