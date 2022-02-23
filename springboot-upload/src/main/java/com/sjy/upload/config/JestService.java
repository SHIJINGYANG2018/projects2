/*
package com.sjy.upload.config;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tinet.vlink2.es.AggregationsType;
import com.tinet.vlink2.es.EsQueryCondition;
import com.tinet.vlink2.es.EsQueryResult;
import com.tinet.vlink2.utils.DateUtil;
import io.searchbox.action.BulkableAction;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchScroll;
import io.searchbox.core.Update;
import io.searchbox.core.search.aggregation.MetricAggregation;
import io.searchbox.indices.CloseIndex;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.OpenIndex;
import io.searchbox.indices.aliases.AddAliasMapping;
import io.searchbox.indices.aliases.ModifyAliases;
import io.searchbox.indices.mapping.PutMapping;
import io.searchbox.indices.reindex.Reindex;
import io.searchbox.indices.template.PutTemplate;
import io.searchbox.params.Parameters;
import io.searchbox.snapshot.CreateSnapshot;
import io.searchbox.snapshot.DeleteSnapshot;
import io.searchbox.snapshot.GetSnapshot;
import io.searchbox.snapshot.RestoreSnapshot;
import io.searchbox.snapshot.SnapshotStatus;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

*/
/**
 * Created by jiashiran on 2017/11/11.
 *//*

public class JestService {

    private Logger logger = LoggerFactory.getLogger(JestService.class);

    private JestClient client;

    private List<String> esHostList;
    private String user;
    private String password;

    public JestService(String host, int maxTotalConnectionPerRoute, int maxTotalConnection, String user, String password) {
        String[] hosts = host.split(";");
        List<String> hostList = Lists.newArrayList();
        for (String s : hosts) {
            hostList.add(s);
        }
        esHostList = hostList;
        JestClientFactory factory = new JestClientFactory();
        HttpClientConfig httpClientConfig = null;
        if (!StringUtils.isEmpty(user) && !StringUtils.isEmpty(password)) {
            httpClientConfig = new HttpClientConfig
                    .Builder(hostList)
                    .multiThreaded(true)
                    .defaultMaxTotalConnectionPerRoute(maxTotalConnectionPerRoute)
                    .maxTotalConnection(maxTotalConnection)
                    .defaultCredentials(user, password)
                    .readTimeout(6000)
                    .build();
            this.user = user;
            this.password = password;
        } else {
            httpClientConfig = new HttpClientConfig
                    .Builder(hostList)
                    .multiThreaded(true)
                    .defaultMaxTotalConnectionPerRoute(maxTotalConnectionPerRoute)
                    .maxTotalConnection(maxTotalConnection)
                    .readTimeout(6000)
                    .build();
        }


        factory.setHttpClientConfig(httpClientConfig);
        client = factory.getObject();

    }

    */
/**
     * 所有单条数据
     *
     * @param o
     * @param indexName
     * @return
     *//*

    public boolean index(Object o, String indexName) {
        return index(o, indexName, "vlink2");
    }

    */
/**
     * 所有单条数据
     *
     * @param o
     * @param indexName
     * @return
     *//*

    private boolean index(Object o, String indexName, String indexType) {
        try {
            Bulk bulk = new Bulk.Builder()
                    .defaultIndex(indexName)
                    .defaultType(indexType)
                    .addAction(Arrays.asList(toBulkableAction(o))).build();
            JestResult result = client.execute(bulk);
            logger.debug(result.getJsonString());
            return result.isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("JestService.index exception:{}", e);
            return false;
        }
    }

    */
/**
     * 批量创建索引
     *
     * @param list
     * @param indexName
     * @return
     * @throws IOException
     *//*

    public boolean bulkIndex(List<? extends Object> list, String indexName) {
        try {
            boolean success = bulkIndex(list, indexName, "vlink2");
            return success;
        } catch (Exception e) {
            logger.error("jestService bulkIndex exception:{}", e);
            return false;
        }
    }

    */
