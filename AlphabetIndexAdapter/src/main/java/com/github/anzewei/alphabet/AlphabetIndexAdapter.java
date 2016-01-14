package com.github.anzewei.alphabet;

import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * ClassName : AlphabetIndexAdapter <br>
 * 功能描述： <br>
 * History <br>
 * Create User: An Zewei <br>
 * Create Date: 2014-2-8 下午4:51:24 <br>
 * Update User: <br>
 * Update Date:
 */
public abstract class AlphabetIndexAdapter<T extends AlphabetIndexAdapter.ILetterAble> extends BaseAdapter implements SectionIndexer {

    /**
     * 索引是否显示
     */
    protected boolean mbShowKey = false;
    /**
     * index        Key          value(key po)     真实List          虚拟List<br>
     * |   0   |    |   A   |        |   0   |    |    A1       |    |  A       |<br>
     * |   1   |    |   B   |        |   4   |    |    A2       |    |    A1    |<br>
     * |   2   |                                  |    A3       |    |    A2    |<br>
     * |   3   |                                  |    B1       |    |    A3    |<br>
     * |   4   |                                  |    B2       |    |  B       |<br>
     * |   5   |                                                     |    B1    |<br>
     * |   6   |                                                     |    B2    |<br>
     * <p>
     * <br> 由上可知 每个索引对应的数据开始位置为Position = value - index;
     */
    protected SparseIntArray mIndexPosition = new SparseIntArray();
    protected LinkedList<T> mArrayList = new LinkedList<T>();
    private static final String[] sAlpha = new String[]{"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * 清除数据
     */
    public void clear() {
        mArrayList.clear();
        mIndexPosition.clear();
        notifyDataSetChanged();
    }

    /**
     * 设置是否根据索引分组
     *
     * @param show
     */
    public void setShowKey(boolean show) {
        mbShowKey = show;
        notifyDataSetChanged();
    }

    /**
     * 索引组是否显示
     *
     * @return
     */
    public boolean isKeyShow() {
        return mbShowKey;
    }


    /**
     * 获取分组名对应的位置 如果没有返回-1
     *
     * @param sectionIndex
     * @return 获取分组名对应的位置 如果没有返回-1
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        int key = sectionIndex;
        int position = mIndexPosition.get(key, -1);
        if (!mbShowKey) {
            position -= mIndexPosition.indexOfKey(key);
        }
        return position;
    }

    /**
     * @see android.widget.BaseAdapter#getViewTypeCount()
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * 1代表分组，0代表child
     *
     * @see android.widget.BaseAdapter#getItemViewType(int)
     */
    @Override
    public int getItemViewType(int position) {
        if (isGroup(position)) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        if (isGroup(position)) {
            return mIndexPosition.keyAt(mIndexPosition.indexOfValue(position));
        }
        char first = getItem(position).getLetter().charAt(0);
        return char2Key(first);
    }

    /**
     * @param keyindex 指定的索引组
     * @return 指定索引组的个数
     */
    public int getGroupCount(int keyindex) {
        int value = mIndexPosition.get(keyindex, -1);
        if (value < 0) {
            return 0;
        }
        int next = mArrayList.size() + mIndexPosition.size();
        int position = mIndexPosition.indexOfKey(keyindex);
        if (position < mIndexPosition.size() - 1) {// not last
            next = mIndexPosition.valueAt(position + 1);
        }
        return next - value - 1;//由于有个索引项，所以要－1
    }

    /**
     * 去除掉索引组的真实地址
     *
     * @param position ListView的原始地址
     * @return
     */
    public int getFlattenedPos(int position) {
        if (!mbShowKey || isGroup(position)) {
            return position;
        }
        int size = mIndexPosition.size();
        int i = 0;
        for (; i < size; i++) {
            if (mIndexPosition.valueAt(i) > position) {
                break;
            }
        }
        position -= i;
        return position;
    }

    /**
     * 添加多条数据
     *
     * @param items
     */
    public void addAll(Collection<? extends T> items) {
        for (T iLetterAble : items) {
            addItem(iLetterAble);
        }
        notifyDataSetChanged();
    }

    /**
     * 新加
     *
     * @param letterAble
     */
    public void addItem(T letterAble) {
        char first = letterAble.getLetter().charAt(0);
        int key = char2Key(first);
        int position = mIndexPosition.get(key, -1);
        int next = -2;
        int shouldChanged;
        int insertCount = 1;
        if (position == -1) {// 没有添加过,position应该key的下一个位置
            mIndexPosition.put(key, -1);
            insertCount = 0;//由于插入了一组导致后面的value差了1，所以应扣除
        }
        int keyindex = mIndexPosition.indexOfKey(key);
        shouldChanged = keyindex < mIndexPosition.size() - 1 ? mIndexPosition.valueAt(keyindex + 1) - keyindex
                - insertCount : mArrayList.size();
        if (position == -1) {
            position = shouldChanged;
            insertCount = 2;
        } else {
            position -= keyindex;
            next = shouldChanged;
        }
        mIndexPosition.put(key, position + keyindex);
        while (position < next) {// 排序
            if (mArrayList.get(position).getLetter().compareTo(letterAble.getLetter()) >= 0) {
                break;
            }
            position++;
        }
        mArrayList.add(position, letterAble);
        offsetGroup(keyindex, insertCount);
    }

    /**
     * 转换为索引
     *
     * @param a
     * @return
     */
    private int char2Key(char a) {
        a = Character.toUpperCase(a);
        int index = Arrays.binarySearch(sAlpha, String.valueOf(a));
        return Math.max(0, index);
    }

    /**
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        if (mbShowKey) {
            return mArrayList.size() + mIndexPosition.size();
        }
        return mArrayList.size();
    }

    private boolean isGroup(int position) {
        return mbShowKey && mIndexPosition.indexOfValue(position) != -1;
    }

    /**
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public T getItem(int position) {
        if (isGroup(position)) {
            return null;
        }
        position = getFlattenedPos(position);
        return mArrayList.get(position);
    }

    /**
     * this method will cause inteactive bugs..
     *
     * @param position index
     */
    public void delItem(int position) {
        int keyindex = 0;
        int deleteCount = 0;
        if (!isGroup(position)) {
            position = getFlattenedPos(position);
            char first = mArrayList.remove(position).getLetter().charAt(0);
            int key = char2Key(first);
            keyindex = mIndexPosition.indexOfKey(key);
            deleteCount = 1;
            if (getGroupCount(key) == 1) {
                mIndexPosition.removeAt(keyindex);
                deleteCount++;
                keyindex--;
            }
        } else {
            keyindex = mIndexPosition.indexOfValue(position);
            position -= keyindex;
            int last = mArrayList.size();
            if (mIndexPosition.size() > keyindex + 1) {
                int nextValue = mIndexPosition.valueAt(keyindex + 1);
                last = nextValue - keyindex - 1;
            }
            for (int i = position; i < last; i++) {
                mArrayList.remove(i);
                deleteCount++;
            }
            mIndexPosition.removeAt(keyindex);
            deleteCount++;
            keyindex--;
        }
        offsetGroup(keyindex, -deleteCount);
        notifyDataSetChanged();
    }

    private void offsetGroup(int groupIndex, int offset) {

        int size = mIndexPosition.size();
        for (int i = groupIndex + 1; i < size; i++) {
            int tmp = mIndexPosition.valueAt(i);
            tmp += offset;
            int nextkey = mIndexPosition.keyAt(i);
            mIndexPosition.put(nextkey, tmp);
        }
    }

    /**
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (type == 1) {
            return getKeyView(position, convertView, parent);
        }
        return getChildView(getItem(position), convertView, parent);
    }

    /**
     * 获取索引view
     */
    protected abstract View getKeyView(int position, View convertView, ViewGroup parent);

    /**
     * @param item        数据
     * @param convertView
     * @param parent
     * @return
     */
    protected abstract View getChildView(T item, View convertView, ViewGroup parent);

    /**
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public String[] getSections() {
        return sAlpha;
    }


    public interface ILetterAble {
        String getLetter();
    }
}
