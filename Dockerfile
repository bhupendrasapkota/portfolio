FROM ubuntu:latest
LABEL authors="bhupe"

ENTRYPOINT ["top", "-b"]