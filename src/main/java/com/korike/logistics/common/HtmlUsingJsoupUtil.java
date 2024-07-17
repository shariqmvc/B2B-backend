package com.korike.logistics.common;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HtmlUsingJsoupUtil {

    public static void htmlUsingJsoupUtilMethod() throws IOException {
        File input = new File("HTML/index.html");
        Document doc = Jsoup.parse(input, "UTF-8", "https://korikelogistics.com:8443");
        Elements els = doc.getElementsByClass("col-sm-6 text-sm-end");
        for (Element el: els){
            for(Node child : el.childNodes()) {
                System.out.println(child);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        htmlUsingJsoupUtilMethod();
    }
}
