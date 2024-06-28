# https://docs.docker.com/reference/dockerfile/

FROM node:alpine

WORKDIR /usr/src/app

COPY . /usr/src/app

EXPOSE 4200 4201 4202/tcp

RUN npm install -g @angular/cli

RUN npm install

CMD ["ng", "serve", "--configuration", "${environment:-development}", "--host", "0.0.0.0"]