package com.objective.informa.repository;

import com.objective.informa.domain.PushSubscription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PushSubscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PushSubscriptionRepository extends JpaRepository<PushSubscription, Long> {

}
