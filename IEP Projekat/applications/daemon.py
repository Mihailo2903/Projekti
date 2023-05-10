import json

from flask import Flask
from redis import Redis

from applications.models import *
from applications.configuration import Configuration

from sqlalchemy import and_

application = Flask(__name__)
application.config.from_object(Configuration)

if __name__ == "__main__":
    database.init_app(application)
    while True:
        try:
            with Redis(host=Configuration.REDIS_HOST) as redis:
                with application.app_context():
                    redis_bytes = redis.blpop(Configuration.REDIS_PRODUCTS_LIST)
                    error = False
                    prol = json.loads(redis_bytes[1])
                    name = prol["name"]
                    categories = prol["categories"].split("|")
                    quantity = prol["quantity"]
                    price = prol["price"]

                    product = Product.query.filter(Product.name==name).first()

                    if not product:
                        pro = Product(name=name, quantity=quantity, price=price)
                        database.session.add(pro)
                        database.session.commit()

                        for category in categories:
                            categ = Category.query.filter(Category.name == category).first()
                            if not categ:
                                cat = Category(name=category)
                                database.session.add(cat)
                                database.session.commit()
                                procat = ProductCategory(productId=pro.id, categoryId=cat.id)
                            else:
                                procat = ProductCategory(productId=pro.id, categoryId=categ.id)
                            database.session.add(procat)
                            database.session.commit()

                    else:
                        result = [categ.name for categ in product.categories]

                        if len(result) != len(categories):
                            error = True

                        if error == False:
                            for res in result:
                                if res not in categories:
                                    error = True
                                    break

                        if error == False:
                            newPrice = (product.quantity * product.price + quantity * price) / (product.quantity + quantity)
                            product.quantity += quantity
                            product.price = newPrice
                            database.session.commit()

                    if error == False:
                        result = database.session.query(Order.id, OrderProduct.id, OrderProduct.requested,
                                                        OrderProduct.received, Product.id).filter(
                            and_(
                                Order.id == OrderProduct.orderId,
                                Product.id == OrderProduct.productId,
                                OrderProduct.requested != OrderProduct.received
                            )
                        ).order_by(Order.id).all()

                        for res in result:
                            orderProductId = res[1]
                            ordPro = OrderProduct.query.filter(OrderProduct.id == orderProductId).first()
                            productId = res[4]
                            pro = Product.query.filter(Product.id == productId).first()
                            wanted = res[2] - res[3]
                            if wanted <= pro.quantity:
                                pro.quantity = pro.quantity - wanted
                                database.session.commit()
                                ordPro.received = ordPro.requested
                                database.session.commit()
                                ord = Order.query.filter(Order.id == res[0]).first()
                                if ord.requested == ord.fullfiled + 1:
                                    ord.status = "C"
                                ord.fullfiled += 1
                                database.session.commit()
                            else:
                                ordPro.received += pro.quantity
                                pro.quantity = 0
                                database.session.commit()
        except Exception as error:
            print(error)