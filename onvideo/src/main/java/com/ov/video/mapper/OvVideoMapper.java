package com.ov.video.mapper;

import com.ov.video.entity.OvVideo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sir
 * @since 2022-02-25
 */
public interface OvVideoMapper extends BaseMapper<OvVideo> {

    @Select("SELECT * FROM ov_video AS t1 JOIN (SELECT ROUND(RAND()*(SELECT MAX(vid) FROM ov_video)) AS vid) AS t2 WHERE t1.vid>=t2.vid ORDER BY t1.vid LIMIT ${limit};")
    List<OvVideo> getRandomVideoLimit(@Param("limit") Integer limit);

    @Select("SELECT * FROM ov_video AS t1 JOIN (SELECT ROUND(RAND()*(SELECT MAX(vid) FROM ov_video)) AS vid) AS t2 WHERE t1.vid>=t2.vid and t1.vfid=\"${fid}\" ORDER BY t1.vid LIMIT ${limit};")
    List<OvVideo> getRandomVideo(@Param("fid") Integer fid, @Param("limit") int limit);

    @Select("SELECT * FROM ov_video AS t1 JOIN (SELECT ROUND(RAND()*(SELECT MAX(vid) FROM ov_video)) AS vid) AS t2 WHERE t1.vid>=t2.vid and t1.vmid=\"${mid}\" and t1.vfid=\"${fid}\" ORDER BY t1.vid LIMIT ${limit};")
    List<OvVideo> getRandomVideo2(@Param("fid") Integer fid,@Param("mid") Integer mid,  @Param("limit") Integer limit);

    @Select("SELECT * FROM ov_video AS t1 JOIN (SELECT ROUND(RAND()*(SELECT MAX(vid) FROM ov_video)) AS vid) AS t2 WHERE t1.vid>=t2.vid ORDER BY t1.vid LIMIT ${limit};")
    List<OvVideo> getLeftImages(@Param("limit") Integer limit);

    @Select("SELECT * FROM ov_video AS t1 JOIN (SELECT ROUND(RAND()*(SELECT MAX(vid) FROM ov_video)) AS vid) AS t2 WHERE t1.vid>=t2.vid and t1.vfid=\"${fid}\" ORDER BY t1.vid LIMIT ${limit};")
    List<OvVideo> getLeftImages2(@Param("fid") Integer fid,@Param("limit") Integer limit);

}
