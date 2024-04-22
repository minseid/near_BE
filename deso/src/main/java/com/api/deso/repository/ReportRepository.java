package com.api.deso.repository;

import com.api.deso.model.entity.Report;
import com.api.deso.model.network.request.ReportApiRequest;
import com.api.deso.model.network.response.ReportApiResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    Report findByUserIdAndTargetIdAndType(Long uid, Long tid, String type);

    List<Report> OrderByCreatedAt();

}
