package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.common.BusinessException;
import com.example.common.Result;
import com.example.domain.Url;
import com.example.service.UrlService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 文件上传和下载
 */

@RestController
@RequestMapping("/common")
@Slf4j
@Api(tags = "上传下载接口")
public class CommonController {

    @Autowired
    private UrlService urlService;

    private static final String basePath = "D:/file/";

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @SaCheckLogin
    public Result upload(MultipartFile file){
        log.info("file: {}",file);

        if (file == null){
            throw new BusinessException("上传文件不得为空",500);
        }

        //原始文件名
        String originalFilename = file.getOriginalFilename();  //abc.jpg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String[] strings = originalFilename.split("\\.");

        //使用UUID重新生成文件名
        String fileName = UUID.randomUUID().toString() + suffix;

        //创建一个目录对象
        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdir();
        }

        try {
            //将文件转存
            file.transferTo(new File(basePath + fileName));

            //将文件名保存到数据库中
            Url url = new Url();

            url.setUrl(fileName);
            url.setFilename(strings[0]);

//            User user = (User) StpUtil.getSession().get("user");
//            url.setUserId(user.getUserId());

            url.setUserId(1592892250056384514L);
            url.setCreateTime(LocalDateTime.now());
            urlService.save(url);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.success(fileName,"success");
    }

    /**
     * 文件下载
     * @param url_id
     * @param response
     */
    @GetMapping("/download")
    @SaCheckLogin
    public Result download(Long url_id, HttpServletResponse response){

        Url url = urlService.getById(url_id);
        if (url == null){
            throw new BusinessException("该文件不存在", 403);
        }
        String fileName = url.getUrl();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String fileNewName = url.getFilename() + suffix;

        log.info("fileNewName : {}" ,fileNewName);

        OutputStream os = null;
        InputStream is= null;
        try {
            // 取得输出流
            os = response.getOutputStream();
            // 清空输出流
            response.reset();
            response.setContentType("application/x-download;charset=utf-8");
            //Content-Disposition中指定的类型是文件的扩展名，并且弹出的下载对话框中的文件类型图片是按照文件的扩展名显示的，点保存后，文件以filename的值命名，
            // 保存类型以Content中设置的为准。注意：在设置Content-Disposition头字段之前，一定要设置Content-Type头字段。
            //把文件名按UTF-8取出，并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码，中文不要太多，最多支持17个中文，因为header有150个字节限制。
//            response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("utf-8"),"ISO8859-1"));
            response.setHeader("Content-Disposition", "attachment;filename="+  java.net.URLEncoder.encode(fileNewName, "UTF-8"));
            //读取流
            File f = new File(basePath+fileName);
            is = new FileInputStream(f);
            if (is == null) {
                System.out.println("下载附件失败，请检查文件“" + fileName + "”是否存在");
                return Result.failure("下载附件失败，请检查文件“" + fileName + "”是否存在");
            }
            //复制
            IOUtils.copy(is, response.getOutputStream());

            response.getOutputStream().flush();
        } catch (IOException e) {
            return Result.failure("下载附件失败,error:"+e.getMessage());
        }
        //文件的关闭放在finally中
        finally
        {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //其实，这个返回什么都不重要
        return Result.success(200,"下载成功");
    }
}
