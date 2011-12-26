package ua.org.tumakha.spd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue
    private Integer addressId;

    @OneToOne
    @JoinColumn(name = "userId", unique = true, nullable = false, updatable = false, insertable = true)
    private User user;

    private Integer postalCode;

    private String region;

    private String regionEn;

    private String district;

    private String districtEn;

    private String city;

    private String cityEn;

    private String street;

    private String streetEn;

    private Integer house;

    @Column(nullable = true, length = 1)
    private String houseChar;

    @Column(nullable = true, length = 1)
    private String houseCharEn;

    private Integer slashHouse;

    private Integer apartment;

    @Column(nullable = true, length = 1)
    private String apartmentChar;

    @Column(nullable = true, length = 1)
    private String apartmentCharEn;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionEn() {
        return regionEn;
    }

    public void setRegionEn(String regionEn) {
        this.regionEn = regionEn;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictEn() {
        return districtEn;
    }

    public void setDistrictEn(String districtEn) {
        this.districtEn = districtEn;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetEn() {
        return streetEn;
    }

    public void setStreetEn(String streetEn) {
        this.streetEn = streetEn;
    }

    public Integer getHouse() {
        return house;
    }

    public void setHouse(Integer house) {
        this.house = house;
    }

    public Integer getSlashHouse() {
        return slashHouse;
    }

    public void setSlashHouse(Integer slashHouse) {
        this.slashHouse = slashHouse;
    }

    public Integer getApartment() {
        return apartment;
    }

    public void setApartment(Integer apartment) {
        this.apartment = apartment;
    }

    public String getHouseChar() {
        return houseChar;
    }

    public void setHouseChar(String houseChar) {
        this.houseChar = houseChar;
    }

    public String getHouseCharEn() {
        return houseCharEn;
    }

    public void setHouseCharEn(String houseCharEn) {
        this.houseCharEn = houseCharEn;
    }

    public String getApartmentChar() {
        return apartmentChar;
    }

    public void setApartmentChar(String apartmentChar) {
        this.apartmentChar = apartmentChar;
    }

    public String getApartmentCharEn() {
        return apartmentCharEn;
    }

    public void setApartmentCharEn(String apartmentCharEn) {
        this.apartmentCharEn = apartmentCharEn;
    }

}
