#!/bin/bash

FEATURE=$1
BASE_DIR=nabe-bank/src/main/java/com/myproject/nabe_bank/modules

CAP="$(tr '[:lower:]' '[:upper:]' <<< ${FEATURE:0:1})${FEATURE:1}"

mkdir -p $BASE_DIR/$FEATURE/{service/impl,dto,mapper}

touch $BASE_DIR/$FEATURE/${CAP}Controller.java
touch $BASE_DIR/$FEATURE/service/${CAP}Service.java
touch $BASE_DIR/$FEATURE/service/impl/${CAP}ServiceImpl.java
touch $BASE_DIR/$FEATURE/${CAP}Repository.java
touch $BASE_DIR/$FEATURE/dto/${CAP}DTO.java
touch $BASE_DIR/$FEATURE/mapper/${CAP}Mapper.java
touch $BASE_DIR/$FEATURE/${CAP}.java

echo "Feature $FEATURE generated!"