#!/bin/bash
#####################################################################################
#
# Script to remove ACA setup files and database items.
# 
#
#####################################################################################

SCRIPT_DIR=$( dirname -- "$( readlink -f -- "$0"; )"; )
LOG_FILE=/dev/null

# Check for Admin privileges
if [ "$EUID" -ne 0 ]; then
      echo "This script requires root.  ACA setup not removed. Please run as root."
      exit 1
fi

source $SCRIPT_DIR/../db/mysql_util.sh

# Make sure mysql root password is available and set $DB_ADIM_PWD before continuing...
check_mariadb_install

check_mysql_root

# remove the hrs-db and hirs_db user
pushd $SCRIPT_DIR/../db/  &>/dev/null
./db_drop.sh $DB_ADMIN_PWD
popd  &>/dev/null

# remove pki files and config files if not installed by rpm
echo "Removing certificates and config files..."

# Remove /opt/hirs only if not configured by a package basedd install:
if [ ! -f /etc/hirs/aca/VERSION ]; then
  if [ -d "/etc/hirs" ]; then
     rm -rf /etc/hirs >/dev/null 2>&1
  fi
  if [ -d "/opt/hirs" ]; then
     rm -rf /opt/hirs >/dev/null 2>&1
  fi
fi

# Remove crontab and current ACA process
echo "Removing the ACA crontab"
sed -i '/aca_bootRun.sh/d' /etc/crontab
echo "Shutting down the aca..."
ps axf | grep HIRS_AttestationCAPortal.war | grep -v grep | awk '{print "kill " $1}' | sh  >/dev/null 2>&1
echo "ACA setup removal complete."
