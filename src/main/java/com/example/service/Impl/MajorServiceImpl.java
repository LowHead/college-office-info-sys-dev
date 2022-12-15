package com.example.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BusinessException;
import com.example.domain.Major;
import com.example.domain.User;
import com.example.dto.MajorDto;
import com.example.exception.DuplicatePositionException;
import com.example.mapper.MajorMapper;
import com.example.service.MajorService;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

import java.time.LocalDateTime;

import static com.example.util.ServiceConstant.defaultPassword;

@Service
@Slf4j
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major> implements MajorService {

    @Resource
    private UserService userService;


    @Override
    @Transactional
    public Boolean addMajorAndUser(MajorDto majorDto) {

        Major iMajor = getOne(new LambdaQueryWrapper<Major>().eq(Major::getMajorName, majorDto.getMajorName()));

        if (iMajor == null) {
            Major major = new Major();
            major.setMajorName(majorDto.getMajorName());
            major.setCreateUserId(StpUtil.getLoginIdAsLong());
            major.setUpdateUserId(StpUtil.getLoginIdAsLong());
            boolean save = save(major);

            if (!save) {
                return false;
            }
        }

        iMajor = getOne(new LambdaQueryWrapper<Major>().eq(Major::getMajorName, majorDto.getMajorName()));

        log.info("获取到专业信息为：{}",iMajor);

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserMajorId, iMajor.getMajorId());
        lambdaQueryWrapper.eq(User::getUserPosition, majorDto.getUserPosition());

        if (userService.getOne(lambdaQueryWrapper) != null) {
            throw new BusinessException(new DuplicatePositionException("该职位用户已存在"),404);
        }

        return addUser(majorDto, iMajor);
    }

    private boolean addUser(MajorDto majorDto, Major iMajor) {
            User user = new User();
            user.setUsername(majorDto.getUsername());
            user.setPassword(SecureUtil.md5(defaultPassword));
            user.setUserMajorId(iMajor.getMajorId());
            user.setUserPosition(majorDto.getUserPosition());
            user.setUserMail(majorDto.getUserMail());
            user.setUserSex(majorDto.getUserSex());
            user.setUserStatus("1");
            user.setUpdatePerson(StpUtil.getLoginIdAsLong());
            user.setLoginTime(LocalDateTime.now());
            log.info("用户信息为：{}", user);
        return userService.save(user);
    }
}
