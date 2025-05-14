package org.pronsky.transfer_service.service.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.pronsky.transfer_service.data.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSpecification {

    public static Specification<User> hasDateOfBirthAfter(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                date != null ? criteriaBuilder.greaterThan(root.get("dateOfBirth"), date) : null;
    }

    public static Specification<User> hasPhoneNumber(String phoneNumber) {
        return (root, query, criteriaBuilder) ->
                phoneNumber != null ? criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber) : null;
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                email != null ? criteriaBuilder.equal(root.get("email"), email) : null;
    }

    public static Specification<User> nameLike(String name) {
        return (root, query, criteriaBuilder) ->
                name != null ? criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%") : null;
    }
}
