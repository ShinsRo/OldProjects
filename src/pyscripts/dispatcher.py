import citationSearch
import commonSearch
import sju_response
import concurrent.futures

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
                    print(e)
                else:
                    dsres.print(command='log', msg='%s 초기화 완료'%name_done)
    
    except Exception as e:
        dsres.print(command='sysErr', msg='연결에 실패했습니다.')
        dsres.print(command='sysErr', msg='인터넷 연결이나 접속한 장소가 유효한 지 확인해주세요.')
        dsres.print(command='sysErr', msg='혹은 일시적 현상일 수 있으니 잠시 후 다시 접속해주세요.')
        dsres.print(command='log', msg='dispatcher를 종료합니다.')
        dsres.print(command='errObj', msg=e)
        exit(2000)

    dsres.print(command='log', msg='dispatcher가 준비되었습니다.')
    while(True):
        dsres.print(command='res', target='loading', res=False)

        # 검색 서비스 종류
        serviceName = input().strip()

        # 단일 상세 검색
        if serviceName == 'singleCitationSearch':
            query = input().strip()
            startYear = input().strip()
            endYear = input().strip()
            
            try:
                singleCitationSearchObj.generalSearch(query=query, startYear=startYear, endYear=endYear, gubun='TI')
            except Exception as e:
                dsres.print(command='sysErr', msg='심각한 오류')
                dsres.print(command='errObj', msg=str(e))
            else:
                dsres.print(command='log', msg='단일 검색을 마쳤습니다.')

        # 다중 상세 검색
        elif serviceName == 'multiCitationSearch':
            dsres.print(command='err', msg='미구현')
        
        # 다중 일반 검색
        elif serviceName == 'multiCommonSearch':
            startYear = input().strip()
            endYear = input().strip()
            gubun = input().strip()
            inputFilePath = input().strip()
            defaultQueryPackSize = input().strip()

            try:
                multiCommonSearchObj.generalSearch(
                    startYear = startYear,
                    endYear = endYear,
                    gubun = gubun,
                    inputFilePath = inputFilePath,
                    defaultQueryPackSize = 0
                )
            except Exception as e:
                dsres.print(command='sysErr', msg='다중 일반 검색 중 오류가 발생했습니다.')
            else:
                dsres.print(command='log', msg='일반 엑셀 검색이 완료되었습니다.')

        # 알 수 없는 서비스 네임
        else:
            dsres.print(command='sysErr', msg='알 수 없는 서비스 접근')

        dsres.print(command='res', target='loading', res=True)