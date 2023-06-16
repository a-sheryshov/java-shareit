package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.entity.repository.Repository;
import ru.practicum.shareit.item.model.Comment;

import java.util.List;

public interface CommentRepository extends Repository<Comment> {
    List<Comment> findAllByItemId(Long itemId);
}
