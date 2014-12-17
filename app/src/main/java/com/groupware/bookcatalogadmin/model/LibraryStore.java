/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.groupware.bookcatalogadmin.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author heinsohnbusiness
 */
public class LibraryStore implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private BigDecimal geoPositionLat;
    private BigDecimal geoPositionLon;

    public LibraryStore() {
    }

    public LibraryStore(Integer id) {
        this.id = id;
    }

    public LibraryStore(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getGeoPositionLat() {
        return geoPositionLat;
    }

    public void setGeoPositionLat(BigDecimal geoPositionLat) {
        this.geoPositionLat = geoPositionLat;
    }

    public BigDecimal getGeoPositionLon() {
        return geoPositionLon;
    }

    public void setGeoPositionLon(BigDecimal geoPositionLon) {
        this.geoPositionLon = geoPositionLon;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LibraryStore)) {
            return false;
        }
        LibraryStore other = (LibraryStore) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
}
