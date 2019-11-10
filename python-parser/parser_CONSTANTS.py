RABBITMQ_SERVER_URL = 'amqp://guest:guest@localhost:5672/'
# RABBITMQ_SERVER_URL = 'amqp://remote-dev:compact@www.siotman.com:15673/'
JAXWS2REST_SERVER   = 'http://127.0.0.1:9400/'
# JAXWS2REST_SERVER   = 'http://www.siotman.com:19400/'
YOUR_PAPER_SERVER   = 'http://127.0.0.1:9401/'
# YOUR_PAPER_SERVER   = 'http://www.siotman.com:19402/'
WOS_BASE_URL        = 'http://apps.webofknowledge.com'

APP_SERVER = {

}

def get_form_data(action, data):
    '''
        입력된 데이터와 action url에 따른 필요한 폼 데이터를 합해 반환해주는 함수
        :param action: action url
        :param data: form data
        :return: 추가된 form data
    '''
    form_data = {}

    if action == '/WOS_GeneralSearch.do':
        form_data = {
            'fieldCount': '1',              # required_optional
            'action': 'search',
            'product': 'WOS',
            'search_mode': 'GeneralSearch',
            'SID': '',                      # required
            'max_field_count': '25',
            'sa_params': '',                # required
            'formUpdated': 'true', 
            'value(input1)': '',            # required
            'value(select1)': '',           # required
            'value(hidInput1)': '', 
            'limitStatus': 'collapsed',     # required_optional
            'ss_lemmatization': 'On', 
            'ss_spellchecking': 'Suggest', 
            'SinceLastVisit_UTC': '', 
            'SinceLastVisit_DATE': '', 
            'period': 'Year Range',         
            'range': 'CUSTOM',
            'start_year': '',                # required
            'end_year': '',                  # required
            'editions': ['SCI', 'SSCI', 'AHCI', 'ESCI'], 
            'update_back2search_link_param': 'yes', 
            'ssStatus': 'display:none',
            'ss_showsuggestions': 'ON',
            'ss_numDefaultGeneralSearchFields': '1',
            'ss_query_language': '', 
            'rs_sort_by': 'PY.D;LD.D;SO.A;VL.D;PG.A;AU.A',
        }
    elif action == '/OutboundService.do':
        form_data = {
            'selectedIds': '',
            'displayCitedRefs':'',
            'displayTimesCited':'',
            'displayUsageInfo':'true',
            'viewType':'summary',
            'product':'WOS',
            'rurl': '',
            'mark_id':'WOS',
            'colName':'WOS',
            'search_mode':'CitationReport',
            'view_name':'WOS-CitationReport-summary',
            'sortBy': '',
            'mode':'OpenOutputService',
            'qid': '',
            'SID': '',
            'format': 'crsaveToFile',
            'mark_to': '',
            'mark_from': '',
            'queryNatural': '',
            'count_new_items_marked': '0',
            'use_two_ets': 'false',
            'IncitesEntitled': 'no',
            'value(record_select_type)': 'range',
            'markFrom': '',
            'markTo': '',
            'action':'recalulate',
            'start_year_val':'1900',
            'end_year_val':'2019',
            'viewAbstractUrl':'',
            'LinksAreAllowedRightClick': 'full_record.do',
            'filters': '',
            'timeSpan':  '',
            'db_editions': '',
            'additional_qoutput_params': 'cr_qid=',
            'print_opt':'Html',
            'include_mark_from_in_url':'true',
            'end_year': '',
            'start_year': '',
            'piChart': '',
            'toChart': '',
            'fields':'DUMMY_VALUE'
        }
    elif action == '/OutboundService.do?action=go&&':
        form_data = {
            'selectedIds': '',
            'displayCitedRefs': 'true',
            'displayTimesCited': 'true',
            'displayUsageInfo': 'true',
            'viewType': 'summary',
            'product': 'WOS',
            'mark_id': 'WOS',
            'colName': 'WOS',
            'search_mode': 'CitingArticles',
            'locale': 'en_US',
            'research_id': 'U-9523-2018',
            'view_name': 'WOS-CitingArticles-summary',
            'sortBy': 'PY.D;LD.D;SO.A;VL.D;PG.A;AU.A',
            'mode': 'OpenOutputService',
            'qid': '',                      # required
            'SID': '',                      # required
            'format': 'fastSave',
            'mark_to': '',                  # required
            'mark_from': '1',
            'queryNatural': '',             # 의심 
            'count_new_items_marked': '0',
            'use_two_ets': 'false',
            'IncitesEntitled': 'no',
            'value(record_select_type)': 'range',
            'markFrom': '1',
            'markTo': '',                    # required
            'save_options': 'tabWinUnicode'
        }
    else:
        pass

    form_data.update(data)
    return form_data


