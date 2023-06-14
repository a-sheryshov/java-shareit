package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemRequestServiceTest {
    private final ItemRequestService requestService;

    @Test
    @Order(0)
    @Sql(value = { "/test-schema.sql", "/users-create-test.sql" })
    void createTest() {
        ItemRequestDto incomeDto = ItemRequestDto.builder()
                .description("text")
                .requestorId(1L)
                .build();

        Optional<ItemRequestDto> itemRequestDto = Optional.of(requestService.create(incomeDto));

        assertThat(itemRequestDto)
                .isPresent()
                .hasValueSatisfying(i -> {
                    assertThat(i).hasFieldOrPropertyWithValue("id", 1L);
                    assertThat(i).hasFieldOrPropertyWithValue("description", "text");
                    assertThat(i).hasFieldOrProperty("created");
                    assertThat(i.getCreated()).isNotNull();
                    assertThat(i).hasFieldOrPropertyWithValue("requestorId", 1L);
                });
    }

    @Test
    @Order(1)
    @Sql(value = { "/item-for-request-create-test.sql" })
    void readByIdTest() {
        long requestId = 1L;
        long userId = 1L;

        Optional<ItemRequestDto> dto = Optional.of(requestService.readById(requestId, userId));

        assertThat(dto)
                .isPresent()
                .hasValueSatisfying(i -> {
                    assertThat(i).hasFieldOrPropertyWithValue("id", 1L);
                    assertThat(i).hasFieldOrPropertyWithValue("description", "text");
                    assertThat(i).hasFieldOrProperty("created");
                    assertThat(i.getCreated()).isNotNull();
                    assertThat(i).hasFieldOrProperty("items");
                    assertThat(i.getItems()).hasSize(5);
                    assertThat(i.getItems().get(0)).hasFieldOrPropertyWithValue("id", 1L);
                    assertThat(i.getItems().get(0)).hasFieldOrPropertyWithValue("name", "item1");
                    assertThat(i.getItems().get(0)).hasFieldOrPropertyWithValue("description", "description1");
                    assertThat(i.getItems().get(0)).hasFieldOrPropertyWithValue("available", true);
                    assertThat(i.getItems().get(0)).hasFieldOrPropertyWithValue("requestId", 1L);
                });
    }

    @Test
    @Order(2)
    void readByOwnerTest() {
        long userId = 1L;

        List<ItemRequestDto> dtos = requestService.readAllByUser(userId);
        Optional<ItemRequestDto> dto = Optional.of(dtos.get(0));
        assertThat(dtos)
                .hasSize(1);
        assertThat(dto)
                .isPresent()
                .hasValueSatisfying(i -> {
                    assertThat(i).hasFieldOrPropertyWithValue("id", 1L);
                    assertThat(i).hasFieldOrPropertyWithValue("description", "text");
                    assertThat(i).hasFieldOrProperty("created");
                    assertThat(i.getCreated()).isNotNull();
                    assertThat(i).hasFieldOrProperty("items");
                    assertThat(i.getItems()).hasSize(5);
                    assertThat(i.getItems().get(0)).hasFieldOrPropertyWithValue("id", 1L);
                });
    }

    @Test
    @Order(3)
    void readAllTest() {
        long userId = 2L;
        int from = 0;
        int size = 10;

        List<ItemRequestDto> dtos = requestService.readAll(from, size, userId);
        Optional<ItemRequestDto> dto = Optional.of(dtos.get(0));

        assertThat(dtos)
                .hasSize(1);
        assertThat(dto)
                .isPresent()
                .hasValueSatisfying(i -> {
                    assertThat(i).hasFieldOrPropertyWithValue("id", 1L);
                    assertThat(i).hasFieldOrPropertyWithValue("description", "text");
                    assertThat(i).hasFieldOrProperty("created");
                    assertThat(i.getCreated()).isNotNull();
                    assertThat(i).hasFieldOrProperty("items");
                    assertThat(i.getItems()).hasSize(5);
                    assertThat(i.getItems().get(0)).hasFieldOrPropertyWithValue("id", 1L);
                });
    }
}