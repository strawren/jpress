/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by gaowm at 2014-3-29 上午8:40:12
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * gaowm     2014-3-29        Initailized
 */

package com.strawren.jpress.web.action.admin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.Page;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.bsp.util.FileUtils;
import com.strawren.bsp.util.SystemPropsUtils;
import com.strawren.bsp.util.UuidUtils;
import com.strawren.bsp.web.action.BaseMultiActionController;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.model.CmsPost;
import com.strawren.jpress.service.CmsPostService;
import com.strawren.jpress.util.ModelInfoUtils;


/**
 * 多媒体页面
 *
 */
@Controller
@RequestMapping("${adminPath}/media")
public class MediaController extends BaseMultiActionController{
    //媒体文件保存路径
    public static final String FILE_UPLOAD_DIR = SystemPropsUtils.getString("sys.app.upload.dir");

    //媒体文件访问路径
    public static final String ADMIN_MEDIA_DIR = SystemPropsUtils.getString("sys.media.url");

    @Autowired
    CmsPostService cmsPostService;

    /**
     * 多媒体首页
     * @param search
     * @param page
     * @return
     */
    @RequestMapping("/index.action")
    public ModelAndView index(String search,Page<CmsPost> page,HttpServletRequest request) {
        log.debug("media index begin...");

        ModelAndView mv = new ModelAndView("/cms/media/index");

        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_TYPE", JpressConstants.DICT_POST_TYPE_ATTACHMENT));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));

        if (!StringUtils.isBlank(search)) {
            filters.add(new PropertyFilter(MatchType.LIKE, PropertyType.S, "title", search));
        }

        page.setNumPerPage(10);
        page.setOrderDirection("DESC");
        page.setOrderField("ID");
        //TODO
        page = cmsPostService.search(page, filters);
        mv.addObject("page", page);
        mv.addObject("search", search);
        mv.addObject("mediaUrl", ADMIN_MEDIA_DIR);

        log.debug("media index end!!!");
        return mv;
    }

    /**
     * 上传媒体文件
     * @param uploadFile
     * @return
     */
    @RequestMapping("/upload.action")
    @ResponseBody
    public CmsPost uploadMedia(@RequestParam MultipartFile uploadFile,HttpServletResponse response) {
        log.debug("uploadMedia begin...");

        String fileName = uploadFile.getOriginalFilename();
        String targetFileName = UuidUtils.generate().toString();
        String extension = FileUtils.getExtension(fileName);
        targetFileName = targetFileName + "." + extension;

        if (!copyFileToServer(uploadFile,targetFileName)) {
            return null;
        }

        CmsPost mediaFile = new CmsPost();
        mediaFile.setTitle(fileName);
        mediaFile.setMimeType(extension);
        mediaFile.setGuid(targetFileName);
        mediaFile.setContent(targetFileName);

        ModelInfoUtils.createModelInfoBySys(mediaFile);

        mediaFile.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);
        mediaFile.setPostType(JpressConstants.DICT_POST_TYPE_ATTACHMENT);
        cmsPostService.save(mediaFile);


