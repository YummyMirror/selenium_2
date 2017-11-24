package ru.anatoli.practice_selenium.TestStructure.models;

public class CountryData {
    private Boolean status;
    private String code;
    private String code2;
    private String name;
    private String domesticName;
    private String taxIDFormat;
    private String addressFormat;
    private String postcodeFormat;
    private String currencyCode;
    private String phoneCountryCode;

    //Getters
    public Boolean getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getCode2() {
        return code2;
    }

    public String getName() {
        return name;
    }

    public String getDomesticName() {
        return domesticName;
    }

    public String getTaxIDFormat() {
        return taxIDFormat;
    }

    public String getAddressFormat() {
        return addressFormat;
    }

    public String getPostcodeFormat() {
        return postcodeFormat;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

    //Setters
    public CountryData setStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public CountryData setCode(String code) {
        this.code = code;
        return this;
    }

    public CountryData setCode2(String code2) {
        this.code2 = code2;
        return this;
    }

    public CountryData setName(String name) {
        this.name = name;
        return this;
    }

    public CountryData setDomesticName(String domesticName) {
        this.domesticName = domesticName;
        return this;
    }

    public CountryData setTaxIDFormat(String taxIDFormat) {
        this.taxIDFormat = taxIDFormat;
        return this;
    }

    public CountryData setAddressFormat(String addressFormat) {
        this.addressFormat = addressFormat;
        return this;
    }

    public CountryData setPostcodeFormat(String postcodeFormat) {
        this.postcodeFormat = postcodeFormat;
        return this;
    }

    public CountryData setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public CountryData setPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryData that = (CountryData) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
