package com.sun.data.dao;

import com.sun.data.bean.KPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KDao extends JpaRepository<KPo,Long> {

}
