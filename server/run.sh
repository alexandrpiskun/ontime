#!/bin/bash
mvn appengine:devserver -Ddatastore.backing_store=/tmp/local_db.bin
