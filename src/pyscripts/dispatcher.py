import citationSearch
import commonSearch
import sju_response
import concurrent.futures
import datetime
import sys
import os
import traceback

def inputValidation(dsres, serviceName):
    dsres.print(command='log', msg='인풋 값을 검증합니다.')
    now = datetime.datetime.now()
    returnDict = {}
    
    # 단일 상세 검색
    if serviceName == 'singleCitationSearch':
        query = input().strip()
        startYear = input().strip()
        endYear = input().strip()
        pAuthors = input().strip()

        if not len(query) > 2: raise Exception('쿼리의 길이가 너무 짧습니다.')
        if not 1900 <= int(startYear) <= now.year: raise Exception('시작년도가 올바르지 않습니다.')
        if not 1900 <= int(endYear) <= now.year: raise Exception('끝 년도가 올바르지 않습니다.')
        if not int(startYear) <= int(endYear): raise Exception('검색 기간이 올바르지 않습니다.')
        # if not pAuthors: raise Exception('---')
        returnDict['query'] = query 
        returnDict['startYear'] = startYear 
        returnDict['endYear'] = endYear 
        returnDict['pAuthors'] = pAuthors
    # 다중 상세 검색
    elif serviceName == 'multiCitationSearch':
        startYear = input().strip()
        endYear = input().strip()
        gubun = input().strip()
        path = input().strip()
    
        if not 1900 <= int(startYear) <= now.year: raise Exception('시작년도가 올바르지 않습니다.')
        if not 1900 <= int(endYear) <= now.year: raise Exception('끝 년도가 올바르지 않습니다.')
        if not int(startYear) <= int(endYear): raise Exception('검색 기간이 올바르지 않습니다.')
        if not (gubun == 'TI' or gubun == 'DO'): raise Exception('구분이 올바르지 않습니다.')
        if not (
            path.endswith('.csv') 
            or path.endswith('.xls')
            or path.endswith('.xlsx')): raise Exception('파일 확장자가 올바르지 않습니다.')
        returnDict['startYear'] = startYear 
        returnDict['endYear'] = endYear 
        returnDict['gubun'] = gubun 
        returnDict['path'] = path 

    # 다중 일반 검색
    elif serviceName == 'multiCommonSearch':
        startYear = input().strip()
        endYear = input().strip()
        gubun = input().strip()
        inputFilePath = input().strip()
        defaultQueryPackSize = input().strip()

        if not 1900 <= int(startYear) <= now.year: raise Exception('시작년도가 올바르지 않습니다.')
        if not 1900 <= int(endYear) <= now.year: raise Exception('끝 년도가 올바르지 않습니다.')
        if not int(startYear) <= int(endYear): raise Exception('검색 기간이 올바르지 않습니다.')
        if not (gubun == 'TI' or gubun == 'DO'): raise Exception('구분이 올바르지 않습니다.')
        path = inputFilePath
        if not (
            path.endswith('.csv') 
            or path.endswith('.xls')
            or path.endswith('.xlsx')): raise Exception('파일 확장자가 올바르지 않습니다.')
        returnDict['startYear'] = startYear 
        returnDict['endYear'] = endYear 
        returnDict['gubun'] = gubun 
        returnDict['inputFilePath'] = path 
        returnDict['defaultQueryPackSize'] = defaultQueryPackSize 
    else:
        raise Exception('인풋 값 검증 중 알 수 없는 오류')
    return returnDict

