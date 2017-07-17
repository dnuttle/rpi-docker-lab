FROM hypriot/rpi-java

EXPOSE 8082

ENV SQLITE_URL /sqlite_data/sqlite.db

RUN mkdir /sqlite_data

RUN mkdir /app

RUN mkdir /var/log/rpi-docker-lab

WORKDIR /app

ADD build/libs/rpi-docker-persistence-1.0.jar rpi-docker-persistence-1.0.jar

VOLUME /sqlite_data

CMD /bin/bash -c 'java -jar rpi-docker-persistence-1.0.jar >> /var/log/rpi-docker-lab/persistence.log'
#CMD ["bash"]
#CMD ["java" "-jar" "rpi-docker-persistence-1.0.jar" ">> persist.log" "&"]
