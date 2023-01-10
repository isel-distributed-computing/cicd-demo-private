package todolist.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import org.junit.Before;
import org.slf4j.helpers.Util;
import org.springframework.boot.test.mock.mockito.MockBean;
import todolist.service.ToDoListItem;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import todolist.service.ToDoListService;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ToDoListControllerTests {
    @MockBean
    private ToDoListService service;
    @Autowired
    private ToDoListController controller;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testCreateToDoListItem() throws Exception {
        final String username = "jose";
        final String description = "abc";
        when(service.createToDoListItem(username, description))
                .thenReturn(new ToDoListItem(1, username, description));

        String requestBody = "{\n" +
                "    \"username\": \""+username+"\",\n" +
                "    \"description\": \""+description+"\"\n" +
                "}";

        ResultActions result =
            mockMvc.perform(post("/todolist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());

        MvcResult mvcResult = result.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String responseBody = response.getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        ToDoListItem item = mapper.readValue(responseBody, ToDoListItem.class);
        Assertions.assertEquals(item.getUsername(), username);
        Assertions.assertEquals(item.getDescription(), description);
    }

    @Test
    public void testDeleteToDoItem() throws Exception {
        final int id = new Random().nextInt();
        final String username = "jose";
        final String description = "abc";
        when(service.deleteToDoListItem(id))
                .thenReturn(Optional.of(new ToDoListItem(id, username, description)));

        String requestBody = "{\n" +
                "    \"username\": \""+username+"\",\n" +
                "    \"description\": \""+description+"\"\n" +
                "}";

        mockMvc.perform(delete("/todolist/"+id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(id)))
            .andExpect(jsonPath("$.username", is("jose")))
            .andExpect(jsonPath("$.description", is("abc")));
    }

    @Test
    public void testGetAllItemsByUser() throws Exception {
        final String username = "jose";
        final String description = "abc";
        when(service.getToDoListItemList(username))
                .thenReturn(Optional.of(Arrays.asList(
                        new ToDoListItem(1, username, description),
                        new ToDoListItem(2, username, description))));

        // check there are 2 items for this username
        ResultActions result =
            mockMvc.perform(get("/todolist/"+username))
                .andExpect(status().isOk());

        MvcResult mvcResult = result.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String responseBody = response.getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<ToDoListItem> items = mapper.readValue(responseBody, new TypeReference<List<ToDoListItem>>(){});
        Assertions.assertEquals(2, items.size());
    }
}