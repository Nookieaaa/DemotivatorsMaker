package com.nookdev.maker.dem.fragments.list;


import android.content.Context;
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
import com.nookdev.maker.dem.events.RefreshEvent;
import com.nookdev.maker.dem.helpers.FileManager;
import com.nookdev.maker.dem.interfaces.AdapterCallbacks;
import com.nookdev.maker.dem.models.RVItem;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    List<RVItem> data = new ArrayList<>();
    private boolean selectionMode = false;
    private AdapterCallbacks adapterCallbacks;


    public RVAdapter(AdapterCallbacks callbacks) {
        App.getBus().register(this);
        refresh();
        try{
            adapterCallbacks = callbacks;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    public RVAdapter() {
        App.getBus().register(this);
        refresh();
    }

    @Subscribe
    public void onRefreshRequested(RefreshEvent event){
        refresh();
    }

    private void refresh(){
        Observable.create(new Observable.OnSubscribe<List<RVItem>>() {
            @Override
            public void call(Subscriber<? super List<RVItem>> subscriber) {
                subscriber.onNext(FileManager.getInstance().queryFiles());
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setData, Throwable::printStackTrace);
    }

    public void setData(List<RVItem> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) App.getAppContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.card_saved_pic,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Picasso.with(App.getAppContext())
                .load(data.get(position).getFile())
                .fit()
                .centerInside()
                .into(holder.image);
        //holder.cardView.setOnClickListener(v -> adapterCallbacks.openImage(data.get(position).getFile()));
        holder.btnDelete.setOnClickListener(v -> Observable.just(data.get(position))
                .map(item -> FileManager.getInstance().delete(item.getFile()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                        if (result)
                            itemDeleted(position);
                    }
                    ,Throwable::printStackTrace));

        //holder.btnShare.setOnClickListener(v -> adapterCallbacks.share(data.get(position).getFile()));
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
}
