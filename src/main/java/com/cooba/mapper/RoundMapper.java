package com.cooba.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cooba.entity.Round;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoundMapper extends BaseMapper<Round> {

    @Select("SELECT round FROM rounds ORDER BY round DESC LIMIT 1")
    Integer getLatestRound();

    @Insert("INSERT INTO rounds (round) SELECT IFNULL(MAX(round), 0) + 1 FROM rounds")
    void generateNextRound();
}
