package com.midas2018.root.support.typehandler;

import org.apache.ibatis.type.MappedTypes;

import com.midas2018.root.model.CafeMenuAndOptions;

@MappedTypes(CafeMenuAndOptions.class)
public class CafeMenuAndOptionsTypeHandler extends ObjectJsonTypeHandler<CafeMenuAndOptions> {

    public CafeMenuAndOptionsTypeHandler(Class<CafeMenuAndOptions> type) {
        super(type);
    }
}