/**
     * 批量创建索引
     *
     * @param list
     * @param indexName
     * @return
     * @throws IOException
     *//*

    private boolean bulkIndex(List<? extends Object> list, String indexName, String indexType) {
        try {
            List<BulkableAction> actions = new ArrayList<>();
            list.forEach(o -> {
                actions.add(new Index.Builder(o).build());
                logger.debug("bulk object :{}", JSON.toJSONString(o));
            });
            Bulk bulk = new Bulk.Builder()
                    .defaultIndex(indexName)
                    .defaultType(indexType)
                    .addAction(actions).build();
            JestResult result = client.execute(bulk);
            if (!result.isSucceeded()) {
                logger.error(result.getJsonString());
            }
            return result.isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("JestService.bulkIndex es exception:{}", e);
        }
        return false;
    }

    */
/**
     * 查询es
     *
     * @param condition
     * @param <T>
     * @return
     *//*

    public <T> EsQueryResult<T> search(EsQueryCondition condition) {
        EsQueryResult<T> esQueryResult = new EsQueryResult<>();
        try {
            SearchResult result = client.execute(condition.instanceSearch());
            boolean success = result.isSucceeded();
            esQueryResult.setSuccess(success);
            if (!success) {
                JsonObject error = result.getJsonObject().getAsJsonObject("error");
                if (error != null) {
                    JsonElement reason = error.get("reason");
                    if (reason != null) {
                        logger.info("result:{}", error.toString());
                        logger.error("JestService.search fail reason:{}", reason.getAsString());
                        if ("closed".equals(reason.getAsString()) && !condition.isRetry()) {
                            condition.getIndexNames().forEach(indexName -> openIndex(indexName.toString()));
                            condition.setRetry(true);
                            return search(condition);
                        }
                    }
                }
                return esQueryResult;
            }
            Long total = result.getTotal();
            if (total == null) {
                esQueryResult.setTotal(0);
            } else {
                esQueryResult.setTotal(total.intValue());
            }
            List<SearchResult.Hit<T, Void>> hits = result.getHits(condition.getClasz());
            List<T> ts = Lists.newArrayList();
            if (!hits.isEmpty()) {
                hits.forEach(hit -> {
                    if (hit != null) {
                        T t = hit.source;
                        ts.add(t);
                    } else {
                        logger.error("JestService.hit is null");
                    }
                });
            }
            logger.info("hit count:{}", hits.size());
            esQueryResult.setResult(ts);
            MetricAggregation aggregation = result.getAggregations();
            if (aggregation != null) {
                Map<String, Double> aggs = Maps.newHashMap();
                Multimap<String, Object> aggMap = condition.getAggregationsParam();
                if (aggMap != null) {
                    aggMap.keys().elementSet().forEach((k) -> {
                        Collection<Object> collection = aggMap.get(k);
                        AggregationsType aggregationsType = (AggregationsType) collection.iterator().next();
                        switch (aggregationsType) {
                            case AVG:
                                aggs.put(k, aggregation.getAvgAggregation(k).getAvg());
                                break;
                            case MAX:
                                aggs.put(k, aggregation.getMaxAggregation(k).getMax());
                                break;
                            case MIN:
                                aggs.put(k, aggregation.getMinAggregation(k).getMin());
                                break;
                            case SUM:
                                aggs.put(k, aggregation.getSumAggregation(k).getSum());
                                break;
                        }
                    });
                }

                esQueryResult.setAggregations(aggs);
            }
            return esQueryResult;
        } catch (IOException e) {
            e.printStackTrace();
            esQueryResult.setSuccess(false);
            logger.error("JestService.search es exception", e);
        }
        return esQueryResult;
    }


    public <T> EsQueryResult<T> scrollFirst(EsQueryCondition condition, Integer pageSize) {
        EsQueryResult<T> esQueryResult = new EsQueryResult<>();
        try {
            String query = condition.getQueryString();
            Search.Builder builder = new Search.Builder(query);
            logger.info(query);
            condition.getIndexNames().forEach(indexName -> builder.addIndex(indexName.toString()));
            builder.setParameter(Parameters.SIZE, pageSize)
                    .setParameter(Parameters.SCROLL, "3m");
            SearchResult result = client.execute(builder.build());
            boolean isSuccess = result.isSucceeded();
            esQueryResult.setSuccess(isSuccess);
            if (!isSuccess) {
                JsonObject error = result.getJsonObject().getAsJsonObject("error");
                if (error != null) {
                    JsonElement reason = error.get("reason");
                    if (reason != null) {
                        logger.info("result:{}", error.toString());
                        logger.error("JestService.search fail reason:{}", reason.getAsString());
                        if ("closed".equals(reason.getAsString()) && !condition.isRetry()) {
                            condition.getIndexNames().forEach(indexName -> openIndex(indexName.toString()));
                            condition.setRetry(true);
                            return search(condition);
                        }
                    }
                }
                return esQueryResult;
            } else {
                Long total = result.getTotal();
                if (total == null) {
                    esQueryResult.setTotal(0);
                } else {
                    esQueryResult.setTotal(total.intValue());
                }
                List<SearchResult.Hit<T, Void>> hits = result.getHits(condition.getClasz());
                List<T> ts = Lists.newArrayList();
                if (!hits.isEmpty()) {
                    hits.forEach(hit -> {
                        if (hit != null) {
                            T t = hit.source;
                            ts.add(t);
                        } else {
                            logger.error("JestService.hit is null");
                        }
                    });
                }
                esQueryResult.setScrollId(result.getJsonObject().get("_scroll_id").getAsString());
                logger.info("hit count:{}", hits.size());
                esQueryResult.setResult(ts);
            }
            return esQueryResult;
        } catch (Exception e) {
            esQueryResult.setSuccess(false);
            logger.error("JestService.scrollFirst es exception:{}", e);
            return esQueryResult;
        }
    }

    public <T> EsQueryResult<T> scroll(String scrollId, EsQueryCondition condition) {
        EsQueryResult<T> esQueryResult = new EsQueryResult<>();
        try {
            SearchScroll scroll = new SearchScroll.Builder(scrollId, "3m").build();
            JestResult jestResult = client.execute(scroll);
            boolean isSuccess = jestResult.isSucceeded();
            esQueryResult.setSuccess(isSuccess);
            if (isSuccess) {
                esQueryResult.setScrollId(jestResult.getJsonObject().get("_scroll_id").getAsString());
                List result = jestResult.getSourceAsObjectList(condition.getClasz());
                esQueryResult.setResult(result);
                esQueryResult.setTotal(jestResult.getJsonObject().get("hits").getAsJsonObject().get("total").getAsInt());
            } else {
                JsonObject error = jestResult.getJsonObject().getAsJsonObject("error");
                logger.error("scroll error result:{}", error.toString());
            }
            return esQueryResult;
        } catch (Exception e) {
            logger.error("jestService scroll e:{}", e);
            esQueryResult.setSuccess(false);
            return esQueryResult;
        }
    }


    public SearchResult search(Search search) {
        try {
            SearchResult result = client.execute(search);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Object object, String index, String id) {
        Update update = new Update.Builder(object).index(index).type(index).id(id).build();
        try {
            JestResult result = client.execute(update);
            return result.isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("JestService.update exception", e);
        }
        return false;
    }

    */
