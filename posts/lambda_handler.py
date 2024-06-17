import awsgi

from .api import create

APP = create()


def lambda_handler(event, context):
    return awsgi.response(APP, event, context)
