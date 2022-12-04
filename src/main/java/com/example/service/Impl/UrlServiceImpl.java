package com.example.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BusinessException;
import com.example.domain.Url;
import com.example.domain.User;
import com.example.mapper.UrlMapper;
import com.example.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UrlServiceImpl extends ServiceImpl<UrlMapper, Url> implements UrlService {
}
