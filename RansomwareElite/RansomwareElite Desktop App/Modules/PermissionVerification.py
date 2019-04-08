###########################################################
#             PERMISSION VERIFICATION MODULE              #
###########################################################

import os

def checkPermissions(path):

  manifest = "AndroidManifest.xml"  #manifest

  s1='BIND_DEVICE_ADMIN'
  s2='SYSTEM_ALERT_WINDOW'

  s3='RECEIVE_BOOT_COMPLETED'
  s4='READ_PHONE_STATE'
  s5='WRITE_EXTERNAL_STORAGE'
  s6='READ_EXTERNAL_STORAGE'
  s7='WAKE_LOCK'
  s8='GET_ACCOUNT'
  s9='DISABLE_KEYGUARD'
  s10='INSTALL_SHORTCUT'
  s11='INTERNET'
  s12='ACCESS_NETWORK_STATE'
  s13='ACCESS_WIFI_STATE'
  s14='ACCESS_FINE_LOCATION'
  s15='ACCESS_COARSE_LOCATION'
  s16='READ_SMS'
  s17='READ_HISTORY_BOOKMARKS'

  os.chdir(path)

  f = open(manifest, "r")
  content = f.read()

  if s1 in content:
      c1=1
        
  elif s2 in content :
      c1=1

  else:
      c1=0

  c2=0
  
  if s3 in content:
      c2=c2+1
  if s4 in content:
      c2=c2+1
  if s5 in content:
      c2=c2+1
  if s6 in content:
      c2=c2+1
  if s7 in content:
      c2=c2+1
  if s8 in content:
      c2=c2+1
  if s9 in content:
      c2=c2+1
  if s10 in content:
      c2=c2+1
  if s11 in content:
      c2=c2+1
  if s12 in content:
      c2=c2+1
  if s13 in content:
      c2=c2+1
  if s14 in content:
      c2=c2+1
  if s15 in content:
      c2=c2+1
  if s16 in content:
      c2=c2+1
  if s17 in content:
      c2=c2+1
        
  if c2>=13 or c1==1:
      count=1
  else:
      count=0

  return count

  



        
        
    




