package com.midas2018.root.support.typehandler;

import org.apache.ibatis.type.MappedTypes;

import com.midas2018.root.model.OptionData;

@MappedTypes(OptionData.class)
public class OptionDataTypeHandler extends ObjectJsonTypeHandler<OptionData> {

    public OptionDataTypeHandler(Class<OptionData> type) {
        super(type);
    }
}
