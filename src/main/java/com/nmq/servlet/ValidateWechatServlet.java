package com.nmq.servlet;

import com.nmq.domain.MessageText;
import com.nmq.util.MessageUtlis;
import com.nmq.util.ValidateUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by niemengquan on 2016/8/17.
 */
public class ValidateWechatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        PrintWriter pw=resp.getWriter();
        if(ValidateUtils.encode(timestamp,nonce).equals(signature)){
            pw.write(echostr);
        }
    }

    /**
     * 微信发送消息接收
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        ServletOutputStream outputStream = resp.getOutputStream();
        PrintWriter pw=new PrintWriter(outputStream);
        ServletInputStream inputStream = req.getInputStream();
        Map<String, String> map = MessageUtlis.xml2Map(inputStream);
       /*
       //调试使用的信息
       Set<String> keySet = map.keySet();
        for(String key:keySet){
            System.out.println(key+":"+map.get(key));
        }*/
        if(map.get("MsgType").equals(MessageUtlis.MESSAGE_TYPE_TEXT)){
            //文本消息
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String content = map.get("Content");
            String msgId = map.get("MsgId");
            //构建回复消息
             MessageText messageText=new MessageText();
            messageText.setFromUserName(toUserName);
            messageText.setToUserName(fromUserName);
            messageText.setMsgId(msgId);
            messageText.setMsgType(MessageUtlis.MESSAGE_TYPE_TEXT);
            messageText.setCreateTime(new Date().getTime());
            messageText.setContent("我们已经收到您发送的消息："+content);
            String returnMessage = MessageUtlis.messageText2Xml(messageText);
         /*   System.out.println("-----------------------------------------");
            System.out.println(returnMessage);*/
            pw.write(returnMessage);
            pw.close();
            outputStream.close();
        }
    }
}
