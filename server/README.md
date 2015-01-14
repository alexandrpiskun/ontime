ontime server

========
Server part.

curl --data "data=xyz&severity=123" http://done-ontime.appspot.com/api/v1/_save

curl http://done-ontime.appspot.com/api/v1/4sf8GSpr4B

curl http://done-ontime.appspot.com/api/v1/all

---------
curl -X POST -H 'Content-Type: application/json' -H 'Accept: application/json' -d '{"id":"4s24rNjjPr","data":"hello world","severity":2,"items":[{"id":"1q","data":"testw","severity":1,"items":[]},{"id":"1w","data":"tests","severity":2,"items":[]},{"id":"1e","data":"testx","severity":3,"items":[]},{"id":"1r","data":"testr","severity":4,"items":[]}]}'  http://127.0.0.1:8080/api/v1/_save -v

curl -X GET http://127.0.0.1:8080/api/v1/4s24rNjjPr -v