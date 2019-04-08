from textblob.classifiers import NaiveBayesClassifier
from textblob import TextBlob
from subprocess import call
import os
import re
from nltk.tokenize import word_tokenize
from PIL import Image
import pytesseract
import warnings



def checkImage(app):

  warnings.filterwarnings("ignore")

  train = [
  ('Find a retail location near you.','moneypak'),		
  ('Look for a MoneyPak in the prepaid section.','moneypak'),				
  ('Take it to the cashier and load it with cash.',' moneypak'),				
  ('To pay fine you should enter the digits MoneyPak resulting pass in the payment form and press Pay MoneyPak.','moneypak'),
  ('Amount of fine is 500 dollars.','moneypak'),				
  ('After submitting the MoneyPak voucher your fine will be redeemed in  hours.','moneypak'),				
  ('You can settle the fine with MoneyPak vouchers.','moneypak'),				
  ('As soon as the money arrives to the Treasury account your Mobile Device will be unlocked and all information will be decrypted in course of  hours.','moneypak'),
  ('Fine should be paid not later than in 12 hours since current time.','moneypak'),				
  ('To unlock your device and to avoid other legal consequences you are obligated to pay a release fee of 300 dollars','moneypak'),		
  ('Payable through GreenDot MoneyPak.','moneypak'),				
  ('load it with 300 dollars and enter the code.','moneypak'),				
  ('The penalty as a base measure of punishment on you which you are obliged to pay in a current of is imposed.','moneypak'),
  ('Click to win lottery worth ten millions.','moneypak'),
					
  ('This device lock is aimed to stop your illegal activity.','threat'),		
  ('Your Device has been blocked up for safety reasons listed below.','threat'),				
  ('All the actions performed on this device are fixed.','threat'),
  ('You have been blocked to use your device. you need to pay a fee of rupees 500 to unblock.','threat'),
  ('In less than 3 days your device data will be sent to our servers and you will be blocked to use your data. To abort this you should pay a ransom of 500 rupees.','threat'),
  ('Please mind that both your personal identities and location are well identified and criminal case can be opened against you in course of  hours as of commission of crimes per above Articles.','threat'),		
  ('The penalty set must be paid in course of  hours as of the breach.','threat'),	 				
  ( 'On expiration of the term hours that follow will be used for automatic collection of data on yourself and your misconduct and criminal case will be opened against you.','threat'),		
  ('This device is locked due to the violation of the federal laws of the United States of America','threat'),				
  ('The messages with terroristic motives attempts in respect of political persons and also in respect of top public officials were sent from your device','threat'),		
  ('According to these data you will be permitted to pay fine in State Treasury in the consequence of initiative directed on protection of cyber space in U.S.A and in doing so to seize your clerical correspondence and taking your criminal case to court for decision formulation.','threat'),			
  ('In case of penalty non-redemption your case will be taken to court for the further decision formulation and determination of your criminal case.','threat'),			
  ('Seize clerical correspondence taking criminal case court decision formulation','threat'),				
  ('Penalty non redemption case taken court decision formulation determination criminal','threat'),				
  ('For this reason your device has been locked.','threat'),				
  ("Information on your location and snaphots containing your face have been uploaded on the fbi cyber crime department's datacenter.",'threat'),				
  ( 'According to these positions your actions bear criminal character and you are a criminal subject.','threat'),
  ( "If you don't adhere to the instructions provided you can be jailed under cyber crime law.",'threat'),
  ( "Send your phone details if you want to unlock your phone.",'threat'),

  ('install','non-threat'),
  ('@string','non-threat'),
  ('The government policies have been changed','non-threat'),
  ('Under supervision of FBI.U.S.A. Ministry of Interior Interpol Copyright Alliance International Cyber Security Protection Alliance.','non-threat'),
  ('You are accused of committing the crime envisaged by Article 1 of United States of America criminal law.','non-threat'),
  ('Article 1 of United States of America criminal non-threat provides for the punishment of deprivation of liberty for terms from 5 to  years.','non-threat'),				
  ('Article Section Cause','non-threat'),
  ('Please ensure you have this app in your phone.','non-threat'),
  ('The policies of government  has been changed','non-threat'),
  ('Your phone has been affected by virus. Download this software', 'non-threat'),
  ('you have been restricted by government agencies to continue','non-threat'),
  ('Article 1 Section 8 Cause 8 of the Criminal Code provides for a fine of two to five hundred minimal wages or a deprivation of liberty for two to eight years.','non-threat'),	 		
  ('Thus violating article 2 of the Criminal Code of United States of America Article 2 of the Criminal Code provides for a deprivation of liberty for four to twelve years.','non-threat'),	 			
  ('Spam distribution on e-mail addresses of private persons containing the advertisement of illegal drugs addictive substances or psychopharmaceuticals on the territory of the USA','law'),	 		
  ('Disconnection or disposal of the device or your attempts to unlock the device independently will be apprehended as unapproved actions interfering the execution of the law of the united states of america (read section 1509 - obstruction of court orders and section 1510 - obstruction of criminal investigations.','non-threat'),	 				
  ('In this case and in case of penalty non-payment in a current of three calendar days from the date of this notification. The total amount of penalty will be tripled and the respective fines will be charged to the outstanding penalty.','non-threat'),	 			
  ('In case of dissent with the indicted prosecution you have the right to challenge it in court.','non-threat'),	 			
  ('Distribution of false information about politicians.','non-threat'),				
					
  ('You have been subjected to violation of Copyright and Related Rights Law (Video Music Software) and illegally using or distributing copyrighted contents','copyright'),	 		
					
  ('Distribution of episodes containing abuse or porn material with participation of animals','porn'),	 			
  ('Distribution and keeping of porn material with participation of children','porn'),	 				
  ('You are accused of viewing and storage of forbidden child pornography and zoophilia.','porn'),	 				
  ('You have violated World Declaration on non-proliferation of child pornography.','porn'),	 				
  ('You have been viewing or distributing prohibited Pornographic content (Child Pornography photos and etc were found on your device).','porn'),	 				
  ('Video materials of porn content including sexual acts with children (persons up to 9 years old).','porn'),	 			
  ('Video materials containing the scene of child (persons up to 9 years) and animals abuse.','porn'),	 		
  ('Video materials are made with the help of your device contain the scenes of cruel child and animals abuse.','porn'),	 				
  ('As a result of full scanning of your device	 some suspicious files have been found.','porn'),	 			
  ('Your attendance of the forbidden pornographic sites has been fixed.','porn'),	 				
  ('Depiction of animal cruelty.','porn'),	 				
  ('Whoever knowingly creates sells or possesses a depiction of animal cruelty with the intention of placing that depiction in interstate or foreign commerce for commercial gain shall be fined under this title or imprisoned not more than 5 years or both.','porn'),	 
  ('Certain activities relating to material constituting or containing child pornography.','porn'),	 				

  ]

  c1 = NaiveBayesClassifier(train)

  path = "F:\\Apktool\\%s\\res\\" %app
  os.chdir( path )

  all_files = os.listdir(path)
  #print(all_files)

  list = []
  textL = []
  imageName = []
  z=0
  for i in all_files:
      path = "F:\\Apktool\\%s\\res\\%s" % (app,i)
      os.chdir( path )
      all_files_2 = os.listdir(path)

      #print(all_files_2)

      for j in all_files_2:
          ext = os.path.splitext(j)[-1].lower()
          if(ext == ".gif" or ext == ".jpg" or ext == ".png" or ext == ".jpeg"):
              #print("\n" + j)
              imageName.append(j)
              im = Image.open(j)
              text = pytesseract.image_to_string(im, lang = 'eng')
              if(text != ""):
                  print("   ")
                  z+=1
                  #print(text)
                  textL.append(text)
                  blob = TextBlob(text,classifier=c1)
                  sr = blob.classify()
                  #print(sr)
                  list.append(sr)
                  print("   ")

  print("\n")
  while(z>0):
      print"TEXT IS    ",textL[z-1]," ",list[z-1],"           IMAGE IS  ",imageName[z-1]
      #print"Image name is ",imageName[z-1]
      z-=1

  count = 0
  for i in list:
      if(i == "threat"):
          count = count+1
  print("\n")
  if(count >=1):
      print("THREATENING IMAGE PRESENT")
      c=1
  if(count == 0):
      print("Threatening Image Not Present")
      c=0
  return c
           
            
            


    
        
    



