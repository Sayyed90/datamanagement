import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { inject } from '@angular/core';
import { tap } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  httpClient = inject(HttpClient);
baseUrl = 'http://localhost:8080/home';

isLoggedIn() {
  return localStorage.getItem('authUser') !== null;
}

  login(data: any) {
    let params=new HttpParams();
    params=params.append("email",data.email);
    params=params.append("password",data.password);
    return this.httpClient.get(`${this.baseUrl}/login`, {params:params})
      .pipe(tap((result) => {
        localStorage.setItem('authUser', JSON.stringify(result));
      }));
  }

  logout() {
    localStorage.removeItem('authUser');
  }

  signup(data: any) {
    return this.httpClient.post(`${this.baseUrl}/register`, data);
  }


  constructor() { }
}
