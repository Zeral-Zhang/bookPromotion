package com.zeral.util;

import com.zeral.constant.BookPromotionConstant;

public class TestUrl {
	public static void main(String[] args) {
		System.out.println(HttpsUtil.AuthLogin(BookPromotionConstant.VALIDATE_URL, "toProductAdd"));
	}
}
