package com.sjy.elasticsearch;

import com.sjy.elasticsearch.entity.Student;
import org.elasticsearch.client.ElasticsearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author shijingyang
 */
public interface StudentDao extends ElasticsearchRepository<Student,Long> {

}
