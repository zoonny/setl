package com.kt.mmp.setl.setlDstrb.repository;

import com.kt.mmp.setl.setlCalc.domain.SetlItemInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemInfoPk;
import com.kt.mmp.setl.setlDstrb.domain.PtnrInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PtnrInfoRepository extends JpaRepository<PtnrInfo, String> {

}