if __name__ == "__main__":
    dsres = sju_response.SJUresponse('dispatcher')
    dsres.print(command='res', target='loading', res=True)
    dsres.print(command='log', msg='dispatcher를 준비합니다.')
    singleCitationSearchObj = None
    multiCitationSearchObj = None
    multiCommonSearchObj = None

    # 서비스 초기화
    try:
        serviceList = [
            { 'name': 'SingleCitationSearch', 'init': citationSearch.SingleSearch}, 
            { 'name': 'MultiCitationSearch', 'init': citationSearch.MultiSearch},
            { 'name': 'MultiCommonSearch', 'init': commonSearch.MultiSearch},
        ]
        
        dsres.print(command='log', msg='기반 서비스를 초기화합니다. 이 작업은 2~3분이 소요됩니다.')
        dsres.print(command='log', msg='각 서비스의 브라우저 인증, 검색 폼 초기화를 수행합니다.')
        dsres.print(command='log', msg='초기화 진행 중 검색 서비스는 이용할 수 없습니다.')
        with concurrent.futures.ThreadPoolExecutor(max_workers=len(serviceList)) as executor:
            future_service = {
                executor.submit(service['init'], sju_response.SJUresponse(service['name'])): service['name'] for service in serviceList
            }
            for future in concurrent.futures.as_completed(future_service):
                name_done = future_service[future]
                try:
                    tempObj = future.result()
                    if name_done == 'SingleCitationSearch': 
                        singleCitationSearchObj = tempObj
                        dsres.print(command='log', msg='SID : %s'%(singleCitationSearchObj.SID))
                        dsres.print(command='log', msg='jsessionid : %s'%(singleCitationSearchObj.jsessionid))
                        dsres.print(command='log', msg='단일 상세 검색 서비스 초기화가 완료되었습니다.')
                    elif name_done == 'MultiCitationSearch':
                        multiCitationSearchObj = tempObj
                        dsres.print(command='log', msg='다중 상세 검색 서비스 초기화가 완료되었습니다.')
                    elif name_done == 'MultiCommonSearch':
                        multiCommonSearchObj = tempObj
                        dsres.print(command='log', msg='다중 일반 검색 서비스 초기화가 완료되었습니다.')
                        
                except Exception as e:
                    dsres.print(command='sysErr', msg='초기화 중 오류가 발생했습니다.')
                    dsres.print(command='errObj', msg=e)
                else:
                    dsres.print(command='log', msg='%s 초기화 완료'%name_done)
    
    except Exception as e:
        dsres.print(command='sysErr', msg='연결에 실패했습니다. 인터넷 연결이나 접속한 장소가 유효한 지 확인해주세요. 혹은 일시적 현상일 수 있으니 잠시 후 다시 접속해주세요.')
        dsres.print(command='log', msg='dispatcher를 종료합니다.')
        dsres.print(command='errObj', msg=e)
        exit(2000)

    dsres.print(command='log', msg='dispatcher가 준비되었습니다.')
    while(True):
        # 반복 전역 예외 처리
        dsres.print(command='res', target='loading', res=False)
        try:
            
            # 검색 서비스 종류
            serviceName = input().strip()
            dsres.print(command='res', target='loading', res=True)
            
            inputs = ''
            try:
                inputs = inputValidation(dsres, serviceName)
            except Exception as e:
                dsres.print(command='sysErr', msg='인풋 값이 유효하지 않습니다.')
                dsres.print(command='errObj', msg=e)
                continue

            # 단일 상세 검색
            if serviceName == 'singleCitationSearch':
                query = inputs['query']
                startYear = inputs['startYear']
                endYear = inputs['endYear']
                pAuthors = inputs['pAuthors']

                try:
                    singleCitationSearchObj.generalSearch(
                        query=(query, pAuthors), 
                        startYear=startYear, 
                        endYear=endYear, 
                        gubun='TI',
                        resName='res',
                    )
                
                except Exception as e:
                    dsres.print(command='sysErr', msg='심각한 오류')
                    dsres.print(command='errObj', msg=e)
                else:
                    dsres.print(command='log', msg='단일 검색을 마쳤습니다.')

            # 다중 상세 검색
            elif serviceName == 'multiCitationSearch':
                startYear = inputs['startYear']
                endYear = inputs['endYear']
                gubun = inputs['gubun']
                path = inputs['path']

                try:
                    multiCitationSearchObj.generalSearch(
                        startYear=startYear,
                        endYear=endYear,
                        gubun=gubun,
                        path=path
                    )
                except Exception as e:
                    dsres.print(command='sysErr', msg='상세 엑셀 검색 중 오류가 발생했습니다.')
                else:
                    dsres.print(command='log', msg='상세 엑셀 검색이 완료되었습니다.')

            # 다중 일반 검색
            elif serviceName == 'multiCommonSearch':
                startYear = inputs['startYear']
                endYear = inputs['endYear']
                gubun = inputs['gubun']
                inputFilePath = inputs['inputFilePath']
                defaultQueryPackSize = inputs['defaultQueryPackSize']

                try:
                    multiCommonSearchObj.generalSearch(
                        startYear = startYear,
                        endYear = endYear,
                        gubun = gubun,
                        inputFilePath = inputFilePath,
                        defaultQueryPackSize = 0
                    )
                except Exception as e:
                    dsres.print(command='sysErr', msg='일반 엑셀 검색 중 오류가 발생했습니다.')
                else:
                    dsres.print(command='log', msg='일반 엑셀 검색이 완료되었습니다.')

            # 알 수 없는 서비스 네임
            else:
                dsres.print(command='sysErr', msg='알 수 없는 서비스 접근')
        except EOFError as eof:
            dsres.print(command='sysErr', msg='dispatcher와의 연결이 해제되었습니다. 프로그램을 다시 시작해주세요.')
            sys.exit(1)
        except Exception as e:
            dsres.print(command='sysErr', msg='알 수 없는 오류가 발생했습니다.')
            traceback.print_tb(e.__traceback__) 
            continue