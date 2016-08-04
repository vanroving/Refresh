package com.guoxin.refreshdemo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by van on 2016/8/4.
 */
public class ItemFootAdapter extends RecyclerView.Adapter {
    public static final int  PULLUP_LOAD_MORE=0;
    public static final int  LOADING_MORE=1;
    private int load_more_status=0;
    private LayoutInflater mInflater;
    private List<String> mTitles=null;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    public ItemFootAdapter(Context context){
        this.mInflater=LayoutInflater.from(context);
        this.mTitles=new ArrayList<String>();
        for (int i=0;i<20;i++){
            mTitles.add("number"+i);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_ITEM){
            View view=mInflater.inflate(R.layout.item_recycler_layout,parent,false);
            ItemViewHolder itemViewHolder=new ItemViewHolder(view);
            return itemViewHolder;
        }else if(viewType==TYPE_FOOTER){
            View view=mInflater.inflate(R.layout.recycler_load_more_layout,parent,false);
            ItemFootHolder itemFootHolder=new ItemFootHolder(view);
            return itemFootHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  ItemViewHolder){
            ((ItemViewHolder) holder).tv.setText(mTitles.get(position));
            ((ItemViewHolder) holder).tv.setTextColor(Color.BLACK);

        }else if(holder instanceof  ItemFootHolder){
            switch (load_more_status){
                case PULLUP_LOAD_MORE:
                    ((ItemFootHolder) holder).tv.setText("上拉加载更多");
                    ((ItemFootHolder) holder).tv.setTextColor(Color.GREEN);
                    break;
                case LOADING_MORE:
                    ((ItemFootHolder) holder).tv.setText("正在加载中");
                    ((ItemFootHolder) holder).tv.setTextColor(Color.GREEN);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mTitles.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position+1==getItemCount()){
            return TYPE_FOOTER;
        }else {

            return TYPE_ITEM ;
        }
    }
    class ItemViewHolder extends RecyclerView.ViewHolder{

        public  TextView tv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
    class ItemFootHolder extends RecyclerView.ViewHolder{

        public   TextView tv;

        public ItemFootHolder(View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.foot_view_item_tv);
        }
    }
    public void addItem(List<String> newDatas) {
        //mTitles.add(position, data);
        //notifyItemInserted(position);
        newDatas.addAll(mTitles);
        mTitles.removeAll(mTitles);
        mTitles.addAll(newDatas);
        notifyDataSetChanged();
    }

    public void addMoreItem(List<String> newDatas) {
        mTitles.addAll(newDatas);
        notifyDataSetChanged();
    }
    public void changeMoreStatus(int status){
        load_more_status=status;
        notifyDataSetChanged();
    }
}
