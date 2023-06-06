package ru.practicum.shareit.item.repository;

import org.springframework.data.domain.Sort;
import ru.practicum.shareit.entity.repository.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends Repository<Item> {
    List<Item> findAllByOwnerId(Long ownerId, Sort sort);

    List<Item> findAllByNameOrDescriptionContainingIgnoreCaseAndAvailableTrue(String name, String description);
}
