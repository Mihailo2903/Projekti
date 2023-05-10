import json
import re
from flask import Flask, request, Response, jsonify
import io
import csv

from redis import Redis

from applications.admin.adminDecorator import roleCheck
from applications.configuration import Configuration
from applications.models import *
from flask_jwt_extended import JWTManager, create_access_token, jwt_required, create_refresh_token, get_jwt, get_jwt_identity
from sqlalchemy import and_

application = Flask(__name__)
application.config.from_object(Configuration)
jwt = JWTManager(application)

@application.route("/update", methods=["POST"])
@roleCheck(role="warehouse")
def update():
    if "file" not in request.files:
        return Response(json.dumps({"message": "Field file is missing."}), 400)

    content = request.files['file'].stream.read().decode("utf-8")
    stream = io.StringIO(content)
    reader = csv.reader(stream)
    i=0
    lines = []

    with Redis(host=Configuration.REDIS_HOST) as redis:
        for line in reader:
            lines.append(line)
            if len(line) != 4:
                greska = {"message": f"Incorrect number of values on line {i}."}
                return Response(json.dumps(greska), status=400)
            try:
                quan = int(line[2])
            except:
                greska = {"message": f"Incorrect quantity on line {i}."}
                return Response(json.dumps(greska), status=400)
            if quan <= 0:
                greska = {"message": f"Incorrect quantity on line {i}."}
                return Response(json.dumps(greska), status=400)
            try:
                price = float(line[3])
            except:
                greska = {"message": f"Incorrect price on line {i}."}
                return Response(json.dumps(greska), status=400)
            if price <= 0:
                greska = {"message": f"Incorrect price on line {i}."}
                return Response(json.dumps(greska), status=400)

            i = i + 1

        for line in lines:
            product = {
                "categories": line[0],
                "name": line[1],
                "quantity": int(line[2]),
                "price": float(line[3]),
            }
            redis.rpush(Configuration.REDIS_PRODUCTS_LIST, json.dumps(product))

        return Response(status=200)

@application.route("/", methods=["GET"])
def index():
    return "Hello world"

if __name__ == "__main__":
    database.init_app(application)
    application.run(debug=True, host="0.0.0.0", port=5002)