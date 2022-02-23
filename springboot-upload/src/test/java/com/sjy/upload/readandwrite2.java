package com.sjy.upload;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.sjy.common.utils.OkhttpUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class readandwrite2 {
    public static void main(String[] args) throws IOException {
        String inString = "";
        String begin = "";

        boolean a =true;
        HashSet<String> strings = new HashSet<>();
        ArrayList<String> arrayList = new ArrayList<>();
        /**
         * 存在的
         */
        try {
            CsvReader creader = new CsvReader("D:\\dingDownloads\\999.csv", ',', Charset.forName("GB18030"));
            CsvWriter cwriter = new CsvWriter("D:\\ti-en\\out_999.csv",',', Charset.forName("GB18030"));
            while(creader.readRecord()){
                inString = creader.getRawRecord();//读取一行数据
                if ("".equals(inString)) {
                    continue;
                }
                arrayList.add(inString);
            }
            for (String s : arrayList) {
                String[] split = s.split(",");
                String[] strings2 = Arrays.copyOf(split, 3);
                strings2[2] =getMobiles(split[1]);
                System.out.println(strings2[2]);
                cwriter.writeRecord(strings2, true);
            }
            creader.close();
            cwriter.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static String getMobiles(String mobiles){
        HashMap<String, String> map = new HashMap<>();

        map.put("appId","LHOyxLXK");
        map.put("appKey","8h6VuWhW");
        map.put("mobiles",mobiles);
        map.put("type","0");
        String post = OkhttpUtils.post("https://api.253.com/open/unn/batch-ucheck", map);
        return post;
    }

    public static <T> List<List<T>> averageAssign(List<T> source,int n){


        List<List<T>> result=new ArrayList<List<T>>();
        int remaider=source.size()%n;  //(先计算出余数)
        int number=source.size()/n;  //然后是商
        int offset=0;//偏移量
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value=source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }

        return result;

    }
}