FROM node

RUN mkdir -p /opt/app

WORKDIR /opt/app

COPY package.json .

RUN npm install --quiet

COPY index.js .

EXPOSE 3000

CMD node index.js