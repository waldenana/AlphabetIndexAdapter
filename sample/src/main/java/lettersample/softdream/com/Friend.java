package lettersample.softdream.com;


import android.text.TextUtils;

import letter.softdream.com.LetterAdapter;
import letter.softdream.com.PinyinUtils;

public class Friend implements LetterAdapter.ILetterAble {

    public Friend(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String pinyin;

    @Override
    public String getLetter() {
        if (TextUtils.isEmpty(pinyin))
            pinyin = PinyinUtils.getPinYin(name);
        return pinyin;
    }
}
