package org.pronsky.transfer_service.service.specification;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.pronsky.transfer_service.data.entity.EmailData;
import org.pronsky.transfer_service.data.entity.PhoneData;
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
        return (root, query, cb) -> {
            if (phoneNumber == null) return null;

            Subquery<Long> subquery = query.subquery(Long.class);
            Root<PhoneData> phoneRoot = subquery.from(PhoneData.class);

            subquery.select(phoneRoot.get("user").get("id"))
                    .where(cb.equal(phoneRoot.get("phoneNumber"), phoneNumber));

            return cb.in(root.get("id")).value(subquery);
        };
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, cb) -> {
            if (email == null) return null;

            Subquery<Long> subquery = query.subquery(Long.class);
            Root<EmailData> emailRoot = subquery.from(EmailData.class);

            subquery.select(emailRoot.get("user").get("id"))
                    .where(cb.equal(emailRoot.get("email"), email));

            return cb.in(root.get("id")).value(subquery);
        };
    }

    public static Specification<User> nameLike(String name) {
        return (root, query, criteriaBuilder) ->
                name != null ? criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")), name.toLowerCase() + "%") : null;
    }
}
