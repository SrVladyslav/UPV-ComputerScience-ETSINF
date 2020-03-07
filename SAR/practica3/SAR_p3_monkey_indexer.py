# 3.- Mono Indexer

import pickle
import re
import sys
from SAR_p3_monkey_lib import Monkey


if __name__ == "__main__":
    
    if len(sys.argv) != 2 and (len(sys.argv) != 3 or sys.argv[-1] != "tri"):
        print("python %s textfile [tri]" % sys.argv[0])
        sys.exit(-1)

    txt_filename = sys.argv[1]
    use_tri = len(sys.argv) == 3 and sys.argv[-1] == "tri"
    i = txt_filename.rfind('.')
    index_filename = (txt_filename[:i] if i > 0 else txt_filename) + \
        ("_tri.index" if use_tri else ".index")

    m = Monkey()
    m.compute_index(txt_filename, use_tri)
    m.save_index(index_filename)
