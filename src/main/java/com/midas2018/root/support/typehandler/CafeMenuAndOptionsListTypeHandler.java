package com.midas2018.root.support.typehandler;

import org.apache.ibatis.type.MappedTypes;

@MappedTypes(CafeMenuAndOptionsListTypeHandler.class)
public class CafeMenuAndOptionsListTypeHandler extends ObjectJsonTypeHandler<CafeMenuAndOptionsListTypeHandler> {

    public CafeMenuAndOptionsListTypeHandler(Class<CafeMenuAndOptionsListTypeHandler> type) {
        super(type);
    }
}
