package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleMinDTO>> getReport(
			@RequestParam(name="inicialDate", required=false) String inicialDateStr,
			@RequestParam(name="finalDate", required=false) String finalDateStr,
			@RequestParam(name="name", defaultValue = "") String name,
			Pageable pageable) {

		Page<SaleMinDTO> dto = service.getReport(inicialDateStr, finalDateStr, name, pageable);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleSummaryDTO>> getSummary(
			@RequestParam(name="inicialDate", required=false) String inicialDateStr,
			@RequestParam(name="finalDate", required=false) String finalDateStr
			) {
		List<SaleSummaryDTO> dto = service.getSummary(inicialDateStr, finalDateStr);
		return ResponseEntity.ok(dto);
	}
}
