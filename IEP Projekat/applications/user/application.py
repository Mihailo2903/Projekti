import json
import re
from flask import Flask, request, Response, jsonify

from redis import Redis

from applications.admin.adminDecorator import roleCheck
from applications.configuration import Configuration
from applications.models import *
from flask_jwt_extended import JWTManager, create_access_token, jwt_required, create_refresh_token, get_jwt, \
    get_jwt_identity
from sqlalchemy import and_

application = Flask(__name__)
application.config.from_object(Configuration)
jwt = JWTManager(application)


@application.route("/search", methods=["GET"])
@roleCheck(role="user")
def search():
    name = request.args.get("name", default="")
    category = request.args.get("category", default="")

    resCategories = database.session.query(Category.name).filter(
        and_(
            Category.name.like(f"%{category}%"),
            Product.name.like(f"%{name}%"),
            ProductCategory.productId == Product.id,
            ProductCategory.categoryId == Category.id
        )
    ).group_by(Category.id, Category.name).all()

    resProducts = database.session.query(Product.id).filter(
        and_(
            Category.name.like(f"%{category}%"),
            Product.name.like(f"%{name}%"),
            ProductCategory.productId == Product.id,
            ProductCategory.categoryId == Category.id
        )
    ).group_by(Product.id).all()

    categories = []
    for res in resCategories:
        categories.append(res[0])

    products = []
    for res in resProducts:
        id = res[0]
        pro = Product.query.filter(Product.id == id).first()
        cats = [cate.name for cate in pro.categories]

        obj = {
            "categories": cats,
            "id": pro.id,
            "name": pro.name,
            "price": pro.price,
            "quantity": pro.quantity
        }
        products.append(obj)

    finalno = {
        "categories": categories,
        "products": products
    }

    return Response(json.dumps(finalno), status=200)


@application.route("/order", methods=["POST"])
@roleCheck(role="user")
def order1():
    if "requests" not in request.json:
        return Response(json.dumps({"message": "Field requests is missing."}), 400)
    requests = request.json.get("requests", "")

    i = 0
    completed = True
    for req in requests:
        if "id" not in req:
            return Response(json.dumps({"message": f"Product id is missing for request number {i}."}), 400)

        if "quantity" not in req:
            return Response(json.dumps({"message": f"Product quantity is missing for request number {i}."}), 400)

        try:
            id = int(req["id"])
        except ValueError:
            return Response(json.dumps({"message": f"Invalid product id for request number {i}."}), 400)

        if id <= 0:
            return Response(json.dumps({"message": f"Invalid product id for request number {i}."}), 400)

        try:
            quantity = int(req["quantity"])
        except ValueError:
            return Response(json.dumps({"message": f"Invalid product quantity for request number {i}."}), 400)

        if quantity <= 0:
            return Response(json.dumps({"message": f"Invalid product quantity for request number {i}."}), 400)

        product = Product.query.filter(Product.id == id).first()

        if not product:
            return Response(json.dumps({"message": f"Invalid product for request number {i}."}), 400)

        i = i + 1

    email = get_jwt_identity()
    timestamp = datetime.now().isoformat()
    order = Order(price=0, status="A", date=timestamp, email=email, fullfiled=0, requested=0)
    database.session.add(order)
    database.session.commit()

    totalPrice = 0
    numReq = 0
    numFul = 0
    for req in requests:
        numReq += 1
        id = req["id"]
        quantity = int(req["quantity"])

        product = Product.query.filter(Product.id == id).first()
        price = product.price
        productQuantity = product.quantity


        if productQuantity < quantity:
            completed = False
            p = OrderProduct(productId=id, orderId=order.id, requested=quantity, received=productQuantity, price=price)
            database.session.add(p)
            database.session.commit()
            product = Product.query.filter(Product.id == id).first()
            product.quantity = 0
            database.session.commit()

        else:
            p = OrderProduct(productId=id, orderId=order.id, requested=quantity, received=quantity, price=price)
            database.session.add(p)
            database.session.commit()
            product = Product.query.filter(Product.id == id).first()
            product.quantity = product.quantity - quantity
            database.session.commit()
            numFul += 1

        totalPrice += price * quantity

    if completed:
        order.status = "C"
    else:
        order.status = "P"

    order.requested = numReq
    order.fullfiled = numFul
    order.price = totalPrice
    database.session.commit()

    return Response(json.dumps({"id": order.id}), 200)


@application.route("/status", methods=["GET"])
@roleCheck(role="user")
def status():
    email = get_jwt_identity()
    result = database.session.query(Order.id, Order.price, Order.status, Order.date, Product.id, Product.name,
                                    OrderProduct.price,
                                    OrderProduct.received, OrderProduct.requested).filter(
        and_(
            Order.email == email,
            Order.id == OrderProduct.orderId,
            OrderProduct.productId == Product.id
        )
    ).all()

    dictionary = {}

    for res in result:
        proID = res[4]
        prod = Product.query.filter(Product.id == proID).first()
        categories = [categ.name for categ in prod.categories]

        if res[0] in dictionary:
            elem = dictionary[res[0]]
            proizvodi = elem["products"]
            proizvodi.append(
            {
                "categories": categories,
                "name": res[5],
                "price": res[6],
                "received": res[7],
                "requested": res[8],
            }
            )
            elem["products"] = proizvodi
            dictionary[res[0]] = elem

        else:
            pro = {
                "categories": categories,
                "name": res[5],
                "price": res[6],
                "received": res[7],
                "requested": res[8],

            }
            proizvodi = []
            proizvodi.append(pro)
            price = res[1]
            if res[2] == "C":
                status = "COMPLETE"
            else:
                status = "PENDING"
            timestamp = res[3]
            elem = {
                "products": proizvodi,
                "price": price,
                "status": status,
                "timestamp": timestamp
            }
            dictionary[res[0]] = elem

    orders = {"orders": [dictionary[element] for element in dictionary]}

    return Response(json.dumps(orders), status=200)


@application.route("/", methods=["GET"])
def index():
    return "Hello world"


if __name__ == "__main__":
    database.init_app(application)
    application.run(debug=True, host="0.0.0.0", port=5003)
