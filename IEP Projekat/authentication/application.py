import json
import re
from flask import Flask, request, Response, jsonify

from applications.admin.adminDecorator import roleCheck
from configuration import Configuration
from models import *
from email.utils import parseaddr # proverava da li je email u odgovarajućem formatu
from flask_jwt_extended import JWTManager, create_access_token, jwt_required, create_refresh_token, get_jwt, get_jwt_identity
from sqlalchemy import and_

application = Flask(__name__)
application.config.from_object(Configuration)

@application.route("/register", methods=["POST"])
def register():
    email = request.json.get("email","")
    password = request.json.get("password","")
    forename = request.json.get("forename","")
    surname = request.json.get("surname","")
    isCustomer = request.json.get("isCustomer", "")

    emailEmpty = len(email) == 0
    passwordEmpty = len(password) == 0
    forenameEmpty = len(forename) == 0
    surnameEmpty = len(surname) == 0
    isCustomerEmpty = len(str(isCustomer)) == 0

    if forename==None or forenameEmpty:
        greska = { "message" : "Field forename is missing." }
        return Response(json.dumps(greska), status=400)
    if surname==None or surnameEmpty:
        greska = { "message" : "Field surname is missing." }
        return Response(json.dumps(greska), status=400)
    if email==None or emailEmpty:
        greska = { "message" : "Field email is missing." }
        return Response(json.dumps(greska), status=400)
    if password==None or passwordEmpty:
        greska = { "message" : "Field password is missing." }
        return Response(json.dumps(greska), status=400)
    if isCustomer==None or isCustomerEmpty:
        greska = { "message" : "Field isCustomer is missing." }
        return Response(json.dumps(greska), status=400)

    regex = '^[a-z0-9]+[\._]?[a-z0-9]+[@]\w+[.]\w{2,3}$'
    if re.search(regex,email)==None or len(email)>256:
        greska = {"message": "Invalid email."}
        return Response(json.dumps(greska), status=400)

    regex = ("^(?=.*[a-z])(?=." + "*[A-Z])(?=.*\\d)")
    p = re.compile(regex)
    if re.search(p, password)==None or len(password) > 256 or len(password) < 8:
        greska = {"message": "Invalid password."}
        return Response(json.dumps(greska), status=400)

    em = User.query.filter(User.email == email).first()
    if em:
        greska = {"message": "Email already exists."}
        return Response(json.dumps(greska), status=400)

    if isCustomer==True:
        roleId=3
    else:
        roleId=2

    user = User(email=email, password=password, forename=forename, surname=surname, roleId=roleId)
    database.session.add(user)
    database.session.commit()

    return Response(status=200)


jwt= JWTManager(application)

@application.route("/login", methods=["POST"])
def login():
    email = request.json.get("email", "")
    password = request.json.get("password", "")

    emailEmpty = len(email) == 0
    passwordEmpty = len(password) == 0

    if email == None or emailEmpty:
        greska = {"message": "Field email is missing."}
        return Response(json.dumps(greska), status=400)
    if password == None or passwordEmpty:
        greska = {"message": "Field password is missing."}
        return Response(json.dumps(greska), status=400)

    regex = '^[a-z0-9]+[\._]?[a-z0-9]+[@]\w+[.]\w{2,3}$'
    if re.search(regex, email) == None or len(email) > 256:
        greska = {"message": "Invalid email."}
        return Response(json.dumps(greska), status=400)

    user = User.query.filter(
        and_(User.email == email, User.password == password)
    ).first()

    if not user:
        greska = {"message": "Invalid credentials."}
        return Response(json.dumps(greska), status=400)

    if user.roleId == 3:
        isCustomer = True
    else:
        isCustomer = False

    additionalClais={
        "forename": user.forename,
        "surname": user.surname,
        "password": user.password,
        "isCustomer": isCustomer,
        "roles": [str(user.role)]
    }
    accessToken = create_access_token(identity=user.email, additional_claims=additionalClais)
    refreshToken = create_refresh_token(identity=user.email, additional_claims=additionalClais)

    tok = {
        "accessToken": accessToken,
        "refreshToken": refreshToken
    }
    return Response(json.dumps(tok), status = 200)

@application.route("/refresh", methods=["POST"]) #refresuje token
@jwt_required(refresh=True) #mora biti refresh tip
def refresh():
    identity = get_jwt_identity() # dohvata subject polje (identity)
    refreshClaims = get_jwt()# dohvata additional_claims

    additionalClais = {
        "forename": refreshClaims["forename"],
        "surname": refreshClaims["surname"],
        "password": refreshClaims["password"],
        "isCustomer": refreshClaims["isCustomer"],
        "roles": refreshClaims["roles"]
    }

    acctok = create_access_token(identity=identity, additional_claims=additionalClais)
    tok = {
        "accessToken": acctok
    }
    return Response(json.dumps(tok), status=200)

@application.route("/delete", methods=["POST"])
@roleCheck(role="admin")
def delete():
    email = request.json.get("email", "")
    emailEmpty = len(email) == 0

    if email == None or emailEmpty:
        greska = {"message": "Field email is missing."}
        return Response(json.dumps(greska), status=400)

    regex = '^[a-z0-9]+[\._]?[a-z0-9]+[@]\w+[.]\w{2,3}$'
    if re.search(regex, email) == None or len(email) > 256:
        greska = {"message": "Invalid email."}
        return Response(json.dumps(greska), status=400)

    user = User.query.filter(
        and_(User.email == email)
    ).first()

    if not user:
        greska = {"message": "Unknown user."}
        return Response(json.dumps(greska), status=400)

    database.session.delete(user)
    database.session.commit()

    return  Response(status=200)


@application.route("/", methods=["GET"])
def index():
    return "Hello world"


if __name__ == "__main__":
    database.init_app(application)
    application.run(debug=True, host="0.0.0.0", port=5004)