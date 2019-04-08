###########################################################
#             LOCK DETECTOR MODULE                        #
###########################################################

from subprocess import call
import os
import re
from zipfile import ZipFile
import shutil

def checkLock(app):

  path = "F:\\Apktool\\"
  os.chdir( path )

  filename = app
  base = os.path.splitext(filename)[0]
  os.rename(filename, base + ".zip")

  file_name = base + ".zip"
  with ZipFile(file_name, 'r') as zip:
      zip.extractall()
      
  base = os.path.splitext(file_name)[0]
  os.rename(file_name, base + ".apk")

  files = os.listdir( path )
  destination = "F:\\Apktool\\dex2jar-0.0.9.15\\"
  for i in files:
      if i.endswith(".dex"):
          shutil.copy(i,destination)
 
  path = "F:\\Apktool\\dex2jar-0.0.9.15\\"
  os.chdir( path )

  code = "dex2jar classes.dex"
  call(code,shell=True)

  code = "jar xf classes_dex2jar.jar"
  call(code,shell=True)
  name = os.path.splitext(app)[0]
#  path = "F:\\Apktool\\dex2jar-0.0.9.15\\com\\example\\gauta\\%s\\" % name
#  os.chdir( path )
  path = "F:\\Apktool\dex2jar-0.0.9.15\\com\\"
  os.chdir( path )
  all_files = os.listdir( path )
  destination = "F:\\Apktool\\Java Files\\"
  for i in all_files:
    path = "F:\\Apktool\\dex2jar-0.0.9.15\\com\\%s\\" %i
    os.chdir( path )
    all_files2 = os.listdir( path )
    for j in all_files2:
        path = "F:\\Apktool\\dex2jar-0.0.9.15\\com\\%s\\%s\\" %(i,j)
        os.chdir( path )
        all_files3 = os.listdir( path )
        
        for k in all_files3:
          if(os.path.isfile(k)):
            ext = os.path.splitext(k)[-1].lower()
            if(ext == ".class"):
              shutil.copy(k,destination)
              #os.remove(k)
              continue
          if(os.path.isdir(k)):
            path = "F:\\Apktool\\dex2jar-0.0.9.15\\com\\%s\\%s\\%s\\" %(i,j,k)
            os.chdir( path )
            all_files4 = os.listdir( path )
            for t in all_files4:
              if(os.path.isfile(t)):
                ext = os.path.splitext(t)[-1].lower()
                if(ext == ".class"):
                  shutil.copy(t,destination)
                  #os.remove(t)
                  continue
            
#  files = os.listdir( path )
#  for i in files:
#      shutil.copy(i,destination)

  path = "F:\\Apktool\\Java Files\\"
  os.chdir( path )

  files = os.listdir( path )
  for i in files:
      if i.endswith(".class"):
          code = "jad " + i
          call(code,shell=True)
          code = "jad -sjava " + i
          call(code,shell=True)

  s1='extends DeviceAdminReciever'
  s2='new DevicePolicyManager'
  s3='wipeNow'
  s4='restPassword'
  s5='lockNow'
  #s5="package"

  c2=0

  for filename in os.listdir(path):

    if filename.endswith(".java"):

       f = open(filename, "r")
       content = f.read()
    
       if s1 in content:
           c1=1
        
       elif s2 in content :
           c1=1

       elif s3 in content :
           c1=1

       elif s4 in content :
           c1=1

       elif s5 in content :
           c1=1

       else:
           c1=0

       c2=c2+c1

#  f.close()
  return c2
        
        
        
    




