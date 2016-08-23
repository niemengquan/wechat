package com.nmq.util;

import com.nmq.domain.MessageText;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by niemengquan on 2016/8/23.
 */
public class MessageUtlis {
    public  static  final String MESSAGE_TYPE_TEXT="text";//文本消息
    public  static  final String MESSAGE_TYPE_IMAGE="image";//图片消息

    /**
     * xml转换为map
     * @param in
     * @return
     */
    public static Map<String,String> xml2Map(InputStream in){
        Map<String,String> map=new HashMap<String, String>();
        SAXReader reader=new SAXReader();
        try {
            Document doc = reader.read(in);
            Element rootElement = doc.getRootElement();
            List<Element> elements = rootElement.elements();
            for(Element e:elements){
                    map.put(e.getName(),e.getText());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    /**
     *
     * @param messageText
     * @return
     */
    public static String messageText2Xml(MessageText messageText){
        XStream xStream=new XStream();
        xStream.alias("xml",MessageText.class);
        String xml = xStream.toXML(messageText);
        return xml;
    }

    public static void main(String[] args) {
        MessageText messageText=new MessageText();
        messageText.setFromUserName("formUserName");
        messageText.setToUserName("toUserName");
        messageText.setMsgType(MESSAGE_TYPE_TEXT);
        messageText.setContent("this is a content!");
        messageText.setCreateTime(new Date().getTime());
        messageText.setMsgId("121213213123213");
        String xml = messageText2Xml(messageText);
        System.out.println(xml);
    }
}
