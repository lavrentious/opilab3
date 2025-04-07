#!/bin/bash

WILDFLY_PATH="/Users/lavrent/Downloads/wildfly-preview-34.0.0.Final"
DEPLOY_PATH="$(pwd)/dist/app_ant.war"

ant clean ; ant build-war && echo "deploying $DEPLOY_PATH..." && $WILDFLY_PATH/bin/jboss-cli.sh --connect --commands="deploy --force $DEPLOY_PATH" && echo "done"
