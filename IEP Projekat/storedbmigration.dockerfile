FROM python:3

RUN mkdir -p /opt/src
WORKDIR /opt/src

COPY applications/migrate.py ./applications/migrate.py
COPY applications/configuration.py ./applications/configuration.py
COPY applications/models.py ./applications/models.py
COPY applications/requirements.txt ./applications/requirements.txt

RUN pip install -r ./applications/requirements.txt

ENV PYTHONPATH="/opt/src"

ENTRYPOINT ["python","./applications/migrate.py"]