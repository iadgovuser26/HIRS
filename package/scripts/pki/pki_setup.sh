#!/bin/bash
############################################################################################
# Creates 2 Certificate Chains for the ACA:
# 1 RSA 3K SHA 384
# 2 ECC 512 SHA 384
#
############################################################################################

# Capture location of the script to allow from invocation from any location 
SCRIPT_DIR=$( dirname -- "$( readlink -f -- "$0"; )"; )
# Set HIRS PKI  password
if [ -z $HIRS_PKI_PWD ]; then
   # Create a 32 character random password
   PKI_PASS=$(head -c 64 /dev/urandom | md5sum | tr -dc 'a-zA-Z0-9')
   #PKI_PASS="xrb204k"
fi

# Create an ACA properties file using the new password
pushd $SCRIPT_DIR &> /dev/null
  if [ ! -f "/etc/hirs/aca/aca.properties" ]; then
      if [ -d /opt/hirs/scripts/aca ]; then
            ACA_SETUP_DIR="/opt/hirs/scripts/aca"
         else
            ACA_SETUP_DIR=="$SCRIPT_DIR/../aca"
      fi
      echo "ACA_SETUP_DIR is $ACA_SETUP_DIR"
   sh $ACA_SETUP_DIR/aca_property_setup.sh $PKI_PASS
  else
     echo  "aca property file exists, skipping"
  fi

popd &> /dev/null

# Create Cert Chains
if [ ! -d "/etc/hirs/certificates" ]; then
  
   if [ -d /opt/hirs/scripts/pki ]; then
            PKI_SETUP_DIR="/opt/hirs/scripts/pki"
         else
            PKI_SETUP_DIR=="$SCRIPT_DIR/../pki"
      fi
      echo "PKI_SETUP_DIR is $PKI_SETUP_DIR"

  mkdir -p /etc/hirs/certificates/
   
  pushd  /etc/hirs/certificates/ &> /dev/null
  cp $PKI_SETUP_DIR/ca.conf .
  sh $PKI_SETUP_DIR/pki_chain_gen.sh "HIRS" "rsa" "3072" "sha384" "$PKI_PASS"
  sh $PKI_SETUP_DIR/pki_chain_gen.sh "HIRS" "ecc" "512" "sha384" "$PKI_PASS" 
  popd &> /dev/null
else 
  echo "/etc/hirs/certificates exists, skipping"
fi