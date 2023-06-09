package ru.practicum.shareit.item.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.entity.repository.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends Repository<Item> {
    Page<Item> findAllByOwnerId(Long ownerId, Pageable pageable);

    List<Item> findAllByRequestId(Long requestId);

    @Query(" select i from Item i " +
            "where i.available = true and upper(i.name) like upper(concat('%', ?1, '%')) " +
            "or  i.available = true and upper(i.description) like upper(concat('%', ?1, '%')) " +
            "order by i.id asc ")
    Page<Item> search(String text, Pageable pageable);
}
