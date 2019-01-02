# features
import sju_single_search 
import sju_multi_search
import sju_fast_search
import sju_dupl_search

# support
import sju_utiles as _utiles
import sju_models as _models
import sju_CONSTANTS as _CONS
import sju_exceptions as _EXCEP

# std_libs
import re
import sys
import traceback
import threading
import concurrent.futures

from sju_utiles import os
from sju_utiles import datetime
from sju_utiles import requests

if __name__ == "__main__":
    # ui 출력 객체 초기화
    ui_stream = _models.UI_Stream('dispatcher', 'main', 'res')
    # 디스패처 활성화, 로딩 활성화
    ui_stream.push(command='res', target='loading', res=True)
    ui_stream.push(command='log', msg=_CONS.STATE_MSG[101])

    cookies = None
    multiSearchObj = None
    singleSearchObj = None
    authorSearchObj = None
    compactSearchObj = None

    # 서비스 초기화
    while True:
        try:
            service_list = [
                { 'name': 'singleSearch', 'init': sju_single_search.SingleSearch }, 
                { 'name': 'multiSearch', 'init': sju_multi_search.MultiSearch },
                { 'name': 'fastSearch', 'init': sju_fast_search.FastSearch },
                { 'name': 'duplSearch', 'init': sju_dupl_search.DuplSearch },
            ]
            
            ui_stream.push(command='log', msg=_CONS.STATE_MSG[102])
            ui_stream.push(command='log', msg=_CONS.STATE_MSG[103])
            ui_stream.push(command='log', msg=_CONS.STATE_MSG[104])
            with concurrent.futures.ThreadPoolExecutor(max_workers=len(service_list)) as executor:
                future_service = {
                    executor.submit(service['init'], cookies=cookies): service['name'] for service in service_list
                }
                for future in concurrent.futures.as_completed(future_service):
                    name_done = future_service[future]
                    try:
                        tempObj = future.result()

                        if name_done == 'singleSearch': 
                            singleSearchObj = tempObj
                            ui_stream.push(command='log', msg=_CONS.STATE_MSG[106])
                        elif name_done == 'multiSearch':
                            multiSearchObj = tempObj
                            ui_stream.push(command='log', msg=_CONS.STATE_MSG[107])
                        elif name_done == 'fastSearch':
                            fastSearchObj = tempObj
                            ui_stream.push(command='log', msg=_CONS.STATE_MSG[108])
                        elif name_done == 'oneByOneSearch':
                            authorSearchObj = tempObj
                            ui_stream.push(command='log', msg=_CONS.STATE_MSG[109])
                        elif name_done == 'duplSearch':
                            duplSearchObj = tempObj
                            ui_stream.push(command='log', msg=_CONS.STATE_MSG[99])
                    except _EXCEP.InitMultiSessionErr as imse:
                        ui_stream.push(command='sysErr', msg='%s 가용 스레드가 너무 적습니다. 잠시 후 다시 실행해주세요.'%name_done)

                    except Exception as e:
                        ui_stream.push(command='sysErr', msg='%s 초기화 실패'%name_done)
                        raise e

        except _EXCEP.LoginRequired as lr:
            ui_stream.push(command='sysErr', msg=_CONS.STATE_MSG[303])

            # dispatch 강제 종료
            ui_stream.push(command='log', msg=_CONS.STATE_MSG[500])
            ui_stream.push(command='errObj', msg=e)
            exit(2000)

            # 로그인 모듈 개발 완료시 사용
            # ui_stream.push(command='sysErr', msg='교내 IP가 아닌 경우 로그인이 필요합니다.')
            # ui_stream.push(command='login', msg='LOGIN REQEUIRED')

            # login_gubun = input().strip()
            # user_id = input().strip()
            # user_password = input().strip()

            # ui_stream.push(command='log', msg='WOS에 로그인 중입니다.')
            # login = sju_login.Login()
            # res = login.login_wos(user_id, user_password)
            # cookies = res.cookies
            # cookies['SID'] = '"%s"'%cookies['dotmatics.elementalKey']
            # continue

        except requests.exceptions.ConnectionError as ce:
            ui_stream.push(command='sysErr', msg=_CONS.STATE_MSG[303])

            # dispatch 강제 종료
            ui_stream.push(command='log', msg=_CONS.STATE_MSG[500])
            ui_stream.push(command='errObj', msg=e)
            exit(2000)

        except Exception as e:
            ui_stream.push(command='sysErr', msg=_CONS.STATE_MSG[302])

            # dispatch 강제 종료
            ui_stream.push(command='log', msg=_CONS.STATE_MSG[500])
            ui_stream.push(command='errObj', msg=e)
            exit(2000)
        else:
            # 모든 서비스 초기화 완료
            ui_stream.push(command='log', msg=_CONS.STATE_MSG[201])
            break


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

            # 빠른 검색
            if service_name == 'fastSearch':
                query = inputs['query']
                start_year = inputs['start_year']
                end_year = inputs['end_year']
                p_authors = inputs['p_authors']
                organization = inputs['organization']
                gubun = inputs['gubun']

                ui_stream.push(command='log', msg=_CONS.STATE_MSG[116])

                fastSearchObj.start(
                    (query, p_authors, organization), 
                    start_year, 
                    end_year, 
                    gubun,
                )
                ui_stream.push(command='log', msg=_CONS.STATE_MSG[118])

            # 단일 상세 검색
            if service_name == 'singleSearch':
                query = inputs['query']
                start_year = inputs['start_year']
                end_year = inputs['end_year']
                p_authors = inputs['p_authors']
                organization = inputs['organization']
                gubun = inputs['gubun']

                ui_stream.push(command='log', msg=_CONS.STATE_MSG[111])

                singleSearchObj.start(
                    (query, p_authors, organization), 
                    start_year, 
                    end_year, 
                    gubun,
                )
                ui_stream.push(command='log', msg=_CONS.STATE_MSG[112])

            # 다중 상세 검색
            elif service_name == 'multiSearch':
                start_year = inputs['start_year']
                end_year = inputs['end_year']
                gubun = inputs['gubun']
                path = inputs['path']

                ui_stream.push(command='log', msg=_CONS.STATE_MSG[113])

                multiSearchObj.start(
                    start_year=start_year,
                    end_year=end_year,
                    gubun=gubun,
                    path=path
                )

                ui_stream.push(command='log', msg=_CONS.STATE_MSG[114])

            if service_name == 'duplSearch':
                query = inputs['query']
                start_year = inputs['start_year']
                end_year = inputs['end_year']
                p_authors = inputs['p_authors']
                organization = inputs['organization']
                gubun = inputs['gubun']

                ui_stream.push(command='log', msg=_CONS.STATE_MSG[120])

                duplSearchObj.start(
                    (query, p_authors, organization), 
                    start_year, 
                    end_year, 
                    gubun,
                )

                ui_stream.push(command='log', msg=_CONS.STATE_MSG[122])

            # 알 수 없는 서비스 네임
            else:
                pass
                # ui_stream.push(command='sysErr', msg=_CONS.STATE_MSG[402])
        
        except requests.exceptions.ConnectionError as ce:
            ui_stream.push(command='sysErr', msg=_CONS.STATE_MSG[303])

            # dispatch 강제 종료
            ui_stream.push(command='log', msg=_CONS.STATE_MSG[500])
            ui_stream.push(command='errObj', msg=ce)
            ui_stream.push(command='res', target='loading', res=False)
            exit(2000)
        except EOFError as eof:
            ui_stream.push(command='sysErr', msg=_CONS.STATE_MSG[401])
            ui_stream.push(command='res', target='loading', res=False)
            sys.exit(1)
        except Exception as e:
            ui_stream.push(command='sysErr', msg=_CONS.STATE_MSG[400])
            ui_stream.push(command='errObj', msg=e)
            continue