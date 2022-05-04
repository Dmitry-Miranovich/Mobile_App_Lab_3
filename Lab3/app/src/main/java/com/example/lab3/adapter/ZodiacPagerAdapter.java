package com.example.lab3.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.lab3.pages.ZodiacFragment;

public class ZodiacPagerAdapter extends ViewPagerAdapter{
    public ZodiacPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return(ZodiacFragment.newInstance(position));
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
