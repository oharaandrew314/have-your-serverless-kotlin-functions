from werkzeug.serving import run_simple
from .api import create

if __name__ == '__main__':
    run_simple('localhost', 8080, create())
