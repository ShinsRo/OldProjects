import React from 'react';

class KssAutocompletor {
    constructor(name, type, ...arg) {
        this.name = name;
        this.type = type;
        this.dataSet = [];
        switch (type) {
            case "member":
                const members = arg[0];
                this.members = members;
                
                members.forEach(mDto => {
                    this.dataSet.push( {
                        msg : `${mDto.name} <${mDto.eid}>`,
                        mDto,
                    } );
                });
                break;

            default:
                break;
        }
    }
    
    autocompleted(keyword, maxLength) {
        const regex = RegExp(keyword);
        let cnt = 0;
        const autocompleted = this.dataSet.filter(data => {
            if (regex.test(data.msg)) {
                return (cnt++ < maxLength); 
            } else { 
                return false;
            }
        }).map( data => {
            const idxOf = data.msg.indexOf(keyword);
            data.tag = 
                <span>
                    {data.msg.substring(0, idxOf)}
                    <span className="autocomplete-highlight">{keyword}</span>
                    {data.msg.substring(idxOf + keyword.length)}
                </span>;
            return data;
        });

        if (cnt > maxLength) {
            autocompleted.push({ msg: `...(${cnt - maxLength} more)` });
        }

        return autocompleted || [];
    }
}

export default KssAutocompletor;