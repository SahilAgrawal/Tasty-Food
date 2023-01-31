package com.tastyfoodpvtltd.ordermanagementservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tastyfoodpvtltd.ordermanagementservice.domain.Item;
import com.tastyfoodpvtltd.ordermanagementservice.domain.Order;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderManagementServiceApplicationTests {

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
	public void createOrder() throws Exception {
		Order order = new Order();
		// order.setRid("63c126a3da3a344cc2396160");
		// order.setItems(it);
		String createOrder = mapToJson(order);
		String uri = "/order/";

		// When Order is Empty
		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.post(uri).content(createOrder).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

		// When Order have only rid
		order.setRid("63c126a3da3a344cc2396160");
		createOrder = mapToJson(order);
		result = this.mockMvc
				.perform(MockMvcRequestBuilders.post(uri).content(createOrder).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

		// When Order have only rid
		List<Item> items = new ArrayList<>();
		items.add(new Item());
		createOrder = mapToJson(order);
		result = this.mockMvc
				.perform(MockMvcRequestBuilders.post(uri).content(createOrder).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

		items.get(0).setItem("Boeuf Bourguignon");
		items.get(0).setQuantity(2);

		order.setItems(items);
		createOrder = mapToJson(order);
		result = this.mockMvc
				.perform(MockMvcRequestBuilders.post(uri).content(createOrder).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
	}

	@Test
	public void updateOrder() throws Exception {
		Order order = new Order();

		// order.setRid("63c126a3da3a344cc2396160");
		// order.setItems(it);
		String updateOrder = mapToJson(order);
		String id = "63c54d07b3fc57127a0ce634";
		String uri = "/order/update/{id}";

		// When Order is Empty
		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.put(uri, id).content(updateOrder)
						.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

		// When Order have only rid
		order.setRid("63c126a3da3a344cc2396160");
		updateOrder = mapToJson(order);
		result = this.mockMvc
				.perform(MockMvcRequestBuilders.put(uri, id).content(updateOrder).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

		// When Order have only rid
		List<Item> items = new ArrayList<>();
		items.add(new Item());
		updateOrder = mapToJson(order);
		result = this.mockMvc
				.perform(MockMvcRequestBuilders.put(uri, id).content(updateOrder).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

		items.get(0).setItem("Cassoulet");
		items.get(0).setQuantity(3);

		order.setItems(items);
		updateOrder = mapToJson(order);
		result = this.mockMvc
				.perform(MockMvcRequestBuilders.put(uri, id).content(updateOrder).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}

	@Test
	public void testViewOrder() throws Exception {
		String id = "63c54d07b3fc57127a0ce634";
		String uri = "/order/view/{id}";
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get(uri, id)).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}

	@Test
	public void testCancleOrder() throws Exception {
		String id = "63c54d07b3fc57127a0ce634";
		String uri = "/order/cancel/{id}";
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.delete(uri, id)).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}

	@Test
	public void testPlaceOrder() throws Exception {
		String id = "63c54d07b3fc57127a0ce634";
		String uri = "/order/place/{id}";
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get(uri, id)).andReturn();
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}


}
