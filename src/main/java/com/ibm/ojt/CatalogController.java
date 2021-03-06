package com.ibm.ojt;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class CatalogController{
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@GetMapping("/catalog/all")
	public List<Catalog> findAllCatalog() {
		return mongoTemplate.findAll(Catalog.class, "catalogdata");
	}
	
	@GetMapping("/item/{prodcode}")
	public Catalog findByProdCode(@PathVariable String prodcode) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(prodcode));
		Catalog product = mongoTemplate.findOne(query, Catalog.class, "catalogdata");
		return product;
	}
	
	@PutMapping(consumes=MediaType.APPLICATION_JSON_VALUE, value="/item/{prodcode}")
	public void updateProduct(@PathVariable String prodcode, @RequestBody Catalog catalog) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(prodcode));
		Update update = new Update();
		update.set("prodcode", catalog.getProdcode());
		update.set("prodname", catalog.getProdname());
		update.set("proddesc", catalog.getProddesc());
		update.set("prodtype", catalog.getProdtype());
		update.set("prodprice", catalog.getProdprice());
		update.set("imgname", catalog.getImgname());
		update.set("tags", catalog.getTags());
		update.set("prodcolor", catalog.getProdcolor());
		update.set("prodbrand", catalog.getProdbrand());
		update.set("gender", catalog.getGender());
		update.set("discountrate", catalog.getDiscountrate());
		mongoTemplate.updateFirst(query, update, "catalogdata");
	}
	
	@PostMapping(value = "/item", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void createProduct(@RequestBody Catalog catalog) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(catalog.getProdcode()));
		Catalog _catalog = mongoTemplate.findOne(query, Catalog.class, "catalogdata");
		if (_catalog == null) {
			mongoTemplate.save(catalog, "catalogdata");
		}
	}
	
	@DeleteMapping("/item/{prodcode}")
	public void deleteProduct(@PathVariable String prodcode) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(prodcode));
		Catalog _catalog = mongoTemplate.findOne(query, Catalog.class, "catalogdata");
		if (_catalog != null) {
			mongoTemplate.remove(query, "catalogdata");
		}
	}
	
	@PostMapping(value="/item/{prodcode}/review", consumes=MediaType.APPLICATION_JSON_VALUE)
	public void addReview(@RequestBody Review review, @PathVariable String prodcode) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(prodcode));
		Update update = new Update().addToSet("reviews", review);
		mongoTemplate.updateFirst(query, update, "catalogdata");
	}
	
	@PutMapping(value="/item/{prodcode}/review", consumes=MediaType.APPLICATION_JSON_VALUE)
	public void updateReview(@RequestBody Review review, @PathVariable String prodcode) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(prodcode).and("reviews._id").in(new ObjectId(review.get_id())));
		Update update = new Update().set("reviews.$.reviewstring", review.getReviewstring());
		mongoTemplate.updateFirst(query, update, "catalogdata");
	}
	
	@DeleteMapping("/item/{prodcode}/review/{reviewId}")
	public void deleteReview(@PathVariable String prodcode, @PathVariable String reviewId) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(prodcode));
		Catalog _catalog = mongoTemplate.findOne(query, Catalog.class, "catalogdata");
		Review _review = null;
		for (Review i : _catalog.getReviews()) {
			if (i.get_id().toString().equals(reviewId)) {
				_review = i;
				break;
			}
		}
		Update update = new Update().pull("reviews", _review);
		mongoTemplate.updateFirst(query, update, "catalogdata");
	}
	
	@GetMapping("/item/{prodcode}/reviews")
	public List<Review> getReviews(@PathVariable String prodcode) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(prodcode));
		return mongoTemplate.find(query, Review.class, "catalogdata");
	}
	
	@GetMapping("/items/{prodcodes}")
	public List<Catalog> getCatalogInfo(@PathVariable String prodcodes) {
		String[] array = prodcodes.split("-");
		List<Catalog> list = new ArrayList<Catalog>();
		for (String prodcode : array) {
			Query query = new Query().addCriteria(Criteria.where("prodcode").is(prodcode));
			query.fields().include("prodcode");
			query.fields().include("prodprice");
			query.fields().include("prodname");
			query.fields().include("imgname");
			query.fields().include("discountrate");
			list.add(mongoTemplate.findOne(query, Catalog.class, "catalogdata"));
		}
		return list;
	}
	
	@GetMapping("/item/{prodcode}/discount")
	public double getDiscountPrice(@PathVariable String prodcode) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(prodcode));
		Catalog _catalog = mongoTemplate.findOne(query, Catalog.class, "catalogdata");
		double discountedPrice = _catalog.getProdprice() * (1 - _catalog.getDiscountrate());
		return discountedPrice;
	}
	
	@PatchMapping("/view/{prodcode}")
	public void incrementViewCount(@PathVariable String prodcode) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(prodcode));
		Update update = new Update().inc("viewcount", 1);
		System.out.println(mongoTemplate.updateFirst(query, update, "catalogdata"));
	}
}