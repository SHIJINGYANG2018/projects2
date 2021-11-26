package com.sjy.upload;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.sjy.common.utils.OkhttpUtils;

import javax.xml.crypto.Data;
import java.io.*;
import java.nio.charset.Charset;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

public class DownSip {
    public static void main(String[] args) throws IOException {
        String inString = "";

        ArrayList<String> arrayList = new ArrayList<>();

        try {
            CsvReader creader = new CsvReader("C:\\Users\\admin\\Downloads\\20210811.csv", ',', Charset.forName("GB18030"));
            while(creader.readRecord()){
                inString = creader.getRawRecord();//读取一行数据
                if ("".equals(inString)) {
                    continue;
                }
                arrayList.add(inString);
            }
            for (String s : arrayList) {
                String[] split = s.split(",");
                long l = 1628688751;
                String result = OkhttpUtils.get("https://sip-home-wbsipcloud.vlink.cn/api/cdrModule/cdrSipDownload?cdrCallId="+split[0]+"&cdrEnterpriseId="+split[13]+"&startTime="+l);
                writeFile(split[13]+"-"+split[0],result);
            }
            creader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void writeFile(String fileName,String result){
        try {
        String path = "D:\\ti-net\\weibao\\"+fileName+".txt";
        File file = new File(path);
        file.createNewFile();
        // write
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(result);
        bw.flush();
        bw.close();
        fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Date strToDateLong(String strDate) {
           SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
           ParsePosition pos = new ParsePosition(0);
           Date strtodate = formatter.parse(strDate, pos);
           return strtodate;
        }
}