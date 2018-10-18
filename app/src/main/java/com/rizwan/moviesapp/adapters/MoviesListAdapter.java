package com.rizwan.moviesapp.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rizwan.moviesapp.R;
import com.rizwan.moviesapp.Utils;
import com.rizwan.moviesapp.apis.model.MoviesInfo;

import java.util.ArrayList;
import java.util.List;

import static com.rizwan.moviesapp.apis.MoviesApiService.IMAGE_PATH;
import static com.rizwan.moviesapp.apis.MoviesApiService._SCHEME;

/**
 * <p>
 * This is main Adapter which holds the all poster of movies;
 * 1. adding every time new list of information into existing list using addAllItem method.
 * 2. we have n number of pages so based on pages getting new list of movies information.
 * 3. setting into view
 * 4. todo clicking on poster should redirect to detail screen;
 */

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MyViewHolder> {
    private final ListItemOnClickListener mOnClickListener;
    private static final String TAG = MoviesListAdapter.class.getName();
    private List<MoviesInfo> list;

    public void addAllItem(final List<MoviesInfo> mList) {
        if (list == null && mList != null && mList.size() > 0) {
            list = new ArrayList<>();
            list.addAll(mList);
            this.notifyAll();
        } else if (mList != null && mList.size() > 0) {
            int lastIndex = this.list.size();
            this.list.addAll(mList);
            notifyItemRangeChanged(lastIndex, getItemCount());
        }

    }

    public void clearAllItems() {
        if (list != null && list.size() > 0) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView poster;

        MyViewHolder(View view) {
            super(view);
            init(view);
        }

        private void init(View view) {
            poster = view.findViewById(R.id.poster);
            itemView.setOnClickListener(this);
        }

        private void bindView(int position) {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme(_SCHEME)
                    .appendEncodedPath(IMAGE_PATH)
                    .appendEncodedPath(list.get(position).getPosterPath()).build();
            Utils.loadImage(itemView.getContext(), poster, builder.build(), R.drawable.ic_broken_image);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(list.get(getAdapterPosition()), v.findViewById(R.id.poster));
        }
    }

    public MoviesListAdapter(List<MoviesInfo> list, ListItemOnClickListener mOnClickListener) {
        this.list = list;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_list_item_image, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ListItemOnClickListener {
        void onListItemClick(MoviesInfo selectedObject, View view);
    }
}
