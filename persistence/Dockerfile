FROM hypriot/rpi-java

ADD build/libs/rpi-docker-persistence-1.0.jar rpi-docker-persistence-1.0.jar

EXPOSE 8082

ENV SQLITE_URL /sqlite_data/sqlite.db

RUN mkdir /sqlite_data

VOLUME /sqlite_data

CMD /bin/bash -c 'java -jar rpi-docker-persistence-1.0.jar >> persist.log'
#CMD ["bash"]
#CMD ["java" "-jar" "rpi-docker-persistence-1.0.jar" ">> persist.log" "&"]
