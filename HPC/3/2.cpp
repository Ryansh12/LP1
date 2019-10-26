#include <omp.h>   
#include <stdio.h> 


void printArray(int* array, int len)
{
  printf("\n");
  for(int i=0; i<len; i++)
  {
    printf(" %d", array[i]);
  }
}


void merge(int* array, int start, int mid, int end)
{
  int len = (end-start)+1;
  int temp[len];
  int cur=0;
  
  int i = start;
  int j = mid+1;
  while(i<=mid && j<=end)
  {
    if(array[i]>array[j])
    {
      temp[cur] = array[i];
      cur++;
      i++;
    }
    else
    {
      temp[cur] = array[j];
      cur++;
      j++;
    }
  }

  if(i<=mid)
  {
    while(i<=mid)
    {
      temp[cur] = array[i];
      i++;
      cur++;
    }
  }

  else if(j<=end)
  {
    while(j<=end)
    {
      temp[cur] = array[j];
      j++;
      cur++;
    }
  }

  cur=0;
  for(i=start; i<=end; i++)
  {
    array[i] = temp[cur];
    cur++;
  }
}

void merge_sort(int* array, int start, int end)
{
    //printf("id is %d\\n", omp_get_thread_num());
        
    if(start<end)
    {
      int mid = (start+end)/2;

      #pragma omp parallel sections
      {
        #pragma omp section
        merge_sort(array, start, mid);

        #pragma omp section
        merge_sort(array, mid+1, end);
      }

       merge(array, start, mid, end); 


    }
}


int main() 
{ 
  omp_set_num_threads(2);
  
  struct timeval stop, start;
  
  int len = 11;
  int array[]= {7,33,5,5,23,111,75,34,77,121,120};
  
  //use to generate random numbers
  /*
  int array[len];
  for(int i=0; i<len; i++)
  {
    array[i] = rand();
  }
  */
  printArray(array, len);

  gettimeofday(&start, NULL);   
  
  double start_time = omp_get_wtime();
  merge_sort(array, 0, len-1);

  
  double time = omp_get_wtime() - start_time;
  cout<<time<<endl;
  
  printArray(array, len);

}
