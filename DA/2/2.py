#!/usr/bin/env python
# coding: utf-8

# In[1]:


import numpy as np
import pandas as pd
get_ipython().run_line_magic('matplotlib', 'inline')

import matplotlib.pyplot as plt
import seaborn as sns


# In[3]:


data=pd.read_csv('Pima.csv')


# In[4]:


data.head(5) #########printing data


# In[5]:


data.shape ##########how many features and rows


# In[6]:


data.info()  ### info about dataset  2.2 Sumarraizing dataset for prediction


# In[7]:


data['Pregnancies'].describe()  ###statistics similary of others


# In[8]:


data.dtypes  ## datatypes


# In[9]:


train=np.array(data.iloc[0:600])  ##2.1 Loading data into training and testing
test=np.array(data.iloc[600:768])


# In[10]:


train.shape  ### traiinging data size


# In[11]:


test.shape ### testing datasize


# In[12]:


from sklearn.naive_bayes import GaussianNB   ####importing guassian model


# In[13]:


model = GaussianNB() 


# In[14]:


model.fit(train[:,0:8], train[:,8])  ##2.3 training the data for prediction


# In[15]:


predicted= model.predict(test[:,0:8])
print(test[:,8])
print(predicted)  ## predicted data


# In[16]:


count=0            ###### calulating acuracy
for l in range(168):
    if(predicted[l]==test[l,8]):
        count=count+1



# In[17]:


print(count)  ###### print no of correctly matched samples out of 168 


# In[18]:


############ Accuracy is


print(count/168)


# In[ ]:




