package com.example.samuraitravel.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.Favorite;
import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
	public List<Favorite> findByHouse(House house);
	public Page<Favorite> findByUser(User user, Pageable pageable);
	
	@Transactional
	public void deleteByHouseIdAndUserId(Integer houseId, Integer userId);
	
	public Favorite getByHouseIdAndUserId(Integer houseId, Integer userId);
	
}