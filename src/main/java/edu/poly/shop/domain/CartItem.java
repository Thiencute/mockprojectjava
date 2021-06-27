package edu.poly.shop.domain;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
private int productId;
private String name;
private int quatity;
private double unitPrice;
public int getProductId() {
	return productId;
}
public void setProductId(int productId) {
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


}
