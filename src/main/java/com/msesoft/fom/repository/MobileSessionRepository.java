package com.msesoft.fom.repository;

import com.msesoft.fom.domain.MobileSession;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by oguz on 27.07.2016.
 */
public interface MobileSessionRepository extends GraphRepository <MobileSession>{
    MobileSession findByUniqueId(String uniqueId);

    MobileSession findByToken(String token);
}
