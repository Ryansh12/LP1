// OpenMP header 
#include<iostream>
#include <omp.h>   
#include <stdio.h> 
#include <stdlib.h> 
  
using namespace std;


//p stands for parallel
void p_bubble_sort(int array[], int count)
{
  omp_set_num_threads(count/2);
  static int swap_flag=0;
  do
  {
    swap_flag=0;
    for(int i=0; i<(count-2); i++)
    {
  
      if(i%2==0)  //if i is even
      {
        #pragma omp parallel for
        for(int j=0; j<( (count/2) ); j++)
        {
          int temp;
          if( array[2*j] > array[2*j+1])
          {
            //swap
            temp = array[2*j];
            array[2*j] = array[2*j+1];
            array[2*j+1] = temp;
            swap_flag=1;
          }
        }
      }

      else  //it is odd
      {
        #pragma omp parallel for
        for(int j=0; j<( ((count-1)/2)); j++)
        {
          int temp;
          if( array[2*j+1] > array[2*j+2])
          {
            //swap
            temp = array[2*j+1];
            array[2*j+1] = array[2*j+2];
            array[2*j+2] = temp;
            swap_flag=1;
          }
        }
      }   

    }
  }while(swap_flag!=0);
}


int main(int argc, char* argv[]) 
{ 
  int size = 10;
  int array[] = {3, 1, 6, 2, 1, 8, 5, 44, 2, 9};
  
  printf("Unsorted Array: \n");
  for(int i=0; i<size; i++)
    printf("%d, ", array[i]);
  printf("\n"); 

  double start_time = omp_get_wtime();
  p_bubble_sort(array, size);
  double time = omp_get_wtime() - start_time;
  cout<<time<<endl;
  printf("Sorted Array: \n");
  for(int i=0; i<size; i++)
    printf("%d, ", array[i]);
  printf("\n"); 

}

//g++ -fopenmp bubble.cpp 
//a.out
