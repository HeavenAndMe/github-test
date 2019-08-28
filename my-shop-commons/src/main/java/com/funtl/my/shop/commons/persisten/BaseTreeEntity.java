package com.funtl.my.shop.commons.persisten;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Zhan
 * @create 2019/8/24 - 9:47
 */
@Data
public abstract class BaseTreeEntity<T extends BaseEntity> extends BaseEntity implements Serializable {
    private T parent;
    private Boolean isParent;

}
