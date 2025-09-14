package com.example.bankcards.repository.specification;

import com.example.bankcards.entity.CardEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class CardSpecification {


    public static Specification<CardEntity> numberLike(String number) {
        return (root, query, cb) -> {
            if (StringUtils.hasLength(number)) {
                return cb.like(root.get("number"), "%" + number + "%");
            }
            return cb.conjunction();
        };
    }

    public static Specification<CardEntity> expiryDateGTE(LocalDate expiryDate) {
        return (root, query, cb) -> {
            if (Objects.nonNull(expiryDate)) {
                return cb.greaterThanOrEqualTo(root.get("expiryDate"), expiryDate);
            }
            return cb.conjunction();
        };
    }

    public static Specification<CardEntity> expiryDateLTE(LocalDate expiryDate) {
        return (root, query, cb) -> {
            if (Objects.nonNull(expiryDate)) {
                return cb.lessThanOrEqualTo(root.get("expiryDate"), expiryDate);
            }
            return cb.conjunction();
        };
    }

    public static Specification<CardEntity> balanceGTE(BigDecimal balance) {
        return (root, query, cb) -> {
            if (Objects.nonNull(balance)) {
                return cb.greaterThanOrEqualTo(root.get("balance"), balance);
            }
            return cb.conjunction();
        };
    }

    public static Specification<CardEntity> balanceLTE(BigDecimal balance) {
        return (root, query, cb) -> {
            if (Objects.nonNull(balance)) {
                return cb.lessThanOrEqualTo(root.get("balance"), balance);
            }
            return cb.conjunction();
        };
    }


    public static Specification<CardEntity> ownerById(Long userId) {
        return (root, query, cb) -> {
            if (Objects.nonNull(userId)) {
                return cb.equal(root.get("owner").get("id"), userId);
            }
            throw new RuntimeException("Empty userId");
        };
    }
}
