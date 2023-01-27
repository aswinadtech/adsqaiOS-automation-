#!/usr/bin/env bash

main()
{
  /usr/local/bin/ideviceinfo | awk -F'UniqueDeviceID:'  '/UniqueDeviceID:/ {print $2}'
}
main