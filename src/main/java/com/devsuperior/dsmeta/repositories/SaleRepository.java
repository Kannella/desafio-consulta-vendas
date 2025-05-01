package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT s FROM Sale s WHERE s.date BETWEEN :startDate AND :endDate AND UPPER(s.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<Sale> searchReport(LocalDate startDate, LocalDate endDate, String name, Pageable pageable);

    @Query(value = """
    SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(
        s.seller.name,
        SUM(s.amount)
    )
    FROM Sale s
    WHERE s.date BETWEEN :inicialDateStr AND :finalDateStr
    GROUP BY s.seller.name
    ORDER BY SUM(s.amount) DESC
    """)
    List<SaleSummaryDTO> searchSummary(LocalDate  inicialDateStr, LocalDate finalDateStr);
}
