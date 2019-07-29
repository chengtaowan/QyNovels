package com.jdhd.qynovels.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTBannerAd;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.BookInfoBean;
import com.jdhd.qynovels.ui.activity.MainActivity;
import com.jdhd.qynovels.ui.activity.MuluActivity;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.utils.FastBlurUtil;
import com.jdhd.qynovels.widget.ExpandTextView;
import com.jdhd.qynovels.widget.RatingBar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class XqymAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements GfAdapter.onItemClick{
    private static final int TYEP_BOOK=0;
    private static final int TYEP_TOP=1;
    private static final int TYEP_CENTER=2;
    private static final int TYEP_BOTTOM=3;
    private Context context;
    private Activity activity;
    private TTAdNative mTTAdNative;
    private TTAdDislike mTTAdDislike;


    public XqymAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    private BookInfoBean.DataBean dataBean=new BookInfoBean.DataBean();
    private List<BookInfoBean.DataBean.ListBean> xlist=new ArrayList<>();
    public void refresh(BookInfoBean.DataBean dataBean){
        this.dataBean=dataBean;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //step2:创建TTAdNative对象，createAdNative(Context context) banner广告context需要传入Activity对象
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(context);
        RecyclerView.ViewHolder viewHolder=null;
        if(viewType==TYEP_BOOK){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xq, parent, false);
            viewHolder=new BookViewHolder(view);
        }
        else if(viewType==TYEP_TOP){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xqtop, parent, false);
            viewHolder=new TopViewHolder(view);
        }
        else if(viewType==TYEP_CENTER){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xqcenter, parent, false);
            viewHolder=new CenterViewHolder(view);
        }
        else if(viewType==TYEP_BOTTOM){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xqbottom, parent, false);
            viewHolder=new BottomViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       if(holder instanceof BookViewHolder){
           BookViewHolder viewHolder= (BookViewHolder) holder;
           Log.e("databean",(dataBean.getBook()==null)+"");
           if(dataBean.getBook()==null){
               return;
           }
           else {
              Log.e("bookbean",dataBean.getBook().toString());
           }
           new MyThread(dataBean.getBook().getImage(),viewHolder.bj).start();
           Glide.with(context).load(dataBean.getBook().getImage()).apply(RequestOptions.bitmapTransform(new RoundedCorners(MyApp.raduis))).into(viewHolder.book);
           //Glide.with(XqActivity.this).load(bookInfoBean.getData().getBook().getImage()).into(bj);
           //       scaledBitmap为目标图像，10是缩放的倍数（越大模糊效果越高）
           if(dataBean.getBook().getFinishStatus()==10){
               viewHolder.finishtype.setText("连载");
           }
           else{
               viewHolder.finishtype.setText("完结");
           }

           viewHolder.name.setText(dataBean.getBook().getName());
           viewHolder.num.setText(dataBean.getBook().getGrade()+"");
           viewHolder.author.setText(dataBean.getBook().getAuthor());
           viewHolder.zs.setText(dataBean.getBook().getNumber()+"字");
           BigDecimal bigDecimal=new BigDecimal(viewHolder.num.getText().toString());
           BigDecimal pf = bigDecimal.setScale(0, BigDecimal.ROUND_UP);
           int zs= (Integer.parseInt(pf.toString())/2);
           int ys=(Integer.parseInt(pf.toString())%2);
           viewHolder.start.setSelected(false);

           viewHolder.start.setStartTotalNumber(zs+ys);
           if(ys==0){
               viewHolder.start.setSelectedNumber(Float.parseFloat(zs+0.8+""));
           }
           else if(ys==1){
               viewHolder.start.setSelectedNumber(Float.parseFloat(zs+0.5+""));
           }

       }
       else if(holder instanceof TopViewHolder){
           TopViewHolder viewHolder= (TopViewHolder) holder;
           loadBannerAd(viewHolder.img,viewHolder.gg);
           if(dataBean.getBook()==null){
               return;
           }
           viewHolder.rq.setText(dataBean.getBook().getAttention()+"");
           viewHolder.zd.setText(dataBean.getBook().getReading()+"");
           viewHolder.bookdes.setText(dataBean.getBook().getIntro());
           viewHolder.classname.setText(dataBean.getBook().getClassName());
           String data = DeviceInfoUtils.changeData(dataBean.getBook().getUpdateTime() + "");
           viewHolder.time.setText(data);
           viewHolder.ml.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent=new Intent(context, MuluActivity.class);
                   intent.putExtra("id",dataBean.getBook().getBookId());
                   intent.putExtra("name",dataBean.getBook().getName());
                   context.startActivity(intent);
               }
           });
       }
       else if(holder instanceof CenterViewHolder){
           if(dataBean.getList()==null){
               return;
           }
           CenterViewHolder viewHolder= (CenterViewHolder) holder;
           LinearLayoutManager manager=new LinearLayoutManager(context);
           viewHolder.rv.setLayoutManager(manager);
           GfAdapter adapter=new GfAdapter(context,1,1);
           int[] ints = randomCommon(0, dataBean.getList().size() - 1, 3);
           for(int i=0;i<ints.length;i++){
               xlist.add(dataBean.getList().get(ints[i]));
           }
           adapter.refreshbooklist(xlist);
           viewHolder.rv.setAdapter(adapter);
           adapter.setOnItemClick(this);
           viewHolder.hyh.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int[] ints = randomCommon(0, dataBean.getList().size() - 1, 3);
                   xlist.clear();
                   for(int i=0;i<ints.length;i++){
                       xlist.add(dataBean.getList().get(ints[i]));
                   }
                   adapter.refreshbooklist(xlist);
                   adapter.setOnItemClick(new GfAdapter.onItemClick() {
                       @Override
                       public void onGfclick(int index) {
                           Intent intent=new Intent(context, XqActivity.class);
                           intent.putExtra("id",xlist.get(index).getBookId());
                           context.startActivity(intent);
                       }
                   });
               }
           });
       }
       else if(holder instanceof BottomViewHolder){
           BottomViewHolder viewHolder= (BottomViewHolder) holder;
       }
    }

    private void loadBannerAd(FrameLayout mBannerContainer,RelativeLayout relativeLayout) {
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("901121895") //广告位id
                .setSupportDeepLink(true)
                .setImageAcceptedSize(300, 110)
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadBannerAd(adSlot, new TTAdNative.BannerAdListener() {

            @Override
            public void onError(int code, String message) {
                Toast.makeText(context,message+"--"+code,Toast.LENGTH_SHORT).show();
                mBannerContainer.removeAllViews();
            }

            @Override
            public void onBannerAdLoad(final TTBannerAd ad) {
                if (ad == null) {
                    return;
                }
                View bannerView = ad.getBannerView();
                if (bannerView == null) {
                    return;
                }
                //设置轮播的时间间隔  间隔在30s到120秒之间的值，不设置默认不轮播
                //ad.setSlideIntervalTime(30 * 1000);
                mBannerContainer.removeAllViews();
                mBannerContainer.addView(bannerView);
                //设置广告互动监听回调
                ad.setBannerInteractionListener(new TTBannerAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        //Toast.makeText(context,"广告被点击",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        Log.e("bannergg","广告展示");
                        //Toast.makeText(context,"广告展示",Toast.LENGTH_SHORT).show();

                    }
                });
                //（可选）设置下载类广告的下载监听
                bindDownloadListener(ad);
                //在banner中显示网盟提供的dislike icon，有助于广告投放精准度提升
                ad.setShowDislikeIcon(new TTAdDislike.DislikeInteractionCallback() {
                    @Override
                    public void onSelected(int position, String value) {
                        //Toast.makeText(context,"点击"+value,Toast.LENGTH_SHORT).show();

                        //用户选择不喜欢原因后，移除广告展示
                        mBannerContainer.removeAllViews();
                        relativeLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancel() {
                        //Toast.makeText(context,"点击取消",Toast.LENGTH_SHORT).show();

                    }
                });

                //获取网盟dislike dialog，您可以在您应用中本身自定义的dislike icon 按钮中设置 mTTAdDislike.showDislikeDialog();
                /*mTTAdDislike = ad.getDislikeDialog(new TTAdDislike.DislikeInteractionCallback() {
                        @Override
                        public void onSelected(int position, String value) {
                            TToast.show(mContext, "点击 " + value);
                        }

                        @Override
                        public void onCancel() {
                            TToast.show(mContext, "点击取消 ");
                        }
                    });
                if (mTTAdDislike != null) {
                    XXX.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTTAdDislike.showDislikeDialog();
                        }
                    });
                } */

            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
           return TYEP_BOOK;
        }
        else if(position==1){
            return TYEP_TOP;
        }
        else if(position==2){
            return TYEP_CENTER;
        }
        else if(position==3){
            return TYEP_BOTTOM;
        }
        return -1;
    }

    @Override
    public void onGfclick(int index) {
        Intent intent=new Intent(context, XqActivity.class);
        intent.putExtra("id",xlist.get(index).getBookId());
        context.startActivity(intent);
    }

    class BookViewHolder extends RecyclerView.ViewHolder{
        private ImageView bj,back,book;
        private RatingBar start;
        private TextView num,name,author,finishtype,zs;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bj=itemView.findViewById(R.id.xq_bj);
            back=itemView.findViewById(R.id.xq_back);
            book=itemView.findViewById(R.id.xq_book);
            num=itemView.findViewById(R.id.xq_num);
            name=itemView.findViewById(R.id.xq_name);
            author=itemView.findViewById(R.id.xq_author);
            finishtype=itemView.findViewById(R.id.xq_finishtype);
            start=itemView.findViewById(R.id.xq_start);
            zs=itemView.findViewById(R.id.xq_zs);

        }
    }

    class TopViewHolder extends RecyclerView.ViewHolder{
        private TextView rq,zd,classname;
        private ExpandTextView bookdes;
        private RelativeLayout ml;
        private FrameLayout img;
        private RelativeLayout gg;
        private TextView time;
        public TopViewHolder(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.xq_time);
            rq=itemView.findViewById(R.id.xq_rq);
            zd=itemView.findViewById(R.id.xq_zd);
            bookdes=itemView.findViewById(R.id.xq_bookdes);
            classname=itemView.findViewById(R.id.xq_type);
            ml=itemView.findViewById(R.id.ml);
            img=itemView.findViewById(R.id.xq_img);
            gg=itemView.findViewById(R.id.xq_gg);
        }
    }

    class CenterViewHolder extends RecyclerView.ViewHolder{
        private TextView hyh;
        private RecyclerView rv;
        public CenterViewHolder(@NonNull View itemView) {
            super(itemView);
            hyh=itemView.findViewById(R.id.xq_hyh);
            rv=itemView.findViewById(R.id.xq_jxrv);
        }
    }

    class BottomViewHolder extends RecyclerView.ViewHolder{

        public BottomViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public class MyThread extends Thread{
        private String url;
        private ImageView bj;

        public MyThread(String url, ImageView bj) {
            this.url = url;
            this.bj = bj;
        }

        @Override
        public void run() {
            super.run();
            final Bitmap blurBitmap2 = FastBlurUtil.GetUrlBitmap(url, 2);
            // 刷新ui必须在主线程中执行
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bj.setImageBitmap(blurBitmap2);
                }
            });
        }
    }

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n 随机数个数
     */
    public static int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }

    private boolean mHasShowDownloadActive = false;

    private void bindDownloadListener(TTBannerAd ad) {
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
               // Toast.makeText(context, "点击图片开始下载", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                if (!mHasShowDownloadActive) {
                    mHasShowDownloadActive = true;
                    //Toast.makeText(context, "下载中，点击图片暂停", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
               // Toast.makeText(context, "下载暂停，点击图片继续", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                //Toast.makeText(context, "下载失败，点击图片重新下载", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                //Toast.makeText(context, "安装完成，点击图片打开", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
               // Toast.makeText(context, "点击图片安装", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
