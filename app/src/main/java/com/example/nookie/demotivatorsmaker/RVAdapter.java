package com.example.nookie.demotivatorsmaker;


import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.nookie.demotivatorsmaker.interfaces.AdapterCallbacks;
import com.example.nookie.demotivatorsmaker.models.RVItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    List<RVItem> data = new ArrayList<>();
    private boolean selectionMode = false;
    private AdapterCallbacks adapterCallbacks;


    public RVAdapter(AdapterCallbacks callbacks) {
        refresh();
        try{
            adapterCallbacks = callbacks;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.image.setImageBitmap(data.get(position).getThumbnail());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallbacks.openImage(data.get(position).getFile());
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallbacks.delete(data.get(position).getFile(), position);
            }
        });
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallbacks.share(data.get(position).getFile());
            }
        });
    }

    public void itemDeleted(int position){
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
    }

    public void updateView(int position){
        notifyItemChanged(position);
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

        @Bind(R.id.card_ibtn_delete)
        ImageButton btnDelete;

        @Bind(R.id.card_ibtn_share)
        ImageButton btnShare;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

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
