package com.kt.mmp.setl.setlDstrb.repository;

import com.kt.mmp.setl.setlCalc.domain.SetlItemInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemInfoPk;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemDstrbDtl;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemDstrbDtlPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetlItemDstrbDtlRepository extends JpaRepository<SetlItemDstrbDtl, SetlItemDstrbDtlPk> {

}
