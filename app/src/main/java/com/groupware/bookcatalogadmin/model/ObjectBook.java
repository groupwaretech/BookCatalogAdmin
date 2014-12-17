/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.groupware.bookcatalogadmin.model;

import java.io.Serializable;
import java.util.List;


/**
 *
 * @author heinsohnbusiness
 */
public class ObjectBook implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private String code;
    private String tittle;
    private String author;
    private String publication;
    private String physicalDescription;
    private String notes;
    private String category;
    private String languageContent;
    private String isbn;
    private List<Author> authorList;
    private List<Category> categoryList;
    private LibraryStore libraryStore;
    private ObjectBookType objectBookType;
    private List<Image> imageList;

    public ObjectBook() {
    }

    public ObjectBook(String code) {
        this.code = code;
    }

    public ObjectBook(String code, String tittle) {
        this.code = code;
        this.tittle = tittle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getPhysicalDescription() {
        return physicalDescription;
    }

    public void setPhysicalDescription(String physicalDescription) {
        this.physicalDescription = physicalDescription;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguageContent() {
        return languageContent;
    }

    public void setLanguageContent(String languageContent) {
        this.languageContent = languageContent;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public LibraryStore getLibraryStore() {
        return libraryStore;
    }

    public void setLibraryStore(LibraryStore libraryStore) {
        this.libraryStore = libraryStore;
    }

    public ObjectBookType getObjectBookType() {
        return objectBookType;
    }

    public void setObjectBookType(ObjectBookType objectBookType) {
        this.objectBookType = objectBookType;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ObjectBook)) {
            return false;
        }
        ObjectBook other = (ObjectBook) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }


    
}
