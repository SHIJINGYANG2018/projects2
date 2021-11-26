package com.sjy.elasticsearch;

import com.sjy.elasticsearch.config.RestService;
import com.sjy.elasticsearch.entity.LogMessage;
import com.sjy.elasticsearch.entity.Student;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@SpringBootTest
class ElasticsearchApplicationTests {


    @Autowired
    private StudentDao studentDao;

    @Test
    void saveAll() {
        for (int j = 1; j <= 100; j++) {
            ArrayList<Student> students = new ArrayList<>();
            for (int i = 1; i <= 500; i++) {
                Student student = new Student(j * (i + 1), "student name" + j * (i + 1), j * (i + 1), j * (i + 1) + "", "I don't know");
                students.add(student);
            }
            long start = System.currentTimeMillis();
            studentDao.saveAll(students);
            long end = System.currentTimeMillis();
            System.out.println(j + " : " + (end - start));
        }
    }

    @Autowired
    RestService restService;

    @Test
    void indexExists() {
        boolean student = restService.indexExists("student");
        boolean student22 = restService.indexExists("student11");
        boolean student33 = restService.indexExists("student22");
        System.out.println("student22 = " + student22);
        System.out.println("student33 = " + student33);
        System.out.println("student = " + student);
    }

    @Test
    void test2() {

    }

    @Test
    void putTemplate() {
        // restService.putTemplate("content-template", "{\"index_patterns\":\"content_*\",\"order\":1,\"settings\":{\"number_of_shards\":4,\"number_of_replicas\":1},\"mappings\":{\"properties\":{\"@timestamp\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd HH:mm:ss\"}}}}");
        System.out.println(restService.putTemplate("log-template", "{\"index_patterns\":\"log-message-*\",\"order\":1,\"settings\":{\"number_of_shards\":2,\"number_of_replicas\":0,\"max_result_window\":10000000},\"mappings\":{\"properties\":{\"id\":{\"index\":true,\"type\":\"keyword\"},\"level\":{\"index\":true,\"type\":\"keyword\"},\"createTime\":{\"index\":true,\"type\":\"long\"},\"msg\":{\"index\":true,\"type\":\"text\"}}}}"));
    }

    @Test
    void deleteIndex() {
        System.out.println(restService.deleteIndex(LogMessage.class, "dev", LocalDate.now().toString()));
    }

    @Test
    void createIndex() {
        System.out.println(restService.createIndex(LogMessage.class, "dev", LocalDate.now().toString()));
    }

    @Test
    void closeIndex() {
        System.out.println(restService.closeIndex(LogMessage.class, "dev", LocalDate.now().toString()));
    }

    @Test
    void openIndex() {
        System.out.println(restService.openIndex(LogMessage.class, "dev", LocalDate.now().toString()));
    }

    @Test
    void index() {
        LogMessage logMessage = new LogMessage();
        logMessage.setId(UUID.randomUUID().toString());
        logMessage.setCreateTime(System.currentTimeMillis());
        logMessage.setMsg("elasticsearch yyds");
        logMessage.setLevel("INFO");
        System.out.println(restService.index(LogMessage.class, logMessage, "dev", LocalDate.now().toString()));
    }

    @Test
    void bulkIndex() {
        for (int j = 0; j < 100; j++) {
            ArrayList<LogMessage> logMessages = new ArrayList<>();
            for (int i = 0; i < 500; i++) {
                LogMessage logMessage = new LogMessage();
                logMessage.setId(UUID.randomUUID().toString());
                logMessage.setCreateTime(System.currentTimeMillis());
                logMessage.setMsg("elasticsearch yyds " + i + "-" + j);
                logMessage.setLevel("INFO");
                logMessages.add(logMessage);
            }
            restService.bulkIndex(logMessages, LogMessage.class, "dev", LocalDate.now().toString());
        }
    }

    @Test
    void scroll() {
        QueryBuilder age = QueryBuilders.matchQuery("msg", "elasticsearch yyds 0");
        RangeQueryBuilder createTime = QueryBuilders.rangeQuery("createTime").gte(1637754072221L).lte(1637754072221L);

        int pageSize = 100;
        Query query = new NativeSearchQueryBuilder().withQuery(age).withQuery(createTime).withPageable(Pageable.ofSize(pageSize)).build();
        String dateStr = LocalDate.now().toString();
        SearchScrollHits<?> searchHits = restService.scrollFirst(query, LogMessage.class,"dev", dateStr);
        long totalHits = searchHits.getSearchHits().size();

        System.out.println(totalHits);
        String scrollId = searchHits.getScrollId();
        while (totalHits == pageSize) {
            SearchScrollHits<?> scroll = restService.scroll(scrollId, LogMessage.class,"dev", dateStr);
            scrollId = scroll.getScrollId();
            totalHits = scroll.getSearchHits().size();
            System.out.println(totalHits);
        }
        System.out.println("down");
    }

    @Test
    void search() {
        QueryBuilder age = QueryBuilders.matchQuery("msg", "elasticsearch yyds 0");
        RangeQueryBuilder createTime = QueryBuilders.rangeQuery("createTime").gte(1637754072221L);
        int pageSize = 10000;
        PageRequest pageRequest = PageRequest.of(0, pageSize, Sort.Direction.ASC, "createTime");
        Query query = new NativeSearchQueryBuilder().withQuery(age).withQuery(createTime).withPageable(pageRequest).build();
        SearchHits<?> search = restService.search(query, LogMessage.class,"dev", LocalDate.now().toString());
        System.out.println(search.getSearchHits());
    }

    @Test
    void search2() {

        int pageSize = 10000;
        PageRequest pageRequest = PageRequest.of(0, pageSize, Sort.Direction.ASC, "createTime");
        Query query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("id","8fc3f5aa-ea68-447e-aae9-9ae21e289153")).withQuery(QueryBuilders.matchQuery("id","c87848bd-244a-4dc6-b2c3-1607ffc436b6")).withPageable(pageRequest).build();
        SearchHits<?> search = restService.search(query, LogMessage.class,"dev", LocalDate.now().toString());
        System.out.println(search.getSearchHits());
    }
}
