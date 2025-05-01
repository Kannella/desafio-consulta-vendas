package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> getReport(String inicialDateStr, String finalDateStr, String name, Pageable pageable) {
		LocalDate finalDate = (finalDateStr == null)
				? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
				: LocalDate.parse(finalDateStr);

		LocalDate inicialDate = (inicialDateStr == null)
				? finalDate.minusYears(1L)
				: LocalDate.parse(inicialDateStr);

		Page<Sale> result = repository.searchReport(inicialDate, finalDate, name, pageable);
		return result.map(x -> new SaleMinDTO(x));

	}

	public List<SaleSummaryDTO> getSummary(String inicialDateStr, String finalDateStr) {
		LocalDate finalDate = (finalDateStr == null)
				? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
				: LocalDate.parse(finalDateStr);

		LocalDate inicialDate = (inicialDateStr == null)
				? finalDate.minusYears(1L)
				: LocalDate.parse(inicialDateStr);

		return repository.searchSummary(inicialDate, finalDate);
	}
}
