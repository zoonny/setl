package com.kt.mmp.comn.base.mvc.service;

import com.kt.mmp.comn.base.mvc.domain.BaseEntity;
import com.kt.mmp.comn.base.util.ComnUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
public abstract class BaseService {

  protected void setCretCtrlField(Object object, BaseEntity baseEntity) {
    ComnUtils.setCretCtrlField(object, baseEntity);
  }

  protected void setAmdCtrlField(Object object, BaseEntity baseEntity) {
    ComnUtils.setAmdCtrlField(object, baseEntity);
  }

}
