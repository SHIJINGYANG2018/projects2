package com.sjy.abcpay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sjy.abcpay.bean.PayConfig;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 *
 */
public interface IPayConfigService extends IService<PayConfig> {

}
