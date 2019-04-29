package it.smartcommunitylab.storagemanager.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.smartcommunitylab.storagemanager.common.NoSuchResourceException;
import it.smartcommunitylab.storagemanager.model.Resource;
import it.smartcommunitylab.storagemanager.service.ResourceService;

@RestController
public class ResourceController {

	@Autowired
	private ResourceService resourceService;

	/*
	 * Resource
	 */

	/*
	 * List
	 */

	@GetMapping("/resources")
	@ResponseBody
	public List<Resource> list(HttpServletRequest request, HttpServletResponse response, Pageable pageable) {

		String userId = getUserId(request);
		System.out.println("userId " + userId);
		long total = resourceService.count(userId);
		List<Resource> results = resourceService.list(userId, pageable.getPageNumber(), pageable.getPageSize());
		// add total count as header
		response.setHeader("X-Total-Count", String.valueOf(total));

		return results;
	}

	/*
	 * Exceptions
	 */

	@ExceptionHandler(NoSuchResourceException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public String notFound(NoSuchResourceException ex) {
		return ex.getMessage();
	}

	/*
	 * Helper
	 */
	private String getUserId(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			return principal.getName();
		} else {
			return "anonymous";
		}
	}
//	private String getUserId() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (!(authentication instanceof AnonymousAuthenticationToken)) {
//			String currentUserName = authentication.getName();
//			return currentUserName;
//		}
//	}

}