STATE_MSG = {
    # dispatcher MSG
    100: '디스패처 로그 메세지',

    101: 'dispatcher를 준비합니다.',
    102: '기반 서비스를 초기화합니다. 이 작업은 2~3분이 소요됩니다.',
    103: '각 서비스의 브라우저 인증, 검색 폼 초기화를 수행합니다.',
    104: '초기화 진행 중 검색 서비스는 이용할 수 없습니다.',
    106: '검색하기 서비스 초기화가 완료되었습니다.',
    107: '엑셀로 검색하기 서비스 초기화가 완료되었습니다.',
    108: '빠른 검색 서비스 초기화가 완료되었습니다.',
    109: '저자명 기준 검색 서비스 초기화가 완료되었습니다.',
    99 : '중복 검색 서비스 초기화가 완료되었습니다.',

    201: '모든 서비스 초기화 완료.',
    301: '초기화 중 오류 발생.',
    302: 'wos 연결에 실패했습니다. 인터넷이나 접속 장소가 유효한 지 확인해주세요.',
    303: '연결 지연으로 세션 연결에 실패했습니다.',

    110: 'dispatcher가 준비되었습니다.',

    111: '상세 검색을 호출합니다.',
    112: '상세 검색을 마쳤습니다.',
    113: '엑셀 검색을 호출합니다.',
    114: '엑셀 검색을 마쳤습니다.',
    116: '빠른 검색을 호출합니다.',
    118: '빠른 검색을 마쳤습니다.',
    120: '중복 허용 검색을 호출합니다.',
    122: '중복 허용 검색을 마쳤습니다.',

    400: '알 수 없는 오류가 발생했습니다.',
    401: 'dispatcher와의 연결이 해제되었습니다. 프로그램을 재실행해주세요.',
    402: '알 수 없는 서비스 접근입니다.',

    500: 'dispatcher를 강제 종료합니다.',

    # single_search MSG
    1000: '상세 단일 검색 기능 로그 메세지',
    1300: ['쿼리에 "=" 문자를 포함할 수 없습니다.'],

    1001: '세션을 갱신합니다.',
    1101: '세션과 SID를 요청합니다.',
    1201: 'SID를 얻는데 성공하였습니다.',
    1301: ['SID를 얻는 중 오류가 발생했습니다.'],

    1002: '검색 프로세스를 시작합니다.',
    1102: '검색을 요청합니다.',
    1202: '검색에 성공하였습니다.',
    1302: ['검색결과가 없습니다.', '검색 결과가 하나 이상입니다. 검색 결과가 하나가 되도록 검색해주세요.', '해당 쿼리 상세 정보를 읽는 중 오류가 발생했습니다.', 'WOS 서버가 에러를 반환했습니다.'],

    1003: '상세 정보를 가져옵니다.',
    1103: '상세 정보 페이지를 요청합니다.',
    1203: '상세 정보를 가져오는데에 성공했습니다.',
    1303: ['상세 정보를 가져오는데에 실패했습니다.'],

    1004: '인용 논문 정보를 가져옵니다.',
    1104: '인용 리포트를 요청합니다.',
    1204: 'Fast 5000을 다운로드합니다.',
    1404: 'Fast 5000 데이터를 가공합니다.',
    1504: '기준 저자에 따라 검증합니다.',
    1304: ['논문을 인용한 논문이 없으므로 검색을 종료합니다.', '인용한 논문이 너무 많으므로 검색을 종료합니다.', '인용중 논문 검색 중 WOS 서버에서 에러를 보내왔습니다.', '해당 논문의 인용 정보는 접근 불가능합니다.'],

    1005: '',
    1105: '엑셀 파일 제작을 요청합니다.',
    1205: '파일 제작 요청에 성공했습니다.',
    1305: ['파일 제작 요청에 서버가 에러를 반환했습니다.'],

    1006: '',
    1106: '엑셀을 다운로드 받습니다.',
    1206: '엑셀 다운로드에 성공했습니다.',
    1306: ['서버가 엑셀이 아닌 에러를 반환했습니다.'],

    1200: ['상세 검색을 완료했습니다.'],

    # multi_search MSG
    2000: '상세 엑셀 검색 기능 로그 메세지',

    2001: '',
    2101: '서브 스레드를 초기화합니다.',
    2201: '초기화가 완료되었습니다.',
    2301: ['서브 스레드 초기화를 실패했습니다.'],

    2002: '상세 엑셀 검색을 준비합니다',
    2102: '상세 엑셀 검색을 시작합니다.',
    2202: '상세 엑셀 검색을 완료했습니다.',
    2302: ['검색 중 에러가 발생했습니다.'],

    # author_search MSG
    3000: '저자 기준 검색 기능 로그 메세지',
    # 3000: '',
    # 3000: '',
    # 3000: '',
    # 3000: '',
    # 3000: '',
    # 3000: '',

    # dupl_search MSG
    4000: '',
    4200: ['[dupl] 상세 검색을 완료했습니다.'],

    4002: '[dupl] 검색 프로세스를 시작합니다.',
    4102: '[dupl] 검색을 요청합니다.',
    4202: '[dupl] 검색에 성공하였습니다.',
    4302: ['[dupl] 검색결과가 없습니다.',
            '[dupl] 해당 쿼리 상세 정보를 읽는 중 오류가 발생했습니다.', 
            '[dupl] WOS 서버가 에러를 반환했습니다.'],
    
    4003: '[dupl] 상세 정보를 가져옵니다.',
    4103: '[dupl] 상세 정보 페이지를 요청합니다.',
    4203: '[dupl] 상세 정보를 가져오는데에 성공했습니다.',
    4303: ['[dupl] 상세 정보를 가져오는데에 실패했습니다.'],

    4004: '[dupl] 인용 논문 정보를 가져옵니다.',
    4104: '[dupl] 인용 리포트를 요청합니다.',
    4304: ['[dupl] 논문을 인용한 논문이 없으므로 검색을 종료합니다.', 
        '[dupl] 인용한 논문이 너무 많으므로 검색을 종료합니다.', 
        '[dupl] 인용중 논문 검색 중 WOS 서버에서 에러를 보내왔습니다.', 
        '[dupl] 해당 논문의 인용 정보는 접근 불가능합니다.'],
    4204: '[dupl] Fast 5000을 다운로드합니다.',
    4404: '[dupl] Fast 5000 데이터를 가공합니다.',
    

    4999: '[dupl] paper_data cnt_link error',

    #search part MSG
    5000: '[검색] 연결이 지연되어 검색이 불가능합니다.',
}

