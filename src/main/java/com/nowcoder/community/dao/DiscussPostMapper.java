package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Dai
 * @Date: 2024/03/21 13:25
 * @Description: DiscussPostMapper
 * @Version: 1.0
 */
@Mapper
public interface DiscussPostMapper {

    //首页查询评论时，userId赋为0，表示查询所有，取其他值时，查询个人用户评论
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    //@Param注解用于给参数取别名
    // 如果只有一个参数，并且在<if>里使用，则必须加别名
    //查询表里有多少条数据/评论(全部/个人)
    int selectDiscussPostRows(@Param("userId") int userId);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);

    int updateCommentCount(int id, int commentCount);

}
