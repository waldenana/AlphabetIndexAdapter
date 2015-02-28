package lettersample.softdream.com;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import letter.softdream.com.LetterAdapter;


public class FriendAdapter extends LetterAdapter<Friend> {

    private LayoutInflater mInflater;
    private Context mContext;
    public FriendAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext=context;
    }

    @Override
    protected View getKeyView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null){
            textView = new TextView(mContext);
            textView.setTextColor(Color.parseColor("#dcdcdc"));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        }else {
            textView = (TextView) convertView;
        }
        textView.setText(String.valueOf(getKey(position)));
        return textView;
    }

    @Override
    protected View getChildView(Friend item, View convertView, ViewGroup parent) {

        TextView textView;
        if (convertView == null){
            textView = new TextView(mContext);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            textView.setPadding(10,10,10,10);
        }else {
            textView = (TextView) convertView;
        }
        textView.setText(item.getName());
        return textView;
    }
}
