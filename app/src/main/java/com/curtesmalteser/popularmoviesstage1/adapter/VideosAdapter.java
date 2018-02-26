package com.curtesmalteser.popularmoviesstage1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.utils.VideosModel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

/**
 * Created by António "Curtes Malteser" Bastião on 25/02/2018.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosViewHolder> {

    private List<VideosModel> mVideosArrayList;
    final private VideosAdapter.ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(VideosModel videosModel);
    }

    public VideosAdapter(List<VideosModel> videosModelArrayList,
                         VideosAdapter.ListItemClickListener listener) {
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

        YouTubeThumbnailView tubeThumbnailView;
        TextView title;
        TextView type;

        public VideosViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.videoTitle);
            type = itemView.findViewById(R.id.videoType);

            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            VideosModel model = mVideosArrayList.get(listIndex);
            Log.d("AJDB", "bind: " + model.getName());

            title.setText(model.getName());
            type.setText(model.getType());


        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            VideosModel videosModelList = mVideosArrayList.get(clickedPosition);
            mOnClickListener.onListItemClick(videosModelList);
        }
    }
}
