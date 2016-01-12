package lettersample.softdream.com;


import android.text.TextUtils;

import com.github.anzewei.alphabet.AlphabetIndexAdapter;
import com.github.anzewei.alphabet.PinyinUtils;

public class Friend implements AlphabetIndexAdapter.ILetterAble {

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
