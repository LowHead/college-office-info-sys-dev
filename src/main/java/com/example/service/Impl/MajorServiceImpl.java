package com.example.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BusinessException;
import com.example.domain.Major;
import com.example.domain.User;
import com.example.dto.MajorDto;
import com.example.exception.DuplicateMajorNameException;
import com.example.exception.DuplicatePositionException;
import com.example.mapper.MajorMapper;
import com.example.service.MajorService;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        if (iMajor != null) {
            throw new BusinessException(new DuplicateMajorNameException("专业名称已存在，请勿重复添加"),500);
        }

        List<String> positions = majorDto.getUserPosition();

        if (Objects.equals(positions.get(0), positions.get(1))) {
            throw new BusinessException(new DuplicatePositionException("职位已存在，请勿重复添加"),500);
        }

        Major major = new Major();
        major.setMajorName(majorDto.getMajorName());
        major.setCreateUserId(StpUtil.getLoginIdAsLong());
        major.setUpdateUserId(StpUtil.getLoginIdAsLong());
        boolean save = save(major);

        if (!save) {
            return false;
        }

        iMajor = getOne(new LambdaQueryWrapper<Major>().eq(Major::getMajorName, majorDto.getMajorName()));

        assert iMajor != null;
        log.info("获取到专业信息为：{}",iMajor);

        return addUser(majorDto, iMajor);
    }

    private boolean addUser(MajorDto majorDto, Major iMajor) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            User user = new User();
            user.setUsername(majorDto.getUsername().get(i));
            user.setPassword(SecureUtil.md5(defaultPassword));
            user.setUserMajorId(iMajor.getMajorId());
            user.setUserPosition(majorDto.getUserPosition().get(i));
            user.setUserMail(majorDto.getUserMail().get(i));
            user.setUserSex(majorDto.getUserSex().get(i));
            user.setUserStatus("1");
            user.setUpdatePerson(StpUtil.getLoginIdAsLong());
            user.setLoginTime(LocalDateTime.now());
            log.info("用户信息为：{}", user);
            users.add(user);
        }
        return userService.saveBatch(users);
    }
}
