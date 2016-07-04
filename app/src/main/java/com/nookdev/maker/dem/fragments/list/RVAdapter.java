package com.nookdev.maker.dem.fragments.list;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nookdev.maker.dem.App;
import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.events.DeleteDemEvent;
import com.nookdev.maker.dem.events.DemDeletedEvent;
import com.nookdev.maker.dem.events.EmptyListEvent;
import com.nookdev.maker.dem.events.RefreshEvent;
import com.nookdev.maker.dem.events.ShareOpenEvent;
import com.nookdev.maker.dem.helpers.FileManager;
import com.nookdev.maker.dem.interfaces.AdapterCallbacks;
import com.nookdev.maker.dem.models.RVItem;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    List<RVItem> data = new ArrayList<>();


    public RVAdapter(AdapterCallbacks callbacks) {
        App.getBus().register(this);
        refresh();
    }

    public RVAdapter() {
        App.getBus().register(this);
    }

    @Subscribe
    public void onRefreshRequested(RefreshEvent event){
        refresh();
    }

    @Subscribe
    public void onDemDeletedEvent(DemDeletedEvent event){
        itemDeleted(event.getPosition());
    }

    private void refresh(){
        Observable.just(FileManager.getInstance().queryFiles())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setData,Throwable::printStackTrace);

    }

    public void setData(List<RVItem> newData) {
        if(data.size()>0){
            notifyItemRangeRemoved(0,data.size());
        }
        data.clear();
        data.addAll(newData);
        if(data.size()>0){
            notifyItemRangeInserted(0,data.size());
        }
        App.getBus().post(new EmptyListEvent());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_saved_pic,null,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        File f = new File(data.get(holder.getAdapterPosition()).getFile().getPath());
        if(f.exists())
        Picasso.with(App.getAppContext())
                .load(data.get(position).getFile())
                .fit()
                .centerInside()
                .into(holder.image);
        holder.cardView.setOnClickListener(v -> App.getBus().post(new ShareOpenEvent(data.get(position).getFile(),false)));
        holder.btnDelete.setOnClickListener(v -> {
                if(data.size()>=holder.getAdapterPosition()){
                    DeleteDemEvent event = new DeleteDemEvent(data.get(holder.getAdapterPosition()).getFile(),holder.getAdapterPosition());
                    App.getBus().post(event);
                }
            }
        );

        holder.btnShare.setOnClickListener(v ->
                App.getBus().post(
                        new ShareOpenEvent(
                                data.get(holder.getAdapterPosition()).getFile(),true)));
    }


    public void itemDeleted(int position){
        data.remove(position);
        notifyItemRemoved(position);
        App.getBus().post(new EmptyListEvent());
        //notifyItemRangeChanged(position, data.size());
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
}
