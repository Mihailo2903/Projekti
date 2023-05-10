FROM python:3

RUN mkdir -p /opt/src
WORKDIR /opt/src

COPY authentication/migrate.py ./authentication/migrate.py
COPY authentication/configuration.py ./authentication/configuration.py
COPY authentication/models.py ./authentication/models.py
COPY authentication/requirements.txt ./authentication/requirements.txt

RUN pip install -r ./authentication/requirements.txt

ENV PYTHONPATH="/opt/src"

ENTRYPOINT ["python","./authentication/migrate.py"]