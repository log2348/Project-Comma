package com.JMThouseWeb.JMThouse.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.JMThouseWeb.JMThouse.dto.ApproveDto;
import com.JMThouseWeb.JMThouse.dto.HostTableDto;
import com.JMThouseWeb.JMThouse.dto.HoustWaitDto;
import com.JMThouseWeb.JMThouse.model.BookedDate;
import com.JMThouseWeb.JMThouse.model.Guest;
import com.JMThouseWeb.JMThouse.model.Host;
import com.JMThouseWeb.JMThouse.model.House;
import com.JMThouseWeb.JMThouse.model.Reservation;
import com.JMThouseWeb.JMThouse.model.ReservationType;
import com.JMThouseWeb.JMThouse.model.RoleType;
import com.JMThouseWeb.JMThouse.model.User;
import com.JMThouseWeb.JMThouse.repository.BookedDateRepository;
import com.JMThouseWeb.JMThouse.repository.GuestRepository;
import com.JMThouseWeb.JMThouse.repository.HostRepository;
import com.JMThouseWeb.JMThouse.repository.HostTableRepository;
import com.JMThouseWeb.JMThouse.repository.HouseRepository;
import com.JMThouseWeb.JMThouse.repository.ReservationRepository;

@Service
public class ReservationService {
<<<<<<< HEAD

=======
	public static String tid;
	
>>>>>>> feature_junic
	@Autowired
	private HostTableRepository hostTableRepository;

	// User레파지 스토리도 필요하다
	@Autowired
	private GuestRepository guestRepository;

	@Autowired
	private HostRepository hostRepository;

	@Autowired
	private BookedDateRepository bookedDateRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private HouseRepository houseRepository;

	@Transactional
	public void makeReservation(Reservation reservation) {
		int[] tempIdList = reservation.getTempIdBox();
		House house = houseRepository.findById(tempIdList[2]).orElseThrow(() -> {
			return new RuntimeException("해당 숙소를 찾을 수 없습니다.");
		});
		Guest guest = guestRepository.findById(tempIdList[0]).orElseThrow(() -> {
			return new RuntimeException("해당 게스트를 찾을 수 없습니다.");
		});
		Host host = hostRepository.findById(tempIdList[1]).orElseThrow(() -> {
			return new RuntimeException("해당 호스트를 찾을 수 없습니다.");
		});

		reservation.setHouseId(house);
		reservation.setGuestId(guest);
		reservation.setHostId(host);
<<<<<<< HEAD
<<<<<<< HEAD
		calculateBookedDates(reservation.getCheckInDate(), reservation.getCheckOutDate(),reservation);
=======
		calculateBookedDates(reservation.getCheckInDate(), reservation.getCheckOutDate(), reservation);
>>>>>>> feature_junic
		reservation.setApprovalStatus(ReservationType.WAITING);
		reservationRepository.save(reservation);
	}

	private void calculateBookedDates(Date checkinDate, Date checkOutDate, Reservation res) {
=======
		calculateBookedDates(reservation.getCheckInDate(), reservation.getCheckOutDate(), house);
		reservation.setApprovalStatus(ReservationType.WAITING);
		reservationRepository.save(reservation);
	}

	private void calculateBookedDates(Date checkinDate, Date checkOutDate, House house) {
>>>>>>> aeef87eda6e5a819e058acafc4bf5cf7f0385a18
		int range = getRangeDay(checkinDate, checkOutDate);

		for (int i = 0; i < range; i++) {
			BookedDate bookedDate = new BookedDate();
			bookedDate.setReservation(res);
			bookedDate.setBookedDate(changeToLocalDate(checkinDate).plusDays(i));
			bookedDateRepository.save(bookedDate);
		}

	}

	private LocalDate changeToLocalDate(Date date) {
		return new java.sql.Date(date.getTime()).toLocalDate();
	}

	private int getRangeDay(Date checkinDate, Date checkOutDate) {
		long sec = (checkOutDate.getTime() - checkinDate.getTime()) / 1000;
		int result = (int) sec / (24 * 60 * 60);
		System.out.println(result);
		return result;
	}

	@Transactional(readOnly = true)
	public List<Reservation> getReservation(User user) {
		List<Reservation> reservation;
		if (user.getRole() == RoleType.GUEST) {
			reservation = reservationRepository.findByGuestId(user.getId());
		} else {
			reservation = reservationRepository.findByHostId(user.getId());
		}
		return reservation;
	}
<<<<<<< HEAD
<<<<<<< HEAD
	
=======

>>>>>>> feature_junic
	@Transactional(readOnly = true)
	public List<HostTableDto> getTableInfo(int hostId, int houseId, int month) {
		return hostTableRepository.getlist(hostId, houseId, month);
	}
	
	@Transactional(readOnly = true)
	public List<HostTableDto> getTableInfo(int hostId, int month) {
		return hostTableRepository.getlist(hostId, month);
	}

	@Transactional(readOnly = true)
	public List<BookedDate> getListBookedDate(int houseid) {
		List<BookedDate> list = bookedDateRepository.findAllByHouseId(houseid);
		return list;
	}

	@Transactional(readOnly = true)
	public List<HoustWaitDto> getWaitCount(int hostid) {
		return hostTableRepository.getWaitCount(hostid);
	}

	@Transactional
	public void cancelReservation(int id) {
		bookedDateRepository.deleteAllByResId(id);
		reservationRepository.deleteById(id);
	}

	@Modifying
	@Transactional
	public void changeResType(ApproveDto approveDto) {
		Reservation reservation = findByResId(approveDto.getResId());
		reservation.setApprovalStatus(parseResEnumType(approveDto.getApprove()));
	}

	private ReservationType parseResEnumType(String type) {
		ReservationType enumType = ReservationType.WAITING;
		switch (type) {
		case "WAITING":
			enumType = ReservationType.WAITING;
			break;
		case "CANCELED":
			enumType = ReservationType.CANCELED;
			break;
		case "APPROVED":
			enumType = ReservationType.APPROVED;
			break;
		case "COMPLETED":
			enumType = ReservationType.COMPLETED;
		}
		return enumType;
	}

	public Reservation findByResId(int resId) {
		Reservation res = reservationRepository.findById(resId).orElseThrow(()->{
			return new RuntimeException("해당 예약을 찾을 수 없습니다.");
		});
		return res;
		
	}
	
<<<<<<< HEAD
=======

	public List<HostTableDto> getTableInfo(int hostId, int houseId) {
		return hostTableRepository.getlist(hostId, houseId);
	}

	public ArrayList<BookedDate> getListBookedDate(int hostid) {
		ArrayList<BookedDate> list = (ArrayList<BookedDate>) bookedDateRepository.findAllByHouseId(hostid);
		return list;
	}

	public List<HoustWaitDto> getWaitCount(int hostid) {
		return hostTableRepository.getWaitCount(hostid);
	}

	@Transactional(readOnly = true)
	public List<Reservation> getReservationList(int guestId) {
		return reservationRepository.findAllByGuestId(guestId).orElseGet(() -> {
			return new ArrayList<>();
		});
	}

>>>>>>> aeef87eda6e5a819e058acafc4bf5cf7f0385a18
=======
	public boolean kakaoPaymentApprove(int resId) {
		Reservation res = reservationRepository.findById(resId).get();
		if(res == null) {
			return false;
		}
		res.setApprovalStatus(ReservationType.COMPLETED);
		return true;
	}


>>>>>>> feature_junic
}
