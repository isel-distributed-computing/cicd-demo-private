#!/bin/bash
docker login http://harbor-registry.dyn.fil.e.ipl.pt:80 -u solvit

docker tag authuser:latest harbor-registry.dyn.fil.e.ipl.pt:80/solvit-cicd/authuser:latest
docker image push harbor-registry.dyn.fil.e.ipl.pt:80/solvit-cicd/authuser:latest

docker tag notifications:latest harbor-registry.dyn.fil.e.ipl.pt:80/solvit-cicd/notifications:latest
docker image push harbor-registry.dyn.fil.e.ipl.pt:80/solvit-cicd/notifications:latest

docker tag todolist:latest harbor-registry.dyn.fil.e.ipl.pt:80/solvit-cicd/todolist:latest
docker image push harbor-registry.dyn.fil.e.ipl.pt:80/solvit-cicd/todolist:latest