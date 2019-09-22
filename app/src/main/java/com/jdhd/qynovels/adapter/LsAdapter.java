package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.activities.ExtendReaderActivity;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.BookBean;
import com.jdhd.qynovels.ui.activity.LsActivity;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

public class LsAdapter extends RecyclerView.Adapter<LsAdapter.LsViewHolder>{
    private Context context;
    private List<BookBean> list;
    private DbUtils dbUtils;
    private SQLiteDatabase database;
    private String token;

    public LsAdapter(Context context, List<BookBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        dbUtils=new DbUtils(context);
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ls, parent, false);
        LsViewHolder viewHolder=new LsViewHolder(view);
        SharedPreferences sharedPreferences=context.getSharedPreferences("token",Context.MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final LsViewHolder holder, final int position) {
        if(list.get(position).getImg()!=null){
            GlideUrl url = DeviceInfoUtils.getUrl(list.get(position).getImg());
            Glide.with(context)
                    .load(url)
                    .apply(new RequestOptions().error(R.mipmap.book_100))
                    .apply(new RequestOptions().placeholder(R.mipmap.book_100))
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(MyApp.raduis))).into(holder.book);


        }
        holder.name.setText(list.get(position).getName());
        holder.zj.setText(list.get(position).getDes());
        holder.day.setText(list.get(position).getTime());
       holder.del.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               database=dbUtils.getWritableDatabase();
               database.execSQL("delete from readhistory where name='"+list.get(position).getName()+"'");
               list.remove(position);
               notifyDataSetChanged();
               holder.sml.quickClose();

           }
       });
       holder.rl.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Log.e("click","1111");
               Intent intent=new Intent(context, ExtendReaderActivity.class);
               intent.putExtra("id",list.get(position).getBookid());
               intent.putExtra("token",token);
               intent.putExtra("img",list.get(position).getImg());
               intent.putExtra("name",list.get(position).getName());
               intent.putExtra("backlistid",0);
               intent.putExtra("charIndex",0);
               intent.putExtra("type",0);
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class LsViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name,zj,day;
        private Button del;
        private SwipeMenuLayout sml;
        private LinearLayout ll;
        private RelativeLayout rl;
        public LsViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.ls_book);
            name=itemView.findViewById(R.id.ls_name);
            zj=itemView.findViewById(R.id.ls_zj);
            day=itemView.findViewById(R.id.ls_day);
            del=itemView.findViewById(R.id.ls_del);
            sml=itemView.findViewById(R.id.sml);
            ll=itemView.findViewById(R.id.ll);
            rl=itemView.findViewById(R.id.rl);
        }
    }
}
