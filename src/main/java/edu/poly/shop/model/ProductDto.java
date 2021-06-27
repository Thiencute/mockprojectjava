package edu.poly.shop.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
private Long productId;
private String name;
private int quatity;
private double unitPrice;
private String image;
private MultipartFile imageFile;
private String description;
private double discount;
private Date enteredDate;
private short status;
private Long categoryId;

private Boolean isEdit;

public Long getProductId() {
	return productId;
}

public void setProductId(Long productId) {
	this.productId = productId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getQuatity() {
	return quatity;
}

public void setQuatity(int quatity) {
	this.quatity = quatity;
}

public double getUnitPrice() {
	return unitPrice;
}

public void setUnitPrice(double unitPrice) {
	this.unitPrice = unitPrice;
}

public String getImage() {
	return image;
}

public void setImage(String image) {
	this.image = image;
}

public MultipartFile getImageFile() {
	return imageFile;
}

public void setImageFile(MultipartFile imageFile) {
	this.imageFile = imageFile;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public double getDiscount() {
	return discount;
}

public void setDiscount(double discount) {
	this.discount = discount;
}

public Date getEnteredDate() {
	return enteredDate;
}

public void setEnteredDate(Date enteredDate) {
	this.enteredDate = enteredDate;
}

public short getStatus() {
	return status;
}

public void setStatus(short status) {
	this.status = status;
}

public Long getCategoryId() {
	return categoryId;
}

public void setCategoryId(Long categoryId) {
	this.categoryId = categoryId;
}

public Boolean getIsEdit() {
	return isEdit;
}

public void setIsEdit(Boolean isEdit) {
	this.isEdit = isEdit;
}


}
