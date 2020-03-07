# 3.- Mono Info

import pickle
import re
import sys
from SAR_p3_monkey_lib import Monkey


if __name__ == "__main__":
    
    if len(sys.argv) != 2:
        print("python %s indexfile" % sys.argv[0])
        sys.exit(-1)

    index_filename = sys.argv[1]
    i = index_filename.rfind('.')
    info_filename = (index_filename[:i] if i > 0 else index_filename) + ".info"

    m = Monkey()
    m.load_index(index_filename)
    m.save_info(info_filename)