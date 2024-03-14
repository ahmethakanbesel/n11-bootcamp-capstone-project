FROM alpine:3.19

RUN apk add curl bash && apk cache clean