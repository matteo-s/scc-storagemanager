package it.smartcommunitylab.storagemanager.controller;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.smartcommunitylab.storagemanager.common.NoSuchConsumerException;
import it.smartcommunitylab.storagemanager.dto.ConsumerDTO;
import it.smartcommunitylab.storagemanager.model.Registration;
import it.smartcommunitylab.storagemanager.model.Resource;
import it.smartcommunitylab.storagemanager.service.ConsumerService;

@RestController
@RequestMapping("/consumers")
public class ConsumerController {

	private final static Logger _log = LoggerFactory.getLogger(ConsumerController.class);

	@Autowired
	private ConsumerService consumerService;

	/*
	 * Resource
	 */
	@GetMapping("/{id}")
	@ResponseBody
	public ConsumerDTO get(
			@PathVariable("id") long id,
			HttpServletRequest request, HttpServletResponse response)
			throws NoSuchConsumerException {

		String userId = getUserId(request);

		_log.debug("get " + String.valueOf("id") + " by " + userId);

		Registration reg = consumerService.get(userId, id);

		return ConsumerDTO.fromRegistration(reg);
	}

	@PostMapping("/")
	@ResponseBody
	public ConsumerDTO add(
			@RequestBody ConsumerDTO res,
			HttpServletRequest request, HttpServletResponse response) throws NoSuchConsumerException {

		String userId = getUserId(request);

		// parse fields from post
		Map<String, Serializable> propertiesMap = Resource.propertiesFromValue(res.getProperties());

		Registration reg = consumerService.add(userId, res.getType(), res.getConsumer(), propertiesMap);

		return ConsumerDTO.fromRegistration(reg);

	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public void delete(
			@PathVariable("id") long id,
			HttpServletRequest request, HttpServletResponse response)
			throws NoSuchConsumerException {

		String userId = getUserId(request);
		consumerService.delete(userId, id);

	}

	/*
	 * List
	 */

	@GetMapping("/")
	@ResponseBody
	public List<ConsumerDTO> list(
			HttpServletRequest request, HttpServletResponse response,
			Pageable pageable) {

		String userId = getUserId(request);
		System.out.println("userId " + userId);
		long total = consumerService.count(userId);
		List<Registration> registrations = consumerService.list(userId, pageable.getPageNumber(),
				pageable.getPageSize());
		List<ConsumerDTO> results = registrations.stream().map(r -> ConsumerDTO.fromRegistration(r))
				.collect(Collectors.toList());
		// add total count as header
		response.setHeader("X-Total-Count", String.valueOf(total));

		return results;
	}
	
	

	/*
	 * Exceptions
	 */

	@ExceptionHandler(NoSuchConsumerException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public String notFound(NoSuchConsumerException ex) {
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

}
