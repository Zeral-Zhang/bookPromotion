package com.zeral.action;

public interface ICarAction {
	/**
	 * 加入购物车
	 * @return
	 */
	public String add(String productId, Integer num);
	
	/**
	 * 初始化购物车
	 * @return
	 */
	public String initShopCar();
	
	/**
	 * 从购物车中清除商品
	 */
	public void removeFromCar(String productId);
	
	/**
	 * 增加或减少购物车商品数量
	 */
	public String changeQuantity(String productId, Integer num);
}
