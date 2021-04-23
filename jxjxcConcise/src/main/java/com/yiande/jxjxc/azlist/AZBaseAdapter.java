package com.yiande.jxjxc.azlist;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class AZBaseAdapter<T extends AZItemEntity, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> mDataList;

    public AZBaseAdapter(List<T> dataList) {
        mDataList = dataList;
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(List<T> dataList) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        if (dataList != mDataList) {
            mDataList.clear();
            if (dataList != null && dataList.size() > 0) {
                mDataList.addAll(dataList);
            }
        } else {
            if (dataList != null && dataList.size() > 0) {
                List<T> list = new ArrayList<>(dataList);
                mDataList.clear();
                mDataList.addAll(list);
            } else {
                mDataList.clear();
            }

        }
        notifyDataSetChanged();
    }

    public String getSortLetters(int position) {
        if (mDataList == null || mDataList.isEmpty()) {
            return null;
        }
        return mDataList.get(position).getSortLetters();
    }

    public int getSortLettersFirstPosition(String letters) {
        if (mDataList == null || mDataList.isEmpty()) {
            return -1;
        }
        int position = -1;
        for (int index = 0; index < mDataList.size(); index++) {
            if (mDataList.get(index).getSortLetters().equals(letters)) {
                position = index;
                break;
            }
        }
        return position;
    }

    public int getNextSortLetterPosition(int position) {
        if (mDataList == null || mDataList.isEmpty() || mDataList.size() <= position + 1) {
            return -1;
        }
        int resultPosition = -1;
        for (int index = position + 1; index < mDataList.size(); index++) {
            if (!mDataList.get(position).getSortLetters().equals(mDataList.get(index).getSortLetters())) {
                resultPosition = index;
                break;
            }
        }
        return resultPosition;
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }
}
