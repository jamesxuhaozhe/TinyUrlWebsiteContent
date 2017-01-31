package com.james.tinyurl.dao;

import com.james.tinyurl.model.URL;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by haozhexu on 1/30/17.
 */
public interface URLMappingRepository extends JpaRepository<URL, Long>{
}
