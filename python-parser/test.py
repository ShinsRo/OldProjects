import sju_single_search
import re
from wos_parser import ParserInterface


def parse_link(raw):
    souerce_url_regex = r'<val name="sourceURL">\n<!\[CDATA\[(.+)\]\]>\n</val>'
    """
    D1IOvz3C9uDrkdc6eiS
    <val name="sourceURL"><![CDATA[__TARGET_URL__]]></val>
    """
    compiled = re.compile(souerce_url_regex, re.MULTILINE)
    arr = re.findall(compiled, raw.replace("><", ">\n<"))

    return arr

if __name__ == '__main__':
    fd = open("/Users/siotman/Documents/Projects/WosBatch/python-parser/lamr_res.xml", "r")
    fd2 = open("/Users/siotman/Documents/Projects/WosBatch/python-parser/urls.txt", "w")
    arr = parse_link(fd.readlines()[0])
    # arr = parse_link('<val name="sourceURL"><![CDATA[__TARGET_URL__]]></val><val name="sourceURL"><![CDATA[__TARGET_URL__2]]></val>')

    parser = ParserInterface("D1IOvz3C9uDrkdc6eiS")
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
    by_reprint = {};
    for url in arr[:3]:
        print(url)
        paper_data = parser.start(url);
        print(paper_data)
