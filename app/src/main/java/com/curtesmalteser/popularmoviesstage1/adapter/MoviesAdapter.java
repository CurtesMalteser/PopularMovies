package com.curtesmalteser.popularmoviesstage1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel;
import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by António "Curtes Malteser" Bastião on 17/02/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private Context mContext;
    private List<MoviesModel> mMoviesArrayList;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(MoviesModel moviesModel);
    }

    public MoviesAdapter(Context context, List<MoviesModel> moviesModelArrayList,
                         ListItemClickListener listener) {
        this.mContext = context;
        this.mMoviesArrayList = moviesModelArrayList;
        this.mOnClickListener = listener;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.single_card_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        MoviesViewHolder viewHolder = new MoviesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mMoviesArrayList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        ImageView poster;

        public MoviesViewHolder(View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.poster);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            MoviesModel model = mMoviesArrayList.get(listIndex);
            Picasso.with(mContext)
                    .load(NetworkUtils.getPosterUrl(mContext.getString(R.string.poster_width_segment), model.getPosterPath()))
                    .into(poster);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            MoviesModel moviesModelList = mMoviesArrayList.get(clickedPosition);
            mOnClickListener.onListItemClick(moviesModelList);
        }
    }
}
