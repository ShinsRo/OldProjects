import sys
from src.modules.scraper import Scraper

results = []

# sample
########################################
results = {}
for idx, arg in enumerate(sys.argv[1:]):
    results[idx] = "result for %s"%arg
########################################

print(results)