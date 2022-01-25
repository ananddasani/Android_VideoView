package com.example.fetchanddownload.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fetchanddownload.Fragments.ImageFragment;
import com.example.fetchanddownload.Fragments.VideoFragment;

public class PagerAdapter extends FragmentStateAdapter {

    public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 1)
            return new VideoFragment();
        else
            return new ImageFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
