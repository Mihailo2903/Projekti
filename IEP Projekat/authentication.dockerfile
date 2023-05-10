FROM python:3

RUN mkdir -p /opt/src
WORKDIR /opt/src

COPY applications/admin/adminDecorator.py ./applications/admin/adminDecorator.py
COPY authentication/application.py ./authentication/application.py
COPY authentication/configuration.py ./authentication/configuration.py
COPY authentication/models.py ./authentication/models.py
COPY authentication/requirements.txt ./authentication/requirements.txt

RUN pip install -r ./authentication/requirements.txt

ENV PYTHONPATH="/opt/src"

ENTRYPOINT ["python","./authentication/application.py"]