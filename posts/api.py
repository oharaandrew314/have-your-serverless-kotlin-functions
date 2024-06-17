from marshmallow import Schema, fields
from falcon import Request, Response, API

from .post import Post


class _PostSchema(Schema):
    id = fields.UUID(required=True)
    title = fields.String(required=True)
    content = fields.String(required=True)


class _Posts(object):

    schema = _PostSchema()

    def on_get(self, request: Request, response: Response):
        posts = list(Post.scan())

        response.media = self.schema.dump(posts, many=True)


def create() -> API:
    app = API()
    app.add_route("/posts", _Posts())
    return app
