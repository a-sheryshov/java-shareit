package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.shareit.entity.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.exception.ForbiddenException;
import ru.practicum.shareit.item.service.ItemService;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemServiceTest {
    private final ItemService itemService;

    @Test
    @Order(0)
    @Sql(value = { "/test-schema.sql", "/users-create-test.sql" })
    void createTest() {
        ItemDto incomeDto = ItemDto.builder()
                .name("item")
                .description("item one")
                .available(false)
                .ownerId(1L)
                .build();
        Optional<ItemDto> itemDto = Optional.of(itemService.create(incomeDto));

        Assertions.assertThat(itemDto)
                .isPresent()
                .hasValueSatisfying(i -> {
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("id", 1L);
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("name", "item");
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("description", "item one");
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("available", false);
                });
    }

    @Test
    @Order(1)
    void updateAvailableTest() {
        ItemDto incomeDto = ItemDto.builder()
                .ownerId(1L)
                .available(true)
                .build();
        Optional<ItemDto> itemDto = Optional.of(itemService.update(1L, incomeDto));

        Assertions.assertThat(itemDto)
                .isPresent()
                .hasValueSatisfying(i -> {
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("id", 1L);
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("name", "item");
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("description", "item one");
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("available", true);
                });
    }

    @Test
    @Order(2)
    void updateDescriptionTest() {
        ItemDto incomeDto = ItemDto.builder()
                .description("item one updated")
                .ownerId(1L)
                .build();
        Optional<ItemDto> itemDto = Optional.of(itemService.update( 1L, incomeDto));

        Assertions.assertThat(itemDto)
                .isPresent()
                .hasValueSatisfying(i -> {
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("id", 1L);
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("name", "item");
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("description", "item one updated");
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("available", true);
                });
    }

    @Test
    @Order(3)
    void updateNameTest() {
        ItemDto incomeDto = ItemDto.builder()
                .name("updated item")
                .ownerId(1L)
                .build();
        Optional<ItemDto> itemDto = Optional.of(itemService.update(1L, incomeDto));

        Assertions.assertThat(itemDto)
                .isPresent()
                .hasValueSatisfying(i -> {
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("id", 1L);
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("name", "updated item");
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("description", "item one updated");
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("available", true);
                });
    }

    @Test
    @Order(4)
    void updateForbiddenTest() {
        ItemDto incomeDto = ItemDto.builder()
                .name("updated item")
                .description("item one updated")
                .available(true)
                .ownerId(2L)
                .build();

        ForbiddenException exception = assertThrows(ForbiddenException.class,
                () -> itemService.update(1L, incomeDto));

        Assertions.assertThat(exception)
                .hasMessage("Forbidden");
    }

    @Test
    @Order(5)
    void updateNotExistingItemTest() {
        ItemDto incomeDto = ItemDto.builder()
                .name("updated item")
                .description("item one updated")
                .available(true)
                .ownerId(1L)
                .build();

        assertThrows(ObjectNotFoundException.class, () -> itemService.update(10L, incomeDto));
    }

    @Test
    @Order(6)
    @Sql(value = { "/bookings-create-test.sql" })
    void addCommentTest() {
        CommentDto incomeCommentDto = CommentDto.builder()
                .text("text 1 comment")
                .authorName("comment 1 authorName")
                .build();
        Optional<CommentDto> commentDto = Optional.of(itemService.createComment(1L, 2L, incomeCommentDto));

        Assertions.assertThat(commentDto)
                .isPresent()
                .hasValueSatisfying(i -> {
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("id", 1L);
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("text", "text 1 comment");
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("authorName", "name1");
                });
    }

    @Test
    @Order(7)
    void readByWrongIdTest() {
        assertThrows(ObjectNotFoundException.class, () -> itemService.read(100L, 1L));
    }

    @Test
    @Order(8)
    void readByIdOwnerTest() {
        Optional<ItemDto> itemDto = Optional.of(itemService.read(1L, 1L));

        Assertions.assertThat(itemDto)
                .isPresent()
                .hasValueSatisfying(i -> {
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("id", 1L);
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("name", "updated item");
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("description", "item one updated");
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("available", true);
                    Assertions.assertThat(i).hasFieldOrProperty("lastBooking");
                    Assertions.assertThat(i.getLastBooking()).hasFieldOrPropertyWithValue("bookerId", 2L);
                    Assertions.assertThat(i).hasFieldOrProperty("nextBooking");
                    Assertions.assertThat(i.getNextBooking()).isNull();
                    Assertions.assertThat(i).hasFieldOrProperty("comments");
                    Assertions.assertThat(i.getComments()).hasSize(1);
                });
    }

    @Test
    @Order(9)
    void readByIdNotOwnerTest() {
        Optional<ItemDto> itemDto = Optional.of(itemService.read(1L, 3L));

        Assertions.assertThat(itemDto)
                .isPresent()
                .hasValueSatisfying(i -> {
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("id", 1L);
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("name", "updated item");
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("description", "item one updated");
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("available", true);
                    Assertions.assertThat(i.getLastBooking()).isNull();
                    Assertions.assertThat(i).hasFieldOrProperty("comments");
                    Assertions.assertThat(i.getComments()).hasSize(1);
                });
    }


    @Test
    @Order(11)
    void searchTest() {
        List<ItemDto> items = itemService.search("item",0, 10);

        Assertions.assertThat(items)
                .hasSize(1);

        Assertions.assertThat(items.get(0))
                .hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    @Order(12)
    @Sql(value = { "/item-create-test.sql" })
    void readAllByUserIdPaginationTest() {
        List<ItemDto> items = itemService.readAll(1L, 0, 10);

        Assertions.assertThat(items)
                .hasSize(6);
        Assertions.assertThat(items.get(0))
                .hasFieldOrPropertyWithValue("name", "updated item");

        items = itemService.readAll(1L,2, 2);

        Assertions.assertThat(items)
                .hasSize(2);
        Assertions.assertThat(items.get(0))
                .hasFieldOrPropertyWithValue("name", "item4");
    }

    @Test
    @Order(13)
    void deleteTest() {
        itemService.readAll().forEach(itemDto -> itemService.delete(itemDto.getId()));
        List<ItemDto> items = itemService.readAll(1L, 0, 10);

        Assertions.assertThat(items)
                .isEmpty();
    }

    @Test
    @Order(14)
    @Sql(value = {"/request-create-test.sql"})
    void createTestWithRequest() {
        ItemDto incomeDto = ItemDto.builder()
                .name("item")
                .description("item 3")
                .available(true)
                .requestId(1L)
                .ownerId(3L)
                .build();
        Optional<ItemDto> itemDto = Optional.of(itemService.create(incomeDto));

        Assertions.assertThat(itemDto)
                .isPresent()
                .hasValueSatisfying(i -> {
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("id", 7L);
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("name", "item");
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("description", "item 3");
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("available", true);
                    Assertions.assertThat(i).hasFieldOrPropertyWithValue("requestId", 1L);
                });
    }

    @Test
    @Order(15)
    void createRequestNotExistTest() {
        ItemDto incomeDto = ItemDto.builder()
                .name("item")
                .description("users 1 item")
                .available(false)
                .requestId(100L)
                .ownerId(1L)
                .build();

        assertThrows(ObjectNotFoundException.class, () -> itemService.create(incomeDto));
    }

    @Test
    @Order(16)
    void createUserNotExistTest() {
        ItemDto incomeDto = ItemDto.builder()
                .name("item")
                .description("users 1 item")
                .available(false)
                .ownerId(100L)
                .build();

        assertThrows(ObjectNotFoundException.class, () -> itemService.create(incomeDto));
    }

}
