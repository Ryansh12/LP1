#include<iostream>
using namespace std;

#define THREADS_PER_BLOCK 256

__global__ void max_per_block(int *a_d,int *b_d,int n){
    int block = blockDim.x*blockIdx.x;
    int max=0;
    for(int i=block;i<min(block+blockDim.x,n);i++){
        if(max<a_d[i]){
            max=a_d[i];
        }
    }
    b_d[blockIdx.x]=max;
}

int main() {
    int n;
    cout<<"Enter the no of elements";
    cin>>n;

    int *arr = new int[n];
    for(int i=0;i<n;i++){
        arr[i]=i+1;
    }
    
    int no_of_blocks = (n+THREADS_PER_BLOCK-1)/THREADS_PER_BLOCK;
    int size = n*sizeof(int);
    int *arr_d,*b_d;
    cudaMalloc(&arr_d,size);
    cudaMalloc(&b_d,no_of_blocks*sizeof(int));
    cudaMemcpy(arr_d,arr,size,cudaMemcpyHostToDevice);

    while(n>1){
        max_per_block<<<no_of_blocks,THREADS_PER_BLOCK>>>(arr_d,b_d,n);
        n=(n+THREADS_PER_BLOCK-1)/THREADS_PER_BLOCK;
        cudaMemcpy(arr_d,b_d,no_of_blocks*sizeof(int),cudaMemcpyDeviceToDevice);
    }
    int ans;
    cudaMemcpy(&ans,arr_d,sizeof(int),cudaMemcpyDeviceToHost);
    cout<<ans;

    // clock_t cpu_start = clock();
    // clock_t cpu_stop = clock();
    // clock_t cpu_elapsed_time = 1000*(cpu_stop - cpu_start)/CLOCKS_PER_SEC;
    //int( pow(double(input[i]- *mean),2.0));
    // cudaEvent_t gpu_start,gpu_stop;

	// cudaEventCreate(&gpu_start);
	// cudaEventCreate(&gpu_stop);

	// cudaEventRecord(gpu_start,0);
    // cudaEventRecord(gpu_stop, 0);
	// cudaEventSynchronize(gpu_stop);
	// cudaEventElapsedTime(&gpu_elapsed_time, gpu_start, gpu_stop);
}