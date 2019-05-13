/** 
 * 자동완성 클래스 정의
 * 
 * KssAutocompletor는 자동완성 기능을 위한 클래스이다.
 * 클래스 내에 타입을 정의하여 자동완성 기능을 확장할 수 있다.
 * 검색을 위해 정규식을 사용한다. 
 * 
 * 2019.05.13
 * @file 이 파일은 KssAutocompletor를 정의한다.
 * @author 김승신
 */
import React from 'react';

class KssAutocompletor {
    /**
     * 생성자에서 해당 객체의 타입에 따라 데이터 셋을 만든다.
     * 데이터 셋은 정규식 검색의 대상이며,
     * 사용자에게 보여질 메세지(msg)와 원본 데이터(*Dto)를 감싼 오브젝트를
     * 하나의 원소로 한다.
     * 
     * @param {String} name 자동완성 클래스의 이름
     * @param {String} type 자동완성 클래스의 타입
     * @param  {...any} arg 타입에 따라 원본 데이터를 인자로 넘겨받는다.
     */
    constructor(name, type, ...arg) {
        this.name = name;               // 자동완성 클래스의 이름
        this.type = type;               // 자동완성 클래스의 타입
        this.dataSet = [];
        switch (type) {
            // 멤버를 기준으로 자동완성한다.
            case "member":              
                const members = arg[0];
                this.members = members;
                
                /* 검색 대상 데이터 셋 제작 */
                members.forEach(mDto => {
                    this.dataSet.push( {
                        msg : `${mDto.name} <${mDto.eid}>`, // 정규식 검색의 대상, 형식 : "{사원 이름} <{사원 번호}>" 
                        tag : '',                           // 데이터의 React JSX 컴포넌트식 표현
                        mDto,                               // 원본 데이터
                    } );
                });
                /* 검색 대상 데이터 셋 제작 끝 */
                break;

            default:
                break;
        }
    }
    
    /**
     * 데이터 셋에서 키워드를 정규식으로 검색하여 필터링한 결과를 반환한다.
     * 태그로 스타일링한 결과는 데이터 셋의 tag 속성에 담긴다.
     * 결과 값이 없을 경우 빈 배열을 반환한다.
     * 
     * @param {String} keyword 데이터 셋에서 검색할 키워드
     * @param {Number} maxLength 반환할 검색 결과 갯수의 제한 
     * @return {Array} JSX 컴포넌트(.tag)를 포함한 데이터 원소 집합
     */
    autocompleted(keyword, maxLength) {
        const regex = RegExp(keyword);
        let cnt = 0;
        
        /* 정규식 검색 */
        const autocompleted = this.dataSet.filter(data => {
            // 검색에 실패하거나 갯수가 제한을 넘으면 필터링한다.
            if (regex.test(data.msg)) {
                return (cnt++ < maxLength);
            } else { 
                return false;
            }
        }).map( data => {
            // 필터링한 결과에 대해 키워드 주변을 감싸 하이라이트한다.
            // 이러한 컴포넌트를 DataSet.tag 속성에 저장한다.
            const idxOf = data.msg.indexOf(keyword);
            data.tag = 
                <span>
                    {data.msg.substring(0, idxOf)}
                    <span className="autocomplete-highlight">{keyword}</span>
                    {data.msg.substring(idxOf + keyword.length)}
                </span>;
            return data;
        });
        /* 정규식 검색 끝 */

        // 제한을 넘은 경우 반환 배열 끝에 "...({갯수} more"을 추가하여
        // 검색결과가 더 존재함을 암시한다.
        if (cnt > maxLength) {
            autocompleted.push({ msg: `...(${cnt - maxLength} more)` });
        }

        return autocompleted || [];
    }
}

export default KssAutocompletor;