/**
     * @param index
     * @param mappingString "{"properties":{"productId":{"type":"text","fielddata":true}}}
     * @return
     *//*

    public JestResult putMapping(String index, String mappingString) {
        PutMapping putMapping = new PutMapping.Builder(index, "vlink2", mappingString).build();
        try {
            JestResult result = client.execute(putMapping);
            boolean isSucceeded = result.isSucceeded();
            if (isSucceeded) {
                logger.info("putMapping 成功");
            } else {
                logger.info("putMapping 失败," + result.getJsonString());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("JestService.putMapping exception", e);
        }
        return null;
    }

    */
/**
     * 创建索引快照
     *
     * @param index
     * @return
     *//*

    public boolean createSnapshot(String repository, String index) {
        final Settings.Builder registerRepositorySettings = Settings.builder();
        registerRepositorySettings.put("indices", index);
        registerRepositorySettings.put("ignore_unavailable", "true");
        registerRepositorySettings.put("include_global_state", "false");

        CreateSnapshot createSnapshot = new CreateSnapshot.Builder(repository, "snapshot_" + index)
                .settings(registerRepositorySettings.build())
                .build();
        try {
            JestResult result = client.execute(createSnapshot);
            boolean isSucceeded = result.isSucceeded();
            if (isSucceeded) {
                logger.info("创建快照成功");
            } else {
                logger.info("创建快照失败," + result.getJsonString());
            }
            return isSucceeded;
        } catch (IOException e) {
            logger.error("JestService.createSnapshot exception:", e);
        }
        return false;
    }

    */
