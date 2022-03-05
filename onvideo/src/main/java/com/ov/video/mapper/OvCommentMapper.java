package com.ov.video.mapper;

import com.ov.video.entity.OvComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ov.video.entity.OvUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sir
 * @since 2022-02-25
 */
public interface OvCommentMapper extends BaseMapper<OvComment> {

    @Select("select * from ov_user where uid = \"${uid}\"")
    OvUser getUserInfo(@Param("uid") Integer uid);

}
