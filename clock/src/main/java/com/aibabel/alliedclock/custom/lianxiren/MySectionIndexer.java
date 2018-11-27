package com.aibabel.alliedclock.custom.lianxiren;

import android.util.Log;

import java.util.Arrays;

public class MySectionIndexer implements android.widget.SectionIndexer {

    private String[] mSections;
    private int[] mPositions;
    private int mCount;

    public MySectionIndexer(String[] sections, int[] counts) {
        if (sections == null || counts == null) {
            throw new NullPointerException();
        }
        if (sections.length != counts.length) {
            throw new IllegalArgumentException("The sections and counts arrays must have the same length");
        }
        this.mSections = sections;
        mPositions = new int[counts.length];
        int position = 0;
        for (int i = 0; i < counts.length; i++) {
            if (mSections[i] == null) {
                mSections[i] = "";
            } else {
                mSections[i] = mSections[i].trim();
            }
            mPositions[i] = position;
            position += counts[i];
            Log.i("MySectionIndexer", "counts[" + i + "]:" + counts[i]);
        }
        mCount = position;
    }

    @Override
    public String[] getSections() {
        return mSections;
    }

    /**
     * 根据section的索引，获取该分组中的第一个item在listview中的位置
     * 如在该项目中：sectionIndex为1时，返回9；sectionIndex为2时，返回20。
     *
     * @param sectionIndex
     * @return
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        if (sectionIndex < 0 || sectionIndex >= mSections.length) {
            return -1;
        }
        return mPositions[sectionIndex];
    }

    /**
     * 根据数据(item子项)的索引，获得该数据所在分组索引中的位置。
     * 如在该项目中：北京、保定都属于字母B，都返回2；大理、大同都属于字母D，都返回4。
     * 注意这个方法的返回值，它就是index<0时，返回-index-2的原因
     * 解释Arrays.binarySearch，如果搜索结果在数组中，刚返回它在数组中的索引，如果不在，刚返回第一个比它大的索引的负数-1
     * 如果没弄明白，请自己想查看api
     *
     * @param position
     * @return
     */
    @Override
    public int getSectionForPosition(int position) {
        if (position < 0 || position >= mCount) {
            return -1;
        }
        int index = Arrays.binarySearch(mPositions, position);
        return index >= 0 ? index : -index - 2;
    }
}
