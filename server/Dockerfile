# always use LTS version
FROM node:12.16-alpine3.11

# set environment variables that will never change
ENV NODE_ENV=production
# increase Node heap memory (to avoid out of memory errors, code 127)
ENV NODE_OPTIONS=--max_old_space_size=16384

# install tini for graceful process termination
# clean cache to reduce size of image
RUN apk upgrade && apk add --no-cache tini

# create and cd into /node directory
WORKDIR /node

# create app directory and change permissions to node user (available in Node images)
RUN mkdir ./app && chown node:node -R ./

# cd into app directpry
WORKDIR /node/app

# copy package.json and  package-lock.json inside current container directory (/node/app)
COPY package*.json ./

# install dependencies
# clean cache to reduce size of image
RUN yarn install && yarn cache clean

# run the container as node user (from node group, user:group) 
# for security run container as non-root, (by default Docker runs as root)
# will only use that user for RUN, ENTRYPOINT and CMD commands
USER node:node

# copy source code from host to container with node user permissions
COPY --chown=node:node ./ ./


# for documentation only; by default all ports on Docker network are exposed
EXPOSE 1337

# set environment variables that can change 
ENV DATABASE_HOST=${DATABASE_HOST}
ENV DATABASE_PORT=${DATABASE_PORT}
ENV DATABASE_NAME=${DATABASE_NAME}
ENV DATABASE_USERNAME=${DATABASE_USERNAME}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}

# wraps Node process with tini 
# it let's Docker handle signals for graceful termination of Node process)
ENTRYPOINT ["/sbin/tini", "--"]

# always run Node process directly for graceful termination setup
CMD [ "node", "./server.js" ]