        log.debug("uploadMedia end!!!");
        return mediaFile;
    }

    /**
     * 拷贝文件至指定路径
     * @param uploadFile
     * @param targetFileName
     * @return
     */
    private boolean copyFileToServer(MultipartFile uploadFile, String targetFileName) {
        InputStream fis  = null;
        try {
            fis = uploadFile.getInputStream();
            String targetFilePath = FILE_UPLOAD_DIR + targetFileName;
            File uf = new File(targetFilePath);
            FileUtils.copyInputStreamToFile(fis, uf);
        }
        catch (IOException e) {
            log.error("uploadMedia file fail");
            log.error(e);
            return false;
        }
        finally{
            if (fis!=null) {
                try {
                    fis.close();
                }
                catch (IOException e) {
                    log.error("close the stream fail on uploadMedia file ");
                }
            }
        }

        return true;
    }

    /**
     * 获取单个媒体文件
     * @param guid
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getMedia.action")
    public void getMedia(String guid,HttpServletResponse response) throws IOException {
        log.debug("getMedia begin...");
        String targetFilePath = FILE_UPLOAD_DIR+guid;

        File targetFile = new File(targetFilePath);

        ServletOutputStream outputStream = null;
        BufferedInputStream bis = null;
        try {
            outputStream = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(targetFile));
            // 缓冲数组
            byte[] b = new byte[1024];
            int len;
            while ((len = bis.read(b)) != -1) {
                outputStream.write(b, 0, len);
            }
            outputStream.flush();
        }
        catch (IOException e) {
            log.info("getMedia fail");
        }
        finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
            catch (IOException e) {
                log.debug("close stream fail");
            }

        }
        log.debug("getMedia end...");
    }

    /**
     * 批量删除媒体文件
     * @param ids 多个ID用,分割
     * @return
     */
    @RequestMapping("/remove.action")
    public ModelAndView removeMedia(String ids){
        log.debug("/cms/media/removeMedia begin...");

            String[] mediaIds = ids.split(",");
            cmsPostService.batchRemoveMedia(mediaIds);

        log.debug("/cms/media/removeMedia end!!");
        return new ModelAndView("redirect:/cms/media/index.action?menu=menu_multimedia");
    }

    /**
     * 媒体文件编辑页面
     * @param ids 多个ID用,分割
     * @return
     */
    @RequestMapping("/edit.action")
    public ModelAndView editMedia(Long id){
        log.debug("/cms/media/editMedia begin...");

        ModelAndView mv = new ModelAndView("/cms/media/edit");

        CmsPost media = cmsPostService.get(id);
        mv.addObject("media", media);
        mv.addObject("mediaUrl", ADMIN_MEDIA_DIR);

        log.debug("/cms/media/editMedia end!!");
        return mv;
    }

    /**
     * 更新媒体文件相关信息
     * @return
     */
    @RequestMapping(value="/update.action",method=RequestMethod.POST)
    public ModelAndView updateMedia(CmsPost media) {
        log.debug("/cms/media/updateMedia begin...");

        ModelInfoUtils.updateModelInfoBySys(media);

        cmsPostService.update(media);

        log.debug("/cms/media/updateMedia end!!");

        return new ModelAndView("redirect:/cms/media/index.action?menu=menu_multimedia");
    }

    /**
     * 获取某一目录下的所有图片
     * @return
     */
    @RequestMapping("/getImagesByPath.action")
    @ResponseBody
    public List<String> getImagesByPath(String path) {
        log.debug("/cms/media/imageTree begin...");

        File dir = new File(path);
        File[] imageFiles = dir.listFiles(new FileFilter() {

            public boolean accept(File file) {
                return file.isFile();
            }
        });

        File root = new File(FILE_UPLOAD_DIR);
        String rootPath = root.getAbsolutePath();

        List<String> images = new ArrayList<String>();
        for (int i = 0; i < imageFiles.length; i++) {
            images.add(imageFiles[i].getAbsolutePath().substring(rootPath.length()).replaceAll("\\\\", "/"));
        }

        Collections.sort(images);

        log.debug("/cms/media/imageTree end!!");

        return images;
    }

    /**
     * 获取图片目录树
     * @return
     */
    @RequestMapping("/imageTree.action")
    @ResponseBody
    public List<Map<String, Object>> loadImageTree() {
        log.debug("/cms/media/imageTree begin...");

        List<Map<String, Object>> imageTree = getImageTreeByPath(FILE_UPLOAD_DIR);

        log.debug("/cms/media/imageTree end!!");

        return imageTree;
    }

    /**
     * 获取图片目录树,以Map形式组织
     * @param path
     * @return
     */
private List<Map<String, Object>> getImageTreeByPath(String path){
        List<Map<String, Object>> imageTree = new ArrayList<Map<String, Object>>();

        File root = new File(path);
        File[] subFiles = root.listFiles(new FileFilter() {

            public boolean accept(File file) {
                return file.isDirectory();
            }
        });


        if (subFiles != null && subFiles.length > 0) {
            for (int i = 0; i < subFiles.length; i++) {
                File subFile = subFiles[i];
                Map<String, Object> fileMap = new HashMap<String, Object>();
                fileMap.put("id", subFile.getAbsolutePath());
                fileMap.put("name", subFile.getName());
                fileMap.put("pId", root.getAbsolutePath());
                fileMap.put("children", getImageTreeByPath(subFile.getAbsolutePath()));
                imageTree.add(fileMap);
            }

        }

        Collections.sort(imageTree, imageTreeComparator);
        return imageTree;
   }

    /**
     * 比较器,用于显示图片目录树时按字母升序排列.
     *
     */
    private final Comparator<Map<String, Object>> imageTreeComparator = new Comparator<Map<String, Object>>() {

        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
            String name1 = (String) o1.get("name");
            String name2 = (String) o2.get("name");
            return name1.compareTo(name2);
        }

    };

}
