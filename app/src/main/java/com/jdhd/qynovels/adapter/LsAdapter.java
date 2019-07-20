package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.BookBean;
import com.jdhd.qynovels.utils.DbUtils;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

public class LsAdapter extends RecyclerView.Adapter<LsAdapter.LsViewHolder>{
    private Context context;
    private List<BookBean> list;
    private DbUtils dbUtils;
    private SQLiteDatabase database;

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
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final LsViewHolder holder, final int position) {
        Glide.with(context).load(list.get(position).getImg()).into(holder.book);
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
        public LsViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.ls_book);
            name=itemView.findViewById(R.id.ls_name);
            zj=itemView.findViewById(R.id.ls_zj);
            day=itemView.findViewById(R.id.ls_day);
            del=itemView.findViewById(R.id.ls_del);
            sml=itemView.findViewById(R.id.sml);
        }
    }
}
