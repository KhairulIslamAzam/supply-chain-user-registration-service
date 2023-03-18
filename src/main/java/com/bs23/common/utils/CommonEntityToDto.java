package com.bs23.common.utils;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;

public class CommonEntityToDto {
    public static  <E, D> D entityToDto(E entity, Class<D> dtoClass)  {
        D dto = null;
        try {
            dto = dtoClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        ; // create a new instance of the DTO using its default constructor
        BeanUtils.copyProperties(entity, dto); // copy the properties from the entity to the DTO
        return dto; // return the new DTO
    }
}
