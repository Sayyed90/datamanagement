import { Injectable, Input } from '@angular/core';
import { HttpClient,HttpParams } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { Data, Responses } from './data';
@Injectable({
  providedIn: 'root'
})
export class DataService {
  
  
  
  
  private baseURL="http://localhost:8080/data";
  private token: string='';
  private content: Data[];
  private singleData: Data;
  constructor(private httpClient: HttpClient) { 
    
  }

  updateData(dataSourceDto: any): Observable<Boolean> {
    const headers ={'Authorization':"Bearer "+this.getToken()}
  
    return this.httpClient.put<Boolean>(this.baseURL+"/update/"+dataSourceDto.id,dataSourceDto,{headers:headers});
  
  }

  deleteData(id: number) : Observable<Boolean>{
    const headers ={'Authorization':"Bearer "+this.getToken()}
    console.log("${id} "+id);
    return this.httpClient.delete<Boolean>(this.baseURL+"/delete/"+id,{headers:headers});
  
  }

  getDatas(tokens: string, currentPage: number, itemsPerPage: number): Observable<Responses>{
    let params=new HttpParams();
    if(currentPage===undefined){
      currentPage=1;
    }if(itemsPerPage===undefined){
      itemsPerPage=10;
    }
    params=params.append("pageNo",currentPage);
    params=params.append("pageSize",itemsPerPage);
    const headers ={'Authorization':"Bearer "+tokens}
    return this.httpClient.get<Responses>(this.baseURL+"/all",{params:params,headers:headers});
  }

  searchByValue(value: Partial<{ key: string | null; type: string | null; }>, token: string, currentPage: number, itemsPerPage: number) : Observable<Responses>{
    let params=new HttpParams();
    if(currentPage===undefined){
      currentPage=1;
    }if(itemsPerPage===undefined){
      itemsPerPage=10;
    }
    params=params.append("pageNo",currentPage);
    params=params.append("pageSize",itemsPerPage);
    const headers ={'Authorization':"Bearer "+token}
    if(value.type ==="custId"){
      return this.httpClient.get<Responses>(this.baseURL+"/retrieve/csId/"+value.key,{params:params,headers:headers});
    }else if(value.type ==="acctNum"){
      return this.httpClient.get<Responses>(this.baseURL+"/retrieve/accNum/"+value.key,{params:params,headers:headers});
    }
    return this.httpClient.get<Responses>(this.baseURL+"/retrieve/desc/"+value.key,{params:params,headers:headers});
  }

  setToken(token:string){
    this.token= token;
  }

  getToken(): string{
    return this.token;
  }

  setContent(content:Data[]){
    this.content=content;
  }

  getContent(){
    return this.content;
  }
  setSingleData(singleData:Data){
    this.singleData=singleData;
  }

  getSingleDatat(){
    return this.singleData;
  }
}
