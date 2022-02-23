package com.sjy.websocketnetty.chatbotTest;

/**
 * 移动 消息类型
 */
public enum MobileContentTypeEnum {
    TEM("template", 0), //
    DYNAMIC_TEM("dynamic-template", 1),
    TEST_PLAIN("text/plain", 3),
    MULTIPART_NEXT("multipart/mixed; boundary=\"next\"", 4),
    APPLCATION_XML("application/vnd.gsma.rcs-ft-http+xml", 5), //普通文件消息，可以是图片、音频、视频消息。
    APPLCATION_JSON("application/vnd.gsma.botmessage.v1.0+json", 6), //chatbot卡片消息
    MULTIPART_DELIMITER("multipart/mixed; boundary=\"[delimiter]\"", 7);    //携带悬浮菜单的消息
    private String name;
    private int index;
    private MobileContentTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 普通方法
    public static String getName(int index) {
        for (MobileContentTypeEnum c : MobileContentTypeEnum.values()) {
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
