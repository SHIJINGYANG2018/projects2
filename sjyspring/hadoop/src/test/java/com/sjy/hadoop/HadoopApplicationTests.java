package com.sjy.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest
class HadoopApplicationTests {
    FileSystem fileSystem = null;

    public void init() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();

        conf.set("fs.defaultFS", "hdfs://114.116.253.47:9000/");
        fileSystem = FileSystem.get(new URI("hdfs://114.116.253.47:9000/"), conf, "root");

    }


    @Test
    public void upload() throws IOException, URISyntaxException, InterruptedException {

        this.init();
        //fileSystem.copyFromLocalFile(new Path("D:/JAVA/jdk-8u172-linux-x64.tar.gz"),new Path("hdfs://114.116.253.47:9000/"));
        fileSystem.copyFromLocalFile(new Path("D:/JAVA/1.jpg"), new Path("hdfs://114.116.253.47:9000/sjy"));

    }
}
