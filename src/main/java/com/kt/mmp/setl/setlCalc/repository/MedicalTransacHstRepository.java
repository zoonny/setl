package com.kt.mmp.setl.setlCalc.repository;

import com.kt.mmp.setl.setlCalc.domain.MedicalTransacHst;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalTransacHstRepository extends JpaRepository<MedicalTransacHst, String> {

  public List<MedicalTransacHst> findByOccDtBetweenOrderByOccDt(LocalDateTime stDt, LocalDateTime fnsDt);

}
