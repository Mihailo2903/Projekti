from flask import Flask
from sqlalchemy_utils import database_exists, create_database
from configuration import Configuration
from flask_migrate import Migrate, init, upgrade,migrate
from models import *

application = Flask(__name__)
application.config.from_object(Configuration)

migrateObject = Migrate(application, database)

done = False


while not done:
    try:
        if not database_exists(Configuration.SQLALCHEMY_DATABASE_URI):
            create_database(Configuration.SQLALCHEMY_DATABASE_URI)

        database.init_app(application)

        with application.app_context() as context:
            init()
            migrate(message="Production migration")
            upgrade()

            adminRole = Role(name="admin");
            userRole = Role(name="user")
            warehouseRole = Role(name="warehouse")

            database.session.add(adminRole)
            database.session.add(userRole)
            database.session.add(warehouseRole)
            database.session.commit()

            admin = User(
                email="admin@admin.com",
                password="1",
                forename="admin",
                surname="admin",
                roleId=1
            )

            database.session.add(admin)
            database.session.commit()

            done = True
    except Exception as error:
        print(error)
