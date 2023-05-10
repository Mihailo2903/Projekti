import os

databaseurl=os.environ["DATABASE_URL"]

class Configuration():
    #SQLALCHEMY_DATABASE_URI="mysql+pymysql://root:root@localhost/storeDBProject"
    SQLALCHEMY_DATABASE_URI = f"mysql+pymysql://root:root@{databaseurl}/storeProj"
    REDIS_HOST="redis"
    REDIS_PRODUCTS_LIST = "products"
    JWT_SECRET_KEY = "JWT_SECRET_KEY"