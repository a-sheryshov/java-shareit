package ru.practicum.shareit.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.practicum.shareit.entity.model.AbstractEntity;

@NoRepositoryBean
public interface Repository<E extends AbstractEntity> extends JpaRepository<E, Long> {

}
