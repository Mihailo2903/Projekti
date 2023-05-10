import json
from flask import Flask, request, Response

from applications.admin.adminDecorator import roleCheck
from applications.configuration import Configuration
from applications.models import *
from flask_jwt_extended import JWTManager
from sqlalchemy import and_, func, desc

application = Flask(__name__)
application.config.from_object(Configuration)
jwt = JWTManager(application)


@application.route("/productStatistics", methods=["GET"])
@roleCheck(role="admin")
def productStatistics():
    result = database.session.query(Product.id, Product.name, func.sum(OrderProduct.requested), func.sum(OrderProduct.requested - OrderProduct.received)).filter(
        and_(
            Product.id == OrderProduct.productId,
        )
    ).group_by(Product.id, Product.name).having(func.sum(OrderProduct.requested) > 0).all()

    products = []
    for res in result:
        product = {
            "name": res[1],
            "sold": int(res[2]),
            "waiting": int(res[3])
        }
        products.append(product)

    stat = {
        "statistics": products
    }

    return Response(json.dumps(stat), status=200)


@application.route("/categoryStatistics", methods=["GET"])
@roleCheck(role="admin")
def categoryStatistics():
    result = database.session.query(Category.id, Category.name).filter(
        and_(
            OrderProduct.productId == Product.id,
            Product.id == ProductCategory.productId,
            ProductCategory.categoryId == Category.id
        )
    ).group_by(Category.id, Category.name).having(func.sum(OrderProduct.requested) > 0).order_by(desc(func.sum(OrderProduct.requested)), Category.name).all()


    categories = []
    for res in result:
        categories.append(res[1])

    allCats = Category.query.order_by(Category.name).all()

    for cat in allCats:
        if cat.name not in categories:
            categories.append(cat.name)

    stat = {
        "statistics": categories
    }
    return Response(json.dumps(stat), status=200)


@application.route("/", methods=["GET"])
def index():
    return "Hello world"


if __name__ == "__main__":
    database.init_app(application)
    application.run(debug=True, host="0.0.0.0", port=5001)