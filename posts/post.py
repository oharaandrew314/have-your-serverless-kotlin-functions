from os import environ

from pynamodb.models import Model
from pynamodb.attributes import UnicodeAttribute


class Post(Model):
    class Meta:
        table_name = environ["TABLE_NAME"]

    id = UnicodeAttribute(hash_key=True)
    title = UnicodeAttribute(null=False)
    content = UnicodeAttribute(null=True)
