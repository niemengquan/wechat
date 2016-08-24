package com.nmq.domain;

/**
 * 微信文本消息对象
 * Created by niemengquan on 2016/8/23.
 */
public class MessageText extends BaseMessage{
    private String Content;
    private String MsgId;


    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}
