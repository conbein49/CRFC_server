package com.jerome.chengrui.house.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jerome.chengrui.house.model.House;
import com.jerome.chengrui.house.model.User;
import com.jerome.chengrui.house.util.FileToBytes;

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

	@GetMapping("/recommendHouseList")
	public List<House> getRecommendHoustList(@RequestParam String province,
			@RequestParam String city, @RequestParam String county) {
		List<House> res = new ArrayList<>();
		res.add(new House(1));
		res.add(new House(2));
		return res;
	}

	@GetMapping("/houseInfo/{houseID}")
	public House getHouseInfo(@PathVariable("houseID") long houseID) {
		House house = new House(houseID);
		house.setHouseDescribe("精装");
		house.setHouseLayout("一室一厅");
		house.setHouselc(6);
		house.setHouseLocal("金扬五街坊");
		house.setMztype(0);
		house.setZffkfs(0);
		house.setHouseImg(FileToBytes.getFileBytes("1.jpg"));
		return house;
	}
}
