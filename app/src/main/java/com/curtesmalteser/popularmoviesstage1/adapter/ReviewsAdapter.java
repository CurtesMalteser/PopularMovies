package com.curtesmalteser.popularmoviesstage1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.utils.ReviewsModel;

import java.util.List;

/**
 * Created by António "Curtes Malteser" Bastião on 26/02/2018.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private Context mContext;
    private List<ReviewsModel> mReviewsArrayList;
    final private ReviewsAdapter.ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(ReviewsModel reviewsModel);
    }

    public ReviewsAdapter(Context context, List<ReviewsModel> reviewsArrayList,
                          ReviewsAdapter.ListItemClickListener listener) {
        this.mContext = context;
        this.mReviewsArrayList = reviewsArrayList;
        this.mOnClickListener = listener;
    }

    @Override
    public ReviewsAdapter.ReviewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.reviews_single_row;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ReviewsAdapter.ReviewsViewHolder viewHolder = new ReviewsAdapter.ReviewsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ReviewsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mReviewsArrayList.size();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView author;
        TextView content;

        public ReviewsViewHolder(View itemView) {
            super(itemView);

            author = itemView.findViewById(R.id.tvAuthor);
            content = itemView.findViewById(R.id.tvContent);

            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            ReviewsModel model = mReviewsArrayList.get(listIndex);

            author.setText(model.getAuthor());
            content.setText(model.getContent());


        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            ReviewsModel reviewsModelList = mReviewsArrayList.get(clickedPosition);
            mOnClickListener.onListItemClick(reviewsModelList);
        }
    }
}