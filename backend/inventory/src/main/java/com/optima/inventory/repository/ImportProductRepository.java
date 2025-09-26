package com.optima.inventory.repository;

import com.optima.inventory.dto.response.ImportProductResponseDto;
import com.optima.inventory.entity.ImportProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportProductRepository extends JpaRepository<ImportProductEntity, Long> {
    List<ImportProductEntity> findImportProductByLogId(Long logId);


    @Query("""
    select p.quantity
    from ImportProductEntity p
    where p.id = :id
    """)
    Integer findImportProductById(@Param("id") Long id);

}
