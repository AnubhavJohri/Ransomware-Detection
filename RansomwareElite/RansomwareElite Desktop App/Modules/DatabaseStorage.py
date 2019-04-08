from firebase import firebase
import json

def sendtoServer(name,cond):

    import json
    from firebase import firebase
    firebase = firebase.FirebaseApplication('https://pythonprojectdemo.firebaseio.com/', None)
    #new_user = 'Ozgur Vatansever'
    #result = firebase.post('/users', {'user':2}, {'arg': 'pretty'}, {'X_FANCY_HEADER': 'VERY FANCY'})
    
    appname=name
    appcond=cond

    jsonob={"appname" : appname,"condition" : appcond}

    result = firebase.post('/Threatning-Apps/', jsonob)

    print result



