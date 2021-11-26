package com.sjy.upload;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * webcall 话单按公司去重
 */
public class readandwrite {
    public static void main(String[] args) throws IOException { 
        String inString = "";
        String begin = "";

        boolean a =true;
        HashSet<String> strings = new HashSet<>();
        /**
         * 存在的
         */
        HashSet<String> strings1 = new HashSet<>();
        HashMap<String, Object> idToInfo = new HashMap<>();
        try {
            CsvReader creader = new CsvReader("D:\\安装包\\6.7-6.8.csv", ',', Charset.forName("GB18030"));

            while(creader.readRecord()){
                inString = creader.getRawRecord();//读取一行数据
                if (a){
                    begin = inString;
                    a=false;
                }


                if ("".equals(inString)) {
                    continue;
                }
                String[] split = inString.split(",");
                String uniqueId = split[2];
                if (strings.contains(uniqueId)) {
                    if ((strings1.contains(uniqueId))) {
                        continue;
                    }
                    strings1.add(uniqueId);

                    String cdrEnterpriseId = split[0];
                    if ((idToInfo.containsKey(cdrEnterpriseId))) {
                        CsvWriter csvWriter = (CsvWriter) idToInfo.get(cdrEnterpriseId);
                        csvWriter.writeRecord(split, true);
                        csvWriter.flush();//刷新数据
                        continue;
                    }
                    CsvWriter cwriter = new CsvWriter("D:\\ti-net\\chongfu\\out_"+ cdrEnterpriseId +".csv",',', Charset.forName("GB18030"));
                    idToInfo.put(cdrEnterpriseId,cwriter);
                    //第一个参数表示要写入的字符串数组，每一个元素占一个单元格，第二个参数为true时表示写完数据后自动换行
                    cwriter.writeRecord(begin.split(","), true);
                    cwriter.writeRecord(split, true);
                    cwriter.flush();//刷新数据

                }
                strings.add(uniqueId);


            }  
            creader.close();
            for (Map.Entry<String, Object> stringObjectEntry : idToInfo.entrySet()) {
                Object value = stringObjectEntry.getValue();
                if (value !=null) {
                    CsvWriter value1 = (CsvWriter) value;
                    value1.close();
                }
            }

            /**
             * 总重复10882
             * 总话单5407603
             *
             *
             * 总重复10882
             * 总话单1736841
             */
            System.out.println("总重复"+strings1.size());
            System.out.println("总话单"+strings.size());
            System.out.println("执行完成");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}