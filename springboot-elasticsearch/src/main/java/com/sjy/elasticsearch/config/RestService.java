package com.sjy.elasticsearch.config;

import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CloseIndexRequest;
import org.elasticsearch.client.indices.CloseIndexResponse;
import org.elasticsearch.client.indices.PutIndexTemplateRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author shijingyang
 */
@Component
public class RestService {
    protected static final Logger log = LoggerFactory.getLogger(RestService.class);

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    private static final String PLACEHOLDERS = "%s";


    /**
     * 判断索引是否存在
     *
     * @return boolean
     */
    public boolean indexExists(String... indexName) {
        try {
            return elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName)).exists();
        } catch (Exception e) {
            log.error("RestService##indexExists:", e);
            return false;
        }
    }

    /**
     * 创建索引
     *
     * @param clazz 实体
     * @return boolean
     */
    public boolean createIndex(Class<?> clazz, String... placeholders) {
        try {
            String[] indexName = getIndexName(clazz, placeholders);
            if (!this.indexExists(indexName)) {
                HashMap<String, Object> settingMap = new HashMap<>();
                settingMap.put("number_of_shards", getShardsFromClass(clazz));
                settingMap.put("number_of_replicas", getReplicasFromClass(clazz));
                settingMap.put("max_result_window", 10000000);
                Document settings = Document.from(settingMap);
                return elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName)).create(settings);
            } else {
                log.info("{} 索引已经存在", Arrays.toString(indexName));
            }
        } catch (Exception e) {
            log.error("RestService##createIndex:", e);
        }
        return false;
    }

    /**
     * 删除索引
     *
     * @param clazz 实体
     * @return boolean
     */
    public boolean deleteIndex(Class<?> clazz, String... placeholders) {
        try {
            String[] indexName = getIndexName(clazz, placeholders);
            if (this.indexExists(indexName)) {
                return elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName)).delete();
            } else {
                log.info("{} 索引不存在", Arrays.toString(indexName));
            }
        } catch (Exception e) {
            log.error("RestService##deleteIndex:", e);
        }
        return false;
    }

    /**
     * 关闭索引
     *
     * @param clazz 实体
     * @return boolean
     */
    public boolean closeIndex(Class<?> clazz, String... placeholders) {
        try {
            String[] indexName = getIndexName(clazz, placeholders);
            CloseIndexRequest request = new CloseIndexRequest(indexName);
            CloseIndexResponse execute = elasticsearchRestTemplate.execute(client -> client.indices().close(request, RequestOptions.DEFAULT));
            boolean acknowledged = execute.isAcknowledged();
            if (acknowledged) {
                log.info("close index {} success" ,Arrays.toString(indexName) );
            }else{
                log.info("close index {} fail" ,Arrays.toString(indexName) );
            }
            return acknowledged;
        } catch (Exception e) {
            log.error("RestService##deleteIndex:", e);
            return false;
        }
    }

    /**
     * 打开索引
     *
     * @param clazz 实体
     * @return boolean
     */
    public boolean openIndex(Class<?> clazz, String... placeholders) {
        try {
            String[] indexName = getIndexName(clazz, placeholders);
            OpenIndexRequest request = new OpenIndexRequest(indexName);
            OpenIndexResponse execute = elasticsearchRestTemplate.execute(client -> client.indices().open(request, RequestOptions.DEFAULT));
            boolean acknowledged = execute.isAcknowledged();
            if (acknowledged) {
                log.info("open index {} success" , Arrays.toString(indexName) );
            }else{
                log.info("open index {} fail" ,Arrays.toString(indexName) );
            }
            return acknowledged;
        } catch (Exception e) {
            log.error("RestService##deleteIndex:", e);
            return false;
        }
    }

    /**
     * 设置索引Mapping
     *
     * @param clazz
     * @param json
     * @param placeholders
     * @return
     */
    public boolean putMapping(Class<?> clazz, String json, String... placeholders) {
        try {
            String[] indexName = getIndexName(clazz, placeholders);
            return elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName)).putMapping(Document.parse(json));
        } catch (Exception e) {
            log.error("RestService##deleteIndex:", e);
            return false;
        }
    }

    /**
     * 设置 template
     *
     * @param name
     * @param source
     * @return
     */
    public boolean putTemplate(String name, String source) {
        try {
            PutIndexTemplateRequest builder = new PutIndexTemplateRequest(name);
            builder.source(source, XContentType.JSON);
            AcknowledgedResponse execute = elasticsearchRestTemplate.execute(client -> client.indices().putTemplate(builder, RequestOptions.DEFAULT));
            return execute.isAcknowledged();
        } catch (Exception e) {
            log.error("RestService##putTemplate:", e);
            return false;
        }
    }

    /**
     * 新增文档
     *
     * @param clazz
     * @param source
     * @param placeholders
     * @param <T>
     * @return
     */
    public <T> String index(Class<T> clazz, T source, String... placeholders) {
        String[] indexName = getIndexName(clazz, placeholders);
        return elasticsearchRestTemplate.index(new IndexQueryBuilder().withId(getIdFromSource(source))
                .withObject(source).build(), IndexCoordinates.of(indexName));
    }

    /**
     * 批量插入
     *
     * @param list
     * @param clazz
     * @param placeholders
     * @return
     */
    public boolean bulkIndex(List<?> list, Class<?> clazz, String... placeholders) {
        String[] indexName = getIndexName(clazz, placeholders);
        try {
            if (list != null && !list.isEmpty()) {
                List<IndexQuery> indexQueries = new ArrayList<>();
                list.forEach(source ->
                        indexQueries.add(new IndexQueryBuilder().withId(getIdFromSource(source)).withObject(source).build()));
                elasticsearchRestTemplate.bulkIndex(indexQueries, IndexCoordinates.of(indexName));
            }
        } catch (Exception e) {
            log.error("RestService##bulkIndex:", e);
            return false;
        }
        return true;
    }


    /**
     * 滚动查询
     *
     * @param query
     * @param clazz
     * @param placeholders
     * @return
     */
    public SearchScrollHits<?> scrollFirst(Query query, Class<?> clazz, String... placeholders) {
        String[] indexName = getIndexName(clazz, placeholders);
        try {
            return elasticsearchRestTemplate.searchScrollStart(60000, query, clazz, IndexCoordinates.of(indexName));
        } catch (Exception e) {
            log.error("RestService##scrollFirst:", e);
        }
        return null;
    }

    public SearchScrollHits<?> scroll(String scrollId, Class<?> clazz, String... placeholders) {
        String[] indexName = getIndexName(clazz, placeholders);
        try {
            return elasticsearchRestTemplate.searchScrollContinue(scrollId, 60000, clazz, IndexCoordinates.of(indexName));
        } catch (Exception e) {
            log.error("RestService##scrollFirst:", e);
        }
        return null;
    }

    public SearchHits<?> search(Query query, Class<?> clazz, String... placeholders) {
        try {
            String[] indexName = getIndexName(clazz, placeholders);
            return elasticsearchRestTemplate.search(query, clazz, IndexCoordinates.of(indexName));
        } catch (Exception e) {
            log.error("RestService##scrollFirst:", e);
        }
        return null;
    }

    /**
     * 获取 @id 属性的数据
     *
     * @param source
     * @return
     */
    private String getIdFromSource(Object source) {
        if (source == null) {
            return null;
        } else {
            Field[] fields = source.getClass().getDeclaredFields();
            Field[] var2 = fields;
            int var3 = fields.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Field field = var2[var4];
                if (field.isAnnotationPresent(Id.class)) {
                    try {
                        field.setAccessible(true);
                        Object name = field.get(source);
                        return name == null ? null : name.toString();
                    } catch (IllegalAccessException var7) {
                    }
                }
            }
            return null;
        }
    }

    /**
     * 获取实体类注解索引名称
     *
     * @param source
     * @return
     */
    private String getIndexFromClass(Class<?> source) {
        try {
            return source.getAnnotation(org.springframework.data.elasticsearch.annotations.Document.class).indexName();
        } catch (Exception e) {
            log.error("RestService##getIndexFromClass", e);
        }
        return null;
    }

    private long getShardsFromClass(Class<?> source) {
        try {
            return source.getAnnotation(Setting.class).shards();
        } catch (Exception e) {
            log.error("RestService##getShardsFromClass", e);
        }
        return 1;
    }

    private long getReplicasFromClass(Class<?> source) {
        try {
            return source.getAnnotation(Setting.class).replicas();
        } catch (Exception e) {
            log.error("RestService##getReplicasFromClass", e);
        }
        return 1;
    }

    /**
     * 获取索引名称
     *
     * @param clazz
     * @param placeholders
     * @return
     */
    private String[] getIndexName(Class<?> clazz, String... placeholders) {
        String indexName = getIndexFromClass(clazz);
        Assert.notNull(indexName, "indexName must not be null");
        if (indexName.contains(PLACEHOLDERS)) {
            Assert.notEmpty(placeholders, "placeholders must not be null");
            for (int i = 0; i < placeholders.length; i++) {
                placeholders[i] = String.format(indexName, "dev",placeholders[i]);
            }

            return placeholders;
        }
        return new String[]{indexName};
    }
}