/**
     * 删除索引快照
     *
     * @param index
     * @return
     *//*

    public boolean deleteSnapshot(String repository, String index) {
        DeleteSnapshot deleteSnapshot = new DeleteSnapshot.Builder(repository, "snapshot_" + index).build();
        try {
            JestResult result = client.execute(deleteSnapshot);
            logger.info(result.getJsonString());
            logger.info(result.getJsonString());
        } catch (IOException e) {
            logger.error("JestService.deleteSnapshot exception:", e);
        }
        return false;
    }

    */
/**
     * 查询所有快照信息
     *
     * @return
     *//*

    public boolean getSnapshot(String repository) {
        GetSnapshot getSnapshotRepository = new GetSnapshot.Builder(repository).build();
        try {
            JestResult result = client.execute(getSnapshotRepository);
            logger.info(result.getJsonString());
            return result.isSucceeded();
        } catch (IOException e) {
            logger.error("JestService.getSnapshot exception:", e);
        }
        return false;
    }

    */
/**
     * 将索引从快照恢复到es
     *
     * @param index
     * @return
     *//*

    public boolean restoreSnapshot(String repository, String index) {
        final Settings.Builder registerRepositorySettings = Settings.builder();
        registerRepositorySettings.put("indices", index);
        registerRepositorySettings.put("ignore_unavailable", "true");
        registerRepositorySettings.put("include_global_state", "false");
        //registerRepositorySettings.put("rename_pattern", "index_(.+)");
        // registerRepositorySettings.put("rename_replacement", "restored_index_$1");

        RestoreSnapshot restoreSnapshot = new RestoreSnapshot.Builder(repository, "snapshot_" + index)
                .settings(registerRepositorySettings.build()).build();

        try {
            JestResult result = client.execute(restoreSnapshot);
            logger.info(result.getJsonString());
            return result.isSucceeded();
        } catch (IOException e) {
            logger.error("JestService.restoreSnapshot exception:", e);
        }
        return false;
    }

    */
/**
     * 查询快照状态
     *
     * @param index
     *//*

    public void snapshotStatus(String repository, String index) {
        SnapshotStatus snapshotStatus = new SnapshotStatus.Builder(repository).addSnapshot("snapshot_" + index).build();
        try {
            JestResult result = client.execute(snapshotStatus);
            logger.info(result.getJsonString());
        } catch (IOException e) {
            logger.error("JestService.snapshotStatus exception:", e);
        }
    }

    */
/**
     * 删除索引
     *
     * @param indexName
     * @return
     *//*

    public boolean deleteInex(String indexName) {
        JestResult jr = null;
        try {
            jr = client.execute(new DeleteIndex.Builder(indexName).build());
            return jr.isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("JestService.deleteInex exception", e);
        }
        return false;
    }

    */
/**
     * 根据查询条件删除索引数据
     *
     * @param queryString
     * @param indexNames
     * @return
     *//*

    public boolean deleteByQuery(String queryString, Set<String> indexNames) {
        try {
            DeleteByQuery.Builder builder = new DeleteByQuery.Builder(queryString);
            indexNames.forEach(indexName -> {
                builder.addIndex(indexName);
            });
            DeleteByQuery build = builder.addType("vlink2").build();
            JestResult jestResult = client.execute(build);
            return jestResult.isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("JestService.deleteInex exception", e);
        }
        return false;
    }

    */
/**
     * 根据查询条件删除索引数据
     *
     * @param condition
     * @return
     *//*

    public boolean deleteByQuery(EsQueryCondition condition) {
        try {
            String queryString = condition.getQueryString();
            Set<String> indexNames = condition.getIndexNames();
            return deleteByQuery(queryString, indexNames);
        } catch (Exception e) {
            logger.error("JestService.deleteByQuery exception", e);
        }
        return false;
    }

    */
/**
     * 根据时间获取存在的索引名称
     *
     * @param startTime
     * @param endTime
     * @param indexPattern
     * @return
     *//*

    public String[] getIndexs(Date startTime, Date endTime, String indexPattern) {
        ArrayList<String> indexList = Lists.newArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FMT_DATE_YYYY_MM_DD);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        if (startTime != null && endTime != null) {
            while (startTime.before(endTime)) {
                String index = String.format(indexPattern, sdf.format(startTime));
                if (this.isExists(index)) {
                    indexList.add(index);
                }
                startTime = DateUtil.addDay(startTime, 1);
            }
            String index = String.format(indexPattern, sdf.format(startTime));
            if (this.isExists(index)) {
                indexList.add(index);
            }
        }
        String[] indexs = new String[indexList.size()];
        indexList.toArray(indexs);
        return indexs;
    }

    */
