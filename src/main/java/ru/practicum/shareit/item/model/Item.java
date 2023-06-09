package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.entity.model.AbstractEntity;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "items")
@Data
@RequiredArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Item extends AbstractEntity {
    public static final int MAX_DESCRIPTION_LEN = 200;
    public static final int MAX_NAME_LEN = 50;
    public static final String DESC_LEN_VALIDATION_ERR_MSG =
            "Description should be less than " + MAX_DESCRIPTION_LEN + " symbols";
    public static final String NAME_LEN_VALIDATION_ERR_MSG =
            "Name should be less than " + MAX_NAME_LEN + " symbols";

    @Column(length = MAX_NAME_LEN, nullable = false)
    @NotBlank(message = "Name is mandatory")
    @Size(max = MAX_NAME_LEN, message = NAME_LEN_VALIDATION_ERR_MSG)
    private String name;

    @Column(length = MAX_DESCRIPTION_LEN, nullable = false)
    @NotBlank(message = "Description is mandatory")
    @Size(max = MAX_DESCRIPTION_LEN, message = DESC_LEN_VALIDATION_ERR_MSG)
    private String description;
    @Column
    @NotNull(message = "Status is mandatory")
    private Boolean available;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private User owner;
    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    private ItemRequest request;
}
