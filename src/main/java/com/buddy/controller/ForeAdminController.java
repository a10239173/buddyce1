package com.buddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForeAdminController {
	
	
	@GetMapping("/admin")
	public String list() {
		return "redirect:admin_list_mission";
	}

	@GetMapping("/admin_list_mission")
	public String listMission() {
		return "admin/listMission";
	}
	

	
}
