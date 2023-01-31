package com.tastyfoodpvtltd.restaurantsearchservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tastyfoodpvtltd.restaurantsearchservice.domain.Item;
import com.tastyfoodpvtltd.restaurantsearchservice.domain.Restaurant;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class RestaurantSearchServiceApplicationTests {

	@Autowired
	protected MockMvc mockMvc;

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	@Test
	public void testFindById() throws Exception {

		String id = "63c126a3da3a344cc2396160";
		String uri = "/restaurant/{id}";
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get(uri, id)).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

	}

	@Test
	public void testFindMenuByRestaurantId() throws Exception {

		String id = "63c126a3da3a344cc2396160";
		String uri = "/restaurant/{id}/item";
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get(uri, id)).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		if (result.getResponse().getStatus() != HttpStatus.NOT_FOUND.value()) {
			Item[] items = mapFromJson(result.getResponse().getContentAsString(), Item[].class);
			assertTrue(items.length > 0);
		}

	}

	@Test
	public void testFindByName() throws Exception {

		String name = "The Ratatouille";
		String uri = "/restaurant/name";
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get(uri).param("name", name)).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

	}

	@Test
	public void testFindByLocation() throws Exception {

		String location = "Indore";
		String uri = "/restaurant/location";
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get(uri).param("location", location))
				.andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		if (result.getResponse().getStatus() != HttpStatus.NOT_FOUND.value()) {
			Restaurant[] restaurants = mapFromJson(result.getResponse().getContentAsString(), Restaurant[].class);
			assertTrue(restaurants.length > 0);
		}

	}

	@Test
	public void testFindByCuisine() throws Exception {

		String cuisine = "French";
		String uri = "/restaurant/cuisine";
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get(uri).param("cuisine", cuisine))
				.andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		if (result.getResponse().getStatus() != HttpStatus.NOT_FOUND.value()) {
			Restaurant[] restaurants = mapFromJson(result.getResponse().getContentAsString(), Restaurant[].class);
			assertTrue(restaurants.length > 0);
		}
	}

	@Test
	public void testFindByBudget() throws Exception {

		String totalBudget = "1500";
		String person = "6";
		String uri = "/restaurant/budget";
		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.get(uri).param("person", person).param("totalBudget", totalBudget))
				.andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		if (result.getResponse().getStatus() != HttpStatus.NOT_FOUND.value()) {
			Restaurant[] restaurants = mapFromJson(result.getResponse().getContentAsString(), Restaurant[].class);
			assertTrue(restaurants.length > 0);
		}
	}

	@Test
	public void testFindByDistance() throws Exception {

		String distance = "3";
		String latitude = "23.216207700521732";
		String longitude = "82.20329458148271";
		String uri = "/restaurant/distance";
		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.get(uri).param("distance", distance).param("latitude", latitude)
						.param("longitude", longitude))
				.andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		if (result.getResponse().getStatus() != HttpStatus.NOT_FOUND.value()) {
			Restaurant[] restaurants = mapFromJson(result.getResponse().getContentAsString(), Restaurant[].class);
			assertTrue(restaurants.length > 0);
		}
	}

}
