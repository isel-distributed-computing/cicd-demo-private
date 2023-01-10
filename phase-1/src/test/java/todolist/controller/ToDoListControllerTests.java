package todolist.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import org.junit.Before;
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

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ToDoListControllerTests {
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
        String requestBody = "{\n" +
            "    \"username\": \"jose\",\n" +
            "    \"description\": \"abc\"\n" +
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
        Assertions.assertEquals(item.getUsername(), "jose");
        Assertions.assertEquals(item.getDescription(), "abc");
    }

    @Test
    public void testDeleteToDoItem() throws Exception {
        String requestBody = "{\n" +
            "    \"username\": \"jose\",\n" +
            "    \"description\": \"abc\"\n" +
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

        mockMvc.perform(delete("/todolist/"+item.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(item.getId())))
            .andExpect(jsonPath("$.username", is("jose")))
            .andExpect(jsonPath("$.description", is("abc")));
    }

    @Test
    public void testGetAllItensByUser() throws Exception {
        String requestBody = "{\n" +
            "    \"username\": \"jose\",\n" +
            "    \"description\": \"abc\"\n" +
            "}";

        // add 2 itens
        mockMvc.perform(post("/todolist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
        mockMvc.perform(post("/todolist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isOk());

        // check there are 2 itens for this username
        ResultActions result =
            mockMvc.perform(get("/todolist/jose"))
                .andExpect(status().isOk());

        MvcResult mvcResult = result.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String responseBody = response.getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<ToDoListItem> items = mapper.readValue(responseBody, new TypeReference<List<ToDoListItem>>(){});
        Assertions.assertEquals(2, items.size());
    }
}