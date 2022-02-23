package com.sjy.web.webtest.chatbotTest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class TypeConvert {


    public static String encodeBase64String(String info) throws Exception {
        return new BASE64Encoder().encode(info.getBytes("utf-8"));
    }

    public static String getSHA256(String path) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(path.getBytes("UTF-8"));
        return byteArrayToHexString(messageDigest.digest());

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

    public static int byte2int(byte b[], int offset) {
        return b[offset + 3] & 0xff | (b[offset + 2] & 0xff) << 8 | (b[offset + 1] & 0xff) << 16 | (b[offset] & 0xff) << 24;
    }

    /**
     * 将数字转成相应的字母
     *
     * @param x
     * @return 旧方法，新版本中已经不适用。
     */
    public static String toLetterString(int x) {
        if (x < 1) {
            x = 1;
        }

        if (x < 27) {
            return String.valueOf((char) ('a' + x - 1));
        }
        if (x % 26 == 0) {
            return toLetterString(x / 26 - 1);
        }

        return String.valueOf((char) ('a' + x % 26 - 1));
    }

    // 将协议中内容头转成2位16进制字符
    public static String getHexString(byte b) throws Exception {

        String result = Integer.toString((b & 0xff) + 0x100, 16).substring(1);
        return result;
    }

    public static String getHexDump(byte[] data) {
        String dump = "";
        try {
            int dataLen = data.length;
            for (int i = 0; i < dataLen; i++) {
                dump += Character.forDigit((data[i] >> 4) & 0x0f, 16);
                dump += Character.forDigit(data[i] & 0x0f, 16);
            }
        } catch (Throwable t) {
            dump = "Throwable caught when dumping = " + t;
        }
        return dump;
    }

    public static void long2byte(long n, byte buf[], int offset) {
        buf[offset] = (byte) (int) (n >> 56);
        buf[offset + 1] = (byte) (int) (n >> 48);
        buf[offset + 2] = (byte) (int) (n >> 40);
        buf[offset + 3] = (byte) (int) (n >> 32);
        buf[offset + 4] = (byte) (int) (n >> 24);
        buf[offset + 5] = (byte) (int) (n >> 16);
        buf[offset + 6] = (byte) (int) (n >> 8);
        buf[offset + 7] = (byte) (int) n;
    }

    public static byte[] stringToByteBuf(String str, int maxLen) {
        byte[] bTemp = new byte[maxLen];
        byte[] bytes = str.getBytes();
        int len = bytes.length;

        if (len > maxLen) {
            // BytesCopy(bytes, bTemp, 0, maxLen - 1, 0);
            BytesCopy_20(bytes, bTemp, 0, maxLen - 1, 0);
        } else {
            // BytesCopy(bytes, bTemp, 0, len - 1, 0);
            BytesCopy_20(bytes, bTemp, 0, len - 1, 0);
        }
        return bTemp;
    }

    public static void BytesCopy(byte source[], byte dest[], int sourcebegin,
                                 int sourceend, int destbegin) {
        int j = 0;
        for (int i = sourcebegin; i <= sourceend; i++) {
            dest[destbegin + j] = source[i];
            j++;
        }
    }

    public static int stringToInt(String str) {
        if (str == null || str.trim().equals(""))
            return 0;
        else
            return new Integer(str.trim());
    }

    // 20130129 pq add 若位数不能存满补空格(空格的ASCII码为0x20)
    public static void BytesCopy_20(byte source[], byte dest[],
                                    int sourcebegin, int sourceend, int destbegin) {
        int maxLen = dest.length;
        int j = 0;
        for (int i = sourcebegin; i < maxLen; i++) {
            if (i <= sourceend) {
                dest[destbegin + j] = source[i];
            } else {
                dest[destbegin + j] = 0x20;
            }
            j++;
        }
    }

    public static String getStrFromByteBuf(ByteBuffer buf, int maxLen) {
        byte[] bTemp = new byte[maxLen];
        buf.get(bTemp, 0, maxLen);

        int len = 0;
        for (int i = 0; i < maxLen; i++) {
            if (bTemp[i] == 0x00) {
                break;
            }
            len++;
        }
        return new String(bTemp, 0, len).trim();
    }

    public static String getCharFromByteBuf(ByteBuffer buf, int maxLen) {
        byte[] bTemp = new byte[maxLen];
        buf.get(bTemp, 0, maxLen);

        int len = 0;
        for (int i = 0; i < maxLen; i++) {
            if (bTemp[i] == 0x00) {
                break;
            }
            len++;
        }
        return new String(bTemp, 0, len);
    }

    public static byte[] getByteFromByteBuf(ByteBuffer buf, int maxLen) {
        byte[] bTemp = new byte[maxLen];
        buf.get(bTemp, 0, maxLen);
        return bTemp;
    }

    public static int byteToInt(byte b) {
        return b;
    }

    public static byte intToByte(int i) {
        return (byte) i;
    }

    public static int bytesToInt(byte bytes[]) {
        return (0xff & bytes[0]) << 24 | (0xff & bytes[1]) << 16
                | (0xff & bytes[2]) << 8 | bytes[3];
    }

    public static byte[] intToBytes(int i) {
        byte bytes[] = new byte[2];
        bytes[1] = (byte) (0xff & i);
        bytes[0] = (byte) ((0xff00 & i) >> 8);
        return bytes;
    }

    public static byte[] intToBytes4(int i) {
        byte bytes[] = new byte[4];
        bytes[3] = (byte) (0xff & i);
        bytes[2] = (byte) ((0xff00 & i) >> 8);
        bytes[1] = (byte) ((0xff0000 & i) >> 16);
        bytes[0] = (byte) ((0xff000000 & i) >> 24);
        return bytes;
    }

    public static byte[] longToBytes8(long l) {
        byte bytes[] = new byte[8];
        bytes[7] = (byte) (int) ((long) 255 & l);
        bytes[6] = (byte) (int) (((long) 65280 & l) >> 8);
        bytes[5] = (byte) (int) (((long) 0xff0000 & l) >> 16);
        bytes[4] = (byte) (int) (((long) 0xff000000 & l) >> 24);
        int high = (int) (l >> 32);
        bytes[3] = (byte) (0xff & high);
        bytes[2] = (byte) ((0xff00 & high) >> 8);
        bytes[1] = (byte) ((0xff0000 & high) >> 16);
        bytes[0] = (byte) ((0xff000000 & high) >> 24);
        return bytes;
    }

    public static void intToBytes(int i, byte bytes[]) {
        bytes[1] = (byte) (0xff & i);
        bytes[0] = (byte) ((0xff00 & i) >> 8);
    }

    public static void IntToBytes4(int i, byte bytes[]) {
        bytes[3] = (byte) (0xff & i);
        bytes[2] = (byte) ((0xff00 & i) >> 8);
        bytes[1] = (byte) ((0xff0000 & i) >> 16);
        bytes[0] = (byte) ((0xff000000 & i) >> 24);
    }

    public static int Bytes4ToInt(byte bytes[]) {
        return (0xff & bytes[0]) << 24 | (0xff & bytes[1]) << 16
                | (0xff & bytes[2]) << 8 | 0xff & bytes[3];

    }

    public static long Bytes8ToLong(byte bytes[]) {
        return (0xff & bytes[0]) << 56 | (0xff & bytes[1]) << 48
                | (0xff & bytes[2]) << 40 | (0xff & bytes[3]) << 32
                | (0xff & bytes[4]) << 24 | (0xff & bytes[5]) << 16
                | (0xff & bytes[6]) << 8 | 0xff & bytes[7];
    }

    public static void setFieldData(DataOutput dos, String value, int len) {
        value = leftFillSpace(value, len);
        byte[] bytes = stringToByteBuf(value, len);
        try {
            dos.write(bytes);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * 将长度不足的数据进行左补空格
     *
     * @param len   数据最大长度
     * @param value 需处理的数据
     * @return 结果
     */
    public static String leftFillSpace(long value, int len) {
        String result = String.valueOf(value);
        while (result.length() < len) {
            result = " " + result;
        }
        return result;
    }

    public static String leftFillSpace(String value, int len) {
        String result = value;

        while (result.length() < len) {
            result = " " + result;
        }

        return result;
    }

    /**
     * 将16进制的字串转换成对应的二进制数据流
     *
     * @param vStr   String 原始字符串
     * @param MaxLen int 转换成字节数组后的最大长度
     * @return byte[] 转换以后的二进制数据流
     */
    public static byte[] hexStrToBytesBuf(String vStr, int MaxLen) {
        byte[] btemp = new byte[MaxLen];
        vStr = vStr.trim();
        int strlen = vStr.length();
        int j = strlen / 2;
        if (j >= MaxLen) {
            strlen = MaxLen * 2;
        } else {
            strlen = j * 2;
        }
        ByteBuffer buf = ByteBuffer.allocate(MaxLen);
        char[] c = vStr.toCharArray();
        byte bb;
        String s = null;
        for (int i = 0; i < strlen; i += 2) {
            s = new String(c, i, 2);
            try {
                bb = (byte) (Integer.parseInt(s, 16) & 0xFFFFFFFF);
                buf.put(bb);
            } catch (NumberFormatException ex) {
                throw new NumberFormatException(ex.toString());
            }

        }
        btemp = buf.array();
        return btemp;
    }

    // 根据规定长度拆分短信
    public static String[] splitMsg(String Text) {
        String[] Str;
        int iMax = 67;
        String eStr = "";
        if (Text.length() <= iMax) {
            Str = new String[1];
            Str[0] = Text;
        } else {
            int m = iMax - eStr.length();
            int c = (Text.length() + m - 1) / m;

            Str = new String[c];
            for (int i = 0; i < c; i++) {
                if (i == c - 1)
                    Str[i] = Text.substring(i * m);
                else
                    Str[i] = Text.substring(i * m, i * m + m) + eStr;
            }
        }
        return Str;
    }

    // 根据规定长度拆分短信
    public static String[] splitMsg_2(String Text) {
        String[] Str;
        int iMax = 67;
        String eStr = "";
        if (Text.length() <= iMax) {
            Str = new String[2];
            Str[0] = Text;
            Str[1] = " ";
        } else {
            int m = iMax - eStr.length();
            int c = (Text.length() + m - 1) / m;

            Str = new String[c + 1];
            for (int i = 0; i < c; i++) {
                if (i == c - 1)
                    Str[i] = Text.substring(i * m);
                else
                    Str[i] = Text.substring(i * m, i * m + m) + eStr;
            }
            Str[c] = " ";
        }
        return Str;
    }

    // 根据规定长度170拆分短信
    public static String[] splitMsg170(String Text) {
        String[] Str;
        int iMax = 67;
        String eStr = "";
        if (Text.length() <= 170) {
            Str = new String[1];
            Str[0] = Text;
        } else {
            int m = iMax - eStr.length();
            int c = (Text.length() + m - 1) / m;

            Str = new String[c];
            for (int i = 0; i < c; i++) {
                if (i == c - 1)
                    Str[i] = Text.substring(i * m);
                else
                    Str[i] = Text.substring(i * m, i * m + m) + eStr;
            }
        }
        return Str;
    }

    public static byte[] HexStringTobyteArray(String S) {
        byte[] b = new byte[S.length() / 2];

        for (int i = 0; i < S.length(); i = i + 2) {
            b[i / 2] = HexStringTobyte(S.substring(i, i + 2));
        }

        return b;
    }


    public static byte HexStringTobyte(String S) {
        int i = (int) S.toUpperCase().charAt(0);
        int j = (int) S.toUpperCase().charAt(1);
        if (i > (int) '9')
            i = i - (int) 'A' + 10;
        else
            i = i - (int) '0';
        if (j > (int) '9')
            j = j - (int) 'A' + 10;
        else
            j = j - (int) '0';

        return (byte) (i * 16 + j);
    }


    public static long Bytes4ToLong(byte abyte0[]) {
        return (255L & (long) abyte0[0]) << 24
                | (255L & (long) abyte0[1]) << 16
                | (255L & (long) abyte0[2]) << 8 | 255L & (long) abyte0[3];
    }

    public static byte[] int2byte(int n) {
        byte b[] = new byte[4];
        b[0] = (byte) (n >> 24);
        b[1] = (byte) (n >> 16);
        b[2] = (byte) (n >> 8);
        b[3] = (byte) n;
        return b;
    }


    public static byte[] BASE64StringToBin(String base64) {
        byte[] data = null;

        BASE64Decoder dec = new BASE64Decoder();
        try {
            data = dec.decodeBuffer(base64);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    // 将 s 进行 BASE64 编码
    public static String getBASE64(String s) {
        try {
            if (s == null) return null;
            return (new BASE64Encoder()).encode(s.getBytes("GBK"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 将 BASE64 编码的字符串 s 进行解码
    public static String getFromBASE64(String s) {
        if (s == null) return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        /*	String[] a=splitMsg170("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十180字");
			int len = a == null ? 0 : a.length;
			for (int i = 0; i < a.length; i++) {
				System.out.println("第"+i+"条"+a[i]);
			}
			System.out.println(len);
			String sendid="00";
			System.out.println(HexStringTobyteArray(sendid));*/

//			String str="啦啦啦啦啦了";
//			String string = new String(str.getBytes(),"utf-8");
//			System.out.println(string);
//			String base64 = getBASE64(string);
//			System.out.println(base64);
//			String fromBASE64 = getFromBASE64(base64);
//			System.out.println(fromBASE64);

        String str = "啦啦啦啦啦了";
        String base64 = getBASE64(str);
        System.out.println(base64);
        String fromBASE64 = getFromBASE64(base64);
        System.out.println(fromBASE64);

    }
}
