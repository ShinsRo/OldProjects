# features
import sju_single_search 
import sju_author_search
import sju_multi_search

# support
import sju_utiles as _utiles
import sju_models as _models
import sju_CONSTANTS as _CONS
import sju_exceptions as _EXCEP

# std_libs
import os
import re
import sys
import datetime
import requests
import traceback
import threading
import concurrent.futures


if __name__ == "__main__":
    # ui 출력 객체 초기화
    ui_stream = _models.UI_Stream('dispatcher', 'main', 'res')

    # 디스패처 활성화, 로딩 활성화
    ui_stream.push(command='res', target='loading', res=True)
    ui_stream.push(command='log', msg=_CONS.STATE_MSG[101])

    multiSearchObj = None
    singleSearchObj = None
    authorSearchObj = None
    compactSearchObj = None

    # 서비스 초기화
    try:
        service_list = [
            { 'name': 'SingleCitationSearch', 'init': sju_single_search.SingleSearch }, 
            # { 'name': 'MultiCitationSearch', 'init': citationSearch.MultiSearch },
            # { 'name': 'oneByOneSearch', 'init': citationSearch.OneByOneSearch },
            # { 'name': 'MultiCommonSearch', 'init': commonSearch.MultiSearch },
        ]
        
        ui_stream.push(command='log', msg=_CONS.STATE_MSG[102])
        ui_stream.push(command='log', msg=_CONS.STATE_MSG[103])
        ui_stream.push(command='log', msg=_CONS.STATE_MSG[104])
        with concurrent.futures.ThreadPoolExecutor(max_workers=len(service_list)) as executor:
            future_service = {
                executor.submit(service['init'], ): service['name'] for service in service_list
            }
            for future in concurrent.futures.as_completed(future_service):
                name_done = future_service[future]
                tempObj = future.result()

                try:
                    if name_done == 'SingleCitationSearch': 
                        singleSearchObj = tempObj
                        ui_stream.push(command='log', msg=_CONS.STATE_MSG[106])
                    elif name_done == 'MultiCitationSearch':
                        multiSearchObj = tempObj
                        ui_stream.push(command='log', msg=_CONS.STATE_MSG[107])
                    elif name_done == 'MultiCommonSearch':
                        compactSearchObj = tempObj
                        ui_stream.push(command='log', msg=_CONS.STATE_MSG[108])
                    elif name_done == 'oneByOneSearch':
                        authorSearchObj = tempObj
                        ui_stream.push(command='log', msg=_CONS.STATE_MSG[109])
                except Exception as e:
                    raise Exception('%s 초기화 실패'%name_done)

    except Exception as e:
        ui_stream.push(command='sysErr', msg=_CONS.STATE_MSG[302])

        # dispatch 강제 종료
        ui_stream.push(command='log', msg=_CONS.STATE_MSG[500])
        ui_stream.push(command='errObj', msg=e)
        exit(2000)
    else:
        # 모든 서비스 초기화 완료
        ui_stream.push(command='log', msg=_CONS.STATE_MSG[201])


    # 입력 대기 시작
    ui_stream.push(command='log', msg=_CONS.STATE_MSG[110])
    while(True):
        ui_stream.push(command='res', target='loading', res=False)
        # 반복 전역 예외 처리
        try:
            # 검색 서비스 종류
            service_name = input().strip()
            ui_stream.push(command='res', target='loading', res=True)
            inputs = ''
            try:
                inputs = _utiles.input_validation(service_name)
            except _EXCEP.InputValidationError as ive:
                ui_stream.push(command='sysErr', msg=str(ive))
                ui_stream.push(command='errObj', msg=ive)
                continue

            # 단일 상세 검색
            if service_name == 'singleCitationSearch':
                query = inputs['query']
                startYear = inputs['startYear']
                endYear = inputs['endYear']
                pAuthors = inputs['pAuthors']
                organization = inputs['organization']

                ui_stream.push(command='log', msg=_CONS.STATE_MSG[111])

                singleSearchObj.start(
                    (query, pAuthors, organization), 
                    startYear, 
                    endYear, 
                    'TI',
                )
                ui_stream.push(command='log', msg=_CONS.STATE_MSG[112])

            # # 다중 상세 검색
            # elif service_name == 'multiCitationSearch':
            #     startYear = inputs['startYear']
            #     endYear = inputs['endYear']
            #     gubun = inputs['gubun']
            #     path = inputs['path']

            #     try:
            #         multiSearchObj.generalSearch(
            #             startYear=startYear,
            #             endYear=endYear,
            #             gubun=gubun,
            #             path=path
            #         )
            #     except Exception as e:
            #         dsres.print(command='sysErr', msg='상세 엑셀 검색 중 오류가 발생했습니다.')
            #     else:
            #         dsres.print(command='log', msg='상세 엑셀 검색이 완료되었습니다.')

            # # 다중 일반 검색
            # elif service_name == 'multiCommonSearch':
            #     startYear = inputs['startYear']
            #     endYear = inputs['endYear']
            #     gubun = inputs['gubun']
            #     inputFilePath = inputs['inputFilePath']
            #     defaultQueryPackSize = inputs['defaultQueryPackSize']

            #     try:
            #         compactSearchObj.generalSearch(
            #             startYear = startYear,
            #             endYear = endYear,
            #             gubun = gubun,
            #             inputFilePath = inputFilePath,
            #             defaultQueryPackSize = 0
            #         )
            #     except Exception as e:
            #         dsres.print(command='sysErr', msg='일반 엑셀 검색 중 오류가 발생했습니다.')
            #     else:
            #         dsres.print(command='log', msg='일반 엑셀 검색이 완료되었습니다.')

            # # 저자명 기준 검색
            # if service_name == 'citationSearchByAuthor':
            #     query = inputs['query']
            #     startYear = inputs['startYear']
            #     endYear = inputs['endYear']
            #     pAuthors = inputs['pAuthors']
            #     organization = inputs['organization']
            #     try:
            #         authorSearchObj.generalSearch(
            #             query=(query, pAuthors, organization),
            #             startYear=startYear,
            #             endYear=endYear, 
            #             gubun='AU',
            #             resName='ares',
            #         )
                
            #     except Exception as e:
            #         dsres.print(command='sysErr', msg='심각한 오류')
            #         dsres.print(command='errObj', msg=e)
            #     else:
            #         dsres.print(command='log', msg='저자명 검색을 마쳤습니다.')
            # 알 수 없는 서비스 네임
            else:
                ui_stream.push(command='sysErr', msg=_CONS.STATE_MSG[402])
        except EOFError as eof:
            ui_stream.push(command='sysErr', msg=_CONS.STATE_MSG[401])
            sys.exit(1)
        except Exception as e:
            ui_stream.push(command='sysErr', msg=_CONS.STATE_MSG[400])
            traceback.print_tb(e.__traceback__) 
            continue