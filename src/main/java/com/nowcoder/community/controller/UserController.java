package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.entity.*;
import com.nowcoder.community.service.*;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Dai
 * @Date: 2024/04/07 12:53
 * @Description: UserController
 * @Version: 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController implements CommunityConstant {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;

    @Value("${qiniu.key.access}")
    private String accessKey;

    @Value("${qiniu.key.secret}")
    private String secretKey;

    @Value("${qiniu.bucket.header.name}")
    private String headerBucketName;

    @Value("${qiniu.bucket.header.url}")
    private String headerBucketUrl;

    @Autowired
    private CommentService commentService;

    @Autowired
    private DiscussPostService discussPostService;

    @LoginRequired
    @RequestMapping(path = "/setting",method = RequestMethod.GET)
    public String getSettingPage(Model model) {
        // 上传文件名称
        String fileName = CommunityUtil.generateUUID();
        //设置响应信息
        StringMap policy = new StringMap();
        policy.put("returnBody",CommunityUtil.getJsonString(0));
        // 生成上传凭证
        Auth auth = Auth.create(accessKey, secretKey);
        String uploadToken = auth.uploadToken(headerBucketName, fileName, 3600, policy);

        model.addAttribute("uploadToken",uploadToken);
        model.addAttribute("fileName",fileName);

        return "/site/setting";
    }

    //更新头像的路径
    @RequestMapping(path = "/header/url",method = RequestMethod.POST)
    @ResponseBody
    public String updateHeaderUrl(String fileName){
        if (StringUtils.isBlank(fileName)){
            return CommunityUtil.getJsonString(1,"文件名不能为空！");
        }
        String url = headerBucketUrl + "/" + fileName;
        userService.updateHeader(hostHolder.getUser().getId(),url);
        return CommunityUtil.getJsonString(0);
    }

    // 废弃
    @LoginRequired
    @RequestMapping(path = "/upload",method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model){
        if(headerImage == null){
            model.addAttribute("error","您还没有选择图片！");
            return "/site/setting";
        }

        String filename = headerImage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if(StringUtils.isBlank(suffix)){
            model.addAttribute("error","文件格式不正确！");
            return "/site/setting";
        }
        // 生成随机文件名
        filename = CommunityUtil.generateUUID() + suffix;
        // 确定文件存放的路径
        File dest = new File(uploadPath + "/" + filename);
        try {
            // 存储文件
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败：" + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常！",e);
        }
        // 更新当前用户的头像路径（web访问路径）
        // http://localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + filename;
        userService.updateHeader(user.getId(),headerUrl);

        return "redirect:/index";

    }


    //废弃
    @RequestMapping(path = "/header/{fileName}",method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String filename, HttpServletResponse response){
        // 服务器存放路径
        filename = uploadPath + "/" + filename;
        // 文件后缀
        String suffix = filename.substring(filename.lastIndexOf("."));
        // 响应图片
        response.setContentType("image/" + suffix);
        try (
                FileInputStream fis = new FileInputStream(filename);
                OutputStream os = response.getOutputStream();
        ){
            byte[] buffer = new byte[1024];
            int b = 0;
            while((b = fis.read(buffer)) != -1){
                os.write(buffer,0,b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败：" + e.getMessage());
        }
    }

    //修改密码
    @RequestMapping(path = "/updatePassword",method = RequestMethod.POST)
    public String updatePassword(Model model, String oldPassword,String newPassword,String confirmPassword){
        if(StringUtils.isBlank(oldPassword)){
            model.addAttribute("oldPasswordMsg","原密码不能为空！");
            return "/site/setting";
        }
        if(StringUtils.isBlank(newPassword)){
            model.addAttribute("newPasswordMsg","新密码不能为空！");
            return "/site/setting";
        }
        if(StringUtils.isBlank(confirmPassword)){
            model.addAttribute("confirmPasswordMsg","确认密码不能为空！");
            return "/site/setting";
        }
        if(!newPassword.equals(confirmPassword)){
            model.addAttribute("confirmPasswordMsg","两次密码输入不一致！");
            return "/site/setting";
        }
        User user = userService.findUserByName(hostHolder.getUser().getUsername());
        if(!user.getPassword().equals(CommunityUtil.md5(oldPassword + user.getSalt()))){
            model.addAttribute("oldPasswordMsg","原密码输入错误！");
            return "/site/setting";
        }
        if(newPassword.equals(oldPassword)){
            model.addAttribute("newPasswordMsg","输入的新密码与旧密码一致！");
            return "/site/setting";
        }
        userService.updatePassword(user,newPassword);
        model.addAttribute("msg","修改密码成功！");
        return "redirect:/index";
    }


    //个人主页
    @RequestMapping(path = "/profile/{userId}",method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId,Model model){
        User user = userService.findUserById(userId);
        if(user == null){
            throw new RuntimeException("该用户不存在！");
        }
        //用户
        model.addAttribute("user",user);
        //点赞数量
        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount",likeCount);
        //关注数量
        long followeeCount = followService.findFolloweeCount(userId,ENTITY_TYPE_USER);
        model.addAttribute("followeeCount",followeeCount);
        //粉丝数量
        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER, userId);
        model.addAttribute("followerCount",followerCount);
        // 是否已关注
        boolean hasFollowed = false;
        if(hostHolder.getUser() != null){
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
        }
        model.addAttribute("hasFollowed",hasFollowed);
        return "/site/profile";
    }


    @RequestMapping(path = "/myPost/{userId}",method = RequestMethod.GET)
    public String getMyPostPage(@PathVariable("userId") int userId,Page page,Model model){
        User user = userService.findUserById(userId);
        if(user == null){
            throw new RuntimeException("该用户不存在！");
        }
        //分页信息
        page.setLimit(5);
        page.setRows(discussPostService.findDiscussPostRows(userId));
        page.setPath("/user/myPost/"+userId);
        //帖子列表
        List<DiscussPost> discussPostList = discussPostService.findDiscussPosts(userId, page.getOffset(), page.getLimit(),0);
        List<Map<String,Object>> discussPosts = new ArrayList<>();
        if(discussPostList != null){
            for(DiscussPost discussPost:discussPostList){
                Map<String,Object> map = new HashMap<>();
                //帖子的id
                map.put("postId",discussPost.getId());
                //帖子的标题
                map.put("postTitle",discussPost.getTitle());
                //帖子的内容
                map.put("postContent",discussPost.getContent());
                //帖子的点赞数
                map.put("likeCount",likeService.findEntityLikeCount(ENTITY_TYPE_POST, discussPost.getId()));
                //帖子的发布时间
                map.put("postTime",discussPost.getCreateTime());
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        //回复数量
        model.addAttribute("postCount",discussPostService.findDiscussPostRows(userId));
        return "site/my-post";
    }


    @RequestMapping(path = "/myReply/{userId}",method = RequestMethod.GET)
    public String getMyReplyPage(@PathVariable("userId") int userId, Page page, Model model){
        User user = userService.findUserById(userId);
        if(user == null){
            throw new RuntimeException("该用户不存在！");
        }
        //分页信息
        page.setLimit(5);
        page.setRows(commentService.findCommentCountByUserId(ENTITY_TYPE_POST, userId));
        page.setPath("/user/myReply/"+userId);
        //回复列表

        List<Comment> commentList = commentService.findCommentsByUser(ENTITY_TYPE_POST, userId, page.getOffset(), page.getLimit());
        List<Map<String,Object>> comments = new ArrayList<>();
        if(commentList != null){
            for(Comment comment:commentList){
                Map<String,Object> map = new HashMap<>();
                //回复的帖子id
                map.put("postId",comment.getEntityId());
                //回复的帖子标题
                map.put("postTitle",discussPostService.findDiscussPostById(comment.getEntityId()).getTitle());
                //回复内容
                map.put("content",comment.getContent());
                //回复时间
                map.put("commentTime",comment.getCreateTime());
                comments.add(map);
            }
        }
        model.addAttribute("comments",comments);
        //回复数量
        model.addAttribute("commentCount",commentService.findCommentCountByUserId(ENTITY_TYPE_POST, userId));
        return "site/my-reply";
    }

}
