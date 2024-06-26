#!/bin/bash
#########################################################################################
#    HIRS Reference Integrity Manifest System Tests
#
#########################################################################################
testResult=false
totalTests=0;
failedTests=0;

# Start ACA Reference Integrity Manifest Tests
# provisionTpm2 takes 1 parameter (the expected result): "pass" or "fail"
# Note that the aca_policy_tests have already run several RIM system tests

writeToLogs "### ACA RIM TEST 1: Test a RIM from an OEM and a Supplemental RIM from a VAR ###"
clearAcaDb
uploadTrustedCerts
setPolicyEkPcFw
setPlatformCerts "laptop" "varOsInstall"
setRims "laptop" "varOsInstall" "clear"
provisionTpm2 "pass"

writeToLogs "### ACA RIM TEST 2: Test a RIM from an OEM with a bad reference measurement and a Supplemental RIM from a VAR ###"
clearAcaDb
uploadTrustedCerts
setPolicyEkPcFw
setPlatformCerts "laptop" "badOemInstall"
setRims "laptop" "badOemInstall" "clear"
provisionTpm2 "fail"

writeToLogs "### ACA RIM TEST 3: Test a RIM from an OEM and a Supplemental RIM from a VAR with a bad reference measurement ###"
clearAcaDb
uploadTrustedCerts
setPolicyEkPcFw
setPlatformCerts "laptop" "badVarInstall"
setRims "laptop" "badVarInstall" "clear"
provisionTpm2 "fail"

#  Process Test Results, any single failure will send back a failed result.
if [[ $failedTests != 0 ]]; then
    export TEST_STATUS=1;
    echo "****  $failedTests out of $totalTests ACA RIM Tests Failed! ****"
  else
    echo "****  $totalTests ACA RIM Tests Passed! ****"
fi