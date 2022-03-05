FROM node
COPY onlineVideo /root/onlineVideo
COPY runvue.sh /runvue.sh
RUN chmod 777 /runvue.sh && npm config set registry https://registry.npm.taobao.org &&cd /root/onlineVideo/ && npm install
ENTRYPOINT ["bash","/runvue.sh"]


