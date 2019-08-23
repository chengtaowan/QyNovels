package com.jdhd.qynovels.readeradpater;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.jdhd.qynovels.R;
import com.jdhd.qynovels.entry.ChapterItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garrett on 2018/12/5.
 * contact me krouky@outlook.com
 */
public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.ViewHolder> {

    private List<ChapterItemBean> mList=new ArrayList<>();
    public void refresh(List<ChapterItemBean> mList){
        this.mList=mList;
        notifyDataSetChanged();
    }

    private OnItemClickListener mOnItemClickListener;

    public CatalogueAdapter(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_catalogue, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mTextView.setText(mList.get(i).getChapterName());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onClicked(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClicked(int position);
    }
}
