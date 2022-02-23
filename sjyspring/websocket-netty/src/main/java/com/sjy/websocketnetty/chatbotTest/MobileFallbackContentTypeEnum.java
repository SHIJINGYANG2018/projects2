package com.sjy.websocketnetty.chatbotTest;

/**
 * 移动 rcsBodyText的消息格式
 */
public enum MobileFallbackContentTypeEnum {
    TEST_PLAIN("text/plain", 3), //普通文本(包含地位位置消息)。5G消息系统中默认Chatbot下发位置信息采用Geolocation fallback SMS方式。
    MULTIPART_NEXT("multipart/mixed; boundary=\"next\"", 4),
    APPLCATION_XML("application/vnd.gsma.rcs-ft-http+xml", 5), //普通文件消息，可以是图片、音频、视频消息。
    APPLCATION_JSON("application/vnd.gsma.botmessage.v1.0+json", 6);
    private String name;
    private int index;
    private MobileFallbackContentTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 普通方法
    public static String getName(int index) {
        for (MobileFallbackContentTypeEnum c : MobileFallbackContentTypeEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
