#!/bin/bash
docker rmi lj/frame-util
docker build -t lj/frame-util .
docker rm frame-util
docker run -d --name frame-util -p 8087:8087 lj/frame-util