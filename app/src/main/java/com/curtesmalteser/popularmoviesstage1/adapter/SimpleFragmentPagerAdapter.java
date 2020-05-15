package com.curtesmalteser.popularmoviesstage1.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.fragment.OverviewFragment;
import com.curtesmalteser.popularmoviesstage1.fragment.ReviewsFragment;
import com.curtesmalteser.popularmoviesstage1.fragment.VideosFragment;

/**
 * Created by António "Curtes Malteser" Bastião on 23/02/2018.
 */


public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new OverviewFragment();
            case 1:
                return new VideosFragment();
            case 2:
                return new ReviewsFragment();
            default:
                return new OverviewFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.string_overview);
            case 1:
                return mContext.getString(R.string.string_videos);
            case 2:
                return mContext.getString(R.string.string_reviews);
            default:
                return null;
        }
    }
}
