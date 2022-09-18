package com.xxl.pdf;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.*;

/**
 * 富文本字符串（HTML）转为文本（Text）
 * https://www.jianshu.com/p/c3bb5a54ba77
 *
 * @Author: xxl
 * @Date: 2022/09/16 11:52
 */
public class Html2TextUtil extends HTMLEditorKit.ParserCallback {

    private static Html2TextUtil html2Text = new Html2TextUtil();

    StringBuffer s;

    public Html2TextUtil() {
    }

    public void parse(String str) throws IOException {

        InputStream iin = new ByteArrayInputStream(str.getBytes());
        Reader in = new InputStreamReader(iin);
        s = new StringBuffer();
        ParserDelegator delegator = new ParserDelegator();
        // the third parameter is TRUE to ignore charset directive
        delegator.parse(in, this, Boolean.TRUE);
        iin.close();
        in.close();
    }

    @Override
    public void handleText(char[] text, int pos) {
        s.append(text);
    }

    public String getText() {
        return s.toString();
    }

    public static String getContent(String str) {
        try {
            html2Text.parse(str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return html2Text.getText();
    }
}