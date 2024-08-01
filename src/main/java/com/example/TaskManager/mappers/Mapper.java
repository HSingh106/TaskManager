package com.example.TaskManager.mappers;

/**
 * General interface for mapper functionality implementation
 * for entities and their corresponding DTO's
 * @param <A> represents the entity
 * @param <B> represents the DTO
 */
public interface Mapper<A, B> {

    /**
     * returns a DTO based on the information within the entity passed in
     * @param a entity passed in
     * @return DTO corresponding to the entity
     */
    B mapTo(A a);

    /**
     * returns entity based on the information within the DTO passed in
     * @param b DTO passed in
     * @return entity corresponding to the DTO
     */
    A mapFrom(B b);

}
