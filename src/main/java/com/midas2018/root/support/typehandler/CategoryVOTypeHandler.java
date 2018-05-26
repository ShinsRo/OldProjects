package com.midas2018.root.support.typehandler;

import org.apache.ibatis.type.MappedTypes;

import com.midas2018.root.model.CategoryVO;

@MappedTypes(CategoryVO.class)
public class CategoryVOTypeHandler extends ObjectJsonTypeHandler<CategoryVO> {

    public CategoryVOTypeHandler(Class<CategoryVO> type) {
        super(type);
    }
}
