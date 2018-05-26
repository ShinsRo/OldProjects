package com.midas2018.root.support.typehandler;

import org.apache.ibatis.type.MappedTypes;

import com.midas2018.root.model.CategoryStatus;

@MappedTypes(CategoryStatus.class)
public class CategoryVOTypeHandler extends ObjectJsonTypeHandler<CategoryStatus> {

    public CategoryVOTypeHandler(Class<CategoryStatus> type) {
        super(type);
    }
}
