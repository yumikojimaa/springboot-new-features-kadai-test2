package com.example.samuraitravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.EditForm;
import com.example.samuraitravel.form.RegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.ReviewService;

@Controller
@RequestMapping("/houses/{houseId}/review")
public class ReviewController {
	private final ReviewRepository reviewRepository;
	private final ReviewService reviewService;
	private final HouseRepository houseRepository;
	
	 public ReviewController(ReviewRepository reviewRepository,HouseRepository houseRepository,ReviewService reviewService) {
		    this.reviewRepository = reviewRepository;
		    this.reviewService = reviewService;
		    this.houseRepository = houseRepository;
		  }
	 //家の詳細ページ
	 @GetMapping
	  public String index(Model model,@PageableDefault(page = 0, size = 6, sort = "id", direction = Direction.ASC)Pageable pageable) {
		   Page<Review> reviewPage;   
			   reviewPage = reviewRepository.findAll(pageable);
		   model.addAttribute("reviewPage", reviewPage);
		   return "houses/show";
	  }
	 //レビュー一覧の表示
	 @GetMapping("/table")
	  public String table(@PathVariable(name = "houseId") Integer houseId,Model model,@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC)Pageable pageable) {
		  Page<Review> reviewPage;   
		  reviewPage = reviewRepository.findAll(pageable);
		  House house = houseRepository.getReferenceById(houseId);
		  model.addAttribute("house", house);
	      model.addAttribute("reviewPage", reviewPage);
	      return "review/table"; 
	  }
	  
	 //レビューの投稿
	  @GetMapping("/register")
	  public String register(@PathVariable(name = "houseId") Integer houseId, Model model) {
	      House house = houseRepository.getReferenceById(houseId);
	      model.addAttribute("house", house);
	      model.addAttribute("RegisterForm", new RegisterForm());
	      return "review/register";
	  }

	  //レビューの作成
	  @PostMapping("/create")
	  public String create(@ModelAttribute @Validated RegisterForm RegisterForm, BindingResult bindingResult,
			  RedirectAttributes redirectAttributes,
			  @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
			  ) {
	      if (bindingResult.hasErrors()) {
	          return "houses/show/review/register";
	      }
	      User user = userDetailsImpl.getUser();
	      RegisterForm.setUserId(user.getId());
	      reviewService.create(RegisterForm);
	 
	      redirectAttributes.addFlashAttribute("successMessage", "レビューを登録しました。");
	      return "redirect:/houses/{houseId}";
	  }

	  //レビューの編集
	  @GetMapping("/{id}/edit")
	  public String edit(@PathVariable(name = "id") Integer id,
			             @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			             @PathVariable(name = "houseId") Integer houseId,		             
			             Model model) {
		  House house = houseRepository.getReferenceById(houseId);
	      Review review = reviewRepository.getReferenceById(id);
	      EditForm EditForm = new EditForm(review.getId(),house.getId(),review.getStar(), review.getReview());
	      model.addAttribute("house", house);
	      model.addAttribute("EditForm", EditForm);
	      return "review/edit";
	  }

	  //レビューの更新
	  @PostMapping("{id}/update")
	  public String update(@ModelAttribute@Validated EditForm EditForm, BindingResult bindingResult,@PathVariable(name = "houseId") Integer houseId, Model model, RedirectAttributes redirectAttributes) {
		   if(bindingResult.hasErrors()) {
			   return "review/edit";
		   }
		   House house = houseRepository.getReferenceById(houseId);
		   model.addAttribute("house", house);
		   reviewService.update(EditForm);
		   redirectAttributes.addFlashAttribute("successMessage", "レビューを編集しました。");
		   return "redirect:/houses/{houseId}";

	  }
	  
	  //レビューの削除
	  @GetMapping("/{id}/delete")
	  public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
		   reviewRepository.deleteById(id);
		   redirectAttributes.addFlashAttribute("successMessage", "レビューを削除しました。");
		   return "redirect:/houses/{houseId}";
	  }

}