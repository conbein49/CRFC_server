package com.jerome.chengrui.house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jerome.chengrui.house.model.User;

@RestController
@RequestMapping("/weixin")
public class WXController {

	private static String APPID = "wx2a22d01cd1ade921";
	private static String SECRET = "e2edcb5c1ae7654a04be3fabf1ffc4f6";
	private static String GRANT_TYPE = "authorization_code";

	private static String LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%1$s&secret=%2$s"
			+ "&js_code=%3$s&grant_type=%4$s"; 

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/weixinInfo")
	public User getWeinxinUserInfo(String code) {
		String url =String.format(LOGIN_URL, APPID, SECRET, code, GRANT_TYPE);
		User user = restTemplate.getForObject(url, User.class);
		return user;
	}
}
