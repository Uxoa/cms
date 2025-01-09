package io.airboss.cms.routes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RouteRepository extends JpaRepository<Route, Integer>, JpaSpecificationExecutor<Route> {
    
}