USER_AGENT_LIST = [
   #Chrome
    'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36',
    'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36',
    'Mozilla/5.0 (Windows NT 5.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36',
    'Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36',
    'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36',
    'Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36',
    'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36',
    'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36',
    'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36',

    #Firefox
    'Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1)',
    'Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko',
    'Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)',
    'Mozilla/5.0 (Windows NT 6.1; Trident/7.0; rv:11.0) like Gecko',
    'Mozilla/5.0 (Windows NT 6.2; WOW64; Trident/7.0; rv:11.0) like Gecko',
    'Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko',
    'Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.0; Trident/5.0)',
    'Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko',
    'Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)',
    'Mozilla/5.0 (Windows NT 6.1; Win64; x64; Trident/7.0; rv:11.0) like Gecko',
    'Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)',
    'Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)',
    'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)'

    #else
    'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36	Chrome 44	Web Browser	Computer	Very common'
    'Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9a1) Gecko/20070308 Minefield/3.0a1	Minefield 3	Web Browser	Computer	Very common'
    'Apache/2.4.25 (Debian) (internal dummy connection)	None		Computer	Very common'
    'Wget/1.12 (linux-gnu)	Wget 1.12	Application	Computer	Very common'
    'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36'
    'Mozilla/5.0 (X11; U; Linux i686; fr; rv:1.9.2.17) Gecko/20110422 Ubuntu/10.04 (lucid) Firefox/3.6.17'
    'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:15.0) Gecko/20100101 Firefox/15.0'
    'Mozilla/5.0 (Unknown; Linux) AppleWebKit/538.1 (KHTML, like Gecko) Chrome/v1.0.0 Safari/538.1'
    'Mozilla/5.0 (X11; U; Linux i686; pt-BR; rv:1.9.0.15) Gecko/2009102815 Ubuntu/9.04 (jaunty) Firefox/3.0.15'
    'Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20150101 Firefox/47.0 (Chrome)'
    'Mozilla/5.0 (X11; U; Linux i686; fr; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.04 (lucid) Firefox/3.6.13'
    'Mozilla/5.0 (X11; U; Linux i686; fr; rv:1.9.2.16) Gecko/20110323 Ubuntu/10.04 (lucid) Firefox/3.6.16'
    'Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.11) Gecko GranParadiso/3.0.11'
    'Mozilla/5.0 (SMART-TV; X11; Linux armv7l) AppleWebKit/537.42 (KHTML, like Gecko) Chromium/25.0.1349.2 Chrome/25.0.1349.2 Safari/537.42'
    'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML	Webkit based browser	'
    'Mozilla/5.0 (Linux; NetCast; U) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/34.0.1847.137 Safari/537.31 SmartTV/6.0'
    'Mozilla/5.0 (X11; Linux x86_64; rv:52.0) Gecko/20100101 Firefox/52.0'
    'Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20150101 Firefox/20.0 (Chrome)'
    'Mozilla/5.0 (Linux; U) AppleWebKit/537.4 (KHTML, like Gecko) Chrome/22.0.1229.79 Safari/537.4'
    'magpie-crawler/1.1 (U; Linux amd64; en-GB; +http://www.brandwatch.net)'
    'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.94 Safari/537.36'
    'Mozilla/5.0 (compatible; Linux x86_64; Mail.RU_Bot/2.0; +http://go.mail.ru/help/robots)'
    'Mozilla/5.0 (X11; Linux x86_64; rv:45.0) Gecko/20100101 Firefox/45.0	'
    'Mozilla/5.0 (X11; Linux i686; rv:2.0.1) Gecko/20100101 Firefox/4.0.1'
    'Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.10) Gecko/2009042513 Ubuntu/8.04 (hardy) Firefox/3.0.10	Firefox 3'
    'Mozilla/5.0 (X11; Linux i686; rv:10.0.2) Gecko/20100101 Firefox/10.0.2 DejaClick/2.4.1.6	AlertSite Monitoring Bot 2.4'
    'Mozilla/5.0 (Linux; NetCast; U) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.33 Safari/537.31 SmartTV/5.0'
    'Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0'
    'Mozilla/5.0 (X11; Datanyze; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36	Chrome 65'
    'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:47.0) Gecko/20100101 Firefox/47.0	Firefox 47'
    'Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.3) Gecko/2008092510 Ubuntu/8.04 (hardy) Firefox/3.0.3'
    'Mozilla/5.0 (SMART-TV; Linux; Tizen 2.4.0) AppleWebkit/538.1 (KHTML, like Gecko) SamsungBrowser/1.1 TV Safari/538.1'
    'Mozilla/5.0 (X11; U; Linux i686; fr; rv:1.9.2.15) Gecko/20110303 Ubuntu/10.04 (lucid) Firefox/3.6.15'
    'Mozilla/5.0 (SMART-TV; LINUX; Tizen 3.0) AppleWebKit/538.1 (KHTML, like Gecko) Version/3.0 TV Safari/538.1'
    'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36'
    'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.4 (KHTML, like Gecko; Google Web Preview) Chrome/22.0.1229 Safari/537.4'
    'Mozilla/5.0 (compatible; Konqueror/4.4; Linux) KHTML/4.4.5 (like Gecko) Kubuntu	Konqueror 4.4'
    'Mozilla/5.0 (X11; Linux x86_64; rv:44.0) Gecko/20100101 Firefox/44.0	Firefox 44'
    'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:30.0) Gecko/20100101 Firefox/30.0'
    'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/534.24 (KHTML, like Gecko) Chrome/11.0.696.34 Safari/534.24'
    'Mozilla/5.0 (Web0S; Linux/SmartTV) AppleWebKit/537.36 (KHTML, like Gecko) QtWebEngine/5.2.1 Chrome/38.0.2125.122 Safari/537.36'
    'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:50.0) Gecko/20100101 Firefox/50.0	Firefox 50'
    'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36'
    'Mozilla/5.0 (X11; Linux) AppleWebKit/537.21 (KHTML, like Gecko) webbrowser/1.0 Safari/537.21'
    'Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.5) Gecko/2008121622 Ubuntu/8.10 (intrepid) Firefox/3.0.5'
    'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36'
    'Mozilla/5.0 (X11; Linux x86_64; rv:2.0.1) Gecko/20100101 Firefox/4.0.1'
    'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0'
    'Mozilla/5.0 (SMART-TV; Linux; Tizen 2.3) AppleWebkit/538.1 (KHTML, like Gecko) SamsungBrowser/1.0 TV Safari/538.1'
    'Mozilla/5.0 (X11; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0'
]

"""
    AU: 저자명
    BA:
    CA:
    GP:
    RI:
    OI:
    BE:
    Z2:
    TI: 제목
    X1:
    Y1:
    Z1:
    FT:
    PN:
    AE:
    Z3:
    SO: 발행처
    S1:
    SE:
    BS:
    VL: 볼륨 권
    IS: 이슈 호
    SI:
    MA:
    BP: 시작 페이지
    EP: 끝 페이지
    AR:
    DI: 도이
    D2:
    SU:
    PD: 출판 년월
    PY: 출판 년
    AB:
    X4:
    Y4:
    Z4:
    AK:
    CT:
    CY:
    SP:
    CL:
    TC: timse cited
    Z8:
    ZB:
    ZS:
    Z9:
    SN:
    BN:
    UT: WOS 아이디
    PM:


"""