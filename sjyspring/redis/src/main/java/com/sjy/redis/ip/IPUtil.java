package com.sjy.redis.ip;

import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;

public class IPUtil {

    public static String getCityInfo(String ip) {
        //db
        URL ip2region = IPUtil.class.getResource("/ip2region.db");

        if (ip2region != null) {
            String dbPath = ip2region.getPath();
            File file = new File(dbPath);
            if (!file.exists()) {
                System.out.println("Error: Invalid ip2region.db file");
                return null;
            }
            DbSearcher searcher = null;
            //查询算法
            int algorithm = DbSearcher.BTREE_ALGORITHM; //B-tree
            //DbSearcher.BINARY_ALGORITHM //Binary
            //DbSearcher.MEMORY_ALGORITYM //Memory
            try {
                DbConfig config = new DbConfig();
                searcher = new DbSearcher(config, dbPath);
                //define the method
                Method method = null;
                switch (algorithm) {
                    case DbSearcher.BTREE_ALGORITHM:
                        method = searcher.getClass().getMethod("btreeSearch", String.class);
                        break;
                    case DbSearcher.BINARY_ALGORITHM:
                        method = searcher.getClass().getMethod("binarySearch", String.class);
                        break;
                    case DbSearcher.MEMORY_ALGORITYM:
                        method = searcher.getClass().getMethod("memorySearch", String.class);
                        break;
                }

                if (!Util.isIpAddress(ip)) {
                    System.out.println("Error: Invalid ip address");
                    return null;
                }
                DataBlock dataBlock;
                dataBlock = (DataBlock) method.invoke(searcher, ip);
                return dataBlock.getRegion();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (searcher != null) {
                        searcher.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("ip2region.db 文件不存在");
        }
        return null;
    }
}