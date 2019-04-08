print("###########################################################")
print("#                  RANSOMWARE ELITE                       #")
print("###########################################################")

from textblob.classifiers import NaiveBayesClassifier
from textblob import TextBlob
from nltk.stem import PorterStemmer
from nltk.tokenize import word_tokenize
from subprocess import call
import os
import re
from zipfile import ZipFile
import shutil
from firebase import firebase
import json
from PIL import Image
import pytesseract


path = "F:\\Apktool\\"
app_names = os.listdir(path)
print("THESE ARE THE APPS AVAILABLE:\n")
for i in app_names:
    ext = os.path.splitext(i)[-1].lower()
    if(ext == ".apk"):
        print"   ",i

app1 = raw_input("\nEnter the app name with .apk:\n")

print "\nExtracting apk Please Wait..."

app = os.path.splitext(app1)[0]

path = "F:\\Apktool\\"
os.chdir( path )

code = 'apktool d %s.apk' %app
call(code, shell=True)

#   print(package_name)

path = "F:\\Apktool\\%s" %app
os.chdir( path )
files = os.listdir( path )
for i in files:
    if(i=="AndroidManifest.xml"):
        file = open(i,"r")
        st = file.read()
        start = 'package="'
        end = '"'
        package_name = ((st.split(start))[1].split(end)[0])
        
print "\n\t\tPermission Verification Module Working..."
        
from PermissionVerification import checkPermissions

path1 = "F:\\Apktool\\%s\\" %app

count1=checkPermissions(path1)

if count1>0:
    print "SUSPICIOUS PERMISSIONS PRESENT"
else :
    print "Suspicious Permisisons Not Present"

print "\n\t\tLock Detector Working..."
    
from LockDetector import checkLock

co=checkLock(app1)

if co>0:
    print "LOCKING CLASSES AND FUNCTIONS PRESENT"
    count2=1
else :
    print "Locking Classes & Functions Not Present"
    count2=0

print "\n\t\tThreatening Text Detector Working..."
    
from ThreateningTextDetector import checkText

count3=checkText(app)

print "\n\t\tThreatening Image Detector Working..."

from ThreateningImageDetector import checkImage

count4=checkImage(app)

print "\n\n"

if count1==1 and count2==1 and count3==1 and count4==1:
    cond = 'All Present'
    print "THIS APP IS RANSOMWARE"
elif count1==1 and count2==1 and count3==1 and count4==0:
    cond = 'All Present Except Threatening Image'
    print "THIS APP IS SUSPICIOUS"
elif count1==1 and count2==1 and count3==0 and count4==1:
    cond = 'All Present Except Threatening Text'
    print "THIS APP IS SUSPICIOUS"
elif count1==1 and count2==1 and count3==0 and count4==0:
    cond = 'Only Suspicious Permissions And Locking Functions Present'
    print "THIS APP IS SUSPICIOUS" 
elif count1==1 and count2==0 and count3==1 and count4==1:
    cond = 'All Present Except Locking function'
    print "THIS APP IS SUSPICIOUS"
elif count1==1 and count2==0 and count3==1 and count4==0:
    cond = 'Suspicious Permissions And Threatening Text Present'
    print "THIS APP IS SUSPICIOUS"
elif count1==1 and count2==0 and count3==0 and count4==1:
    cond = 'Suspicious Permissions And Threatening Image Present'
    print "THIS APP IS SUSPICIOUS"
elif count1==1 and count2==0 and count3==0 and count4==0:
    cond = 'Only Suspicious Permissions Present'
    print "THIS APP IS SUSPICIOUS"
elif count1==0 and count2==1 and count3==1 and count4==1:
    cond = 'All Present Except Suspicious Permissions'
    print "THIS APP IS SUSPICIOUS"
elif count1==0 and count2==1 and count3==1 and count4==0:
    cond = 'Locking Functions And Threatening Text Present'
    print "THIS APP IS SUSPICIOUS"
elif count1==0 and count2==1 and count3==0 and count4==1:
    cond = 'Locking Functions And Threatening Image Present'
    print "THIS APP IS SUSPICIOUS"
elif count1==0 and count2==1 and count3==0 and count4==0:
    cond = 'Only Locking Functions Present'
    print "THIS APP IS SUSPICIOUS"
elif count1==0 and count2==0 and count3==1 and count4==1:
    cond = 'Threatening Text And Threatening Image Present'
    print "THIS APP IS SUSPICIOUS" 
elif count1==0 and count2==0 and count3==1 and count4==0:
    cond = 'Only Threatening Text Present'
    print "THIS APP IS SUSPICIOUS"
elif count1==0 and count2==0 and count3==0 and count4==1:
    cond = 'Only Threatening Image Present'
    print "THIS APP IS SUSPICIOUS"
else:
    cond = '0'
    print "THIS APP IS SAFE"

if cond!='0':
    from DatabaseStorage import sendtoServer
    sendtoServer(package_name,cond)
    print "Entry Has Been Added"

path = "F:\\Apktool\\Java Files\\"
os.chdir( path )
all_files = os.listdir( path )
for i in all_files:
    ext = os.path.splitext(i)[-1].lower()
    if(ext == ".class" or ext == ".jad" or ext == ".java"):
        os.remove(i)

path = "F:\\Apktool\\dex2jar-0.0.9.15\\"
os.chdir(path)
if(os.path.isdir("F:\\Apktool\\dex2jar-0.0.9.15\\android\\")):
    shutil.rmtree("F:\\Apktool\\dex2jar-0.0.9.15\\android\\" )

path = "F:\\Apktool\\dex2jar-0.0.9.15\\com\\"
shutil.rmtree(path)

        
