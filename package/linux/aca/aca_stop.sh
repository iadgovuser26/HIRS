#!/bin/bash
#####################################################################################
#
# Script to stop the ACA when running
#
#####################################################################################


kill -15 $(pgrep -f HIRS_AttestationCAPortal)