

import re

from bs4 import BeautifulSoup

def parse_detail(soup: BeautifulSoup, uid: str):

    # Return 정의 #
    '''
    리턴 데이터 :
        (
            link: "CITE_CNT_LINK",
            paper_data: {
                uid: "00000",
                timesCited: 0,
                authors: [
                    {
                        name: ""
                        full_name: "Seungshin, K",
                        addresses: ["Sejong Univ Gunjalo ...", "Doorae Univ ...", ...]
                    },
                    ...
                ],
                firstAuthor: authors[0],
                reprint: authors[x],
                jcr: {},
                'grades' : [{caped: "SCI", full: "Sxx Cxx Ixx"}, ...],
            }
        )
    '''
    ret_link    :str    = ''
    paper_data  :dict   = { 
        'uid'           : uid,
        'timesCited'    : 0,
        'authors'       : [],
        'firstAuthor'   : {},
        'reprint'       : {},
        'jcr'           : {},
        'grades'        : [],

    }
    # Return 정의 끝 #

    
    # Tags 정의 #
    TAG = {
        'CITE_CNT_LINK' : 'a.snowplow-citation-network-times-cited-count-link',
        'RECORD_INFOS'  : 'div.block-record-info',
        'JCR_TABLE'     : 'table.JCR_Category_table',
        'GRADES'        : 'div.flex-justify-start > ul > span.box-label',
    }
    # Tags 정의 끝 #


    # 인용 횟수 및 링크 #
    cnt_link = soup.select(TAG['CITE_CNT_LINK'])

    if not cnt_link:
        ret_link = ''
        paper_data['timesCited'] = 0
    else:
        ret_link = cnt_link[0]
        paper_data['timesCited'] = int(cnt_link.span.text)
    # 인용 횟수 및 링크 끝 #

    # 저자 및 주소 #
    authors         = []
    reprint_name    = ''
    address_map     = {}

    raw_author_by       = ''
    raw_author_infos    = ''

    record_infos = soup.select(TAG['RECORD_INFOS'])

    target_flag = 0
    for info in record_infos:
        if target_flag is 2:
            break

        # 저자
        if info.text.find('By:') != -1:
            target_flag += 1
            raw_author_by = info.text.split(':')[1]
        
        # 주소
        if info.text.find('Author Information') != -1:
            target_flag += 1
            raw_author_infos = info.text

    regex_sp = r'\n|(&nbsp)|(  )|(\\xa0)|(\t)';
    raw_author_by       = re.sub(regex_sp, '', raw_author_by, flags=re.M)
    raw_author_infos    = re.sub(regex_sp, '', raw_author_infos, flags=re.M)
    
    ## 원본 주소 데이터 정제 ##
    ptn_reprint = re.compile(r'.+Reprint Address:+(?P<name>[A-Z,a-z ]+)')
    ptn_address = re.compile(r'\[ (?P<address_key>\d) \] (?P<address>[A-Za-z, ]+)')

    m = ptn_reprint.match(raw_author_infos)
    reprint_name = m.group('name').strip()

    for address_tup in ptn_address.findall(raw_author_infos):
        address_map[address_tup[0].strip()] = address_tup[1].strip()

    ## 원본 주소 데이터 정제 끝 ##

    ## 원본 저자 데이터 정제 ##
    '''By:Zhou, B (Zhou, Bo)[1 ]; Xu, YP (Xu, Yanping)[2 ]; Lee, SK (Lee, Seul Ki)[3,4 ]'''
    ptn_author_line   = re.compile(r'(?P<name>[A-Z,a-z ]+) (\((?P<full_name>[A-Z,a-z ]+)\))?(\[(?P<address_keys>[1-9, ]+)\])?')
    
    groups = [m.groupdict() for m in ptn_author_line.finditer(raw_author_by)]

    for g in groups:
        author = {
            'name'      : g['name'].strip(),
            'full_name' : g['full_name'].strip(),
            'addresses' : []
        }
        address_keys = g['address_keys']
    
            

        for key in address_keys.split(','):
            key = key.strip()
            author['addresses'].append(address_map[key])

        if reprint_name == author['name']:
            paper_data['reprint'] = author

        authors.append(author)
    
    paper_data['firstAuthor']   = authors[0]
    paper_data['authors']       = authors
    ## 원본 저자 데이터 정제 끝 ##
    # 저자 및 주소 끝 #


    # JCR #
    # 일단 생략한다.
    # JCR 끝 # 


    # 등급 #
    grades          = []

    labels = soup.select(TAG['GRADES'])

    for label in labels:
        full_grade  = label.text.replace('- ', '').strip()
        caped       = re.sub(r'[ a-z]+', r'', full_grade)

        grades += [{'full_grade': full_grade, 'caped': caped}]

    paper_data['grades'] = grades
    # 등급 끝 #

    return (ret_link, paper_data)