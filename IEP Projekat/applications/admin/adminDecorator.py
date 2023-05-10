import json
from functools import wraps

from flask import Response
from flask_jwt_extended import verify_jwt_in_request, get_jwt

def roleCheck(role):
    def InnerRole(function):
        @wraps(function)
        def decorator(*arguments, **keywordArguments):
            verify_jwt_in_request()
            claims = get_jwt()
            if "roles" in claims and role in claims["roles"]:
                return function(*arguments, **keywordArguments)
            else:
                msg = {
                    "msg": "Missing Authorization Header"
                }
                return Response(json.dumps(msg), status=401)
        return decorator

    return InnerRole