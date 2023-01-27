#!/usr/bin/env bash

main()
{
  /System/Library/PrivateFrameworks/Apple80211.framework/Resources/airport -I  | awk -F' SSID: '  '/ SSID: / {print $2}'
}
main