package ru.practicum.shareit.request.service;

import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.entity.service.Service;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
public interface ItemRequestService extends Service<ItemRequestDto> {
    List<ItemRequestDto> readAllByUser(@Positive Long userId);

    List<ItemRequestDto> readAll(@Min(value = 0, message = "Should be greater than 0") int from,
                                  @Positive int size, @Positive Long userId);

    ItemRequestDto readById(@Positive Long requestId, @Positive Long userId);
}
