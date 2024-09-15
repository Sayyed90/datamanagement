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

  deleteData(id: number) : Observable<Boolean>{
    const headers ={'Authorization':"Bearer "+this.getToken()}
    console.log("${id} "+id);
    return this.httpClient.delete<Boolean>(this.baseURL+"/delete/"+id,{headers:headers});
  
  }

  constructor(private httpClient: HttpClient) { 
    
  }

  getDatas(tokens:string): Observable<Responses>{
    let params=new HttpParams();
    params=params.append("pageNo",0);
    params=params.append("pageSize",18);
    const headers ={'Authorization':"Bearer "+tokens}
    return this.httpClient.get<Responses>(this.baseURL+"/all",{params:params,headers:headers});
  }

  setToken(token:string){
    this.token= token;
  }

  getToken(): string{
    return this.token;
  }
 
}
