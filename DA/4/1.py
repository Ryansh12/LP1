#!/usr/bin/env python
# coding: utf-8

# In[2]:


import numpy as np
import pandas as pd
get_ipython().run_line_magic('matplotlib', 'inline')

from sklearn.preprocessing import LabelEncoder

import matplotlib.pyplot as plt
import seaborn as sns

data=pd.read_csv('store.csv')


# In[3]:


data.head()   ######info about data


# In[4]:


data.info()    ###### info about data


# In[5]:


data.dtypes   ##### info about types


# In[6]:


data['Duration'].describe()  #### statistics


# In[7]:


data=data.drop('Start date',axis=1)#drop these columns
data=data.drop('End date',axis=1)
data=data.drop('Start station',axis=1)
data=data.drop('End station',axis=1)


# In[8]:


data


# In[ ]:


# data.head()                                    ########### label encoder
le = LabelEncoder()
le.fit(data['Member type'])
data['Member type'] = le.transform(data['Member type'])


# In[8]:


le = LabelEncoder()
le.fit(data['Bike number'])
data['Bike number'] = le.transform(data['Bike number'])


# In[9]:


data.head()


# In[10]:


data.shape     ###### data.size


# In[11]:


train=np.array(data.iloc[0:85000])   ### spitting into training and testsign
test=np.array(data.iloc[85000:,]) #Dataframe.iloc[] method is used when the index label of a data frame is something other than numeric series of 0, 1, 2, 3….n or in case the user doesn't know the index label.


# In[12]:


train.shape,test.shape       ########  train and test


# In[13]:


from sklearn.naive_bayes import GaussianNB   ##### guassian
model=GaussianNB()


# In[14]:


model.fit(train[:,0:4],train[:,4])
predicted=model.predict(test[:,0:4])


# In[15]:


predicted.shape


# In[16]:


predicted


# In[17]:


count=0                 ### accuracy
for l in range(30597):  
    if(predicted[l]==test[l,4]):
        count=count+1


# In[18]:


count


# In[19]:


print(count/30597)


# In[ ]:




