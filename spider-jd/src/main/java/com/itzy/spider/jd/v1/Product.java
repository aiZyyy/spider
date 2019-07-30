package com.itzy.spider.jd.v1;

public class Product {
	
	private String id;
	private String name;
	private String title;
	private String price;
	private String maidian;
	private String pinpai;
	private String xinghao;
	private String url;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMaidian() {
		return maidian;
	}
	public void setMaidian(String maidian) {
		this.maidian = maidian;
	}
	public String getPinpai() {
		return pinpai;
	}
	public void setPinpai(String pinpai) {
		this.pinpai = pinpai;
	}
	public String getXinghao() {
		return xinghao;
	}
	public void setXinghao(String xinghao) {
		this.xinghao = xinghao;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", title=" + title + ", price=" + price + ", maidian=" + maidian
				+ ", pinpai=" + pinpai + ", xinghao=" + xinghao + ", url=" + url + "]";
	}

	
}
