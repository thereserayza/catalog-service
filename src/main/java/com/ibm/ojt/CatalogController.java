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
		Update update = new Update().set("catalog", catalog);
		mongoTemplate.updateFirst(query, update, "cart");
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
			_catalog.setSalerate(catalog.getSalerate());
			_catalog.setViewcount(catalog.getViewcount());
			_catalog.setTags(catalog.getTags());
		}
	}
	
	@DeleteMapping("/item/{prodcode}")
	public void deleteProduct(@PathVariable String prodcode, @RequestBody Catalog catalog) {
		
	}
//	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//	public void createCart(@RequestBody Cart cart) {
//		Query query = new Query().addCriteria(Criteria.where("customerId").is(cart.getCustomerId()));
//		Cart _cart = mongoTemplate.findOne(query, Cart.class, "cart");
//		if (_cart == null) {
//			_cart = new Cart();
//			_cart.setCustomerId(cart.getCustomerId());
//			_cart.setCartItems(cart.getCartItems());
//			_cart.setStatus("OP");
//			mongoTemplate.save(_cart, "cart");
//		}
//	}
//	
//	@GetMapping
//	public List<Cart> findAllCarts() {
//		return mongoTemplate.findAll(Cart.class, "cart");
//	}
//	
//	@DeleteMapping("/{_id}")
//	public void deleteCart(@PathVariable String _id) {
//		Query query = new Query(Criteria.where("_id").is(_id));
//		mongoTemplate.findAllAndRemove(query, "cart");
//	}
//
//	@GetMapping("/search/caid/{cartId}")
//	public Cart findByCartId(@PathVariable String _id) {
//		Query query = new Query().addCriteria(Criteria.where("_id").is(_id));
//		Cart _cart = mongoTemplate.findOne(query, Cart.class, "cart");
//		return _cart;
//	}
//	
//	@GetMapping("/search/cuid/{customerId}")
//	public Cart findByCustomerId(@PathVariable String customerId) {
//		Query query = new Query().addCriteria(Criteria.where("customerId").is(customerId));
//		Cart _cart = mongoTemplate.findOne(query, Cart.class, "cart");
//		return _cart;
//	}
//	
//	//adds a new item to cart
//	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{_id}/add")
//	public void addToCart(@RequestBody CartItem cartItem, @PathVariable String _id) {
//		Criteria custCriteria = Criteria.where("_id").is(_id);
//		Query query = new Query(new Criteria().andOperator(custCriteria, Criteria.where("cartItems.prodCode").nin(cartItem.getProdCode())));
//		Update update = new Update().addToSet("cartItems", cartItem);
//		mongoTemplate.updateFirst(query, update, "cart");
//	}
//	
//	//Updates the quantity/size of item
//	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{_id}/update")
//	public void updateCartItem(@RequestBody CartItem cartItem, @PathVariable String _id) {
//		Criteria custCriteria = Criteria.where("_id").is(_id);
//		Query query = new Query(new Criteria().andOperator(custCriteria, Criteria.where("cartItems.prodCode").in(cartItem.getProdCode())));
//		Update update = new Update().set("cartItems.$.itemQty", cartItem.getItemQty());
//		System.out.println(mongoTemplate.updateFirst(query, update, "cart"));
//	}
//	
//	//deletes item from cart
//	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, value="/{_id}/delete")
//	public void deleteCartItem(@RequestBody CartItem cartItem, @PathVariable String _id) {
//		Query query = new Query().addCriteria(Criteria.where("_id").is(_id).and("cartItems.prodCode").in(cartItem.getProdCode()));
//		Cart _cart = mongoTemplate.findOne(query, Cart.class, "cart");
//		if (_cart != null) { // if new item is not in the cart
//			Update update = new Update().pull("cartItems", cartItem);
//			mongoTemplate.updateFirst(query, update, "cart");
//		}
//	}
//	
//	//deletes all items from cart
//	@PutMapping(value="/{_id}/delete/all")
//	public void emptyCart(@PathVariable String _id) {
//		Query query = new Query().addCriteria(Criteria.where("_id").is(_id));
//		Cart _cart = mongoTemplate.findOne(query, Cart.class, "cart");
//		_cart.getCartItems().clear();
//		Update update = new Update().set("cartItems", _cart.getCartItems());
//		mongoTemplate.updateFirst(query, update, "cart");
//	}
//	
//	//updates status of cart to "CL" when customer checks out
//	@PutMapping("/{_id}/checkout")
//	public void closeCart(@PathVariable String _id) {
//		Query query = new Query().addCriteria(Criteria.where("id").is(_id));
//		Update update = new Update().set("status", "CL");
//		mongoTemplate.updateFirst(query, update, "cart");
//	}
}