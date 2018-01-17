package com.ibm.ojt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class CatalogController{
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@GetMapping("/all")
	public List<Catalog> findAllCatalog() {
		return mongoTemplate.findAll(Catalog.class, "catalogdata");
	}
	
	@GetMapping("/item/{prodcode}")
	public Catalog findByProdCode(@PathVariable String prodcode) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(prodcode));
		Catalog product = mongoTemplate.findOne(query, Catalog.class, "catalogdata");
		return product;
	}
	
	//stopped here --> search how to update the entire document
	@PutMapping("/item/{prodcode}")
	public void updateProduct(@PathVariable String prodcode, @RequestBody Catalog catalog) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(prodcode));
		Update update = new Update();
		update.set("prodcode", catalog.getProdcode());
		update.set("prodname", catalog.getProdname());
		update.set("proddesc", catalog.getProddesc());
		update.set("prodtype", catalog.getProdtype());
		update.set("prodprice", catalog.getProdprice());
		update.set("isavailable", catalog.isIsavailable());
		update.set("imgname", catalog.getImgname());
		update.set("tags", catalog.getTags());
		update.set("prodcolor", catalog.getProdcode());
		update.set("prodbrand", catalog.getProdbrand());
		update.set("gender", catalog.getGender());
		update.set("prodsizes", catalog.getProdsizes());
//		update.set("ratercount", catalog.getRatercount());
//		update.set("rateavg", catalog.getRateavg());
		update.set("salerate", catalog.getDiscountrate());
//		update.set("reviews", catalog.getReviews());
//		update.set("viewcount", catalog.getViewcount());
		mongoTemplate.updateFirst(query, update, "catalogdata");
	}
	
	@PostMapping(value = "/item", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void createProduct(@RequestBody Catalog catalog) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(catalog.getProdcode()));
		Catalog _catalog = mongoTemplate.findOne(query, Catalog.class, "catalogdata");
		if (_catalog == null) {
			_catalog = new Catalog();
			_catalog.setGender(catalog.getGender());
			_catalog.setImgname(catalog.getImgname());
			_catalog.setIsavailable(catalog.isIsavailable());
			_catalog.setProdbrand(catalog.getProdbrand());
			_catalog.setProdcode(catalog.getProdcode());
			_catalog.setProdcolor(catalog.getProdcolor());
			_catalog.setProddesc(catalog.getProddesc());
			_catalog.setProdname(catalog.getProdname());
			_catalog.setProdprice(catalog.getProdprice());
			_catalog.setProdsizes(catalog.getProdsizes());
			_catalog.setProdtype(catalog.getProdtype());
			_catalog.setRateavg(catalog.getRateavg());
			_catalog.setRatercount(catalog.getRatercount());
			_catalog.setReviews(catalog.getReviews());
			_catalog.setDiscountrate(catalog.getDiscountrate());
			_catalog.setViewcount(catalog.getViewcount());
			_catalog.setTags(catalog.getTags());
			mongoTemplate.save(_catalog, "catalogdata");
		}
	}
	
	@DeleteMapping("/item/{prodcode}")
	public void deleteProduct(@PathVariable String prodcode, @RequestBody Catalog catalog) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(prodcode));
		Catalog _catalog = mongoTemplate.findOne(query, Catalog.class, "catalogdata");
		if (_catalog != null) {
			mongoTemplate.remove(query, "catalogdata");
		}
	}
	
	@PostMapping("/item/{prodcode}/review")
	public void addReview(@RequestBody Review review, @PathVariable String prodcode) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(prodcode));
		Update update = new Update().addToSet("reviews", review);
		mongoTemplate.updateFirst(query, update, "catalogdata");
	}
	
	@PutMapping("/item/{prodcode}/review")
	public void updateReview(@RequestBody Review review, @PathVariable String prodcode) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(prodcode));
		Update update = new Update().set("reviews.$.reviewstring", review.getReviewstring());
		mongoTemplate.updateFirst(query, update, "catalogdata");
	}
	
	@DeleteMapping("/item/{prodcode}/review")
	public void deleteReview(@RequestBody Review review, @PathVariable String prodcode) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(prodcode));
		Update update = new Update().pull("reviews", review);
		mongoTemplate.updateFirst(query, update, "catalogdata");
	}
	
	@GetMapping("/item/{prodcode}/reviews")
	public List<Review> getReviews(@PathVariable String prodcode) {
		Query query = new Query().addCriteria(Criteria.where("prodcode").is(prodcode));
		return mongoTemplate.find(query, Review.class, "catalogdata");
	}
}