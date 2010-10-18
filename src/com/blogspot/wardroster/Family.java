package com.blogspot.wardroster;

public class Family {
	private long id;
	private String name;
	
	private String phoneNumber1;
	private String phoneNumber2;
	private String phoneNumber3;
	
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String postal;
	
	private long homeTeacher1;
	private long homeTeacher2;
	
	private String email;
	
	private String household;
	
	public Family(){
		
	}
	public Family(long id){
		
	}
	public Family(String name, String pn1, String pn2, String address1, String address2, String city, String state, String country, String postal, long ht1, long ht2, String email, String household){
		this.name = name;
		this.phoneNumber1 = pn1;
		this.phoneNumber2 = pn2;
		this.address1 = address1;
		this.address2 = address2;
		this.homeTeacher1 = ht1;
		this.homeTeacher2 = ht2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.postal = postal;
		this.email = email;
		this.household = household;
	}
	public Family(String name){
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber1() {
		return phoneNumber1;
	}
	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}
	public String getPhoneNumber2() {
		return phoneNumber2;
	}
	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}
	public String getPhoneNumber3() {
		return phoneNumber3;
	}
	public void setPhoneNumber3(String phoneNumber3) {
		this.phoneNumber3 = phoneNumber3;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address) {
		this.address1 = address;
	}
	public long getHomeTeacher1() {
		return homeTeacher1;
	}
	public void setHomeTeacher1(long homeTeacher1) {
		this.homeTeacher1 = homeTeacher1;
	}
	public long getHomeTeacher2() {
		return homeTeacher2;
	}
	public void setHomeTeacher2(long homeTeacher2) {
		this.homeTeacher2 = homeTeacher2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPostal() {
		return postal;
	}
	public void setPostal(String postal) {
		this.postal = postal;
	}
	public String getHousehold() {
		return household;
	}
	public void setHousehold(String household) {
		this.household = household;
	}

}
