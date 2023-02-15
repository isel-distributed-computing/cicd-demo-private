package todolist.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import org.junit.Before;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import todolist.model.CreateToDoListItemRequest;
import todolist.model.ToDoListItemResource;
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
import todolist.service.ToDoUserService;
import todolist.service.UnknownUserException;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ToDoListControllerTests {
    private final String description = "abc";
    private final String username = "jose";
    private final String password = "pwd123";
    @MockBean
    private ToDoListService service;
    @Autowired
    private ToDoListController controller;
    @Autowired
    private ToDoUserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testCreateToDoListItem() throws Exception, UnknownUserException {
        // Arrange
        String username = "testUser";
        String description = "testDescription";
        CreateToDoListItemRequest request = new CreateToDoListItemRequest(username, description);
        request.setUsername(username);
        request.setDescription(description);

        ToDoListItemResource expectedItem = new ToDoListItemResource(username, description);
        expectedItem.setId(1L);

        String token = "testToken";
        when(service.createToDoListItem(username, description)).thenReturn(expectedItem);
        when(userService.validateToken(token)).thenReturn(true);

        // Act
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(post("/todolist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer: " + token)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        // Assert
        String location = result.getResponse().getHeader("Location");
        Assertions.assertEquals(location, "/todolist/1");

        EntityModel<ToDoListItemResource> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<EntityModel<ToDoListItemResource>>() {});
        Assertions.assertEquals(response.getContent(), expectedItem);

        Link expectedSelfLink = linkTo(methodOn(ToDoListController.class).getAllItemsByUser(username, token)).withRel("ToDoList");
        Assertions.assertEquals(response.getLink("self").get(), expectedSelfLink);

        /*//Arrange
        when(service.createToDoListItem(username, description))
                .thenReturn(new ToDoListItemResource(1, username, description));

        CreateToDoListItemRequest request = new CreateToDoListItemRequest(username, description);
        String requestJson = new ObjectMapper().writeValueAsString(request);

        userService.register(username, password);
        String token = userService.login(username, password);

        //Act & Assert
        ResultActions result =
            mockMvc.perform(post("/todolist")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer: " + token)
                            .content(requestJson))
                    .andExpect(status().isOk());

        // Check for HATEOAS links
        // Assert
        String responseJson = result.andReturn().getResponse().getContentAsString();
        ToDoListItemResource item = new ObjectMapper().readValue(responseJson, ToDoListItemResource.class);
        Assertions.assertEquals(item.getUsername(), request.getUsername());
        Assertions.assertEquals(item.getDescription(), request.getDescription());
        //Link selfLink = item.getLink("self");
        //Assertions.assertEquals(selfLink, is(notNullValue()));
        //assertThat(selfLink.getHref(), endsWith("/todolist/" + item.getId()));

        // Verify JSON object returned by operation
        MvcResult mvcResult = result.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String responseBody = response.getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        ToDoListItem item = mapper.readValue(responseBody, ToDoListItem.class);
        Assertions.assertEquals(item.getUsername(), username);
        Assertions.assertEquals(item.getDescription(), description);*/
    }

    @Test
    public void testDeleteToDoItem() throws Exception, UnknownUserException {
        /*//Arrange
        final int id = new Random().nextInt();

        when(service.deleteToDoListItem(id))
                .thenReturn(Optional.of(new ToDoListItemResource(id, username, description)));

        userService.register(username, password);
        String token = userService.login(username, password);

        // Act & Assert
        mockMvc.perform(delete("/todolist/"+id)
                        .header("Authorization", "Bearer: " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.username", is("jose")))
                .andExpect(jsonPath("$.description", is("abc")));

         */
    }

    @Test
    public void testGetAllItemsByUser() throws Exception, UnknownUserException {
        // Arrange
        when(service.getToDoListItemList(username))
                .thenReturn(Optional.of(Arrays.asList(
                        new ToDoListItemResource(1, username, description),
                        new ToDoListItemResource(2, username, description))));

        userService.register(username, password);
        String token = userService.login(username, password);

        // Act & Assert
        ResultActions result =
            mockMvc.perform(get("/todolist/"+username)
                            .header("Authorization", "Bearer: " + token))
                .andExpect(status().isOk());

        MvcResult mvcResult = result.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String responseBody = response.getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<ToDoListItemResource> items = mapper.readValue(responseBody, new TypeReference<List<ToDoListItemResource>>(){});
        Assertions.assertEquals(2, items.size());
    }
}