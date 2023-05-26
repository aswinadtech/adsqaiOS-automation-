#!/usr/bin/env bash

main()
{
  /usr/local/bin/ideviceinfo | awk -F'ProductVersion:'  '/ProductVersion:/ {print $2}'
}
main