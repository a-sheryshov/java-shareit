package ru.practicum.shareit.request.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.entity.repository.Repository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface ItemRequestRepository extends Repository<ItemRequest> {
    List<ItemRequest> findAllByRequestorIdOrderByCreatedAsc(Long userId);

    Page<ItemRequest> findAllByRequestorNotLikeOrderByCreatedAsc(User user, Pageable pageable);
}
