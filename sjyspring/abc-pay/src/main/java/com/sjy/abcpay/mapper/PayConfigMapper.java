package com.sjy.abcpay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sjy.abcpay.bean.PayConfig;
import org.springframework.stereotype.Repository;

@Repository
public interface PayConfigMapper extends BaseMapper<PayConfig> {
    void ddd();
}
