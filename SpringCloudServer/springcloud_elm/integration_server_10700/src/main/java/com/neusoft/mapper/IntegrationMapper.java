package com.neusoft.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.neusoft.po.Integration;

@Mapper
public interface IntegrationMapper {
	
	//public List<Integration> listIntegration(Integration integration);
	
	@Select("select * from integration where userId = #{userId} and integrationState = #{integrationState} order by integrationId")
	public List<Integration> listIntegration(Integration integration);
	
	@Select("select * from integration where userId = #{userId} order by integrationId desc")
	public List<Integration> getDetailByUserId(String userId);
	
	@Select("select COALESCE(sum(points), 0) from integration where userId = #{userId}")
	public Integer getCreditByUserId(String userId);
	
	@Insert ("insert into integration values(null, #{userId}, #{points}, #{usedPoints}, #{integrationDate}, #{integrationState})")
	public int insertIntegration(Integration integration);
	
	@Update("update integration set usedPoints = #{usedPoints}, integrationState = #{integrationState} where integrationId = #{integrationId}")
	public int updateIntegration(Integration integration);
}