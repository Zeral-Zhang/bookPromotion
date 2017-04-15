package com.zeral.constant;


/**
 * 文理后院常量类
 * @author: ZeralZhang
 * @date: 2016年12月29日
 */
/**
 * @author ZeralZhang
 *
 */
public class BookPromotionConstant {
	/**
	 * 加密服务器地址 https
	 * 授权验证要求地址必须为网站，不能为ip
	 */
	public static final String ENCRY_SERVER_URL = "https://wenlibackyard.tunnel.qydev.com/";
	/**
	 * 
	 */
	public static final String SERVER_URL = "http://wenlibackyard.tunnel.qydev.com/";
	/**
	 * 用户认证地址
	 */
	public static final String VALIDATE_URL = ENCRY_SERVER_URL+"bookPromotion/validateUser";
	/**
	 * 文件上传地址
	 */
	public static final String UPLOAD_URL = "/upload/";
	 /**
	 * 微信调用微信JS接口的临时票据
	 */
	public static final String JSAPI_TICKET = "jsapi_ticket";
	/**
	 * 
	 */
	public static final String ACCESS_TOKEN = "accessToken";
	/**
	 * 凭证超时时间
	 */
	public static final String TOKEN_EXPIREIN_TIME = "tokenExpiresInTime";
}