/**
     * 根据时间获取存在的索引名称
     *   月统计
     * @param startTime
     * @param endTime
     * @param indexPattern
     * @return
     *//*

    public String[] getIndexForMonth(Date startTime, Date endTime, String indexPattern) {
        ArrayList<String> indexList = Lists.newArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FMT_DATE_YYYY_MM);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        if (startTime != null && endTime != null) {
            while (startTime.before(endTime)) {
                String index = String.format(indexPattern, sdf.format(startTime));
                if (this.isExists(index)) {
                    indexList.add(index);
                }
                startTime = DateUtil.addMonth(startTime, 1);
            }
            String index = String.format(indexPattern, sdf.format(startTime));
            if (this.isExists(index)) {
                indexList.add(index);
            }
        }
        String[] indexs = new String[indexList.size()];
        indexList.toArray(indexs);
        return indexs;
    }

    */
/**
     * 判断索引是否存在
     *
     * @param indexName
     * @return
     *//*

    public boolean isExists(String indexName) {
        IndicesExists indicesExists = new IndicesExists.Builder(indexName).build();
        try {
            JestResult jestResult = client.execute(indicesExists);
            boolean flag = jestResult.isSucceeded();
            if (flag) {
                logger.info("索引存在:" + indexName);
            }
            return flag;
        } catch (IOException e) {
            logger.error("JestService.isExists exception: ", e);
        }
        return false;
    }

    */
/**
     * 关闭索引
     *
     * @param indexName
     * @return
     *//*

    public boolean closeIndex(String indexName) {
        CloseIndex closeIndex = new CloseIndex.Builder(indexName).build();
        try {
            JestResult execute = client.execute(closeIndex);
            boolean isSucceeded = execute.isSucceeded();
            if (isSucceeded) {
                logger.info("关闭索引：{} 成功", indexName);
            } else {
                logger.info("关闭索引失败," + execute.getJsonString());
            }
            return isSucceeded;
        } catch (IOException e) {
            logger.error("JestService.closeIndex exception:", e);
        }
        return false;
    }

    */
/**
     * 打开索引
     *
     * @param indexName
     * @return
     *//*

    public boolean openIndex(String indexName) {
        if (isExists(indexName)) {
            OpenIndex openIndex = new OpenIndex.Builder(indexName).build();
            try {
                JestResult execute = client.execute(openIndex);
                boolean isSucceeded = execute.isSucceeded();
                if (isSucceeded) {
                    logger.info("打开索引：{} 成功", indexName);
                } else {
                    logger.info("打开索引失败," + execute.getJsonString());
                }
                return isSucceeded;
            } catch (IOException e) {
                logger.error("JestService.openIndex exception:", e);
            }
        }
        return false;
    }


    public static BulkableAction toBulkableAction(Object o) {
        return new Index.Builder(o).build();
    }

    */
/**
     * 创建索引
     * 设置分片和副本数
     *
     * @param name
     * @return
     *//*

    public boolean createIndex(String name, int numberOfShards, int numberOfReplicas) {
        try {
            Settings.Builder settingsBuilder = Settings.builder();
            settingsBuilder.put("number_of_shards", numberOfShards);
            settingsBuilder.put("number_of_replicas", numberOfReplicas);
            settingsBuilder.put("max_result_window", 10000000);
            CreateIndex build = new CreateIndex.Builder(name).settings(settingsBuilder.build()).build();
            JestResult execute = client.execute(build);
            boolean isSucceeded = execute.isSucceeded();
            if (isSucceeded) {
                logger.info("创建index成功");
            } else {
                logger.info("创建index：" + name + "失败," + execute.getJsonString());
            }
            return isSucceeded;
        } catch (IOException e) {
            logger.error("JestService.createIndex error : {}", e);
            logger.error("Index Name" + name);
            return false;
        }

    }

    */
