## ES

#### 练习
```text
数据能够包含多个值的标签、数字和纯文本。
检索任何员工的所有信息。
支持结构化搜索，例如查找30岁以上的员工。
支持简单的全文搜索和更复杂的短语(phrase)搜索
高亮搜索结果中的关键字
能够利用图表管理分析这些数据
```
### 索引index（数据库）
```text
将数据添加到elasticsearch中，需要索引，一个存储关联数据的地方，
索引是一个用来指指向一个或者多个分片的”逻辑命名空间“
一个分片是一个最小的级别的“工作单元”，他只是保存了所有数据的一部分数据
分片就是Lucene实例，本身就是一个完整的搜索引擎。
```

### 类型type(表)
相同类型的表示相同的事物，相同的事物数据结构也是相同的

### 文档 document(行)

### 字段fields(列)


### mget(multi-get)
检索多个文档依旧非常快。合并多个请求可以避免每个请求单独的网络开销

### bulk

### 主分片和复制分片如何交互

### 搜索
```json
{
    "took":15,
    "timed_out":false,
    "_shards":{
        "total":4,
        "successful":4,
        "skipped":0,
        "failed":0
    },
    "hits":{
        "total":{
            "value":10000,
            "relation":"gte"
        },
        "max_score":1,
        "hits":[
            {
                "_index":"student",
                "_type":"_doc",
                "_id":"21",
                "_score":1,
                "_source":{
                    "_class":"com.sjy.elasticsearch.entity.Student",
                    "id":21,
                    "name":"student name21",
                    "age":21,
                    "sex":"21",
                    "desc":"I don't know"
                }
            }
        ]
    }
}
```
### 解释
took 标识查询响应的时间  毫秒
_shards节点告诉我们参与查询的分片数（total字段）
有多少是成功的（successful字段）
有多少的是失败的（failed字段）
。通常我们不希望分片失败，不过这个有可能发生。
如果我们遭受一些重大的故障导致主分片和复制分片都故障，
那这个分片的数据将无法响应给搜索请求。这种情况下，Elasticsearch将报告分片failed，但仍将继续返回剩余分片上的结果。


## 倒排索引






