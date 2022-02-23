package com.sjy.web.webtest.controller;

import com.sjy.web.webtest.chatbotTest.RcsMobileHttpUtil;
import com.sjy.web.webtest.chatbotTest.RcsSubmitMessage;
import com.sjy.web.webtest.chatbotTest.TypeConvert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

@RestController
public class WebController {

    @RequestMapping("/send")
    public String dddd(String url,String mobile,Integer contentTypeString,String appid,
                       Integer num,String appSecret ) throws Exception {

         url ="http://112.35.162.232:8078/mbmp/developer/accesslayer/messaging/group/v1/outbound/sip:"+appid+"@botplatform.rcs.chinamobile.com/requests";
        appSecret =getSHA256(appSecret);
        RcsSubmitMessage rcsSubmitMessage = new RcsSubmitMessage();
        rcsSubmitMessage.setDesAddress(mobile);

        rcsSubmitMessage.setContentType(contentTypeString);
        rcsSubmitMessage.setChatbotNumber(appid);
        String c = getContentXml(num);
        rcsSubmitMessage.setContent(c);
        rcsSubmitMessage.setFallbackSupported(true);
        rcsSubmitMessage.setFallbackContentType(3);
        rcsSubmitMessage.setFallbackRcsBodyText("<![CDATA[测试回落文本]]>");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
        String date = sdf.format(calendar.getTime());
        String authorization = TypeConvert.encodeBase64String(appid + ":" + TypeConvert.getSHA256(appSecret + date));
        String response = RcsMobileHttpUtil.httpRcsMessagePost(url , rcsSubmitMessage, authorization, date);
        System.out.println(response);
        return response;
    }
    private static String getContentXml(int which) {

        String xmlContent = "";
        switch (which) {
            case 1:
                String xmltext1 = "<![CDATA[Test bureau test!!]]>";
                xmlContent = xmltext1;
                break;
            case 2:
                String xmltext2 = "<![CDATA["
                        + "--next\n" + "Content-Type: text/plain\n" + "Content-Length: 4\n\n" + "test\n" + "--next\n"
                        + "Content-Type: application/vnd.gsma.botsuggestion.v1.0+json\n" + "Content-Length: 79\n\n"
                        + "{\"suggestions\":[{\"reply\":{\"displayText\":\"YES\",\"postback\":{\"data\":\"wyrtest\"}}}]}\n"
                        + "--next--" + "]]>";
                xmlContent = xmltext2;
                break;
            case 3:
                String xmlfile1 = "<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                        + "<file xmlns=\"urn:gsma:params:xml:ns:rcs:rcs:fthttp\" xmlns:e=\"urn:gsma:params:xml:ns:rcs:rcs:up:fthttpext\">"
                        + "<file-info type=\"thumbnail\">" + "<file-size>9307</file-size>"
                        + "<content-type>image/jpg</content-type>"
                        + "<data url=\"http://124.42.103.156:8089/group1/M00/00/18/CgrQc10dbDSAUbLLAB9-LgNBkpE811_small.png\" until=\"2019-09-04T15:04:44Z\"/>"
                        + "</file-info>" + "<file-info type=\"file\">" + "<file-size>2063918</file-size>"
                        + "<file-name>test图片.png</file-name>" + "<content-type>image/jpg</content-type>"
                        + "<data url=\"http://124.42.103.156:8089/group1/M00/00/18/CgrQc10dbDSAUbLLAB9-LgNBkpE811.png\" until=\"2019-09-04T15:04:44Z\"/>"
                        + "</file-info>" + "</file>" + "]]>";
                xmlContent = xmlfile1;
                break;
            case 4:
                String xmlfile2 = "<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                        + "<file xmlns=\"urn:gsma:params:xml:ns:rcs:rcs:fthttp\" xmlns:e=\"urn:gsma:params:xml:ns:rcs:rcs:up:fthttpext\">"
                        + "<file-info type=\"thumbnail\">" + "<file-size>7231</file-size>"
                        + "<content-type>image/jpg</content-type>"
                        + "<data url=\"http://124.42.103.156:8089/group1/M00/00/3B/CgrQc12B1WuAGzp7AAAcP6HFWRY852.jpg\" until=\"2019-09-04T15:04:44Z\"/>"
                        + "</file-info>" + "<file-info type=\"file\">" + "<file-size>2057548</file-size>"
                        + "<file-name>test视频.mp4</file-name>" + "<content-type>video/mpeg4</content-type>"
                        + "<data url=\"http://124.42.103.156:8089/group1/M00/00/3B/CgrQc12B1WuAMkC8AB9lTNbc9sU052.mp4\" until=\"2019-09-04T15:04:44Z\"/>"
                        + "</file-info>" + "</file>" + "]]>";
                xmlContent = xmlfile2;
                break;
            case 5:
                String xmlfile3 = "<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                        + "<file xmlns=\"urn:gsma:params:xml:ns:rcs:rcs:fthttp\" xmlns:e=\"urn:gsma:params:xml:ns:rcs:rcs:up:fthttpext\">"
                        + "<file-info type=\"thumbnail\">" + "<file-size>1627</file-size>"
                        + "<content-type>image/png</content-type>"
                        + "<data url=\"http://124.42.103.156:8089/group1/M00/00/3C/CgrQc12IPqqAfuiyAAAGW8W8kOM770.png\" until=\"2019-09-04T15:04:44Z\"/>"
                        + "</file-info>" + "<file-info type=\"file\">" + "<file-size>2320727</file-size>"
                        + "<file-name>test音频.mp3</file-name>" + "<file-disposition>timelen=58000</file-disposition>"
                        + "<content-type>audio/mp3</content-type>"
                        + "<data url=\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11dFSaAIB3eACNpVzc5Ktg060.mp3\" until=\"2019-09-04T15:04:44Z\"/>"
                        + "</file-info>" + "</file>" + "]]>";
                xmlContent = xmlfile3;
                break;
            case 6:
                String xmlfile4 = "<![CDATA[--next\n" + "Content-Type: application/vnd.gsma.rcs-ft-http+xml\n"
                        + "Content-Length: 653\n\n" +

                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                        + "<file xmlns=\"urn:gsma:params:xml:ns:rcs:rcs:fthttp\" xmlns:e=\"urn:gsma:params:xml:ns:rcs:rcs:up:fthttpext\">"
                        + "<file-info type=\"thumbnail\">" + "<file-size>9307</file-size>"
                        + "<content-type>image/jpg</content-type>"
                        + "<data url=\"http://124.42.103.156:8089/group1/M00/00/18/CgrQc10dbDSAUbLLAB9-LgNBkpE811_small.png\" until=\"2019-09-04T15:04:44Z\"/>"
                        + "</file-info>" + "<file-info type=\"file\">" + "<file-size>2063918</file-size>"
                        + "<file-name>test图片.png</file-name>" + "<content-type>image/jpg</content-type>"
                        + "<data url=\"http://124.42.103.156:8089/group1/M00/00/18/CgrQc10dbDSAUbLLAB9-LgNBkpE811.png\" until=\"2019-09-04T15:04:44Z\"/>"
                        + "</file-info>" + "</file>\n\n" +

                        "--next\n" + "Content-Type: application/vnd.gsma.botsuggestion.v1.0+json\n"
                        + "Content-Length: 262\n\n" +

                        "{\"suggestions\":[{\"reply\":{\"displayText\":\"测试1\",\"postback\":{\"data\":\"测试1\"}}},"
                        + "{\"reply\":{\"displayText\":\"测试2\",\"postback\":{\"data\":\"测试2\"}}},"
                        + "{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}]}\n"
                        + "--next--]]>";
                xmlContent = xmlfile4;
                break;
            case 7:
                String xmlfile5 = "<![CDATA[--next\n" + "Content-Type: application/vnd.gsma.rcs-ft-http+xml\n"
                        + "Content-Length: 649\n\n" +

                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                        + "<file xmlns=\"urn:gsma:params:xml:ns:rcs:rcs:fthttp\" xmlns:e=\"urn:gsma:params:xml:ns:rcs:rcs:up:fthttpext\">"
                        + "<file-info type=\"thumbnail\">" + "<file-size>7231</file-size>"
                        + "<content-type>image/jpg</content-type>"
                        + "<data url=\"http://124.42.103.156:8089/group1/M00/00/3B/CgrQc12B1WuAGzp7AAAcP6HFWRY852.jpg\" until=\"2019-09-04T15:04:44Z\"/>"
                        + "</file-info>" + "<file-info type=\"file\">" + "<file-size>2057548</file-size>"
                        + "<file-name>test视频.mp4</file-name>" + "<content-type>video/mpeg4</content-type>"
                        + "<data url=\"http://124.42.103.156:8089/group1/M00/00/3B/CgrQc12B1WuAMkC8AB9lTNbc9sU052.mp4\" until=\"2019-09-04T15:04:44Z\"/>"
                        + "</file-info>" + "</file>\n\n" +

                        "--next\n" + "Content-Type: application/vnd.gsma.botsuggestion.v1.0+json\n"
                        + "Content-Length: 262\n\n" +

                        "{\"suggestions\":" + "[{\"reply\":{\"displayText\":\"测试1\",\"postback\":{\"data\":\"测试1\"}}},"
                        + "{\"reply\":{\"displayText\":\"测试2\",\"postback\":{\"data\":\"测试2\"}}},"
                        + "{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},"
                        + "\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}]}\n" + "--next--]]>";
                xmlContent = xmlfile5;
                break;
            case 8:
                String xmlfile6 = "<![CDATA[--next\n" + "Content-Type: application/vnd.gsma.rcs-ft-http+xml\n"
                        + "Content-Length: 690\n\n" +

                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                        + "<file xmlns=\"urn:gsma:params:xml:ns:rcs:rcs:fthttp\" xmlns:e=\"urn:gsma:params:xml:ns:rcs:rcs:up:fthttpext\">"
                        + "<file-info type=\"thumbnail\">" + "<file-size>1627</file-size>"
                        + "<content-type>image/jpg</content-type>"
                        + "<data url=\"http://124.42.103.156:8089/group1/M00/00/3C/CgrQc12IPqqAfuiyAAAGW8W8kOM770.png\" until=\"2019-09-04T15:04:44Z\"/>"
                        + "</file-info>" + "<file-info type=\"file\">" + "<file-size>2320727</file-size>"
                        + "<file-name>test音频.mp3</file-name>" + "<file-disposition>timelen=58000</file-disposition>"
                        + "<content-type>audio/mp3</content-type>"
                        + "<data url=\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11dFSaAIB3eACNpVzc5Ktg060.mp3\" until=\"2019-09-04T15:04:44Z\"/>"
                        + "</file-info>" + "</file>\n\n" +

                        "--next\n" + "Content-Type: application/vnd.gsma.botsuggestion.v1.0+json\n"
                        + "Content-Length: 262\n\n" +

                        "{\"suggestions\":[{\"reply\":{\"displayText\":\"测试1\",\"postback\":{\"data\":\"测试1\"}}},"
                        + "{\"reply\":{\"displayText\":\"测试2\",\"postback\":{\"data\":\"测试2\"}}},"
                        + "{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},"
                        + "\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}]}\n" + "--next--]]>";
                xmlContent = xmlfile6;
                break;
            case 9:
                String xmlcard1 = "<![CDATA[{ \"message\":{ \"generalPurposeCard\":{ \"layout\":{ \"cardOrientation\":\"VERTICAL\" }, "
                        + "\"content\":{ \"description\":\"【hello】。\", \"suggestions\":["
                        + " { \"reply\":{ \"displayText\":\"订单详情\",\"postback\":{ \"data\":\"订单详情\" } } },"
                        + " { \"reply\":{ \"displayText\":\"寄放在代收点\", \"postback\":{ \"data\":\"寄放在代收点\" } } },"
                        + " { \"reply\":{ \"displayText\":\"指定派送时间\", \"postback\":{ \"data\":\"指定派送时间\" } } } ] } } }}]]>";
                xmlContent = xmlcard1;
                break;
            case 10:
                String xmlcard2 = "<![CDATA[--next\n" + "Content-Type: application/vnd.gsma.botmessage.v1.0+json\n"
                        + "Content-Length: 386\n\n" +

                        "{\"message\":{\"generalPurposeCard\":{\"layout\":{\"cardOrientation\":\"VERTICAL\"},\"content\":{\"description\":\"【hello】\",\"suggestions\":[{\"reply\":{ \"displayText\":\"订单详情\",\"postback\":{\"data\":\"订单详情\"}}},"
                        + "{\"reply\":{\"displayText\":\"寄放在代收点\",\"postback\":{\"data\":\"寄放在代收点\"}}},{\"reply\":{\"displayText\":\"指定派送时间\",\"postback\":{\"data\":\"指定派送时间\"}}}]}}}}\n\n"
                        +

                        "--next\n" + "Content-Type: application/vnd.gsma.botsuggestion.v1.0+json\n"
                        + "Content-Length: 226\n\n" +

                        "{\"suggestions\":[{\"action\":{\"dialerAction\":{\"dialPhoneNumber\":{\"phoneNumber\":\"17745632640\"}},\"displayText\":\"123213\",\"postback\":{\"data\":\"423\"}}},{\"reply\":{\"displayText\":\"111111111111111111111111\",\"postback\":{\"data\":\"上线\"}}}]}\n"
                        + "--next--]]>";
                xmlContent = xmlcard2;
                break;
            case 11:
                String xmlcard3 = "<![CDATA[{\"message\":{\"generalPurposeCard\":"
                        + "{\"content\":{\"description\":\"三生三世\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"image/jpeg\","
                        + "\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991.png\","
                        + "\"thumbnailContentType\":\"image/png\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991_small.png\"},"
                        + "\"suggestions\":[],\"title\":\"三生三世\"},\"layout\":{\"cardOrientation\":\"VERTICAL\",\"cardWidth\":\"MEDIUM_WIDTH\"}}}}]]>";
                xmlContent = xmlcard3;
                break;
            case 12:
                String xmlcard4 = "<![CDATA[{\"message\":{\"generalPurposeCard\":{\"content\":{\"description\":\"视频测试\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"video/mpeg4\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11aCb-AdvJyABu8bsQPdC0906.mp4\",\"thumbnailContentType\":\"image/jpg\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11aCdeARogcAAC8DrcIIFY575.jpg\"},\"suggestions\":[],\"title\":\"视频测试\"},\"layout\":{\"cardOrientation\":\"VERTICAL\",\"cardWidth\":\"MEDIUM_WIDTH\"}}}}]]>";
                xmlContent = xmlcard4;
                break;
            case 13:
                String xmlcard5 = "<![CDATA[{\"message\":{\"generalPurposeCard\":{\"content\":{\"description\":\"春暖花开1\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"audio/mp3\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11dFSaAIB3eACNpVzc5Ktg060.mp3\",\"thumbnailContentType\":\"image/jpg\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/3C/CgrQc12IPqqAfuiyAAAGW8W8kOM770.png\"},\"suggestions\":[],\"title\":\"春暖花开\"},\"layout\":{\"cardOrientation\":\"VERTICAL\",\"cardWidth\":\"MEDIUM_WIDTH\"}}}}]]>";
                xmlContent = xmlcard5;
                break;
            case 14:
                String xmlcard6 = "<![CDATA[{\"message\":{\"generalPurposeCard\":{\"content\":{\"description\":\"视频测试\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"video/mpeg4\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11aCb-AdvJyABu8bsQPdC0906.mp4\",\"thumbnailContentType\":\"image/jpg\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11aCdeARogcAAC8DrcIIFY575.jpg\"},\"suggestions\":[{\"reply\":{\"displayText\":\"测试1\",\"postback\":{\"data\":\"测试1\"}}},{\"reply\":{\"displayText\":\"测试2\",\"postback\":{\"data\":\"测试2\"}}},{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}],\"title\":\"视频测试\"},\"layout\":{\"cardOrientation\":\"VERTICAL\",\"cardWidth\":\"MEDIUM_WIDTH\"}}}}]]>";
                xmlContent = xmlcard6;
                break;
            case 15:
                String xmlcard7 = "<![CDATA[--next\n"
                        + "Content-Type: application/vnd.gsma.botmessage.v1.0+json\n" + "Content-Length: 472\n\n" +

                        "{\"message\":{\"generalPurposeCard\":{\"content\":{\"description\":\"春暖花开\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"audio/mp3\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11dFSaAIB3eACNpVzc5Ktg060.mp3\",\"thumbnailContentType\":\"image/jpg\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/3C/CgrQc12IPqqAfuiyAAAGW8W8kOM770.png\"},\"suggestions\":[],\"title\":\"春暖花开\"},\"layout\":{\"cardOrientation\":\"VERTICAL\",\"cardWidth\":\"MEDIUM_WIDTH\"}}}}\n"
                        + "--next\n" + "Content-Type: application/vnd.gsma.botsuggestion.v1.0+json\n"
                        + "Content-Length: 262\n\n" +

                        "{\"suggestions\":[{\"reply\":{\"displayText\":\"测试1\",\"postback\":{\"data\":\"测试1\"}}},{\"reply\":{\"displayText\":\"测试2\",\"postback\":{\"data\":\"测试2\"}}},{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}]}\n"
                        + "--next--]]>";
                xmlContent = xmlcard7;
                break;
            case 16:
                String xmlcard8 = "<![CDATA[--next\n"
                        + "Content-Type: application/vnd.gsma.botmessage.v1.0+json\n" + "Content-Length: 657\n\n" +

                        "{\"message\":{\"generalPurposeCard\":{\"content\":{\"description\":\"三生三世\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"image/jpeg\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991.png\",\"thumbnailContentType\":\"image/png\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991_small.png\"},\"suggestions\":[{\"reply\":{\"displayText\":\"测试1\",\"postback\":{\"data\":\"测试1\"}}},{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}],\"title\":\"三生三世\"},\"layout\":{\"cardOrientation\":\"VERTICAL\",\"cardWidth\":\"MEDIUM_WIDTH\"}}}}\n"
                        + "--next\n" + "Content-Type: application/vnd.gsma.botsuggestion.v1.0+json\n"
                        + "Content-Length: 262\n\n" +

                        "{\"suggestions\":[{\"reply\":{\"dis\"layText\":\"测试1\",\"postback\":{\"data\":\"测试1\"}}},{\"reply\":{\"displayText\":\"测试2\",\"postback\":{\"data\":\"测试2\"}}},{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}]}\n"
                        + "--next--]]>";
                xmlContent = xmlcard8;
                break;
            case 17:
                String xmlcard9 = "<![CDATA[{\"message\":{\"generalPurposeCardCarousel\":{\"content\":[{\"description\":\"三生三世\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"image/jpeg\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991.png\",\"thumbnailContentType\":\"image/png\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991_small.png\"},\"suggestions\":[],\"title\":\"三生三世\"},{\"description\":\"春暖花开\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"audio/mp3\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11dFSaAIB3eACNpVzc5Ktg060.mp3\",\"thumbnailContentType\":\"image/jpg\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/3C/CgrQc12IPqqAfuiyAAAGW8W8kOM770.png\"},\"suggestions\":[],\"title\":\"春暖花开\"}],\"layout\":{\"cardOrientation\":\"VERTICAL\",\"cardWidth\":\"MEDIUM_WIDTH\"}}}}]]>";
                xmlContent = xmlcard9;
                break;
            case 18:
                String xmlcard10 = "<![CDATA[{\"message\":{\"generalPurposeCardCarousel\":{\"content\":[{\"description\":\"三生三世\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"image/jpeg\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991.png\",\"thumbnailContentType\":\"image/png\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991_small.png\"},\"suggestions\":[],\"title\":\"三生三世\"},{\"description\":\"视频测试\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"video/mpeg4\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11aCb-AdvJyABu8bsQPdC0906.mp4\",\"thumbnailContentType\":\"image/jpg\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11aCdeARogcAAC8DrcIIFY575.jpg\"},\"suggestions\":[],\"title\":\"视频测试\"},{\"description\":\"春暖花开\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"audio/mp3\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11dFSaAIB3eACNpVzc5Ktg060.mp3\",\"thumbnailContentType\":\"image/jpg\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/3C/CgrQc12IPqqAfuiyAAAGW8W8kOM770.png\"},\"suggestions\":[],\"title\":\"春暖花开\"}],\"layout\":{\"cardOrientation\":\"VERTICAL\",\"cardWidth\":\"MEDIUM_WIDTH\"}}}}]]>";
                xmlContent = xmlcard10;
                break;
            case 19:
                String xmlcard11 = "<![CDATA[{\"message\":{\"generalPurposeCardCarousel\":{\"content\":[{\"description\":\"三生三世\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"image/jpeg\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991.png\",\"thumbnailContentType\":\"image/png\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991_small.png\"},\"suggestions\":[{\"reply\":{\"displayText\":\"测试1\",\"postback\":{\"data\":\"测试1\"}}},{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}],\"title\":\"三生三世\"},{\"description\":\"视频测试\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"video/mpeg4\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11aCb-AdvJyABu8bsQPdC0906.mp4\",\"thumbnailContentType\":\"image/jpg\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11aCdeARogcAAC8DrcIIFY575.jpg\"},\"suggestions\":[{\"reply\":{\"displayText\":\"测试1\",\"postback\":{\"data\":\"测试1\"}}},{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}],\"title\":\"视频测试\"},{\"description\":\"春暖花开\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"audio/mp3\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11dFSaAIB3eACNpVzc5Ktg060.mp3\",\"thumbnailContentType\":\"image/png\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/3C/CgrQc12IPqqAfuiyAAAGW8W8kOM770.png\"},\"suggestions\":[{\"reply\":{\"displayText\":\"测试1\",\"postback\":{\"data\":\"测试1\"}}},{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}],\"title\":\"春暖花开\"}],\"layout\":{\"cardOrientation\":\"VERTICAL\",\"cardWidth\":\"MEDIUM_WIDTH\"}}}}]]>";
                xmlContent = xmlcard11;
                break;
            case 20:
                String xmlcard12 = "<![CDATA[--next\n"
                        + "Content-Type: application/vnd.gsma.botmessage.v1.0+json\n" + "Content-Length: 1209\n\n" +

                        "{\"message\":{\"generalPurposeCardCarousel\":{\"content\":[{\"description\":\"三生三世\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"image/jpeg\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991.png\",\"thumbnailContentType\":\"image/png\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991_small.png\"},\"suggestions\":[],\"title\":\"三生三世\"},{\"description\":\"视频测试\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"video/mpeg4\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11aCb-AdvJyABu8bsQPdC0906.mp4\",\"thumbnailContentType\":\"image/jpg\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11aCdeARogcAAC8DrcIIFY575.jpg\"},\"suggestions\":[],\"title\":\"视频测试\"},{\"description\":\"春暖花开\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"audio/mp3\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11dFSaAIB3eACNpVzc5Ktg060.mp3\",\"thumbnailContentType\":\"image/png\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/3C/CgrQc12IPqqAfuiyAAAGW8W8kOM770.png\"},\"suggestions\":[],\"title\":\"春暖花开\"}],\"layout\":{\"cardOrientation\":\"VERTICAL\",\"cardWidth\":\"MEDIUM_WIDTH\"}}}}\n"
                        + "--next\n" + "Content-Type: application/vnd.gsma.botsuggestion.v1.0+json\n"
                        + "Content-Length: 262\n\n" +

                        "{\"suggestions\":[{\"reply\":{\"displayText\":\"测试1\",\"postback\":{\"data\":\"测试1\"}}},{\"reply\":{\"displayText\":\"测试2\",\"postback\":{\"data\":\"测试2\"}}},{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}]}\n"
                        + "--next--]]>";
                xmlContent = xmlcard12;
                break;
            case 21:
                String xmlcard13 = "<![CDATA[--next\n"
                        + "Content-Type: application/vnd.gsma.botmessage.v1.0+json\n" + "Content-Length: 1743\n\n" +

                        "{\"message\":{\"generalPurposeCardCarousel\":"
                        + "{\"content\":[{\"description\":\"三生三世\",\"media\":"
                        + "{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"image/jpeg\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991.png\","
                        + "\"thumbnailContentType\":\"image/png\","
                        + "\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991_small.png\"},"
                        + "\"suggestions\":" + "[{\"reply\":{\"displayText\":\"测试1\","
                        + "\"postback\":{\"data\":\"测试1\"}}},"
                        + "{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}],"
                        + "\"title\":\"三生三世\"},{\"description\":\"视频测试\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"video/mpeg4\","
                        + "\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11aCb-AdvJyABu8bsQPdC0906.mp4\",\"thumbnailContentType\":\"image/jpg\","
                        + "\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11aCdeARogcAAC8DrcIIFY575.jpg\"},"
                        + "\"suggestions\":[{\"reply\":{\"displayText\":\"测试2\",\"postback\":{\"data\":\"测试2\"}}},"
                        + "{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}],\"title\":\"视频测试\"},"
                        + "{\"description\":\"春暖花开\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"audio/mp3\","
                        + "\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11dFSaAIB3eACNpVzc5Ktg060.mp3\","
                        + "\"thumbnailContentType\":\"image/jpg\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/3C/CgrQc12IPqqAfuiyAAAGW8W8kOM770.png\"},"
                        + "\"suggestions\":[{\"reply\":{\"displayText\":\"测试3\",\"postback\":{\"data\":\"测试3\"}}},"
                        + "{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},"
                        + "\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}],\"title\":\"春暖花开\"}],"
                        + "\"layout\":{\"cardOrientation\":\"VERTICAL\",\"cardWidth\":\"MEDIUM_WIDTH\"}}}}\n" + "--next\n"
                        + "Content-Type: application/vnd.gsma.botsuggestion.v1.0+json\n" + "Content-Length: 262\n\n" +

                        "{\"suggestions\":[{\"reply\":{\"displayText\":\"测试1\",\"postback\":{\"data\":\"测试1\"}}},{\"reply\":{\"displayText\":\"测试2\",\"postback\":{\"data\":\"测试2\"}}},{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}]}\n"
                        + "--next--]]>";
                xmlContent = xmlcard13;
                break;
            case 100:
                String xmlSelf = "<![CDATA[{\"message\":"
                        + "{\"generalPurposeCardCarousel\":"
                        + "{\"content\":[{\"description\":\"三生三世\",\"media\":{\"height\":\"SHORT_HEIGHT\","
                        + "\"mediaContentType\":\"image/jpeg\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991.png\","
                        + "\"thumbnailContentType\":\"image/png\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991_small.png\"},"
                        + "\"suggestions\":[],\"title\":\"三生三世\"},"
                        + "{\"description\":\"春暖花开\",\"media\":{\"height\":\"SHORT_HEIGHT\""
                        + ",\"mediaContentType\":\"audio/mp3\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11dFSaAIB3eACNpVzc5Ktg060.mp3\","
                        + "\"thumbnailContentType\":\"image/jpg\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/3C/CgrQc12IPqqAfuiyAAAGW8W8kOM770.png\"},"
                        + "\"suggestions\":[],\"title\":\"春暖花开\"}],"
                        + "\"layout\":{\"cardOrientation\":\"VERTICAL\",\"cardWidth\":\"MEDIUM_WIDTH\"}}}}]]>";
                xmlContent = xmlSelf;
                break;
            case 101:
                String xmlSelf2 = "<![CDATA[--next\n" +
                        "Content-Type: application/vnd.gsma.botmessage.v1.0+json\n" +
                        "Content-Length: 1744\n\n"

                        + "{\"message\":{\"generalPurposeCardCarousel\":"
                        + "{\"content\":["
                        + "{\"description\":\"三生三世\","
                        + "\"media\":"
                        + "{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"image/jpeg\",\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991.png\","
                        + "\"thumbnailContentType\":\"image/png\","
                        + "\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/17/CgrQc10ax2OAAkdaABXHLlcU5h8991_small.png\"},"
                        + "\"suggestions\":"
                        + "[{\"reply\":{\"displayText\":\"测试1\",\"postback\":{\"data\":\"测试1\"}}},"
                        + "{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},"
                        + "\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}],"
                        + "\"title\":\"三生三世\"},"

                        + "{\"description\":\"视频测试\","
                        + "\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"video/mpeg4\","
                        + "\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11aCb-AdvJyABu8bsQPdC0906.mp4\",\"thumbnailContentType\":\"image/jpg\","
                        + "\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11aCdeARogcAAC8DrcIIFY575.jpg\"},"
                        + "\"suggestions\":[{\"reply\":{\"displayText\":\"测试2\",\"postback\":{\"data\":\"测试2\"}}},"
                        + "{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},"
                        + "\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}],"
                        + "\"title\":\"视频测试\"},"

                        + "{\"description\":\"春暖花开\",\"media\":{\"height\":\"SHORT_HEIGHT\",\"mediaContentType\":\"audio/mp3\","
                        + "\"mediaUrl\":\"http://124.42.103.156:8089/group1/M00/00/35/CgrQc11dFSaAIB3eACNpVzc5Ktg060.mp3\","
                        + "\"thumbnailContentType\":\"image/jpg\",\"thumbnailUrl\":\"http://124.42.103.156:8089/group1/M00/00/3C/CgrQc12IPqqAfuiyAAAGW8W8kOM770.png\"},"
                        + "\"suggestions\":[{\"reply\":{\"displayText\":\"测试3\",\"postback\":{\"data\":\"测试3\"}}},"
                        + "{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},"
                        + "\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}],\"title\":"
                        + "\"春暖花开\"}],"
                        + "\"layout\":{\"cardOrientation\":\"VERTICAL\",\"cardWidth\":\"MEDIUM_WIDTH\"}}}}\n" +

                        "--next\n" +
                        "Content-Type: application/vnd.gsma.botsuggestion.v1.0+json\n" +
                        "Content-Length: 263\n\n" +

                        "{\"suggestions\":[{\"reply\":{\"displayText\":\"测试1\",\"postback\":{\"data\":\"测试1\"}}},"
                        + "{\"reply\":{\"displayText\":\"测试2\",\"postback\":{\"data\":\"测试2\"}}},"
                        + "{\"action\":{\"displayText\":\"ceshi\",\"postback\":{\"data\":\"2432\"},\"urlAction\":{\"openUrl\":{\"url\":\"http://baidu.com\"}}}}]}\n" +
                        "--next--]]>";
                xmlContent = xmlSelf2;
                break;
            default:
                break;
        }

        return xmlContent;
    }
    public static String getSHA256(String info) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(info.getBytes("UTF-8"));
            return byteArrayToHexString(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    private static String HexChar[] = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return String.valueOf(HexChar[d1]) + String.valueOf(HexChar[d2]);
    }

    /**
     * 将二进制数据流转换成对应的16进制的字符串
     *
     * @param b byte[] 原始二进制数组
     * @return String 转换以后的字符串
     */
    public static String byteArrayToHexString(byte b[]) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result = String.valueOf(result)
                    + String.valueOf(byteToHexString(b[i]));
        }

        return result;
    }
}
