package com.kt.mmp.setl.setlCalc.service;

import com.kt.mmp.comn.base.mvc.service.JpaService;
import com.kt.mmp.setl.setlCalc.domain.MedicalTransacHst;
import com.kt.mmp.setl.setlCalc.repository.MedicalTransacHstRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class MedicalTransacService extends JpaService<MedicalTransacHst, String> {

  @Autowired
  @Override
  protected void setRepository(JpaRepository<MedicalTransacHst, String> repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public List<MedicalTransacHst> findByOccDtBetweenOrderByOccDt(LocalDateTime stDt, LocalDateTime fnsDt) {
    return ((MedicalTransacHstRepository)repository).findByOccDtBetweenOrderByOccDt(stDt, fnsDt);
  }

}
