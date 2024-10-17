package com.example.samuraitravel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Reservation;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	 public Page<Reservation> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
	 public Page<Reservation> findByHouse(House house, Pageable pageble);

     public Page<Review> findByHouseOrderByCreatedAtDesc(House house, Pageable pageble);
}