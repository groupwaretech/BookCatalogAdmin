/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.groupware.bookcatalogadmin.model;

import java.io.Serializable;

/**
 *
 * @author heinsohnbusiness
 */

public class Image implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String path;
    private ObjectBook objectBook;

    public Image() {
    }

    public Image(Integer id) {
        this.id = id;
    }

    public Image(Integer id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ObjectBook getObjectBook() {
        return objectBook;
    }

    public void setObjectBook(ObjectBook objectBook) {
        this.objectBook = objectBook;
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
        if (!(object instanceof Image)) {
            return false;
        }
        Image other = (Image) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
}
