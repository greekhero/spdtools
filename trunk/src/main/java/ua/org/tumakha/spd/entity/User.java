package ua.org.tumakha.spd.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private Integer userId;

    private boolean active;

    private String firstname;

    private String firstnameEn;

    private String middlename;

    private String middlenameEn;

    private String lastname;

    private String lastnameEn;

    @Column(unique = true, nullable = true)
    private Long PIN;

    private String regNumber;

    private Date regDate;

    private String regDPI;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Address address;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Bank bank;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Contract> contracts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Act> acts;

    @ManyToMany
    @JoinTable(name = "user_group_mapping", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "groupId", referencedColumnName = "groupId"))
    private Set<Group> groups;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstnameEn() {
        return firstnameEn;
    }

    public void setFirstnameEn(String firstnameEn) {
        this.firstnameEn = firstnameEn;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getMiddlenameEn() {
        return middlenameEn;
    }

    public void setMiddlenameEn(String middlenameEn) {
        this.middlenameEn = middlenameEn;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastnameEn() {
        return lastnameEn;
    }

    public void setLastnameEn(String lastnameEn) {
        this.lastnameEn = lastnameEn;
    }

    public Long getPIN() {
        return PIN;
    }

    public void setPIN(Long PIN) {
        this.PIN = PIN;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getRegDPI() {
        return regDPI;
    }

    public void setRegDPI(String regDPI) {
        this.regDPI = regDPI;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    public Set<Act> getActs() {
        return acts;
    }

    public void setActs(Set<Act> acts) {
        this.acts = acts;
    }

    public Contract getLastContract() {
        return (Contract) contracts.toArray()[0];
    }

    public Act getLastAct() {
        return (Act) acts.toArray()[0];
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

}
