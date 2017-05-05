package com.zeral.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 收藏夹
 * @author Zeral
 *
 */
@Entity
@Table(name = "favorite")
public class Favorite implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String favoriteId;
	private ProductInfo productInfo;
	private String userInfoId;
	private Date createDate;
	private String context;
	
	
	private String productId;

	// Constructors

	/** default constructor */
	public Favorite() {
	}
	
	

	public Favorite(String userInfoId, Date createDate, String context, String productId) {
		super();
		this.userInfoId = userInfoId;
		this.createDate = createDate;
		this.context = context;
		this.productId = productId;
	}



	// Property accessors
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "favorite_id", unique = true, nullable = false, length = 32)
	public String getFavoriteId() {
		return this.favoriteId;
	}

	public void setFavoriteId(String favoriteId) {
		this.favoriteId = favoriteId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
	public ProductInfo getProductInfo() {
		return this.productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	
	@Column(name = "product_id")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Column(name = "user_id",  nullable = false)
	public String getUserInfoId() {
		return this.userInfoId;
	}

	public void setUserInfoId(String userInfoId) {
		this.userInfoId = userInfoId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "create_date", nullable = false, length = 10)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "context", length = 50)
	public String getContext() {
		return this.context;
	}

	public void setContext(String context) {
		this.context = context;
	}

}