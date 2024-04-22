package com.api.deso.repository;

import com.api.deso.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {


    // 이메일을 기준으로 company 조회
    Company findByEmail(String email);

    // 전화번호를 기준으로 User 조회
    Company findByBusinessNumber(Long number);
}
