package com.JMThouseWeb.JMThouse.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.JMThouseWeb.JMThouse.dto.ApproveDto;
import com.JMThouseWeb.JMThouse.dto.HostTableDto;
import com.JMThouseWeb.JMThouse.dto.KaKaoPayDto;
import com.JMThouseWeb.JMThouse.dto.ResponseDto;
import com.JMThouseWeb.JMThouse.model.Reservation;
import com.JMThouseWeb.JMThouse.service.ReservationService;

@RestController
@RequestMapping("/test/api/reserve")
public class ReservationApiController {
	@Autowired
	private ReservationService reservationService;
	
	// /test/api/reserve/delete/${reservationId}
	
	@PostMapping("/house")
	public int reserveHouse(@RequestBody Reservation reservation) {
		System.out.println(reservation);
		reservationService.makeReservation(reservation);
		
		return 1;
	}
	
	@GetMapping("/house/{hostId}/{houseId}/{month}")
	public List<HostTableDto> getHouseReservation(@PathVariable int hostId, @PathVariable int houseId, @PathVariable int month){
		System.out.println(">> "+hostId+"+" + houseId);
		
		List<HostTableDto> result = reservationService.getTableInfo(hostId, houseId, month );
		return result;
	}
	
	@GetMapping("/house/{hostId}/{month}")
	public List<HostTableDto> getHouseReservation(@PathVariable int hostId, @PathVariable int month){
		List<HostTableDto> result = reservationService.getTableInfo(hostId, month);
		return result;
	}
	
	@DeleteMapping("/delete/{reservationId}")
	public int deleteReservation(@PathVariable int reservationId) {
		reservationService.cancelReservation(reservationId);
		return reservationId;
	}
	
	@PostMapping("/host/approve")
	public ResponseDto<Integer> approveRes(@RequestBody ApproveDto approveDto) {
		System.out.println("get    "+approveDto);
		reservationService.changeResType(approveDto);
		return new ResponseDto<Integer>(HttpStatus.ACCEPTED.value(), approveDto.getResId());
	}
	
	
	
}
