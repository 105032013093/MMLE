package com.mmle.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mmle.dao.CaseDao;
import com.mmle.entity.Case;
import com.mmle.entity.CaseType;
import com.mmle.serviceImple.ICaseService;

/**
 * @Title: CaseService.java
 * @Package com.mmle.service
 * @Description: TODO(用一句话描述该文件做什么)
 * @author lbb
 * @date 2016年7月23日 上午11:35:24
 * @version V1.0
 */
@Service
public class CaseService implements ICaseService {
	@Resource
	CaseDao caseDao;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> getCaseType() {
		List<CaseType> caseTypes = caseDao.getCaseType();
		Map<String, Object> map = new HashMap<>();
		for (CaseType caseType : caseTypes) {
			caseType.setCount(caseDao.getCaseTypeCountById(caseType.getTypeId()));
		}
		if (caseTypes.size() > 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
		}
		map.put("caseTypes", caseTypes);
		return map;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> getCaseInfo(Case cas) {
		List<Case> cases = new ArrayList<>();
		if (cas != null) {
			if (cas.getCaseType() != null) {
				if (cas.getCaseType().getTypeId() != null) {
					cases = caseDao.getCaseByTypeId(cas.getCaseType().getTypeId());
				}
				if (cas.getCaseType().getName() != null) {
					cases = caseDao.getCaseByTypeId(caseDao.getCaseTypeByName(cas.getCaseType().getName()).getTypeId());
				}
			}
			if (cas.getCaseName() != null) {
				cases = caseDao.getCaseByName(cas.getCaseName());
			}
		} else {
			cases = caseDao.getCase();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("code", 1);
		map.put("cases", cases);
		return map;
	}

	/**
	 * 
	 * @Title: addCaseInfo @Description: TODO(添加案由) @param @param
	 *         cas @param @return 设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> addCaseInfo(Case cas) {
		Map<String, Object> map = new HashMap<>();
		if (cas != null) {
			List<Case> cases = caseDao.getCaseByName(cas.getCaseName());
			System.out.println(cases.size());
			if (cases != null) {
				map.put("code", 101);
			}
			// 这里如果案由类型不存在，就创建案由类型
			CaseType caseType = caseDao.getCaseTypeByName(cas.getCaseType().getName());
			if (caseType == null) {
				caseDao.insertCaseType(cas.getCaseType());
				if (caseDao.insertCase(cas) == 1) {
					map.put("code", 1);
				} else {
					map.put("code", 0);
				}
			}
		} else {
			map.put("code", 102);
		}
		return map;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> deleteCaseInfo(Case cas) {
		Map<String, Object> map = new HashMap<>();
		if (cas != null) {
			if (caseDao.getCaseByCaseId(cas.getCaseId()) != null) {
				if (caseDao.deleteCaseInfo(cas.getCaseId()) == 1) {
					map.put("code", 1);
				}
			} else {
				map.put("code", 0);
			}
		} else {
			map.put("code", 0);
		}
		return map;
	}

	/**
	 * 
	 * @Title: updateCaseInfo
	 * @Description: (更新案由)
	 * @param cas
	 * @return
	 * @see com.mmle.serviceImple.ICaseService#updateCaseInfo(com.mmle.entity.Case)
	 */
	public Map<String, Object> updateCaseInfo(Case cas) {
		Map<String, Object> map = new HashMap<>();
		// 更新案由的同时要判断是否案由类型有改变，查看是否类型是否在数据库（数据库没做关联时需判断）,单由于案由类型是选择库里有的，所以无需判断
		if (caseDao.updateCaseInfo(cas) == 1) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
		}
		return map;
	}

	public Map<String, Object> updateCaseType(CaseType caseType) {
		Map<String, Object> map = new HashMap<>();
		if (caseDao.updateCaseType(caseType) == 1) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
		}
		return map;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> addCaseType(CaseType caseType) {
		Map<String, Object> map = new HashMap<>();
		if (caseType != null) {
			CaseType caseType2 = caseDao.getCaseTypeByName(caseType.getName());
			if (caseType2 != null) {
				map.put("code", 101);
			} else {
				caseDao.insertCaseType(caseType);
				map.put("code", 1);
			}
		}
		return map;
	}

	/**
	 * 
	 * @Title: deleteCaseType
	 * @Description: (删除案由类型)应该不能删除，删除以前的案由怎么办。待讨论（目前定为不可用，然后我取的时候只取可用的）
	 * @param caseType
	 * @return
	 * @see com.mmle.serviceImple.ICaseService#deleteCaseType(com.mmle.entity.CaseType)
	 */
	public Map<String, Object> deleteCaseType(CaseType caseType) {
		Map<String, Object> map = new HashMap<>();
		map.put("code", 0);
		// map.put("code", 102);
		// if (caseType != null) {
		// if (caseType.getTypeId()!=null) {
		// List<CaseType> caseTypes =
		// caseDao.getCaseTypeById(caseType.getTypeId());
		// if (caseTypes.size()>0) {
		// if (caseDao.deleteCaseTypeById(caseTypes.get(0).getTypeId())==1) {
		// map.put("code", 1);
		// }else {
		// map.put("code", 0);
		// }
		// }else{
		// map.put("code", 102);
		// }
		// }
		// }
		if (caseType != null) {
			if (caseType.getTypeId() != null) {
				caseType.setFlag(false);
				if (caseDao.updateCaseType(caseType) == 1) {
					map.put("code", 1);
				}
			}
		}
		return map;
	}
}
