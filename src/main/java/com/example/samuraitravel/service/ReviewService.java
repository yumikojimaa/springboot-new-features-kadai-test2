package com.example.samuraitravel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.EditForm;
import com.example.samuraitravel.form.RegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.repository.UserRepository;

@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final HouseRepository houseRepository;
	private final UserRepository userRepository;

	public ReviewService(ReviewRepository reviewRepository, HouseRepository houseRepository,
			UserRepository userRepository) {
		this.reviewRepository = reviewRepository;
		this.houseRepository = houseRepository;
		this.userRepository = userRepository;
	}
	
	//新規レビューをDBに保存
	@Transactional
	public void create(RegisterForm RegisterForm) {
		Review review = new Review();
		House house = houseRepository.getReferenceById(RegisterForm.getHouseId());
		User user = userRepository.getReferenceById(RegisterForm.getUserId());
		review.setHouse(house);
		review.setUser(user);
		review.setStar(RegisterForm.getStar());
		review.setReview(RegisterForm.getReview());
		reviewRepository.save(review);
	}

	//レビューの変更を保存
	@Transactional
	public void update(EditForm EditForm) {
		Review review = reviewRepository.getReferenceById(EditForm.getId());
		review.setStar(EditForm.getStar());
		review.setReview(EditForm.getReview());
		reviewRepository.save(review);
	}

}

    