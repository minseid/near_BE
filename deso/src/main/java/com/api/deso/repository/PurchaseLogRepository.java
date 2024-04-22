package com.api.deso.repository;

import com.api.deso.model.entity.PurchaseLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseLogRepository extends JpaRepository<PurchaseLog, Long> {
    PurchaseLog findByUserId(Long userId);

    PurchaseLog findByTicketId(Long ticketId);
}
