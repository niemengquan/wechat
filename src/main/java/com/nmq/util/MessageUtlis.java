package com.nmq.util;

import com.nmq.domain.ImageAndTextMessage;
import com.nmq.domain.MessageSubcribeOrUnSubscribe;
import com.nmq.domain.MessageText;
import com.nmq.domain.News;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by niemengquan on 2016/8/23.
 */
public class MessageUtlis {
    /**
     * 微信消息的事件类型
     */
    public  static  final String MESSAGE_TYPE_TEXT="text";//文本消息
    public  static  final String MESSAGE_TYPE_IMAGE="image";//图片消息
    public static final String MESSAGE_TYPE_EVENT="event";//event消息类型
    public static final String MESSAGE_TYPE_SUBCRIBE="subscribe";//关注事件
    public static final String MESSAGE_TYPE_UNSUBCRIBE="unsubscribe";//取消关注事件
    public static final String MESSAGE_TYPE_NEWS="news";//图文消息

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
     *文本消息类型转换为xml
     * @param messageText
     * @return
     */
    public static String messageText2Xml(MessageText messageText){
        XStream xStream=new XStream();
        xStream.alias("xml",MessageText.class);
        String xml = xStream.toXML(messageText);
        return xml;
    }

    /**
     * 关注和取消关注消息类型转换为xml
     * @param messageSubcribeOrUnSubscribe
     * @return
     */
    public static String messageSubcribeOrUnSubcribe2Xml(MessageSubcribeOrUnSubscribe messageSubcribeOrUnSubscribe){
        XStream xStream=new XStream();
        xStream.alias("xml",MessageSubcribeOrUnSubscribe.class);
        return xStream.toXML(messageSubcribeOrUnSubscribe);
    }

    /**
     * 获取关注信息
     * @return
     */
    public static String getSubcribeMessageText(){
        StringBuffer sb=new StringBuffer();
        sb.append("欢迎您的关注,你好这里是您的身边资讯专家：\n\n");
        sb.append("每天为你推荐实时热点新闻资讯，资讯内容包括：要闻、视频、财经、娱乐、体育、时尚、房地产等。\n");
        sb.append("输入？号或者帮助获取帮助菜单。");
        return sb.toString();
    }

    /**
     *获取帮助信息
     * @return
     */
    public static String getHelpText(){
        StringBuffer sb=new StringBuffer();
        sb.append("您好这里是热点资讯：\n");
        sb.append("输入1：获取房地产资讯信息。\n");
        sb.append("输入2：获取房地产资讯信息。\n");
        return sb.toString();
    }

    /**
     * 构建图文消息
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static ImageAndTextMessage getImageAndTextMessage(String toUserName,String fromUserName){
        ImageAndTextMessage imageAndTextMessage=new ImageAndTextMessage();
        imageAndTextMessage.setFromUserName(toUserName);
        imageAndTextMessage.setToUserName(fromUserName);
        imageAndTextMessage.setCreateTime(new Date().getTime());
        imageAndTextMessage.setMsgType(MESSAGE_TYPE_NEWS);
        //构造图文消息
        List<News> Articles=new ArrayList<News>();
        News news=new News();
        news.setTitle("王石、郁亮首次双双缺席万科业绩推介会");
        news.setDescription("　8月22日，万科A（000002）在大梅沙总部举办了2016年中期业绩推介会，董事会主席王石、总裁郁亮均未出席。在过去几年里，王石多次未参加业绩推介会，但郁亮自担任总裁后，几乎没有缺席过公司业绩推介会。这一次实属罕见。");
        news.setPicUrl("http://niemengquan.bceapp.com/image/vanke.png");
        news.setUrl("http://finance.sina.com.cn/stock/hkstock/ggscyd/2016-08-23/doc-ifxvcsrm2239892.shtml");
        Articles.add(news);

        imageAndTextMessage.setArticleCount(Articles.size());
        imageAndTextMessage.setArticles(Articles);
        return imageAndTextMessage;
    }

    /**
     * 图文消息bean对象转化为xml
     * @param message
     * @return
     */
    public static String imageAndTextMessage2Xml(ImageAndTextMessage message){
        XStream xStream=new XStream();
        xStream.alias("xml",ImageAndTextMessage.class);
        xStream.alias("item",News.class);
        return xStream.toXML(message);
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
        ImageAndTextMessage imageAndTextMessage = getImageAndTextMessage("fromUserNmae", "toUserName");
        String imageAndTextMessage2Xml = imageAndTextMessage2Xml(imageAndTextMessage);
        System.out.println(imageAndTextMessage2Xml);
    }
}
