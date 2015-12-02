package com.example.nookie.demotivatorsmaker;


import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.nookie.demotivatorsmaker.models.RVItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    List<RVItem> data = new ArrayList<>();
    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
    private boolean mIsSelectable = false;


    public RVAdapter() {
        refresh();
    }

    private void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }

    private void setSelectable(boolean selectable) {
        mIsSelectable = selectable;
    }

    private boolean isSelectable() {
        return mIsSelectable;
    }

    public void refresh(){
        RefreshTask refreshTask = new RefreshTask();
        refreshTask.execute();
    }

    public void setData(List<RVItem> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)App.getAppContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.card_saved_pic,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Uri uri = data.get(position);
        holder.image.setImageBitmap(data.get(position).getThumbnail());
        holder.checkBox.setChecked(true);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.card_image)
        ImageView image;

        @Bind(R.id.card_checkbox)
        CheckBox checkBox;

        @Bind(R.id.card)
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    CheckBox checkBox = (CheckBox)v.findViewById(R.id.card_checkbox);
                    checkBox.setVisibility(View.VISIBLE);
                    checkBox.setChecked(!checkBox.isChecked());
                    return true;
                }
            });
        }


    }

    private class RefreshTask extends AsyncTask<Void,Void,List<RVItem>>{

        @Override
        protected List<RVItem> doInBackground(Void... params) {
            FileManager fileManager = FileManager.getInstance();
            return fileManager.queryFiles();
        }

        @Override
        protected void onPostExecute(List<RVItem> rvItems) {
            setData(rvItems);
        }
    }
}
