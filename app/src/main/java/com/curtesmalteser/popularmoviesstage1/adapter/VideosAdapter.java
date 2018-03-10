package com.curtesmalteser.popularmoviesstage1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.utils.NetworkUtils;
import com.curtesmalteser.popularmoviesstage1.utils.VideosModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by António "Curtes Malteser" Bastião on 25/02/2018.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosViewHolder> {

    private Context mContext;
    private List<VideosModel> mVideosArrayList;
    final private VideosAdapter.ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(VideosModel videosModel);
    }

    public VideosAdapter(Context context, List<VideosModel> videosModelArrayList,
                         VideosAdapter.ListItemClickListener listener) {
        this.mContext = context;
        this.mVideosArrayList = videosModelArrayList;
        this.mOnClickListener = listener;
    }

    @Override
    public VideosAdapter.VideosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.videos_single_row;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        VideosAdapter.VideosViewHolder viewHolder = new VideosAdapter.VideosViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VideosAdapter.VideosViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mVideosArrayList.size();
    }

    public class VideosViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        ImageView thumbnailView;
        TextView title;

        public VideosViewHolder(View itemView) {
            super(itemView);

            thumbnailView = itemView.findViewById(R.id.thumbnailView);
            title = itemView.findViewById(R.id.videoTitle);

            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            VideosModel model = mVideosArrayList.get(listIndex);

            Picasso.with(mContext)
                    .load(NetworkUtils.getThumbnail(model.getKey()))
                    .into(thumbnailView);

            title.setText(model.getName());
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            VideosModel videosModelList = mVideosArrayList.get(clickedPosition);
            mOnClickListener.onListItemClick(videosModelList);
        }
    }
}
