import sju_single_search
import time 
import re
import json
import threading
import traceback
from wos_parser import ParserInterface
import parser_exceptions


def parse_link(raw):
    souerce_url_regex = r'<val name="sourceURL">\n<!\[CDATA\[(.+)\]\]>\n</val>'
    """
    D1IOvz3C9uDrkdc6eiS
    1. <val name="sourceURL"><![CDATA[__TARGET_URL__]]></val>
    2.

    F2EuwutHMldwB6LrfZN
    """
    compiled = re.compile(souerce_url_regex, re.MULTILINE)
    arr = re.findall(compiled, raw.replace("><", ">\n<"))

    return arr



def starter(start, end):
    fd = open("/Users/siotman/Documents/Projects/WosBatch/python-parser/lamr_res.xml", "r")
    fd2 = open("/Users/siotman/Documents/Projects/WosBatch/python-parser/urls.txt", "w")
    arr = parse_link(fd.readlines()[0])
    # arr = parse_link('<val name="sourceURL"><![CDATA[__TARGET_URL__]]></val><val name="sourceURL"><![CDATA[__TARGET_URL__2]]></val>')

    parser = ParserInterface("F2EuwutHMldwB6LrfZN")
    """
    {
        "author short name": {
            full_name: "full name",
            all_tc_by_year: {2000: 0, 2001: 0, ..., 2019: 0},
            only_reprint_tc_by_year: {2000: 0, 2001: 0, ..., 2019: 0},
            address: ["address"]
        }
    }
    """
    dump_data = {}
    with open("/Users/siotman/Documents/Projects/WosBatch/python-parser/paper_data_dump_%d.json"%(start)) as json_dump:
        dump_data = json.load(json_dump)

    dump_cnt = 0
    for keys in dump_data:
        dump_cnt += 1
    
    by_author = {}
    for idx, url in enumerate(arr):
        if not (start <= idx <= end):
            continue;
        
        print(str(idx) + ": " + url)
        paper_data = {}
        try:
            if str(idx) in dump_data:
                if type(dump_data[str(idx)]) == type(""):
                    paper_data = json.loads(dump_data[str(idx)])
                else:
                    paper_data = dump_data[str(idx)]
                continue
            else:
                paper_data = parser.start(url)

            if not "fail" in paper_data:
                continue
            elif paper_data["reason"] == "RecordNotAvailableError":
                continue
                
            tc_data = paper_data['tc_data']
            authors = paper_data['authors']

            is_reprint_in_sejong = re.search(r'Sejong Univ', paper_data['reprint'])
            if not is_reprint_in_sejong:
                continue
            
            for author in authors:
                
                is_reprint = re.search(author, paper_data['reprint'])

                if not author in by_author:
                    by_author[author] = {
                        "name": author,
                        "all_tc_by_year": {},
                        "only_reprint_tc_by_year": {}
                    }

                for year in range(1945, 2020):
                    if not year in by_author[author]['all_tc_by_year']:
                        by_author[author]['all_tc_by_year'][year] = 0
                        by_author[author]['only_reprint_tc_by_year'][year] = 0
                    
                    by_author[author]['all_tc_by_year'][year] += int(tc_data[str(year)])

                    if is_reprint:
                        by_author[author]['only_reprint_tc_by_year'][year] += int(tc_data[str(year)])
                
            paper_data_json_str = json.dumps(paper_data, indent=2)
            dump_data[str(idx)] = paper_data_json_str

            if dump_cnt < idx:
                with open("/Users/siotman/Documents/Projects/WosBatch/python-parser/paper_data_dump_%d.json"%(start), "w") as json_file:
                    json.dump(dump_data, json_file)
                    
        except parser_exceptions.NoPaperDataError:
            print(idx)
            print("NoPaperDataError")
            dump_data[str(idx)] = {"fail": "True", "reason": "NoPaperDataError"}

            with open("/Users/siotman/Documents/Projects/WosBatch/python-parser/paper_data_dump_%d.json"%(start), "w") as json_file:
                json.dump(dump_data, json_file)
            print(idx, "15min wait")
            time.sleep(1000)

        except parser_exceptions.RecordNotAvailableError:
            print(idx)
            print("RecordNotAvailableError")
            dump_data[str(idx)] = {"fail": "True", "reason": "RecordNotAvailableError"}
            
            with open("/Users/siotman/Documents/Projects/WosBatch/python-parser/paper_data_dump_%d.json"%(start), "w") as json_file:
                json.dump(dump_data, json_file)

        except Exception as e:
            print(idx)
            print("except")
            print(e)
            traceback.print_exc()
            dump_data[str(idx)] = {"fail": "True", "reason": str(e)}

            with open("/Users/siotman/Documents/Projects/WosBatch/python-parser/paper_data_dump_%d.json"%(start), "w") as json_file:
                json.dump(dump_data, json_file)

    with open("/Users/siotman/Documents/Projects/WosBatch/python-parser/paper_data_dump_%d.json"%(start), "w") as json_file:
        json.dump(dump_data, json_file)

    table = ""
    header = "%20s\t"%("NAME")
    for year in range(2014, 2020):
        header += str(year) + "\t"
    
    table += header + "\n"

    for author_name in by_author:
        author = by_author[author_name]
        row = "%20s\t"%(author_name)
        
        for year in range(2014, 2020):
            row += str(author['all_tc_by_year'][year]) + "(" + str(author['only_reprint_tc_by_year'][year]) + ")" + "\t"
        
        table += row + "\n"
    
    fd2.write(table)    
            
if __name__ == '__main__':
    threads = []
    starter(0, 5000)
    # for idx in range(0, 11500, 1000):
    #     x = threading.Thread(target=starter, args=(idx + 1,idx + 1000))
    #     threads.append(x)
    #     x.start()

    # for x in threads:
    #     x.join()
