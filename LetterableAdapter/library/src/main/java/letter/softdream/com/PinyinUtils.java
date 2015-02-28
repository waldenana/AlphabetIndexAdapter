package letter.softdream.com;

import java.util.ArrayList;


/**
 * ClassName : PinyinUtils <br>
 * 功能描述： {@link #HanziToPinyin}取自Frameworks<br>
 * History <br>
 * Create User: An Zewei <br>
 * Create Date: 2014-1-19 下午3:12:42 <br>
 * Update User: <br>
 * Update Date:
 */
public class PinyinUtils {

    /**
     * @param input
     * @return
     */
    public static char getPinYinAlpha(String input) {
        try {
            ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(input);
            // StringBuilder sb = new StringBuilder();
            if (tokens != null && tokens.size() > 0) {
                HanziToPinyin.Token token = tokens.get(0);
                if (HanziToPinyin.Token.PINYIN == token.type) {
                    return token.target.charAt(0);
                }
                return token.source.charAt(0);
            }
        } catch (Exception e) {
        }
        return '#';
    }

    public static String getPinYin(String input) {
        StringBuilder sb = new StringBuilder();
        try {
            ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(input);
            if (tokens != null && tokens.size() > 0) {
                for (HanziToPinyin.Token token : tokens) {
                    if (HanziToPinyin.Token.PINYIN == token.type) {
                        sb.append(token.target);
                    } else {
                        sb.append(token.source);
                    }
                }
            }
        } catch (Exception e) {
            sb.append(input);
        }
        return sb.toString().toLowerCase();
    }
}

