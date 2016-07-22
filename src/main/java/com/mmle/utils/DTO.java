package com.mmle.utils;

import java.util.List;
import java.util.Map;

import com.mmle.entity.Case;
import com.mmle.entity.CaseType;
import com.mmle.entity.Check;
import com.mmle.entity.Exploration;
import com.mmle.entity.User;


/**   
 * @Title: DTO.java 
 * @Package com.mmle.utils 
 * @Description: TODO(数据传输对象) 
 * @author lbb
 * @date 2016年7月22日 下午9:23:50 
 * @version V1.0   
 */
public class DTO {
	//字段没有全 看之后传输的参数还需要什么，开发的时候在添进去，然后记得把ToString方法重写
	private Integer currentPage = 1;
	private Integer size = 10;
	private Map<String, Object> map;
	private Case cas;
	private CaseType caseType;
	private Check check;
	private Exploration exploration;
	private User user;
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public Case getCas() {
		return cas;
	}
	public void setCas(Case cas) {
		this.cas = cas;
	}
	public CaseType getCaseType() {
		return caseType;
	}
	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}
	public Check getCheck() {
		return check;
	}
	public void setCheck(Check check) {
		this.check = check;
	}
	public Exploration getExploration() {
		return exploration;
	}
	public void setExploration(Exploration exploration) {
		this.exploration = exploration;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "DTO [currentPage=" + currentPage + ", size=" + size + ", map=" + map + ", cas=" + cas + ", caseType="
				+ caseType + ", check=" + check + ", exploration=" + exploration + ", user=" + user + "]";
	}

}