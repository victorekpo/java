#!/bin/bash

# Set the AWS region
export AWS_DEFAULT_REGION=us-east-1
export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test

awslocal sqs create-queue --queue-name my-queue
awslocal sqs create-queue --queue-name my-queue-dlq
redrivePolicy="'{\"maxReceiveCount\":5,\"deadLetterTargetArn\":\"arn:aws:sqs:us-east-1:000000000000:my-queue-dlq\"}'"
awslocal sqs set-queue-attributes \
  --queue-url http://localhost:4566/000000000000/my-queue \
  --attributes "RedrivePolicy=${redrivePolicy}"

aws --endpoint-url=http://localhost:4566 sqs get-queue-attributes --queue-url http://localhost:4566/000000000000/my-queue --attribute-names All
# aws --endpoint-url=http://localhost:4566 sqs get-queue-attributes --queue-url http://localhost:4566/000000000000/my-queue --attribute-names RedrivePolicy
