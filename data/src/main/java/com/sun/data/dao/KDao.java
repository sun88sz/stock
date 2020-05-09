package com.sun.data.dao;

import com.sun.data.bean.KPo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sun
 */
public interface KDao extends JpaRepository<KPo,Long> {

    KPo findFirstByCodeOrderByTimeDesc(String code);
}
