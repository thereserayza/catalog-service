package com.ibm.ojt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="catalogdetail")
public class Catalog {
	@Id
	private String id;
	
	private String prodcode;
	private String prodname;
	private String proddesc;
	private String prodtype;
	private double prodprice;
	private boolean isavailable;
	private String imgname;
	private List<String> tags = new ArrayList<String>();
	private String prodcolor;
	private String prodbrand;
	private String gender;
	private List<Double> prodsizes = new ArrayList<Double>();
	private int ratercount;
	private double rateavg;
	private double salerate;
	private List<Review> reviews = new ArrayList<Review>();
	private int viewcount;
	
	public String getProdcode() {
		return prodcode;
	}
	public void setProdcode(String prodcode) {
		this.prodcode = prodcode;
	}
	public String getProdname() {
		return prodname;
	}
	public void setProdname(String prodname) {
		this.prodname = prodname;
	}
	public String getProdtype() {
		return prodtype;
	}
	public void setProdtype(String prodtype) {
		this.prodtype = prodtype;
	}
	public double getProdprice() {
		return prodprice;
	}
	public void setProdprice(double prodprice) {
		this.prodprice = prodprice;
	}
	public boolean isIsavailable() {
		return isavailable;
	}
	public void setIsavailable(boolean isavailable) {
		this.isavailable = isavailable;
	}
	public String getImgname() {
		return imgname;
	}
	public void setImgname(String imgname) {
		this.imgname = imgname;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getProdcolor() {
		return prodcolor;
	}
	public void setProdcolor(String prodcolor) {
		this.prodcolor = prodcolor;
	}
	public String getProdbrand() {
		return prodbrand;
	}
	public void setProdbrand(String prodbrand) {
		this.prodbrand = prodbrand;
	}
	public List<Double> getProdsizes() {
		return prodsizes;
	}
	public void setProdsizes(List<Double> prodsizes) {
		this.prodsizes = prodsizes;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getRatercount() {
		return ratercount;
	}
	public void setRatercount(int ratercount) {
		this.ratercount = ratercount;
	}
	public double getRateavg() {
		return rateavg;
	}
	public void setRateavg(double rateavg) {
		this.rateavg = rateavg;
	}
	public double getSalerate() {
		return salerate;
	}
	public void setSalerate(double salerate) {
		this.salerate = salerate;
	}
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	public String getProddesc() {
		return proddesc;
	}
	public void setProddesc(String proddesc) {
		this.proddesc = proddesc;
	}
	public int getViewcount() {
		return viewcount;
	}
	public void setViewcount(int viewcount) {
		this.viewcount = viewcount;
	}
}
