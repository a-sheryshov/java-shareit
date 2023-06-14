package ru.practicum.shareit.request.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.entity.model.AbstractEntity;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Data
@RequiredArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ItemRequest extends AbstractEntity {
    public static final int MAX_DESCRIPTION_LEN = 200;
    public static final String DESC_LEN_VALIDATION_ERR_MSG =
            "Description should be less than " + MAX_DESCRIPTION_LEN + " symbols";
    @Column(length = MAX_DESCRIPTION_LEN, nullable = false)
    @NotBlank(message = "Description is mandatory")
    @Size(max = MAX_DESCRIPTION_LEN, message = DESC_LEN_VALIDATION_ERR_MSG)
    private String description;

    @ManyToOne
    @JoinColumn(name = "requestor_id", referencedColumnName = "id")
    @NotNull(message = "Requestor ID is mandatory")
    private User requestor;
    @NotNull(message = "Item request creation time is mandatory")
    private LocalDateTime created;
}
