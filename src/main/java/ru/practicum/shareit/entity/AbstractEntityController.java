package ru.practicum.shareit.entity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.entity.dto.AbstractEntityDto;
import ru.practicum.shareit.entity.service.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public abstract class AbstractEntityController<D extends AbstractEntityDto> {
    private static final String INFO_LOG_MSG_RGX = "Request '{}' to '{}', objectId: {}";
    private final Service<D> service;

    @PostMapping
    public D create(@RequestBody final D dto, HttpServletRequest request) {
        log.info(INFO_LOG_MSG_RGX,
                request.getMethod(), request.getRequestURI(), "N/A");
        return service.create(dto);
    }

    @PatchMapping("/{id}")
    public D update(@PathVariable final Long id, @RequestBody final D dto, HttpServletRequest request) {
        log.info(INFO_LOG_MSG_RGX,
                request.getMethod(), request.getRequestURI(), id);
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public D read(@PathVariable final Long id, HttpServletRequest request) {
        log.info(INFO_LOG_MSG_RGX,
                request.getMethod(), request.getRequestURI(), id);
        return service.read(id);
    }

    @GetMapping
    public List<D> readAll(HttpServletRequest request) {
        log.info(INFO_LOG_MSG_RGX,
                request.getMethod(), request.getRequestURI(), "N/A");
        return service.readAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id, HttpServletRequest request) {
        log.info(INFO_LOG_MSG_RGX,
                request.getMethod(), request.getRequestURI(), id);
        service.delete(id);
    }
}