/**
     * 重命名索引
     * 原索引保留，创建一个新索引并复制数据
     *
     * @param source 源
     * @param dest   目的索引
     * @return
     *//*

    public boolean renameIndex(String source, String dest) {
        try {
            HashMap<String, String> sourceMap = Maps.newHashMap();
            sourceMap.put("index", source);
            HashMap<String, String> destMap = Maps.newHashMap();
            destMap.put("index", dest);
            Reindex build = new Reindex.Builder(sourceMap, destMap).build();
            JestResult jestResult = client.execute(build);
            return jestResult.isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("JestService.renameIndex exception:", e);
            return false;
        }
    }

    */
/**
     * 给索引起别名
     *
     * @param indexName 索引名陈
     * @param alias     索引别名
     * @return
     *//*

    public boolean aliasIndex(String indexName, String alias) {
        try {
            AddAliasMapping addAliasMapping = new AddAliasMapping.Builder(indexName, alias).build();
            ModifyAliases modifyAliases = new ModifyAliases.Builder(addAliasMapping).build();
            JestResult jestResult = client.execute(modifyAliases);
            boolean isSucceeded = jestResult.isSucceeded();
            if (isSucceeded) {
                logger.info("创建别名成功");
            }
            return isSucceeded;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("JestService.aliasIndex exception:", e);
            return false;
        }
    }

    */
/**
     * 创建模板
     *
     * @param templateName   模板名称
     * @param sourceSettings 源配置
     * @return
     *//*

    public boolean createIndexTemplate(String templateName, String sourceSettings) {
        try {
            PutTemplate putTemplate = new PutTemplate.Builder(templateName, sourceSettings).build();
            JestResult jestResult = client.execute(putTemplate);
            boolean isSucceeded = jestResult.isSucceeded();
            if (isSucceeded) {
                logger.info("创建模板成功");
            } else {
                logger.info("创建模板失败," + jestResult.getJsonString());
            }
            return isSucceeded;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("JestService.renameIndex exception:", e);
            return false;
        }
    }

    */
/**
     * 转换错误es的数据
     *
     * @param indexName   索引名称
     * @param type        索引类型
     * @param targetClazz 转换目标class
     * @param pageSize    每页查询数
     * @return
     * @throws Exception
     *//*

    public boolean etlEsData(String indexName, String indexDestination, String type, Class targetClazz, Integer pageSize) {
        boolean etlDataSuccess = true;
        logger.info("etlEsData,indexName:{}", indexName);
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery()).size(pageSize);
            searchSourceBuilder.sort("_doc");
            Search build = new Search.Builder(searchSourceBuilder.toString())
                    .addIndex(indexName)
                    .addType(type)
                    .setParameter(Parameters.SCROLL, "10m")
                    .build();

            JestResult result = client.execute(build);
            if (result.isSucceeded()) {
                JsonObject hits = result.getJsonObject().getAsJsonObject("hits");
                long totalCount = Long.parseLong(hits.get("total").toString());
                if (totalCount != 0) {
                    logger.info("scoll_search,total_count:{}", totalCount);
                    String scrollId = result.getJsonObject().get("_scroll_id").getAsString();
                    long totalPage = totalCount / pageSize + 1;
                    logger.info("scoll_search,total_page:{}", totalPage);

                    //bulk data
                    List sourceAsObjectList = result.getSourceAsObjectList(targetClazz, false);
                    this.bulkIndex(sourceAsObjectList, indexDestination);
                    for (int i = 1; i < totalPage; i++) {
                        SearchScroll scroll = new SearchScroll.Builder(scrollId, "10m").build();
                        result = client.execute(scroll);
                        boolean succeeded = result.isSucceeded();
                        if (succeeded) {
                            sourceAsObjectList = result.getSourceAsObjectList(targetClazz, false);
                            this.bulkIndex(sourceAsObjectList, indexDestination);
                        } else {
                            etlDataSuccess = false;
                            logger.error("scoll_search_error,page : {}", i);
                            logger.error("scoll_search_error,msg : {}", result.getErrorMessage());
                            break;
                        }
                        scrollId = result.getJsonObject().getAsJsonPrimitive("_scroll_id").getAsString();
                        logger.info("scoll_search,page:{}", i);
                    }
                }
            } else {
                etlDataSuccess = false;
                logger.error("init_scoll_search_error,msg : {}", result.getErrorMessage());
            }
        } catch (Exception e) {
            etlDataSuccess = false;
            logger.error("scoll_search_error,error : {}", e);
        }
        return etlDataSuccess;
    }
}
*/
